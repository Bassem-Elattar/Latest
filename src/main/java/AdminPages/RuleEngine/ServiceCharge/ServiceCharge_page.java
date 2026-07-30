package AdminPages.RuleEngine.ServiceCharge;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.By;
import utilities.FakerSingleton;

import java.util.List;

public class ServiceCharge_page {
    private SHAFT.GUI.WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private SHAFT.TestData.JSON testData;
    public static String ServiceName;

    public ServiceCharge_page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    /// /Add Service Charge////////////
    private final By btn_RuleEngine = By.xpath("//a[@id='p-tabpanel-3-label']");
    private final By btn_ServiceCharge = By.xpath("//li[@class='ng-star-inserted']//a[@href='/rule-engine/service-charge']");

    private final By btn_AddServiceCharge = By.xpath("//button[@routerlink='add']");
    private final By txt_ServiceChargeName = By.xpath("//input[@id='id-ServiceChargeName']");
    private final By txt_SelectValidityFrom = By.xpath("//input[@id='id-ValidityFrom']");
    private final By txt_SelectValidityTo = By.xpath("//input[@id='id-ValidityTo']");
    //private final By btn_DateIconFrom = By.xpath("//button[@class='p-element p-ripple p-datepicker-trigger ng-tns-c52-18 p-button p-component p-button-icon-only ng-star-inserted']");
    //private final By btn_DateIconTo = By.xpath("//button[@class='p-element p-ripple p-datepicker-trigger ng-tns-c52-19 p-button p-component p-button-icon-only ng-star-inserted']");
    private final By txt_ServiceChargeDescription = By.xpath("//textarea[@placeholder='Type your service charge description...']");
    private final By Lst_selectCountryPOS = By.xpath("//p-multiselect[.//input[@id=\"id-CountryPOS\"]]");
    private final By txt_searchCountryPOS = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    private final By btn_selectCountryPOS = By.xpath("//div[@role='checkbox' and contains(@class,'p-checkbox-box') and @aria-checked='false']");


    private final By btn_selectBranch = By.xpath("//p-multiselect[.//input[@id='id-Branch']]");
    private final By txt_selectBranch = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    private final By btn_selectBranchOption = By.xpath("//div[@role='checkbox' and contains(@class,'p-checkbox-box') and @aria-checked='false']");
    //private final By btn_closeDropDrown = By.xpath("(//span[@class=\"dropdown-arrow\"])[2]");


    private final By btn_selectAgency = By.xpath("(//span[contains(@class,'dropdown-arrow')])[3]");
    private final By txt_selectAgency = By.xpath("//input[@class='p-inputtext p-component p-element search-input']");
    private final By btn_selectAgencyOption = By.xpath("//input[@class='ng-star-inserted']");


    private final By btn_selectAttribute = By.xpath("//span[text()='Select Attribute']");
    private final By btn_selectAttributeOption = By.xpath("//p-dropdownitem//li[@aria-label='Supplier']");


    private final By btn_selectOperator = By.xpath("//span[text()='Select Operator']");
    private final By btn_selectOperatorOption = By.xpath("//p-dropdownitem//li[@aria-label='IN']");

    private final By btn_searchIcon = By.xpath("//button[@class='pi pi-search btn']");
    //private final By txt_SearchByName = By.xpath("//input[@class='(//input[@placeholder='Search...' and contains(@class,'p-inputtext')])[1]']");
    private final By btn_selectAll = By.xpath("(//p-checkbox[@value='All'])[1]");
    private final By btn_Add = By.xpath("//button[normalize-space()='Add']");
    private final By btn_SaveValue = By.xpath("//button[normalize-space()='Save']");
    // private final By btn_ALL = By.xpath("//div[contains(@class,'p-radiobutton-box')])[1]");
    //private final By btn_BYPAX = By.xpath("//div[contains(@class,'p-radiobutton-box')])[2]");
    private final By btn_SelectFareType = By.xpath("(//div[@class='w-full p-dropdown p-component p-dropdown-clearable'])[1]");
    private final By btn_FareTypeOption = By.xpath("(//li[@aria-label='Total Fare'])[1]");
    private final By btn_SelectAmountType = By.xpath("(//div[@class='w-full p-dropdown p-component p-dropdown-clearable'])[1]");
    private final By btn_AmountOption = By.xpath("(//li[@aria-label='Fixed Amount'])[1]");
    private final By btn_Value = By.xpath("(//input[@placeholder='0.00'])[1]");

