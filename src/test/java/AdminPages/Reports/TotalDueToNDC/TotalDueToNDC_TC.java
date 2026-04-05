package AdminPages.Reports.TotalDueToNDC;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import AdminPages.Reports.Statement.State;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

public class TotalDueToNDC_TC extends TestBase_TC {
    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;
    TotalDueToNDC_Page Due;

    @BeforeTest
    public void login() {
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        Due = new TotalDueToNDC_Page(driver);
        new Reports_Common(driver).clickReports().clickTotalDueToNDC();
        testData = new SHAFT.TestData.JSON("TotalDue.json");
    }

    @Test
    public void validSearchForTotalDue() throws InterruptedException {
                Due
                .selectBranch()
                .selectAgency()
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .Submit()
                .verifyThatResultsIsDisplayed();
    }

    @Test
    public void validSearchForTotalDueWithMandatoryFieldsOnly10() throws InterruptedException {
                Due
                .selectBranch()
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .Submit()
                .verifyThatResultsIsDisplayed();
    }

    @Test
    public void searchForTotalDueWithNoOutputAndSelectSameDayFromDate() throws InterruptedException {
                Due
                .selectBranch()
                .searchValidFromDate(testData.getTestData("validDataNoOutput.From_Date"), testData.getTestData("validDataNoOutput.FromYear"), testData.getTestData("validDataNoOutput.FromMonth"))
                .searchValidToDate(testData.getTestData("validDataNoOutput.To_Date"), testData.getTestData("validDataNoOutput.ToYear"), testData.getTestData("validDataNoOutput.ToMonth"))
                .Submit()
                .verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput();
    }

    @Test
    public void validateThatPaginationWorksCorrectly() throws InterruptedException {
                Due
                .selectBranch()
                .selectAgency()
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .Submit()
                .clickOnNextButton()
                .verifyThatThePaginationIsWorkingCorrectly();
    }

    @Test
    public void validateThatUserCanSearchWithAllField() throws InterruptedException {
                Due
                .selectBranch()
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .sendInvoiceNumber()
                .sendCustomerName()
                .sendBookingRefernce()
                .Submit()
                .verifyThatResultsIsDisplayed();
    }
    @Test
    public void validateThatUserCanExportTheFile() throws InterruptedException {
                Due
                .selectBranch()
                .selectAgency()
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .Submit()
                .VerifyThatTheExportButtonIsClickable();
    }

}
