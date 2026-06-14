package AdminPages.RuleEngine.Markup;

import Drive_Factory.CommonMethod;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;
import utilities.DataUtils;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UpdateMarkup {
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        // Load JSON with multiple sets
        testData = new SHAFT.TestData.JSON("Markup.json");
        RuleEngine_Common Markup = new RuleEngine_Common(driver);
        Markup.clickRuleEngine().clickMarkup();
    }

    @Test
    public void UpdateMarkup() throws InterruptedException {
        Markup_Page updateMarkup = new Markup_Page(driver);
        updateMarkup.navigateToRuleEngine();

        String[] SearchSet1 = {"SearchSet1"};
        for (String setKey1 : SearchSet1) {
            String country = testData.getTestData(setKey1 + ".Country");
            String branch = testData.getTestData(setKey1 + ".Branch");

            // Search the markup first
            updateMarkup.searchMarkup(country, branch);

            // Update data
            String updatePath = "UpdateSet1";
            updateMarkup.updateMarkup(
                    testData.getTestData(updatePath + ".name"),
                    testData.getTestData(updatePath + ".MarkupName"),
                    testData.getTestData(updatePath + ".MarkupDis"),
                    testData.getTestData("UpdateSet1.FromYear"),
                    testData.getTestData("UpdateSet1.FromMonth"),
                    testData.getTestData("UpdateSet1.FromDate"),
                    testData.getTestData("UpdateSet1.ToYear"),
                    testData.getTestData("UpdateSet1.ToMonth"),
                    testData.getTestData("UpdateSet1.ToDate"),
//                    testData.getTestData(updatePath + ".Faretype"),
                    testData.getTestData(updatePath + ".Amounttype"),
                    testData.getTestData(updatePath + ".AmountValue"),
                    testData.getTestData(updatePath + ".Remarks")
            );

            // Wait for success message and verify
//            By successMsg = By.xpath("//div[contains(@class,'p-toast')]//div[contains(.,'Updated Successfully')]");
////            driver.element().waitToBeVisible(successMsg, 10, true);
//            driver.verifyThat().element(successMsg).exists().perform();
        }
    }

}

