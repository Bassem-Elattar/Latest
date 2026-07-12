package AdminPages.RuleEngine.Discount;

import Drive_Factory.CommonMethod;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import utilities.DataUtils;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class E2EDiscount {
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Discount_Page discount;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("Discount.json");
        discount = new Discount_Page(driver);
        RuleEngine_Common Dis = new RuleEngine_Common(driver);
        Dis.clickRuleEngine().clickDiscount();
    }
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
    @Test(priority = 1)
    public void TC01_Adddiscount() throws InterruptedException {
        discount.navigateToRuleEngine();

        discount.adddiscount(
                testData.getTestData("Set1.discountDis"),
                testData.getTestData("Set1.FromYear"),
                testData.getTestData("Set1.FromMonth"),
                testData.getTestData("Set1.FromDate"),
                testData.getTestData("Set1.ToYear"),
                testData.getTestData("Set1.ToMonth"),
                testData.getTestData("Set1.ToDate"),
                testData.getTestData("Set1.Country"),
                testData.getTestData("Set1.Branch"),
                testData.getTestData("Set1.Agency"),
                testData.getTestData("Set1.Attribute"),
                testData.getTestData("Set1.Operator"),
                testData.getTestData("Set1.Value"),
                testData.getTestData("Set1.Faretype"),
                testData.getTestData("Set1.Amounttype"),
                testData.getTestData("Set1.AmountValue")
        );
    }
    @Test(priority = 2)
    public void TC01_Search_Discount(){
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        discount.search_Discount(country,branch);
        discount.inactivestatus();
//        discount.goToLastPage();
        driver.verifyThat().element(discount.Table_FirstRow).exists();
    }
    @Test(priority = 3)
    public void TC02_Update_Discount(){
        discount.navigateToRuleEngine();
        discount.updatediscount(
                testData.getTestData("UpdateSet1.discountName"),
                testData.getTestData("UpdateSet1.discountDis"),
                testData.getTestData("UpdateSet1.FromYear"),
                testData.getTestData("UpdateSet1.FromMonth"),
                testData.getTestData("UpdateSet1.FromDate"),
                testData.getTestData("UpdateSet1.ToYear"),
                testData.getTestData("UpdateSet1.ToMonth"),
                testData.getTestData("UpdateSet1.ToDate"),
//                testData.getTestData("UpdateSet1.Faretype"),
                testData.getTestData("UpdateSet1.Amounttype"),
                testData.getTestData("UpdateSet1.AmountValue"),
                testData.getTestData("UpdateSet1.Remarks")
        );
    }
    @Test(priority = 5)
    public void TC04_Search_Discount(){
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        String discountname = testData.getTestData("Actions.discountname");
        discount.search_Discount(country,branch);
        discount.activestatus();
        discount.findDiscountInPages();
//        Assert.assertTrue(discount.findDiscountInPages(discountname), testData.getTestData("SearchSet1.ErrorMessage"));
        driver.verifyThat().element(discount.Table_FirstRow).exists();
    }


}
