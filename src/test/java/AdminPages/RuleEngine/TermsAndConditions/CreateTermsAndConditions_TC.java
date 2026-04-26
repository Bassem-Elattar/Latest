package AdminPages.RuleEngine.TermsAndConditions;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.Airline.Airline_Page;
import AdminPages.Master.Flight.Airline.CreateAirline_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateTermsAndConditions_TC extends TestBase_TC {
    private TermsAndConditions_Page TermAndCon;
    private TermsAndConditions_Page createTerm;
    private LogIn_Page logIn;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeMethod
    public void sign() throws InterruptedException {
        logIn = new LogIn_Page(driver);
        logIn.ClickSuperAdmin();
        logIn.ClickOnLoginButton();
        Thread.sleep(2000);

    }


    @Test(priority = 1)
    public void VerifyErrorMessages() throws InterruptedException {
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnAdd();
        TermAndCon.ClickOnSendForApprovalAdd();
        String Expected ="Required";
        String Expected2 ="Required";
        String Expected3 ="Please check atleast one event type option.";
        String Expected4 ="Required";
        Assert.assertEquals(TermAndCon.ActualResult(),Expected);
        Assert.assertEquals(TermAndCon.ActualResult2(),Expected2);
        Assert.assertEquals(TermAndCon.ActualResult3(),Expected3);
        Assert.assertEquals(TermAndCon.ActualResult4(),Expected4);
        Thread.sleep(2000);

    }
    @Test(priority = 2, dataProvider = "JsonProvider")
    public void CreateTermsAndConditions(Map<String,            String> Create) throws InterruptedException {
        TermAndCon = new TermsAndConditions_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickTermsAndConditions();
        TermAndCon.ClickOnAdd();
        String SelectSupplier = Create.get("SelectSupplier");
        TermAndCon.ChooseSupplier(SelectSupplier);
        TermAndCon.ChooseAllCountry();
        String Description = Create.get("Description");
        TermAndCon.FillDescription(Description);
        TermAndCon.ClickOnSendForApprovalAdd();
        TermAndCon.ChooseAllBranch();
        TermAndCon.ClickOnSendForApprovalAdd();
        TermAndCon.ClickOnETicket();
        TermAndCon.ClickOnConfirmationPage();

        TermAndCon.ClickOnSendForApprovalAdd();
        Thread.sleep(2000);
        String Expected = "http://192.168.1.216/rule-engine/terms";
        String Actual = driver.browser().getCurrentURL();
        Assert.assertEquals(Actual,Expected);
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
