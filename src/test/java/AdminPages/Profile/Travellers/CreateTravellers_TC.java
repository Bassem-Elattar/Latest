package AdminPages.Profile.Travellers;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Profile.Profile_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.FileUploadUtil;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateTravellers_TC {
    private CreateTravellers_Page createTravellers;
    private SearchTravellers_Page searchTravellers;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }
    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

    }
    @Test(dataProvider = "JsonProvider")
    public void CreateTraveller(Map<String, String> create) throws Exception {
        createTravellers = new CreateTravellers_Page(driver);
        searchTravellers = new SearchTravellers_Page(driver);
        new Profile_Common(driver).clickProfile().clickTraveller();
        String BranchName = create.get("BranchName");
        String Title = create.get("Title");
        String Firstname = create.get("Firstname");
        String LastName = create.get("LastName");
        String year = create.get("year");
        String month = create.get("month");
        String From = create.get("From");
        String Email = create.get("Email");
        String Nationality = create.get("Nationality");
        String Gender = create.get("Gender");
        String Phonenumber = create.get("Phonenumber");
        String Address = create.get("Address");
        String PassportNo = create.get("PassportNo");
        String ExpDate = create.get("ExpDate");
        String CountryofIssue = create.get("CountryofIssue");
        createTravellers.setPersonalDetail(BranchName,Title,year,month ,From,Email,Nationality,Gender,Phonenumber,Address);
        createTravellers.setPassportdetails(PassportNo,ExpDate,CountryofIssue);
        // Specify the file input locator and file path
        By fileInputLocator = By.xpath("//input[@type='file']");
        String filePath = "src/test/resources/image_200x200.png"; // Replace with your file path
        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file
        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath);
        createTravellers.setSave();
        String Expected = "Added Successfully";
        Assert.assertEquals(createTravellers.Actual(),Expected);

    }
}
