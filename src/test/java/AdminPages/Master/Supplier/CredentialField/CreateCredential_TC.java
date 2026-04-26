package AdminPages.Master.Supplier.CredentialField;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Supplier.Supplier.SearchSupplier_Page;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateCredential_TC extends TestBase_TC {

    private LogIn_Page logIn;
    private CreateCredentialField_Page createCredentialField;
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
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
        Assert.assertEquals(createCredentialField.Actual(),Expected);
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
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
