package AdminPages.Master.Supplier.FlightSearchLimitation;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class FlightSearchLimitationUpdate_TC extends TestBase_TC {

    String ExpectedResult;

    private LogIn_Page logIn;
    FlightSearchLimitation_Page FlightLimit;
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void SignIn (){
        logIn = new LogIn_Page(driver);
        logIn.ClickSuperAdmin();
        logIn.ClickOnLoginButton();
        FlightLimit = new FlightSearchLimitation_Page(driver);
        testData = new SHAFT.TestData.JSON("FlightSearchLimitationUpdate.json");
    }

    @Test(priority = 1)
    public void VerifyThatSupplierNameIsNotClickable() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnEditButton();
        Validations.verifyThat().element(driver.getDriver(), FlightLimit.EditSupplierName).isVisible().perform();
    }

    @Test(priority = 2)
    public void VerifyThatSupplierCredentialIsNotClickable() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnEditButton();
        Validations.verifyThat().element(driver.getDriver(), FlightLimit.EditSupplierCredentials).isVisible().perform();
    }

    @Test(priority = 3)
    public void VerifyThatUpdateIsUpdatedSuccessfully() throws InterruptedException{
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnEditButton();
        FlightLimit.ClickonLimitDropDown();
        FlightLimit.ClickOnUpdate();
        WebElement Select = driver.getDriver().findElement(FlightLimit.SuccessfullAlert);
        ExpectedResult = Select.getText();
        if(testData.getTestData("Edit").equals(ExpectedResult)) {
            System.out.println("Test Case VerifyThatUpdateIsUpdatedSuccessfully passed");
        }
        else {
            throw new RuntimeException("Test Case VerifyThatUpdateIsUpdatedSuccessfully Failed");
        }
    }

    @Test(priority = 4)
    public void VerifyThatCanselIsNotUpdated() throws InterruptedException{
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnEditButton();
        FlightLimit.ClickonLimitDropDown();
        FlightLimit.ClickOnCancel();
        WebElement Select = driver.getDriver().findElement(FlightLimit.GetLimit);
        ExpectedResult = Select.getText();
        if(testData.getTestData("IndexInSearch").equals(ExpectedResult)) {
            System.out.println("Test Case VerifyThatCanselIsNotUpdated passed");
        }
        else {
            throw new RuntimeException("Test Case VerifyThatCanselIsNotUpdated Failed");
        }
    }
    //Reflections
    @Test(priority = 5)
    public void VerifyThatSuccessfulUpdatedOnSupplier() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnEditButton();
        FlightLimit.ClickonLimitDropDown();
        FlightLimit.ClickOnUpdate();
        WebElement Select = driver.getDriver().findElement(FlightLimit.GetLimit);
        ExpectedResult = Select.getText();
        if(testData.getTestData("IndexInSearch").equals(ExpectedResult)) {
            System.out.println("Test Case VerifyThatUpdateIsUpdatedSuccessfully passed");
        } else {
            throw new RuntimeException("Test Case VerifyThatUpdateIsUpdatedSuccessfully Failed");
        }
    }

    @Test(priority = 6)
    public void VerifyThatSuccessfulUpdatedFromSupplierCredentialOnSearch() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnEditButton();
        FlightLimit.ClickonLimitDropDown();
        FlightLimit.ClickOnUpdate();
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplierCredencial();
        FlightLimit.ClickOnEditButton();
        FlightLimit.ClickOnSaveInSupplierCredential();
        FlightLimit.ClickOnSearch();
        Thread.sleep(1000);
        WebElement Select = driver.getDriver().findElement(FlightLimit.SupplierSearchLimit);
        ExpectedResult = Select.getText();
        if (testData.getTestData("IndexInSearch").equals(ExpectedResult)) {
            System.out.println("Test Case VerifyThatUpdateIsUpdatedSuccessfully passed");
        } else {
            throw new RuntimeException("Test Case VerifyThatUpdateIsUpdatedSuccessfully Failed");
        }
    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}