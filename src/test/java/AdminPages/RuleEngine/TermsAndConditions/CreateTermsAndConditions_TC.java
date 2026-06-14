package AdminPages.RuleEngine.TermsAndConditions;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.Airline.Airline_Page;
import AdminPages.Master.Flight.Airline.CreateAirline_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateTermsAndConditions_TC {
    private TermsAndConditions_Page TermAndCon;
    private TermsAndConditions_Page createTerm;
    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;



    @BeforeMethod
    public void sign() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("CreateTermsAndConditions.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).AdminLogin();
        TermAndCon = new TermsAndConditions_Page(driver);
    }


    @Test(priority = 1)
    public void VerifyErrorMessages() throws InterruptedException {

        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnAdd();
        TermAndCon.ClickOnSendForApprovalAdd();
        String Expected =testData.getTestData("Required");
        String Expected2 =testData.getTestData("Required");
        String Expected3 =testData.getTestData("ValidationMessage");
        String Expected4 =testData.getTestData("Required");
        Assert.assertEquals(TermAndCon.ActualResult(),Expected);
        Assert.assertEquals(TermAndCon.ActualResult2(),Expected2);
        Assert.assertEquals(TermAndCon.ActualResult3(),Expected3);
        Assert.assertEquals(TermAndCon.ActualResult4(),Expected4);
        Thread.sleep(2000);

    }
    @Test(priority = 2)
    public void CreateTermsAndConditions() throws InterruptedException {
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnAdd();
        String SelectSupplier = testData.getTestData("SelectSupplier");
        TermAndCon.ChooseSupplier(SelectSupplier);
        TermAndCon.ChooseAllCountry();
        String Description = testData.getTestData("Description");
        TermAndCon.FillDescription(Description);
        TermAndCon.ClickOnSendForApprovalAdd();
        TermAndCon.ChooseAllBranch();
        TermAndCon.ClickOnSendForApprovalAdd();
        TermAndCon.ClickOnETicket();
        TermAndCon.ClickOnConfirmationPage();

        TermAndCon.ClickOnSendForApprovalAdd();
        Thread.sleep(2000);
//        String Expected = "http://192.168.1.216/rule-engine/terms";
//        String Actual = driver.browser().getCurrentURL();
//        Assert.assertEquals(Actual,Expected);
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
