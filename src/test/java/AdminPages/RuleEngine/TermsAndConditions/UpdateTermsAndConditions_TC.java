package AdminPages.RuleEngine.TermsAndConditions;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.Airline.Airline_Page;
import AdminPages.Master.Flight.Airline.UpdateAirline_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class UpdateTermsAndConditions_TC extends TestBase_TC {
    private TermsAndConditions_Page TermAndCon;
    private LogIn_Page logIn;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }
    @BeforeTest
    public void sign() throws InterruptedException {
        logIn = new LogIn_Page(driver);
        logIn.ClickSuperAdmin();
        logIn.ClickOnLoginButton();
        Thread.sleep(2000);

    }
    @Test(priority = 1, dataProvider = "JsonProvider")
    public void UpdateTermsAndConditions(Map<String, String> Update) throws InterruptedException {
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
        String Expected = "Required";
        String Expected2 = "Required";
        String Expected3 = "Required";
        String Expected4 = "Required";
        Assert.assertEquals(TermAndCon.ActualResult(), Expected2);
        Thread.sleep(2000);
        TermAndCon.ChooseAllCountryEdit();
        driver.element().click(TermAndCon.Lst_CountryEdit);
        String Remarks = Update.get("Remarks");
        TermAndCon.RemarksSendForApprovalEdit(Remarks);
        TermAndCon.ClickOnSendForApprovalEdit();
        TermAndCon.ChooseAllBranch();
        TermAndCon.ClickOnSendForApprovalEdit();
        TermAndCon.ClickOnETicket();
        TermAndCon.ClickOnSendForApprovalEdit();
        Thread.sleep(2000);
        String Expected5 = "http://192.168.1.70/rule-engine/terms";
        String Actual = driver.browser().getCurrentURL();
        Assert.assertEquals(Actual, Expected5);

    }
}