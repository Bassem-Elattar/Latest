package AdminPages.Reports.Statement;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.Airport.Airport_Page;
import AdminPages.Reports.Reports_Common;
import com.shaft.driver.SHAFT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class StateTC extends TestBase_TC {

    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;
    State Statement;

    @BeforeTest
    public void sign() {
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        Statement = new State(driver);
        testData = new SHAFT.TestData.JSON("StatementReport.json");
        new Reports_Common(driver).clickReports().clickStatement();
    }


    @Test(priority = 1)
    public void SearchWithValidData() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickStatement();
        Statement.searchValidBranch(testData.getTestData("ValidBranch.BranchName"));
        //Statement.searchValidAgency(testData.getTestData("ValidBranch.AgencyName")); // Uncomment to search for agencies
        Statement.searchValidFromDate(testData.getTestData("ValidBranch.InvoiceFromDate"), testData.getTestData("ValidBranch.FromYear"), testData.getTestData("ValidBranch.FromMonth"));
        Statement.searchValidToDate(testData.getTestData("ValidBranch.InvoiceToDate"), testData.getTestData("ValidBranch.ToYear"), testData.getTestData("ValidBranch.ToMonth"));
        Statement.Submit();
    }

    @Test(priority = 2)
    public void SearchInvalidBranch() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickStatement();
        Statement.searchValidFromDate(testData.getTestData("ValidBranch.InvoiceFromDate"), testData.getTestData("ValidBranch.FromYear"), testData.getTestData("ValidBranch.FromMonth"));
        Statement.searchValidToDate(testData.getTestData("ValidBranch.InvoiceToDate"), testData.getTestData("ValidBranch.ToYear"), testData.getTestData("ValidBranch.ToMonth"));
        Statement.Submit();
        String Actual=driver.element().getText(Statement.BranchError);
        String Expected = testData.getTestData("InvalidFromAfterTo.ExpectedError");
        Assert.assertEquals(Actual,Expected);
    }

    @Test(priority = 3)
    public void SearchInvalidFromDate() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickStatement();
        Statement.searchValidBranch(testData.getTestData("ValidBranch.BranchName"));
        Statement.searchValidToDate(testData.getTestData("ValidBranch.InvoiceToDate"), testData.getTestData("ValidBranch.ToYear"), testData.getTestData("ValidBranch.ToMonth"));
        Statement.Submit();
        String Actual=driver.element().getText(Statement.FromError);
        String Expected = testData.getTestData("InvalidFromAfterTo.ExpectedError");
        Assert.assertEquals(Actual,Expected);
    }

    @Test(priority = 4)
    public void SearchInvalidToDate() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickStatement();
        Statement.searchValidBranch(testData.getTestData("ValidBranch.BranchName"));
        Statement.searchValidFromDate(testData.getTestData("ValidBranch.InvoiceToDate"), testData.getTestData("ValidBranch.ToYear"), testData.getTestData("ValidBranch.ToMonth"));
        Statement.Submit();
        String Actual=driver.element().getText(Statement.ToError);
        String Expected = testData.getTestData("InvalidFromAfterTo.ExpectedError");
        Assert.assertEquals(Actual,Expected);
    }

    @Test(priority = 5)
    public void SearchInvalidFromAfterTo(){
        new Reports_Common(driver).clickReports().clickStatement();
        Statement.SearchInvalidFromAfterTo(testData.getTestData("InvalidFromAfterTo.BranchName"), testData.getTestData("InvalidFromAfterTo.InvoiceFromDate"), testData.getTestData("InvalidFromAfterTo.InvoiceToDate"));
        String Actual=driver.element().getText(Statement.ErrorFromAfterTo);
        String Expected = testData.getTestData("InvalidFromAfterTo.FromDateAfterToDateError");
        Assert.assertEquals(Actual,Expected);

    }
    @Test(priority = 6)
    public void SearchInvalid60Days() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickStatement();
        Statement.searchValidBranch(testData.getTestData("ValidBranch.BranchName"));
        Statement.searchValidFromDate(testData.getTestData("InvalidMoreThan60Days.InvoiceFromDate"), testData.getTestData("InvalidMoreThan60Days.FromYear"), testData.getTestData("InvalidMoreThan60Days.FromMonth"));
        Statement.searchValidToDate(testData.getTestData("InvalidMoreThan60Days.InvoiceToDate"), testData.getTestData("InvalidMoreThan60Days.ToYear"), testData.getTestData("InvalidMoreThan60Days.ToMonth"));
        Statement.Submit();
        String Actual=driver.element().getText(Statement.BeforeError);
        String Expected = testData.getTestData("InvalidMoreThan60Days.MoreThan60DaysError");
        Assert.assertEquals(Actual,Expected);
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
