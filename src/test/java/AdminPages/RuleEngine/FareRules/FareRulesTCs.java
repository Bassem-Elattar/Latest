package AdminPages.RuleEngine.FareRules;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Login.TestBase_TC;
import AdminPages.RuleEngine.RuleEngine_Common;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FareRulesTCs extends TestBase_TC {
    private SHAFT.TestData.JSON testData;

    @BeforeTest
    public void login(){
            testData = new SHAFT.TestData.JSON("FareRule.json");
            new LogIn_Page(driver).ClickAdmin();
            new LogIn_Page(driver).ClickOnLoginButton();
    }

    @Test(priority = 1)
    public void createFareRule() throws InterruptedException {

        new RuleEngine_Common(driver).clickRuleEngine().clickFareRuleMaster();
        new FareRules_page(driver).ClickAtAddFareRule()
                .SelectSuplier()
                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
                .SelectCountry()
                .EnterFareName(testData.getTestData("validData.FareName"))
                .EnterFareDescription(testData.getTestData("validData.Description"))
                .ClickSubmit();

    }
    @Test(priority = 2)
    public void SearchFareRule() throws InterruptedException {
        new FareRules_page(driver)
                .SelectSupplierType()
                .ClickInactiveButton()
                .ClickSearch();
    }
    @Test(priority = 3)
    public void ApproveFareRule(){
        new FareRules_page(driver)
                .ApproveFareRule()
                .EnterRemarkText(testData.getTestData("validData.Remark"))
                .SubmitTheRemark();
    }

}
