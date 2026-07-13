package AdminPages.Admin.Company.TopUpRequest;
import AdminPages.Admin.AdminMenu;
import AdminPages.Admin.Company.Department_Page;
import AdminPages.Admin.Company.TopUpRequest_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import utilities.DataUtils;
import utilities.FileUploadUtil;
import utilities.JsonDataUtil;

public class AddTopUpRequest_TC{

    private Department_Page createDepartment;
    private TopUpRequest_Page TopupRequest;
    SHAFT.GUI.WebDriver driver;
    private LogIn_Page logIn;


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
        // Admin login
        new LogIn_Page(driver).AdminLogin();
        new AdminMenu(driver).openSubAdmin().Company().TopUp();

    }
    @Test(dataProvider = "JsonProvider")
    public void AddTopUpRequest(Map<String, String> top) throws InterruptedException {
        createDepartment = new Department_Page(driver);
        TopupRequest = new TopUpRequest_Page(driver);
//        createDepartment.setCompany();
//        TopupRequest.Advance_Credit();
        String BranchName = top.get("BranchName");
        String AgencyName = top.get("AgencyName");
        String TopUpAmount = top.get("TopUpAmount");
        String PaymentType = top.get("PaymentType");
        String Remarks = top.get("Remarks");
        TopupRequest.AddRequest(BranchName,AgencyName,TopUpAmount,PaymentType,Remarks);
        // Specify the file input locator and file path
        By fileInputLocator = By.xpath("//input[@type='file']");
        String filePath = "C:\\Users\\Mahmoud\\Desktop\\certificate-shaft-engine-63d44dfaaa1bfc9792080893.pdf"; // Replace with your file path
        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file
        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath);
        TopupRequest.setSendApproval();
        String Expected = "Your Top up has been processed successfully and sent for approval!!";
        Assert.assertEquals(TopupRequest.Actual(),Expected);
    }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
