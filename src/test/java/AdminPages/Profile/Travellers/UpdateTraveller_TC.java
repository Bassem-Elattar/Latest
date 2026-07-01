package AdminPages.Profile.Travellers;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Profile.Profile_Common;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.FileUploadUtil;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class UpdateTraveller_TC extends TestBase_TC {
    private SearchTravellers_Page searchTravellers;
    private UpdateTraveller_Page updateTraveller;
    private LogIn_Page logIn;


    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }

    @Test(dataProvider = "JsonProvider")
    public void UpdateTraveller(Map<String, String> create){
        updateTraveller = new UpdateTraveller_Page(driver);
        searchTravellers = new SearchTravellers_Page(driver);
        new Profile_Common(driver).clickProfile().clickTraveller();
        searchTravellers.setSearchTravellers("","2422","kereloos","","","");
        searchTravellers.setBoth();
        searchTravellers.setSearch();
        String BranchName = create.get("BranchName");
        String Title = create.get("Title");
        String Firstname = create.get("Firstname");
        String LastName = create.get("LastName");
        String Date = create.get("Date");
        String Email = create.get("Email");
        String Nationality = create.get("Nationality");
        String Gender = create.get("Gender");
        String Phonenumber = create.get("Phonenumber");
        String Address = create.get("Address");
        String PassportNo = create.get("PassportNo");
        String ExpDate = create.get("ExpDate");
        String CountryofIssue = create.get("CountryofIssue");
        updateTraveller.setPersonalDetail(BranchName,Title,Firstname,LastName,Date,Email,Nationality,Gender,Phonenumber,Address);
        // Specify the file input locator and file path
        By fileInputLocator = By.xpath("//input[@type='file']");
        String filePath = "C:\\Users\\Mahmoud\\Desktop\\Screenshot(35) - Copy.png"; // Replace with your file path
        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file
        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath);
        updateTraveller.setPassportdetails(PassportNo,ExpDate,CountryofIssue);
        updateTraveller.setSave();
        String Expected = "Updated Successfully";
        Assert.assertEquals(updateTraveller.Actual(),Expected);
    }
}