    private final By btn_SendForApproval = By.xpath("//button[@type='submit']");
    // private final By btn_Cancel = By.xpath("//button[@type='button' and text()='Cancel']");
    ////search Service Charge/////
    private final By btn_SearchCountry = By.xpath("//p-dropdown[.//input[@id=\"id-CountryPOS\"]]");
    private final By txt_selectCountry = By.xpath("//input[@class=\"p-dropdown-filter p-inputtext p-component\"]");


    private final By btn_SearchBranch = By.xpath("//p-dropdown[.//input[@id=\"id-Branch\"]]");
    private final By txt_SearchBranch = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component']");

    private final By btn_BranchOption = By.xpath("//li[@aria-label='Test']");

    // private final By btn_SearchAgency = By.xpath("//p-dropdown[.//input[@id=\"id-Agency\"]]");
    //  private final By btn_SelectAgency = By.xpath("//p-dropdownitem[.//li[@aria-label=\"NDC1_Yara Khaled\"]]");
    private final By Rbtn_Inactive = By.xpath("(//div[@class='p-radiobutton-box'])[1]");
    private final By Rbtn_Active = By.xpath("//div[@class='p-radiobutton-box p-highlight']");
    private final By Rbtn_Both = By.xpath("(//div[@class='p-radiobutton-box'])[2]");
    private final By Btn_SearchInGrid = By.xpath("//button[@type=\"submit\"]");
    By txt_Reamrks = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_Approve = By.xpath("(//button[@type=\"submit\"])[2]");
    // private final By Btn_Next = By.xpath("//span[@id='last']");


    //private final By Btn_ExportToExcel = By.xpath("//button[@class="p-element p-ripple p-button-outlined p-button p-component upper-table-btn ng-star-inserted']";
    private final By Btn_eye = By.xpath("//div[@class='action exception-buttons ng-star-inserted']//i[@class='pi pi-eye']");
    private final By Btn_Close = By.xpath("//button[@label='Close']");
    private final By Btn_approval = By.xpath("//i[@class='pi pi-thumbs-up']");
    By Year = By.xpath("//button[normalize-space()='2026']");

    By RemarksAction = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_SubmitAction = By.xpath("(//button[@type=\"submit\"])[2]");
    By Btn_last = By.xpath("(//span[@class='last text-xs selected-last-page ng-star-inserted'])[1]");
    public By Table_FirstRow = By.xpath("//table//tbody/tr[1]");
    public By StatusCellInactive = By.xpath("//td[normalize-space()='Inactive']");
    public By StatusCellActive = By.xpath("//td[normalize-space()='Active']");
    public By Btn_Next = By.xpath("//button[contains(@class,'next')]//i[contains(@class,'pi-angle-right')]");
    private final By Btn_Update = By.xpath("//div[@class='action ng-star-inserted']/i[@class='pi pi-pencil']");
    private final By Btn_Submit = By.xpath("//button[@type='submit']");

    private By rowService(String ServiceChargeName) {
        return By.xpath("//td[normalize-space()='" + ServiceChargeName + "']");
    }

    private By approveButton(String ServiceChargeName) {
        return By.xpath("//tr[td[normalize-space()='" + ServiceChargeName + "']]//i[contains(@class,'pi-thumbs-up')]");
    }

    private By rejectButton(String ServiceChargeName) {
        return By.xpath("//tr[td[normalize-space()='" + ServiceChargeName + "']]//i[contains(@class,'pi-thumbs-down')]");
    }

    private By statusCell(String Status) {
        return By.xpath("//tr[td[normalize-space()='" + Status + "']]//td[contains(@class,'status') or contains(@class,'Status')]//span | //tr[td[normalize-space()='" + Status + "']]//td[2]//span");
    }

