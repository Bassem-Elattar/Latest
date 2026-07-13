package AdminPages.RuleEngine.Markup;

import Drive_Factory.CommonMethod;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import org.testng.annotations.AfterMethod;
import utilities.DataUtils;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Markup_E2E_TC {

    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Markup_Page page;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("Markup.json");
        page = new Markup_Page(driver);
        RuleEngine_Common Markup = new RuleEngine_Common(driver);
        Markup.clickRuleEngine().clickMarkup();
    }
    @Test(priority = 1)
    public void TC01_AddMarkup() throws InterruptedException {
        page.addMarkup(
                testData.getTestData("Set1.MarkupDis"),
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
    public void TC02_SearchAddedMarkup() throws InterruptedException {
        page.searchMarkup(
                testData.getTestData("SearchSet1.Country"),
                testData.getTestData("SearchSet1.Branch")
        );
        page.inactivestatus();
        driver.verifyThat().element(page.Table_FirstRow).exists();
    }
    @Test(priority = 3)
    public void TC03_UpdateMarkup() {
//        page.navigateToRuleEngine();
        page.goToNextPage();
        String updatePath = "UpdateSet1";
        page.updateMarkup(
                testData.getTestData(updatePath + ".MarkupName"),
                testData.getTestData(updatePath + ".MarkupDis"),
                testData.getTestData("UpdateSet1.FromYear"),
                testData.getTestData("UpdateSet1.FromMonth"),
                testData.getTestData("UpdateSet1.FromDate"),
                testData.getTestData("UpdateSet1.ToYear"),
                testData.getTestData("UpdateSet1.ToMonth"),
                testData.getTestData("UpdateSet1.ToDate"),
//                testData.getTestData(updatePath + ".Faretype"),
                testData.getTestData(updatePath + ".Amounttype"),
                testData.getTestData(updatePath + ".AmountValue"),
                testData.getTestData(updatePath + ".Remarks")
        );
    }
    @Test(priority = 4)
    public void TC04_Search_Markup(){
//        page.navigateToRuleEngine();
        String country = testData.getTestData("SearchSet1.Country");
        String branch  = testData.getTestData("SearchSet1.Branch");
        String markupname = testData.getTestData("Actions.markupname");
        page.searchMarkup(country,branch);
        page.activestatus();
//        page.goToNextPage();
//        page.findMarkupInPages();
        Assert.assertTrue(page.findMarkupInPages(), testData.getTestData("SearchSet1.ErrorMessage"));
        driver.verifyThat().element(page.Table_FirstRow).exists();
    }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }

}
