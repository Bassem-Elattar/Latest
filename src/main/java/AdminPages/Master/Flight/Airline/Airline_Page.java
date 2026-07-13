package AdminPages.Master.Flight.Airline;

import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.openqa.selenium.By.xpath;
public class Airline_Page {

    // By AirLineCode= id("id=\"id-AirlineCode\"");
    private final By Txt_AirLineCode=xpath("// input[@id=\"id-AirlineCode\"]");
    private final By Txt_AirLineName=xpath("//input[@id=\"id-AirlineName\"]");
    private final By Lst_AirLineType=xpath("//p-dropdown[.//input[@id=\"id-AirlineType\"]]");
    private final By Lst_NetworkType=xpath("//p-dropdown[.//input[@id=\"id-NetworkType\"]]");
    private final By Btn_SearchInGrid=xpath("(//button[@type='submit'])[1]");
    private final By Rbtn_Active=xpath("(//div[@class='p-radiobutton-box'])[1]");
    private final By Rbtn_InActive=xpath("(//div[@class='p-radiobutton-box'])[1]");
    private final By Rbtn_Both=xpath("//p-radiobutton[ .//input[@ id=\"id-Status-Both\"]]");
    private final By Btn_AddAirLine=xpath(" //i[@class=\"pi pi-plus\"] ");
    private final By Txt_addAirlineCode=xpath("//input[@ id=\"id-AirlineCode\"]");
    private final By Txt_addAirlineName=xpath("// input[@id=\"id-AirlineName\"]");
    private final By Txt_addairlineType=xpath("//p-dropdown[.//input[@id=\"id-AirlineType\"]]");
    private final By Lst_addnetworkType=xpath("//p-dropdown[.//input[@id=\"id-NetworkType\"]]");
    private final By Cbox_CodeSharing =xpath("//p-checkbox[@class=\"p-element ng-valid ng-dirty ng-touched\"]");
    private final By Btn_Master =By.xpath("//a[@href=\"/master\"]");
    private final By Btn_Flight=By.linkText("Flight");
    private final By Btn_AirLine=By.linkText("Airline");
    private final SoftAssert softAssert;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Action

//    public String NameOfAirlineOnEye(){String S = ElementActions.getInstance().getText(By.xpath("(//span[@class=\"value\"])[1]"));return S;}
//    public String NameOfAirlineOnSearch(){String S = ElementActions.getInstance().getText(By.xpath("(//td[@class=\"ng-star-inserted\"])[2]"));return S;}
    public Airline_Page ClickOnEye(){By Eye = By.xpath("(//i[@class=\"pi pi-eye\"])[1]");driver.element().click(Eye);return this;}
    public Airline_Page ClickOnClose(){By Close = By.xpath("(//button[@type=\"button\"])[5]");driver.element().click(Close);return this;}
    public Airline_Page ClickOnEdit(){By Edit = By.xpath("(//i[@class=\"pi pi-pencil\"])[1]");driver.element().click(Edit);return this;}
    public Airline_Page ClickOnActiveCircle(){By Circle = By.xpath("(//i[@class=\"pi pi-circle-fill\"])[1]");driver.element().click(Circle);return this;}
    public Airline_Page ClickOnInactiveCircle(){By Circle = By.xpath("(//i[@class='pi pi-circle'])[1]");driver.element().click(Circle);return this;}
    public Airline_Page ClickOnActive(){driver.element().click(Rbtn_Active);return this;}
    public Airline_Page ClickOnInactive(){driver.element().click(Rbtn_InActive);return this;}
    public Airline_Page ClickOnThumbsUp(){By ThumbsUp = By.xpath("(//i[@class='pi pi-thumbs-up'])[1]");driver.element().click(ThumbsUp);
        By Remark = By.xpath("(//textarea[@placeholder='remarks...'])[1]");driver.element().type(Remark,"Ok");
        By Approve = By.xpath("(//button[@type='submit'])[2]");driver.element().click(Approve);return this;}
    public Airline_Page ClickOnThumbsDown(){By ThumbsDown = By.xpath("(//i[@class=\"pi pi-thumbs-down\"])[1]");driver.element().click(ThumbsDown);
        By Remark = By.xpath("//textarea[@class=\"p-inputtextarea p-inputtext p-component p-element ng-untouched ng-pristine ng-invalid\"]");driver.element().type(Remark,"Ok");
        By Reject = By.xpath("(//span[@class=\"p-button-label\"])[3]");driver.element().click(Reject);return this;}

