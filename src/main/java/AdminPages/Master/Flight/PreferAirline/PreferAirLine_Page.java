package AdminPages.Master.Flight.PreferAirline;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.*;
import static org.openqa.selenium.By.xpath;
public class PreferAirLine_Page {

    private final SoftAssert softAssert;
    public String columnText;
    // private SoftAssert softAssert;

    public PreferAirLine_Page(SHAFT.GUI.WebDriver driver) {

        this.softAssert = new SoftAssert();
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver;
    private final By AirlineName =xpath( "//*[@id=\"id-AirlineName\"]");
    //xpath("//ndc-fg-input[.//input[@id=\"id-AirlineName\"]]");
    public final By Lst_SupplierName = xpath("//p-dropdown//input[@id=\"id-SupplierName\"]");//dropdowenlist
    private final By  Rbtn_INActiveButton = xpath(" //p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    private final By  Rbtn_ActiveButton = xpath(" //p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    private final By  Rbtn_BothButton = xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");

    private final By Btn_SearchInGrid = xpath("//span[@class=\"p-button-label\"]");
    private final By Btn_CancelButton = xpath("//button[@label='Cancel']");
    private final By Btn_AddAirLineButton = xpath("//span[@ style=\"margin-inline-end: 10px;\"]");
    private final By Txt_AddPreferAirlineName = id("id-Airlinename");
    private final By SPECIFICAddPreferAirlineName = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-add-prefer-airline/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/ndc-fg-input[1]/ndc-fg-auto-complete-input/span/p-autocomplete/span/div/ul/li[1]");
    private final By clickSupplier = xpath("(//div[@role='button'])[1]");
    private final By clickpcc = xpath("(//div[@role='button'])[2]");

    private final By Lst_AddSupplierName = xpath("(//p-dropdownitem[@class=\"p-element ng-star-inserted\"])[1]");
    private final By Lst_AddPCCSupplier = xpath("//p-dropdown[.//input[@id=\"id-SupplierPCC\"]]");
    private final By Txt_AddAirLineName=xpath("//input[@id=\"id-Airlinename\"]");
    private final By Btn_DisLike = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/div[2]/p-table/div/div/table/tbody/tr[1]/td[6]/div/div[2]/i");
    private final By Btn_EditPenPage = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/div[2]/p-table/div/div/table/tbody/tr/td[6]/div/div[1]/i");
    private final By Btn_EditPenPage2 = xpath(" /html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/div[2]/p-table/div/div/table/tbody/tr[2]/td[6]/div/div[1]/i");
    private final By Btn_EditPenPage3 = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div[1]/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/div[2]/p-table/div/div/table/tbody/tr/td[6]/div/div[1]/i");

    private final By Btn_LikeForAre = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/div[2]/p-table/div/div/table/tbody/tr[1]/td[6]/div/div[2]/i");
    //private final By ActiveSign = xpath("//*[@id=\"pr_id_7-table\"]/tbody/tr[2]/td[6]/div/div[3]/i");
    // private final By DisActiveSign = xpath("//*[@id=\"pr_id_7-table\"]/tbody/tr[2]/td[6]/div/div[3]/i");
    private final By Txt_RemarksType = xpath("/html/body/ndc-root/ndc-dialog-modal/p-dialog/div/div/div[3]/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/ndc-fg-input/ndc-fg-text-area-input/div/textarea");
    //private final By RemarkSubmitButton = xpath("/html/body/ndc-root/ndc-dialog-modal/p-dialog/div/div/div[3]/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/div/div[2]/button/span");
    // private final By AirLineNameEdit = xpath("//span[@class=\"ng-tns-c36-10 p-autocomplete p-component\"]");
    private final By Btn_AirLineNameEdit = xpath("//*[@id=\"id-Airlinename\"]");

    // private final By SupplierNameEdit = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-add-prefer-airline/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/ndc-fg-input[2]/ndc-fg-dropdown-input/p-dropdown/div/div[2]");//dropdowenlist
    // private final By SupplierNameEdit = xpath("//span[@class=\"p-dropdown-label p-inputtext p-placeholder ng-star-inserted\"]");//dropdowenlist
    private final By Btn_SupplierNameEdit = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-add-prefer-airline/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/ndc-fg-input[2]/ndc-fg-dropdown-input/p-dropdown/div/div[2]");//dropdowenlist


    private final By Btn_PCCForSupplierEdit = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-add-prefer-airline/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/ndc-fg-input[3]/ndc-fg-dropdown-input/p-dropdown/div/div[2]");//dropdowenlist
    private final By Btn_SendForApprovalButton = xpath("//button[@label='Submit']");
    public final By Btn_MinusButton = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/tilde-paginator/div/div/button[1]/i");
    public final By Btn_plusButton = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/tilde-paginator/div/div/button[2]/i");
    public final By NumberOfRowsInEveryPage = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/tilde-paginator/div[1]/div/span[2]");
    public final By ShowResultOfResult = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/tilde-paginator/div[1]/p");
    public final By AngleLeft = xpath("//i[@class=\"pi pi-angle-left\"]");
    public final By AngleRight = xpath("//i[@class=\"pi pi-angle-right\"]");
    public final By FirstButton = id("frist");
    public final By LastButton = id("last");

    private final By Btn_Master =By.xpath(" //a[@href=\"/master\"]");

    private final By ColumnValue = xpath("//*[@id=\"pr_id_2-table\"]/thead/tr/th[4]");
    private final By rowcount = xpath("//div[@class=\"table-area\"]//tr");
    private final By colcount = xpath("////div[@class=\"table-area\"]//th");

    public PreferAirLine_Page(SoftAssert softAssert) {
        this.softAssert = softAssert;
    }
    private final By Flight=By.linkText("Flight");
    private final By FlightCore=By.xpath("/html/body/div[1]/section/div/div[1]/nav/div/div[1]/div[1]/h4/img");
    private final By  PreferAirLinePage=By.linkText("Prefer Airline");
    private final By  PreferAirLinePageCore=By.id("PreferAirlineMaster");
    private final By RejectedSign=xpath("//i[@class=\"pi pi-thumbs-down\"]");
    private final By ApprovedSign=xpath("//i[@ class=\"pi pi-thumbs-up\"]");

    private final By  Txt_Remark=xpath("//textarea[@class=\"p-inputtextarea p-inputtext p-component p-element ng-pristine ng-invalid ng-touched\"]");

    public final By  RemarkValidtion =xpath("//span[@class=\"fg-error\"]");
    private final By  Btn_RemarkSubmit =xpath("/html/body/ndc-root/ndc-dialog-modal/p-dialog/div/div/div[3]/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/div/div[2]/button/span");
    private final By  Rbtn_ActiveSign=xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-prefer-airline/div/tilde-data-table/div/div/div/div/div[2]/p-table/div/div/table/tbody/tr/td[6]/div/div[3]/i");
    private final By  Rbtn_INActiveSign=xpath("//i[@class=\"pi pi-circle-fill\"]");
    private final By Txt_ADDAirlineName=xpath("//input[@id=\"id-Airlinename\"]");
    public final By ValidationOnAirLineName=xpath("//span[contains(text(),' Required ')]");
    public final By ValidationOnPCCName=xpath("//span[contains(text(),' Required ')]");
    public final By AddSucessfuly=xpath("//div[@aria-label=\"Added Successfully\"]");
    public final By RestricatedAirLine=xpath("//div[@aria-label=\"Remove the restricted airlines to add prefer airline\"]");
    public final By ValidtionInPcc=xpath("//span[contains(text(),'required validation error')]");
    public final By UpdatedSuccessfully=xpath("//div[@aria-label=\"Updated Successfully\"]");

    public void navigateToPreferAirlinePage() throws InterruptedException {

        driver.element().click(Btn_Master);
        Thread.sleep(2000);
        //driver.element().click(Flight );
        // driver.element().click(FlightCore );
        // driver.element().click(PreferAirLinePage);
        driver.element().click(PreferAirLinePageCore);
    }


    public PreferAirLine_Page EnterAirlineName(String airlineName) throws InterruptedException {
        // Click on the input field to focus
        driver.element().click(AirlineName);

        // Locate the input field using its locator and send the airline name keys to it
        WebElement inputField = driver.getDriver().findElement(AirlineName);
        inputField.clear(); // Clear any existing text
        inputField.sendKeys(airlineName);
        Thread.sleep(Long.parseLong("3000"));
        driver.element().click(AirlineName);
        By option = xpath(String.format("//span[contains(text(), '%s')]", airlineName));
        driver.element().click(option);
        return this;
    }



    public PreferAirLine_Page SelectSupplierName(String supplierName) {
//        driver.element().click(Lst_SupplierName);
        By option = xpath(String.format("//span[text()='%s']", supplierName));
        driver.element().click(option);
        return this;
    }
    public PreferAirLine_Page clickOnSupplierList() {
        driver.element().click(clickSupplier);
        return this;
    }
    public PreferAirLine_Page clickOnPCCSupplierList() {
        driver.element().click(clickpcc);
        return this;
    }


    public PreferAirLine_Page clickOnSearchInGrid() {
        driver.element().click(Btn_SearchInGrid);
        return this;
    }
    public PreferAirLine_Page clickOnCancelButton() {
        driver.element().click(Btn_CancelButton);
        return this;
    }

    public PreferAirLine_Page clickActiveButton() {
        driver.element().click(Rbtn_ActiveButton);
        return this;
    }

    public PreferAirLine_Page clickInActiveButton() {
        driver.element().click(Rbtn_INActiveButton);
        return this;
    }

    public PreferAirLine_Page clickBothButton() {
        driver.element().click(Rbtn_BothButton);
        return this;
    }

    public PreferAirLine_Page clickAddAirLineButton() {
        driver.element().click(Btn_AddAirLineButton);
        return this;
    }
    public PreferAirLine_Page clickAddSupplierPCCButton() {
        driver.element().click(Lst_AddPCCSupplier);
        By option = xpath(String.format("//span[text()='%s']", Lst_AddPCCSupplier));
        driver.element().click(option);
        return this;
    }

    public PreferAirLine_Page clickEditPenPage() {
        driver.element().click(Btn_EditPenPage);
        return this;
    }
    public PreferAirLine_Page clickEditPenPage2() {
        driver.element().click(Btn_EditPenPage2);
        return this;
    }
    public PreferAirLine_Page clickEditPenPage3() {
        driver.element().click(Btn_EditPenPage3);
        return this;
    }

    public PreferAirLine_Page clickDisLike() {
        driver.element().click(Btn_DisLike);
        return this;
    }

    public PreferAirLine_Page clickLike() {
        driver.element().click(Btn_LikeForAre);
        return this;
    }

    public PreferAirLine_Page clickActiveSign() {
        driver.element().click(Rbtn_ActiveSign);
        return this;
    }

    public PreferAirLine_Page clickDisActiveSign() {
        driver.element().click(Rbtn_INActiveSign);
        return this;
    }

    public PreferAirLine_Page AirLineNameEdit(String NameEdit) throws InterruptedException {
        driver.element().click(Btn_AirLineNameEdit);
        // Locate the input field using its locator and send the airline name keys to it
        WebElement inputField = driver.getDriver().findElement(Btn_AirLineNameEdit);
        inputField.clear(); // Clear any existing text
        inputField.sendKeys(NameEdit);
        Thread.sleep(Long.parseLong("3000"));
        driver.element().click(Btn_AirLineNameEdit);
        By option = xpath(String.format("//span[contains(text(), '%s')]", NameEdit));
        driver.element().click(option);
        return this;
    }
//public PreferAirLine AirLineNameEdit(String NameEdit) throws InterruptedException {
//    driver.element().type(AirLineNameEdit,NameEdit) ;
//    By option = xpath(String.format("//span[contains(text(), '%s')]", NameEdit));
//    driver.element().click(option);
//    return this;
//}

    public PreferAirLine_Page RemarkType(String remark) {
        driver.element().type(Txt_Remark, remark);
        return this;
    }

    public PreferAirLine_Page SupplierNameEdit(String SupplierName) {
        driver.element().click(Btn_SupplierNameEdit);
        By option = xpath(String.format("//span[text()='%s']", SupplierName));
        driver.element().click(option);
        return this;
    }

    public PreferAirLine_Page SelectPCCForSupplierEdit(String pccSupplierName) {
        driver.element().click(Btn_PCCForSupplierEdit);
        By option = xpath(String.format("//span[text()='%s']", pccSupplierName));
        driver.element().click(option);
        return this;
    }

    public PreferAirLine_Page EnterRemarksType(String RemarksText) {
        driver.element().type(Txt_RemarksType, RemarksText);
        return this;
    }

    public PreferAirLine_Page RemarkSubmitButton() {
        driver.element().click(Btn_RemarkSubmit);
        return this;
    }

    public PreferAirLine_Page EnterAddPreferAirlineName(String EX, String PreferAirlineName) throws InterruptedException {
        driver.element().type(Txt_AddPreferAirlineName,EX);
        By option = xpath(String.format("//span[text()='%s']", PreferAirlineName));
        driver.element().click(option);
        return this;

    }
    public PreferAirLine_Page AddAirlineName(String SubAirlineName, String airlineName) throws InterruptedException {
        // Click on the input field to focus
        driver.element().click(Txt_ADDAirlineName);

        // Locate the input field using its locator and send the airline name keys to it
        WebElement inputField = driver.getDriver().findElement(Txt_ADDAirlineName);
        inputField.clear(); // Clear any existing text
        inputField.sendKeys(SubAirlineName);
        Thread.sleep(Long.parseLong("3000"));
        driver.element().click(Txt_ADDAirlineName);
        By option = xpath(String.format("//span[contains(text(), '%s')]", airlineName));
        driver.element().click(option);
        return this;
    }
    public PreferAirLine_Page SelectAddSupplierName(String supplierName) {
      //  driver.element().click(Lst_AddSupplierName);
        By option = xpath(String.format("//span[text()='%s']", supplierName));
        driver.element().click(option);
        return this;
    }

    public PreferAirLine_Page SelectAddPCCSupplierName(String pccSupplierName) {
       // driver.element().click(Lst_AddPCCSupplier);
        By option = xpath(String.format("//span[text()='%s']", pccSupplierName));
        driver.element().click(option);
        return this;
    }

    public PreferAirLine_Page SendForApprovalButton() {
        driver.element().click(Btn_SendForApprovalButton);
        return this;
    }

    public PreferAirLine_Page ClickOnMinusButton() {

        driver.element().click(Btn_MinusButton);
        return this;
    }

    public PreferAirLine_Page ClickOnPlusButton() {

        driver.element().click(Btn_plusButton);
        return this;
    }

    public PreferAirLine_Page GetNumberOfResults() {

        int valuesNumber = driver.element().getElementsCount(NumberOfRowsInEveryPage);

        return this;
    }

    public PreferAirLine_Page GetResultsOfResults() {

        String valueText = driver.element().getText(ShowResultOfResult);
        return this;
    }


    public String TableColumnDataExtractor(int ColumnSearch,String ExpectedSearch) {
        WebElement table = driver.getDriver().findElement(By.xpath("//div[@class=\"table-area\"] ")); // Adjust the XPath to match your table

        // Locate all the rows in the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        String actualResult = ExpectedSearch;
        // Loop through each row and get the text from the specific column (e.g., second column)
        for (WebElement row : rows) {
            // Locate the cells in the current row
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Check if the row has enough cells (to avoid IndexOutOfBoundsException)
            if (cells.size() > ColumnSearch) {
                // Get text from the second column (index 1)

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
    public PreferAirLine_Page AddAirLineName2(String AirLineName) throws InterruptedException {
        // Typing the airline name in the field
        driver.element().type(Txt_AddAirLineName, AirLineName);
        return this;
    }
}