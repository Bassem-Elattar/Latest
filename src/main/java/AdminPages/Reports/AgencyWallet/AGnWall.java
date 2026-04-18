package AdminPages.Reports.AgencyWallet;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.By.xpath;

public class AGnWall {
    public AGnWall(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    SHAFT.TestData.JSON testData;


    ////////////////////////// LOCATORS //////////////////////////////
    By Branch = By.xpath("//p-multiselect[.//input[@id=\"id-BranchName\"]]");
    By Agency = By.xpath("//p-multiselect[.//input[@id=\"id-AgencyName\"]]");
    By Currency = By.xpath("//p-dropdown[.//input[@id=\"id-Currency\"]]");
    By Show = By.xpath("//p-checkbox[.//input[@id=\"id--ShowOutStandingOnly\"]]");
    By Submit = By.xpath("//button[@type=\"submit\"]");
    By ErrorCurrency = By.xpath("//span[@class='fg-error has-error']");
    By ErrorBranch = By.xpath("//span[@class='fg-error has-error']");


    public void SearchValidData (String branch,String agency,String currency) {

        driver.element().click(Branch);
        By option = xpath(String.format("//span[text()='%s']", branch));
        driver.element().click(option);
        driver.element().select(Agency,agency);
        driver.element().click(Currency);
        By option1 = xpath(String.format("//span[text()='%s']", currency));
        driver.element().click(option1);
        driver.element().click(Show)
                .click(Submit);

    }
    public void SearchInvalidBranch(String currency){

        driver.element().click(Currency);
        By option1 = xpath(String.format("//span[text()='%s']", currency));
        driver.element().click(option1);
        driver.element().click(Show)
                .click(Submit);

    }
    public void SearchInvalidCurrency(String branch,String agency) {


        driver.element().click(Branch);
        By option = xpath(String.format("//span[text()='%s']", branch));
        driver.element().click(option);
                driver.element().select(Agency,agency);
        driver.element().click(Show)
                .click(Submit);

    }

    public String SearchInTable(int ColumnSearch, String ExpectedSearch) {
        testData = new SHAFT.TestData.JSON("AgencyWalletReport.json");
        WebElement table = driver.getDriver().findElement(xpath("//div[@class=\"table-area\"]")); // Adjust the XPath to match your table
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        String actualResult = ExpectedSearch;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > ColumnSearch) {
                String columnText = cells.get(ColumnSearch).getText();
                if (!columnText.equals(actualResult)) {
                    actualResult = testData.getTestData("ValidData.branch");
                    break;
                }
            }
        }
        return actualResult;
    }


}