    public void search_ServiceCharge(String Country, String Branch) {
        driver.element().click(btn_SearchCountry);
        driver.element().select(txt_selectCountry, Country);
        driver.element().click(btn_SearchBranch);
        By item = By.xpath("//li[normalize-space()='" + Branch + "']");
        driver.element().scrollToElement(item);
        driver.element().click(item);
    }

    public void goToNextPage() {
        // Scroll to button if needed
        driver.element().scrollToElement(Btn_Next);

        // Click Next
        driver.element().click(Btn_Next);
    }

    public void goToLastPage() {
        // Wait until Last button is visible and clickable
        driver.element().scrollToElement(Btn_last);

        // Click the Last button
        driver.element().click(Btn_last);
    }

    public void activestatus() {
        driver.element().click(Rbtn_Active);
        driver.element().click(Btn_Submit);
    }

    public void inactivestatus() {
        driver.element().click(Rbtn_Inactive);
        driver.element().click(Btn_Submit);
        if (!driver.getDriver().findElements(Btn_last).isEmpty()) {
            driver.element().click(Btn_last);
        } else if (!driver.getDriver().findElements(Btn_Next).isEmpty()) {
            driver.element().click(Btn_Next);
        }
    }

    public void bothstatus() {
        driver.element().click(Rbtn_Both);
        driver.element().click(Btn_Submit);

    }
/// /////////////////

    /// AddServiceCharge Methods///
    public ServiceCharge_page navigateToRuleEngine() {
        driver.element().click(btn_RuleEngine);
        return this;
    }

    public ServiceCharge_page navigateToServiceCharge() {
        driver.element().click(btn_ServiceCharge);
        return this;
    }

    public ServiceCharge_page ClickAtAddServiceCharge() {
        driver.element().click(btn_AddServiceCharge);
        return this;
    }

    public ServiceCharge_page EnterServiceName() {
        driver.element().type(txt_ServiceChargeName, FakerSingleton.PassengerFactory.firstName());
        ServiceName = driver.element().getText(txt_ServiceChargeName);
        return this;
    }

    public ServiceCharge_page SelectValidityDate(String s1, String s2) {
        driver.element().type(txt_SelectValidityFrom, s1);
        driver.element().type(txt_SelectValidityTo, s2);
        // driver.element().click(btn_DateIconFrom);
        // driver.element().click(btn_DateIconTo);
        return this;
    }

    public ServiceCharge_page EnterServiceDescription(String s) {
        driver.element().type(txt_ServiceChargeDescription, s);
        return this;
    }

