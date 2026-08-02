package AdminPages.RuleEngine.Discount;

import Drive_Factory.CommonMethod;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import utilities.DataUtils;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SearchDiscount_TC {
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Discount_Page discount;
    @BeforeTest
    public void setup() {
        // Setup driver & navigate
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        // Admin login
        new LogIn_Page(driver).superAdminLogin();

        // Load JSON test data
        testData = new SHAFT.TestData.JSON("Discount.json");

        // Initialize POM
        discount = new Discount_Page(driver);
        RuleEngine_Common Dis = new RuleEngine_Common(driver);
        Dis.clickRuleEngine().clickDiscount();

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void TC001_searchByCountryAndBranch() {
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        discount.search_Discount(country, branch);
        driver.verifyThat().element(discount.Table_FirstRow).exists();
    }
    @Test
    public void TC002_filterActive() {
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");

        discount.search_Discount(country, branch);
        discount.activestatus();
        driver.verifyThat().element(discount.StatusCellActive).exists();
    }
    @Test
    public void TC003_filterInactive() {
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        discount.search_Discount(country, branch);
        discount.inactivestatus();
        driver.verifyThat().element(discount.StatusCellInactive).exists();
    }
    @Test
    public void TC004_filterBoth() {
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        discount.search_Discount(country, branch);
        discount.bothstatus();
        driver.verifyThat().element(discount.Table_FirstRow).exists();
    }
    @Test
    public void TC005_paginationNext() {
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        discount.search_Discount(country, branch);
        discount.goToNextPage();
        driver.verifyThat().element(discount.Table_FirstRow).exists();
    }
    @Test
    public void TC006_paginationLast() {
        discount.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        discount.search_Discount(country, branch);
        discount.goToLastPage();
        driver.verifyThat().element(discount.Table_FirstRow).exists();
    }
    @Test
    public void TC007_ApproveDiscount()
    {
        discount.navigateToRuleEngine();
        String discountname = testData.getTestData("Actions.discountname");
        String remark  = testData.getTestData("Actions.remark");
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        discount.search_Discount(country, branch);
        discount.inactivestatus();
        discount.approveAction(remark);
        discount.activestatus();
        String actualMessage = driver.element().getText(By.xpath("//div[contains(@class,'toast-message')]"));

// Construct expected message
        String expectedMessage = discountname + " is Approved Successfully";

// Assertion
        Assert.assertEquals(actualMessage, expectedMessage, "The approval message is incorrect!");

    }
    @Test
    public void TC008_RejectDiscount()
    {
        discount.navigateToRuleEngine();
        String discountname = testData.getTestData("Actions.discountname");
        String remark  = testData.getTestData("Actions.remark");
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        discount.search_Discount(country, branch);
        discount.activestatus();
        discount.rejectAction(remark);
        discount.inactivestatus();
        String actualMessage = driver.element().getText(By.xpath("//div[contains(@class,'toast-message')]"));

// Construct expected message
        String expectedMessage = discountname + " is Rejected Successfully";

// Assertion
        Assert.assertEquals(actualMessage, expectedMessage, "The approval message is incorrect!");


    }
}
