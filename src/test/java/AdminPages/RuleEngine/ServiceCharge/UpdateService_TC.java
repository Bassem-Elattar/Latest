package AdminPages.RuleEngine.ServiceCharge;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Login.TestBase_TC;
import AdminPages.RuleEngine.RuleEngine_Common;
import AdminPages.RuleEngine.ServiceCharge.ServiceCharge_page;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UpdateService_TC extends TestBase_TC {
    private SHAFT.TestData.JSON testData;
    private ServiceCharge_page service;
    private RuleEngine_Common ser;

    @BeforeTest
    public void login(){
        testData = new SHAFT.TestData.JSON("ServiceCharge.json");
        new LogIn_Page(driver).ClickAdmin();
        new LogIn_Page(driver).ClickOnLoginButton();
         service = new ServiceCharge_page(driver);
        ser = new RuleEngine_Common(driver);
        ser.clickRuleEngine().clickServiceCharge();

    }
    @Test(priority = 1)
    public void create() throws InterruptedException {


        String Country = testData.getTestData("SearchSet1.Country");
        String Branch = testData.getTestData("SearchSet1.Branch");
        service.search_ServiceCharge( Country , Branch );
        service.bothstatus();

        service.Edit(
                        testData.getTestData("Update.ServicechargeDescription"),
                testData.getTestData("Update.remark"));

           /*     .EnterServiceName(testData.getTestData("Update.ServicechargeName"))
                .EnterServiceDescription(testData.getTestData("Set1.ServiceChargeDescription"))
                .EnterFareType(testData.getTestData("Update.value"))
                .Submit()
        //  .Search(testData.getTestData("Branch"))
*/




    }

}
