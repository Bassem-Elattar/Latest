package AdminPages.RuleEngine.OfferPricing;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.RuleEngine.Markup.Markup_Page;
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



public class TC_CreateOffer {
    private CreateOffer_Page Create;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;

    @BeforeTest
    public void sign() throws InterruptedException {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("Createoffer.json");
        Create = new CreateOffer_Page(driver);

    }
    @Test
    public void CreateOffer() throws InterruptedException {
        new RuleEngine_Common(driver).clickRuleEngine().clickOfferPricing();
        Create = new CreateOffer_Page(driver);
        String Discount = testData.getTestData("Discount");
        String Discreption = testData.getTestData("Discreption");
        String value = testData.getTestData("DiscountName");
        Create.CreateOffer(Discount,Discreption,value);
        Create.img();
        Create.uploadImage();
        Create.Sendapprove();
        String Expected = testData.getTestData("Success");
        Assert.assertEquals(Create.Actual(),Expected);


    }

}
