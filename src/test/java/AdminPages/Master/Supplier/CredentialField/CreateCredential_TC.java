package AdminPages.Master.Supplier.CredentialField;

import AdminPages.Login.LogIn_Page;
import AdminPages.Master.Master_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class CreateCredential_TC {

    private LogIn_Page logIn;
    private CreateCredentialField_Page createCredentialField;
    SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;

    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        // Admin login
        new LogIn_Page(driver).superAdminLogin();
        testData = new SHAFT.TestData.JSON("CreateCredential.json");
        createCredentialField = new CreateCredentialField_Page(driver);
    }

    @Test(priority = 1)
    public void CreateCredential(){
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickCredentialField();
        createCredentialField.setSupplierCredintial(testData.getTestData("SupplierCredential"), testData.getTestData("Supplier"));
        String Expected = "Added Successfully";
        //Assert.assertEquals(createCredentialField.Actual(),Expected);
    }

    @Test(priority = 2)
    public void setInvalidCredentialField(){
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickCredentialField();
        createCredentialField.setSupplierCredintial("", testData.getTestData("Supplier"));
        String expected1 = "Required";
        Assert.assertEquals(createCredentialField.Actual2(),expected1);
    }

    @AfterMethod
    public void navigateBackToURL() {
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
