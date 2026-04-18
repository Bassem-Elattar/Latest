package AdminPages.Reports.AgencyWallet;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import AdminPages.Reports.Statement.State;
import com.shaft.driver.SHAFT;
import org.junit.jupiter.api.AfterEach;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AGNWallTC extends TestBase_TC {
    AGnWall SearchWall;
    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;

    @BeforeClass
    public void sign() {
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        new Reports_Common(driver).clickReports().clickAgencyWallet();
        testData = new SHAFT.TestData.JSON("AgencyWalletReport.json");
        SearchWall = new AGnWall(driver);
    }

    @Test(priority = 1)
    public void SearchValidData(){
        new Reports_Common(driver).clickReports().clickAgencyWallet();
        SearchWall.SearchValidData(testData.getTestData("ValidData.branch"), testData.getTestData("ValidData.agency"), testData.getTestData("ValidData.currency"));
        assertEquals(testData.getTestData("ValidData.branch"),SearchWall.SearchInTable(0,testData.getTestData("ValidData.branch")));
    }

    @Test(priority = 2)
    public void SearchInvalidBranch(){
        new Reports_Common(driver).clickReports().clickAgencyWallet();
        SearchWall.SearchInvalidBranch(testData.getTestData("ValidData.currency"));
        String Actual=driver.element().getText(SearchWall.ErrorBranch);
        String Expected= testData.getTestData("ValidData.ExpectedMessage");
        Assert.assertEquals(Actual,Expected);
    }

    @Test(priority = 3)
    public void SearchInvalidCurrency(){
        new Reports_Common(driver).clickReports().clickAgencyWallet();
        SearchWall.SearchInvalidCurrency(testData.getTestData("ValidData.branch"), testData.getTestData("ValidData.agency"));
        String Actual=driver.element().getText(SearchWall.ErrorCurrency);
        String Expected= testData.getTestData("ValidData.ExpectedMessage");
        Assert.assertEquals(Actual,Expected);
    }

    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
