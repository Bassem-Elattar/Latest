package AdminPages.RuleEngine.Markup;

import Drive_Factory.CommonMethod;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import utilities.DataUtils;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchMarkup_TC {
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Markup_Page markup;

    @BeforeTest
    public void setup() {
        // Setup driver & navigate
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        // Admin login
        new LogIn_Page(driver).superAdminLogin();

        // Load JSON test data
        testData = new SHAFT.TestData.JSON("Markup.json");

        // Initialize POM
        markup = new Markup_Page(driver);
        RuleEngine_Common Markup = new RuleEngine_Common(driver);
        Markup.clickRuleEngine().clickMarkup();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }


    // ---------------------------------------------------------
    // TC-001: Search by Country + Branch
    // ---------------------------------------------------------
    @Test
    public void TC001_searchByCountryAndBranch() {
        markup.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        markup.searchMarkup(country, branch);
        driver.verifyThat().element(markup.Table_FirstRow).exists();
    }

    // ---------------------------------------------------------
    // TC-002: Filter Active Markups
    // ---------------------------------------------------------
    @Test
    public void TC002_filterActive() {
        markup.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");

        markup.searchMarkup(country, branch);
        markup.activestatus();
        driver.verifyThat().element(markup.StatusCellActive).exists();
    }

    // ---------------------------------------------------------
    // TC-003: Filter Inactive Markups
    // ---------------------------------------------------------
    @Test
    public void TC003_filterInactive() {
        markup.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        markup.searchMarkup(country, branch);
        markup.inactivestatus();
        driver.verifyThat().element(markup.StatusCellInactive).exists();
    }

    // ---------------------------------------------------------
    // TC-004: Filter Both Active + Inactive
    // ---------------------------------------------------------
    @Test
    public void TC004_filterBoth() {
        markup.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        markup.searchMarkup(country, branch);
        markup.bothstatus();
        driver.verifyThat().element(markup.Table_FirstRow).exists();
    }

    // ---------------------------------------------------------
    // TC-006: Pagination → Next Page
    // ---------------------------------------------------------
    @Test
    public void TC006_paginationNext() {
        markup.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        markup.searchMarkup(country, branch);
        markup.goToNextPage();
        driver.verifyThat().element(markup.Table_FirstRow).exists();
    }

    // ---------------------------------------------------------
    // TC-007: Pagination → Last Page
    // ---------------------------------------------------------
    @Test
    public void TC007_paginationLast() {
        markup.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");


        markup.searchMarkup(country, branch);
        markup.goToLastPage();
        driver.verifyThat().element(markup.Table_FirstRow).exists();
    }
    @Test
    public void TC007_ApproveMarkup()
    {
        markup.navigateToRuleEngine();
        String markupname = testData.getTestData("Actions.markupname");
        String remark  = testData.getTestData("Actions.remark");
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        markup.searchMarkup(country, branch);
        markup.inactivestatus();
        markup.approveAction(remark);
        markup.activestatus();
        String actualMessage = driver.element().getText(By.xpath("//div[contains(@class,'toast-message')]"));

// Construct expected message
        String expectedMessage = markupname + " is Approved Successfully";

// Assertion
        Assert.assertEquals(actualMessage, expectedMessage, "The approval message is incorrect!");

    }
    @Test
    public void TC008_RejectMarkup()
    {
        markup.navigateToRuleEngine();
        String markupname = testData.getTestData("Actions.markupname");
        String remark  = testData.getTestData("Actions.remark");
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        markup.searchMarkup(country, branch);
        markup.activestatus();
        markup.rejectAction(remark);
        markup.inactivestatus();
        String actualMessage = driver.element().getText(By.xpath("//div[contains(@class,'toast-message')]"));

// Construct expected message
        String expectedMessage = markupname + " is Rejected Successfully";

// Assertion
        Assert.assertEquals(actualMessage, expectedMessage, "The approval message is incorrect!");


    }
}