    public ServiceCharge_page SelectListCountryPOS(String s) throws InterruptedException {
        driver.element().click(Lst_selectCountryPOS);
      //  driver.element().click(txt_searchCountryPOS);
        driver.element().type(txt_searchCountryPOS,s);
        driver.element().click(btn_selectCountryPOS);
        driver.element().click(Lst_selectCountryPOS);


        return this;
    }
   /* public ServiceCharge_page SelectServiceCountryPOS() {
        driver.element().click(btn_selectCountryPOS);
        return this;
    } */
    public ServiceCharge_page SelectListBranch(String s) {
        driver.element().click(btn_selectBranch);
        driver.element().type(txt_selectBranch , s);
        driver.element().click(btn_selectBranchOption);
        driver.element().click(btn_selectBranch);
        return this;
    }
   /* public ServiceCharge_page SelectServiceBranch() {
        driver.element().click(btn_selectBranchOption);
      //  driver.element().click(btn_closeDropDrown);


        return this;
    }
    */
   /* public ServiceCharge_page SelectServiceAgency(String s) throws InterruptedException {

        driver.element().click(btn_selectAgency);
        driver.element().type(txt_selectAgency , s);
        driver.element().click(btn_selectAgencyOption);

        return this;
    }*/
    public ServiceCharge_page SelectAttribute() {
        driver.element().click(btn_selectAttribute);
        driver.element().click(btn_selectAttributeOption);
        return this;
    }
    public ServiceCharge_page SelectOperator() {
        driver.element().click(btn_selectOperator);
        driver.element().click(btn_selectOperatorOption);
        return this;
    }
    public ServiceCharge_page SelectValue() {
        driver.element().click(btn_searchIcon);
        driver.element().click(btn_selectAll);
        driver.element().click(btn_Add);
        driver.element().click(btn_SaveValue);
        return this;
    }
    public ServiceCharge_page EnterFareType(String s) {
        driver.element().click(btn_SelectFareType);
        driver.element().click(btn_FareTypeOption);
        driver.element().click(btn_SelectAmountType);
        driver.element().click(btn_AmountOption);
        driver.element().click(btn_Value);
        driver.element().type(btn_Value ,s);
        return this;
    }
    public ServiceCharge_page Submit() {


        driver.element().click(btn_SendForApproval);
        return this;
    }
   /* public ServiceCharge_page Search(String s , String s) {


        driver.element().click(btn_SearchCountry);
        driver.element().click(btn_selectCountry);
      //  driver.element().click(btn_SearchBranch);
       // driver.element().type(txt_SearchBranch , s);
      //  driver.element().click(btn_BranchOption);
       // driver.element().click(btn_SearchAgency);
       // driver.element().click(btn_SelectAgency);
        driver.element().click(Rbtn_Both);
        driver.element().click(Btn_SearchInGrid);
        driver.element().click(Btn_Next);

        return this;
    }
    */


    public boolean findServiceInPages(String ServiceChargeName) {
        int maxPages = 20; // safety limit

        for (int i = 0; i < maxPages; i++) {
            // If found in current page, return true
            if (!driver.getDriver().findElements(rowService(ServiceChargeName)).isEmpty()) {
                return true;
            }

            // Check if Next button exists
            if (driver.getDriver().findElements(Btn_Next).isEmpty()) {
                break;
            }

            // Go to next page
            driver.element().scrollToElement(Btn_Next);
            driver.element().click(Btn_Next);

        }

        return false; // not found anywhere
    }
    public void rejectAction(String servicename, String remark) {
        if (!findServiceInPages(servicename)) {
            throw new RuntimeException("Service not found: " + servicename);
        }
//        driver.element().click(actionButton(ServiceChargeName));
        driver.element().click(rejectButton(servicename));
        driver.element().type(RemarksAction, remark);
        driver.element().click(Btn_SubmitAction);
    }

    public void approveAction(String servicename, String remark) {
        if (!findServiceInPages(servicename)) {
            throw new RuntimeException("Service not found: " + servicename);
        }
//        driver.element().click(actionButton(ServiceChargeName));
        driver.element().click(approveButton(servicename));
        driver.element().type(RemarksAction, remark);
        driver.element().click(Btn_SubmitAction);
    }
    public void Edit(String description, String remark) throws InterruptedException {

        By editButton = By.xpath(String.format(
                "//tr[td[normalize-space()='%s']]//i[contains(@class,'pi-pencil')]",
                ServiceName));

        By serviceInTable = By.xpath(String.format(
                "//tr[td[normalize-space()='%s']]",
                ServiceName));

        while (driver.getDriver().findElements(serviceInTable).isEmpty()
                && !driver.getDriver().findElements(Btn_Next).isEmpty()) {

            driver.element().click(Btn_Next);
        }

        if (!driver.getDriver().findElements(serviceInTable).isEmpty()) {
            driver.element().scrollToElement(editButton);
            driver.element().click(editButton);

            Thread.sleep(500);

            driver.element().type(txt_ServiceChargeName, FakerSingleton.PassengerFactory.firstName());
            driver.element().type(txt_ServiceChargeDescription, description);
            driver.element().type(txt_Reamrks, remark)
                    .click(Btn_Approve);
        }
    }
    public ServiceCharge_page searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(txt_SelectValidityFrom);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;

    }

    public ServiceCharge_page searchValidToDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(txt_SelectValidityTo);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;

    }






}
