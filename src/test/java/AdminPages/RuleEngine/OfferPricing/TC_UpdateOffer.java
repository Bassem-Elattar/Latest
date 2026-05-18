package AdminPages.RuleEngine.OfferPricing;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.RuleEngine.RuleEngine_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.FileUploadUtil;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;



public class TC_UpdateOffer  {
    private UpdateOffer_Page Edit;
    private LogIn_Page logIn;
    private SearchOffer_Page Search;
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;


    @BeforeTest
    public void sign() throws InterruptedException {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("Updateoffer.json");
    }
    @Test
    public void UpdateOffer() throws InterruptedException {
        Edit = new UpdateOffer_Page(driver);
        Search = new SearchOffer_Page(driver);
        new RuleEngine_Common(driver).clickRuleEngine().clickOfferPricing();
        Search.setBoth();
        Search.search();
        String Discreption = testData.getTestData("Discreption");
        String Remarks = testData.getTestData("Remarks");
        Edit.UpdateOffer(Discreption,Remarks);
        Edit.Sendapprove();
        String Expected = testData.getTestData("success");
        Assert.assertEquals(Edit.Actual(),Expected);


    }

}
