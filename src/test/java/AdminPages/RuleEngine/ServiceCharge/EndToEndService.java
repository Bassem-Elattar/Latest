package AdminPages.RuleEngine.ServiceCharge;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class EndToEndService  {
    private SHAFT.TestData.JSON testData;
    private ServiceCharge_page service1;
    private RuleEngine_Common ser1;
    SHAFT.GUI.WebDriver driver;
    @BeforeTest
    public void login(){
        testData = new SHAFT.TestData.JSON("ServiceCharge.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).superAdminLogin();

        service1 = new ServiceCharge_page(driver);
        ser1 = new RuleEngine_Common(driver);
        ser1.clickRuleEngine().clickServiceCharge();
    }
    @Test(priority = 1)
    public void E2E() throws InterruptedException {

        new ServiceCharge_page(driver)

                .ClickAtAddServiceCharge()
                .EnterServiceName()
                .searchValidFromDate(testData.getTestData("Set1.FromDate"),testData.getTestData("Set1.FromYear"),testData.getTestData("Set1.FromMonth"))
                .searchValidToDate(testData.getTestData("Set1.ToDate"),testData.getTestData("Set1.ToYear"),testData.getTestData("Set1.ToMonth"))
                .EnterServiceDescription(testData.getTestData("Set1.ServiceChargeDescription"))
                .SelectListCountryPOS(testData.getTestData("Set1.CountryPOS"))
                .SelectListBranch(testData.getTestData("Set1.Branch"))
                .SelectAttribute()
                .SelectOperator()
                .SelectValue()
                .EnterFareType(testData.getTestData("Set1.Value"))
                .Submit();
                service1.search_ServiceCharge(testData.getTestData("SearchSet1.Country"), testData.getTestData("SearchSet1.Branch"));
                service1.bothstatus();
                service1.Edit(
                        testData.getTestData("Update.ServicechargeDescription"),
                        testData.getTestData("Update.remark"));



    }
        //  .Search(testData.getTestData("Branch"))
        @AfterMethod
        public void Reload(){
            new LogIn_Page(driver).ClickOnLogOuTButton();
        }
}
