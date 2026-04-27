package AdminPages.RuleEngine.TermsAndConditions;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.Airline.Airline_Page;
import AdminPages.Master.Flight.Airline.UpdateAirline_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class UpdateTermsAndConditions_TC  {
    private TermsAndConditions_Page TermAndCon;
    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;


    @BeforeTest
    public void sign() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("UpdateTermsAndConditions.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).ClickSuperAdmin();
        TermAndCon = new TermsAndConditions_Page(driver);

    }
    @Test(priority = 1)
    public void UpdateTermsAndConditions() throws InterruptedException {
        System.out.println("Verify Error Messages And Update Successfully");
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnActiveAndInactive();
        TermAndCon.ClickOnSearch();
        TermAndCon.ClickOnEdit();
        driver.element().click(TermAndCon.Lst_CountryEdit);
        TermAndCon.DeselectAll();
        TermAndCon.ClickOnSendForApprovalEdit();
        TermAndCon.ClickOnCheckedReviewPage();
        TermAndCon.ClickOnCheckedETicket();
        TermAndCon.ClickOnSendForApprovalEdit();
        String Expected = testData.getTestData("Required");
        String Expected2 = testData.getTestData("Required");
        String Expected3 = testData.getTestData("Required");
        String Expected4 = testData.getTestData("Required");
        Assert.assertEquals(TermAndCon.ActualResult(), Expected2);
        Thread.sleep(2000);
        TermAndCon.ChooseAllCountryEdit();
        driver.element().click(TermAndCon.Lst_CountryEdit);
        String Remarks = testData.getTestData("Remarks");
        TermAndCon.RemarksSendForApprovalEdit(Remarks);
        TermAndCon.ClickOnSendForApprovalEdit();
        TermAndCon.ChooseAllBranch();
        TermAndCon.ClickOnSendForApprovalEdit();
        TermAndCon.ClickOnETicket();
        TermAndCon.ClickOnSendForApprovalEdit();
        Thread.sleep(2000);
//        String Expected5 = "http://192.168.1.70/rule-engine/terms";
//        String Actual = driver.browser().getCurrentURL();
//        Assert.assertEquals(Actual, Expected5);

    }
}