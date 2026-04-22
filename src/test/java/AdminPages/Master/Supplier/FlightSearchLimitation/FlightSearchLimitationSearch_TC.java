package AdminPages.Master.Supplier.FlightSearchLimitation;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;

import AdminPages.Master.Master_Common;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class FlightSearchLimitationSearch_TC extends TestBase_TC {

    String ExpectedResult;
    private LogIn_Page logIn;
    FlightSearchLimitation_Page FlightLimit;
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void SignIn (){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        FlightLimit = new FlightSearchLimitation_Page(driver);
        testData = new SHAFT.TestData.JSON("FlightSearchLimitationSearch.json");
    }

    @Test (priority = 1)
      public void VerifyThatCredentialNameIsClickableEditableValidCaseExist() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnSupplierAndChooseIndex(testData.getTestData("SupplierName"));
        FlightLimit.ClickAndTypeName(testData.getTestData("TypeName"));
        FlightLimit.ClickOnSearch();
        WebElement Select = driver.getDriver().findElement(FlightLimit.Lst_SelectTextFromTable);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").equals(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidFullName Passed");
        } else {
            throw new RuntimeException("Test Case SearchWithValidFullName Failed");
        }
    }

    @Test (priority = 2)
    public void VerifyThatCredentialNameIsClickableEditableInvalidCaseExist() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnSupplierAndChooseIndex(testData.getTestData("SupplierName"));
        FlightLimit.ClickAndTypeName(testData.getTestData("InvalidType"));
        FlightLimit.ClickOnSearch();
        WebElement Select = driver.getDriver().findElement(FlightLimit.Lst_SelectNoData);
        ExpectedResult = Select.getText();
        if (testData.getTestData("InvalidIndex").equals(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidFullName Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithValidFullName Failed");
        }
    }

    @Test (priority = 3)
    public void VerifyThatStatusActiveIsClickableAndReturnTrueCredentialsWithItsTrueStatus() {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit = new FlightSearchLimitation_Page(driver);
        FlightLimit.ClickOnSupplierAndChooseIndex(testData.getTestData("SupplierName"));
        FlightLimit.ClickAndTypeName(testData.getTestData("TypeName"));
        FlightLimit.ClickOnSearch();
        WebElement Select = driver.getDriver().findElement(FlightLimit.Lst_SelectTextFromTable);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").equals(ExpectedResult)) {
            System.out.println("Test Case VerifyThatStatusActiveBothThatClickableAndReturnTrueCredentialsWithitsTrueStatus Passed");

        } else {
            throw new RuntimeException("Test Case VerifyThatStatusActiveBothThatClickableAndReturnTrueCredentialsWithitsTrueStatus Failed");
        }
    }

    @Test (priority = 4)
    public void VerifyThatStatusInactiveIsClickableAndReturnTrueCredentialsWithItsTrueStatus() {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit = new FlightSearchLimitation_Page(driver);
        FlightLimit.ClickOnSupplierAndChooseIndex(testData.getTestData("SupplierName"));
        FlightLimit.ClickAndTypeName(testData.getTestData("InvalidType"));
        FlightLimit.ClickOnInactive();
        FlightLimit.ClickOnSearch();
        WebElement Select1 = driver.getDriver().findElement(FlightLimit.Lst_SelectNoData);
        ExpectedResult = Select1.getText();
        if (testData.getTestData("InvalidIndex").equals(ExpectedResult)) {
            System.out.println("Test Case VerifyThatStatusInactiveThatClickableAndReturnTrueCredentialsWithitsTrueStatus Passed");

        } else {
            throw new RuntimeException("Test Case VerifyThatStatusInactiveThatClickableAndReturnTrueCredentialsWithitsTrueStatus Failed");
        }
    }

    @Test (priority = 5)
    public void VerifyThatStatusBothIsClickableAndReturnTrueCredentialsWithItsTrueStatus() {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit = new FlightSearchLimitation_Page(driver);
        FlightLimit.ClickOnSupplierAndChooseIndex(testData.getTestData("SupplierName"));
        FlightLimit.ClickAndTypeName(testData.getTestData("TypeName"));
        FlightLimit.ClickOnBoth();
        FlightLimit.ClickOnSearch();
        WebElement Select = driver.getDriver().findElement(FlightLimit.Lst_SelectTextFromTable);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").equals(ExpectedResult)) {
            System.out.println("TC VerifyThatStatusBothThatClickableAndReturnTrueCredentialsWithitsTrueStatus Passed");

        } else {
            throw new RuntimeException("TC VerifyThatStatusBothThatClickableAndReturnTrueCredentialsWithitsTrueStatus Failed");
        }
    }

    @Test (priority = 6)
    public void VerifyThatEditButtonIsClickable ()throws InterruptedException{
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit = new FlightSearchLimitation_Page(driver);
        FlightLimit.ClickOnSupplierAndChooseIndex(testData.getTestData("SupplierName"));
        FlightLimit.ClickAndTypeName(testData.getTestData("TypeName"));
        FlightLimit.ClickOnBoth();
        FlightLimit.ClickOnSearch();
        FlightLimit.ClickOnEditButton();

    }
    @Test (priority = 7)
    public void VerifyThatPaginationButtonIsClickable (){
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickFlightSearchLimitation();
        FlightLimit.ClickOnSearch();
        FlightLimit.ClickOnPagination();
    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
