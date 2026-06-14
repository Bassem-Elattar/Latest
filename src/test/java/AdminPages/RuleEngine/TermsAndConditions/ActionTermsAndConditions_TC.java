package AdminPages.RuleEngine.TermsAndConditions;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.Airline.Airline_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.python.antlr.ast.Str;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ActionTermsAndConditions_TC  {
    private TermsAndConditions_Page TermAndCon;
    String Remarks = "Ok";
    private TermsAndConditions_Page createTerm;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;


    @BeforeTest
    public void sign() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("CreateTermsAndConditions.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).ClickSuperAdmin();

    }


    @Test(priority =1)
    public void VerifyThumbsUp() throws InterruptedException {
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnActiveAndInactive();
        TermAndCon.ClickOnSearch();
        TermAndCon.ActiveTerm(Remarks);
        TermAndCon.ClickOnSearch();


    }
    @Test(priority =2)
    public void VerifyThumbsDown() throws InterruptedException {
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnActiveAndInactive();
        TermAndCon.ClickOnSearch();
        TermAndCon.RejectTerm(Remarks);

        TermAndCon.ClickOnSearch();


    }


    @Test(priority = 3)
    public void ActiveTerm() throws InterruptedException {
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnActiveAndInactive();
        TermAndCon.ClickOnSearch();
        TermAndCon.ActiveTerm(Remarks);


    }
    @Test(priority = 4)
    public void InactiveTerm() throws InterruptedException {
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnActiveAndInactive();
        TermAndCon.ClickOnSearch();
        TermAndCon.ClickOnInactiveCircle();
        TermAndCon.ClickOnActiveAndInactive();
        TermAndCon.ClickOnSearch();

        TermAndCon.ClickOnEdit();
        TermAndCon.ClickOnSendForApprovalEdit();
        Thread.sleep(2000);
//        String Expected3 = "http://192.168.1.216/rule-engine/terms";
//        String Actual = driver.browser().getCurrentURL();
//        Assert.assertEquals(Actual, Expected3);
    }

}