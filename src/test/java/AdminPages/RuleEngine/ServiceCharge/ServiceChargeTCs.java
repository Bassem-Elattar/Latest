package AdminPages.RuleEngine.ServiceCharge;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Login.TestBase_TC;
import AdminPages.RuleEngine.ServiceCharge.ServiceCharge_page;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ServiceChargeTCs extends TestBase_TC {
    private SHAFT.TestData.JSON testData;

    @BeforeTest
    public void login(){
        testData = new SHAFT.TestData.JSON("ServiceCharge.json");
        new LogIn_Page(driver).ClickAdmin();
        new LogIn_Page(driver).ClickOnLoginButton();
    }
    @Test(priority = 1)
    public void create() throws InterruptedException {

        new ServiceCharge_page(driver)
                .navigateToRuleEngine()
                .navigateToServiceCharge()
                .ClickAtAddServiceCharge()
                .EnterServiceName()
                .SelectValidityDate(testData.getTestData("Set1.ValidityFrom"), testData.getTestData("Set1.ValidityTo"))
                .EnterServiceDescription(testData.getTestData("Set1.ServiceChargeDescription"))
                .SelectListCountryPOS(testData.getTestData("Set1.CountryPOS"))
                .SelectListBranch(testData.getTestData("Set1.Branch"))
                .SelectAttribute()
                .SelectOperator()
                .SelectValue()
                .EnterFareType(testData.getTestData("Set1.Value"))
                .Submit()
              //  .Search(testData.getTestData("Branch"))



        ;

    }

}