    public String TableColumnDataExtractorForCodeAction(int ColumnSearch, String ExpectedSearch) {
        // Locate the table element by its XPath
        WebElement table = driver.getDriver().findElement(By.xpath("//div[@class=\"table-area\"]")); // Adjust the XPath to match your table
        // Find all rows within the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        // Initialize actualResult to the expected search term
        String actualResult = ExpectedSearch;

        // Loop through each row
        for (WebElement row : rows) {
            // Find all cells within the current row
            List<WebElement> cells = row.findElements(By.tagName("td"));
            // Check if the current row has enough columns
            if (cells.size() > ColumnSearch) {
                // Get the text content of the specified column
                String columnText = cells.get(ColumnSearch).getText();
                // If the column text does not contain the expected search term
                break;

//                if (!columnText.contains(ExpectedSearch)) {
//                    actualResult = null; // Set actualResult to null
//                    break; // Exit the loop early
//                }
            }
            // Optionally, add assert statements here to verify if the content matches
            // assertEquals(ExpectedSearch, columnText);
        }

        // Return the final result
        return actualResult;
    }

    public Airline_Page(SHAFT.GUI.WebDriver driver) {
        this.softAssert = new SoftAssert();

        this.driver = driver;

    }
    public void setBoth(){
        driver.element().click(Rbtn_Both);
    }


    SHAFT.GUI.WebDriver driver;
    public void navigateToAirlinePage() {
        driver.element().click(Btn_Master);
//        driver.element().click(Flight);
//        driver.element().click(AirLine);

    }

    public Airline_Page EnterAirLineCode(String Code){

        driver.element().type(Txt_addAirlineCode,Code);
        return this;
    }
    public Airline_Page EnterAirLineName(String name) throws InterruptedException {
        // Typing the airline name in the field
        driver.element().type(Txt_addAirlineName, name);
        Thread.sleep(Long.parseLong("2000"));
//        By option = xpath(String.format("//span[text()='%s']", FullName));
//        driver.element().click(option);
        return this;
    }
    public Airline_Page SearchButton(){

        driver.element().click(Btn_SearchInGrid);
        return this;
    }
    public Airline_Page EnterAirLineType(String type ) {
        driver.element().click(Txt_addairlineType);
        By option = xpath(String.format("//span[text()='%s']",type));
        driver.element().click(option);
        return this;
    }
    public Airline_Page EnterNetworkType(String type ) {
        driver.element().click(Lst_addnetworkType);
        By option = xpath(String.format("//span[text()='%s']",type));
        driver.element().click(option);
        return this;
    }
    public String TableColumnDataExtractor(int ColumnSearch, String ExpectedSearch) {
        WebElement table = driver.getDriver().findElement(By.xpath("//div[@class=\"table-area\"]")); // Adjust the XPath to match your table
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        String actualResult = ExpectedSearch;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > ColumnSearch) {
                String columnText = cells.get(ColumnSearch).getText();
                if (!columnText.equals(actualResult)) {
                    actualResult = null;
                    break;
                }
            }
            // i will add  assertEquals(ExpectedSearch, columnText);

        }
        return actualResult;
    }
    public String TableColumnDataExtractorForCode(int ColumnSearch, String ExpectedSearch) {
        // Locate the table element by its XPath
        WebElement table = driver.getDriver().findElement(By.xpath("//div[@class=\"table-area\"]")); // Adjust the XPath to match your table
        // Find all rows within the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        // Initialize actualResult to the expected search term
        String actualResult = ExpectedSearch;

        // Loop through each row
        for (WebElement row : rows) {
            // Find all cells within the current row
            List<WebElement> cells = row.findElements(By.tagName("td"));
            // Check if the current row has enough columns
            if (cells.size() > ColumnSearch) {
                // Get the text content of the specified column
                String columnText = cells.get(ColumnSearch).getText();
                // If the column text does not contain the expected search term
                if (!columnText.contains(ExpectedSearch)) {
                    actualResult = null; // Set actualResult to null
                    break; // Exit the loop early
                }
            }
            // Optionally, add assert statements here to verify if the content matches
            // assertEquals(ExpectedSearch, columnText);
        }

        // Return the final result
        return actualResult;
    }



}

