package AdminPages.RuleEngine.Markup;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.FakerSingleton;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.openqa.selenium.By.xpath;

public class Markup_Page {
    private final SHAFT.GUI.WebDriver driver;
    public static String markupName;
    // Locators
    private final By Lst_CountryPos = By.xpath("//p-dropdown[.//input[@id=\"id-CountryPOS\"]]");
    private final By Lst_Branch = By.xpath("//p-dropdown[.//input[@id=\"id-Branch\"]]");
    private final By txt_MarkupCode = By.xpath("//input[@id=\"id-Markupcode\"]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id='id-Status-Active']]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    private final By Btn_Submit = By.xpath("//button[@type='submit']");
    private final By Btn_RuleEngine = By.xpath("(//a[@class=\"p-element p-ripple p-tabview-nav-link\"])[4]");
    private final By Btn_AddMarkup = By.xpath("//button[@routerlink=\"add\"]");
    /*Add markup*/
    private final By txt_MarkupName = By.xpath("//input[@id=\"id-MarkupName\"]");
    private final By txt_MarkupDisc = By.xpath("//textarea[@placeholder=\"Markup Description\"]");
    private final By Dpick_Validityfrom = By.xpath("//input[@id='id-ValidityPeriodFrom']");
    //  private final By Select_Date = By.xpath("//span[@class=\"p-ripple p-element ng-tns-c52-7 ng-star-inserted\"]");
    private final By Dpick_ValidityTo = By.xpath("//input[@id='id-ValidityPeriodTo']");
    private final By Lst_CountryPosForAdd =By.xpath("//p-multiselect[.//input[@id=\"id-CountryPOS\"]]");
    private final By Lst_BranchForAdd = By.xpath("//p-multiselect[.//input[@id=\"id-Branch\"]]");
    private final By Lst_AgencyForAdd = By.xpath("//p-multiselect[.//input[@id=\"id-Agency\"]]");
    private final By Lst_Attribute = By.xpath("//p-dropdown[@placeholder='Select Attribute']//span[contains(@class,'p-dropdown-label')]");
    private final By Lst_Operator = By.xpath("//p-dropdown[.//input[@placeholder=\"Select Operator\"]]");
    private final By Btn_Search = By.xpath("//button[@class=\"pi pi-search btn\"]");
    private final By txt_SearchData = By.xpath("(//input[@placeholder='Search...' and contains(@class,'p-inputtext')])[1]");
    private final By Btn_AddForAttribute = By.xpath("//button[normalize-space()='Add']");
    private final By Btn_Remove = By.xpath("(//button[@class=\"swap-btn\"])[2]");
    private final By Btn_Save = By.xpath("(//button[@type=\"submit\"])[1]");
    private final By Btn_Cancel = By.xpath("(//button[@class=\"modal-btn\"])[2]");
    private final By Rbtn_All = By.xpath("//p-radiobutton[.//input[@id=\"-All\"]]");
    private final By Rbtn_ByPax = By.xpath("//p-radiobutton[.//input[@id=\"-By Pax\"]]");
    private final By Lst_FareType = By.xpath("(//div[@class='w-full p-dropdown p-component p-dropdown-clearable'])[1]");
    private final By Lst_AmountType = By.xpath("(//div[@class='w-full p-dropdown p-component p-dropdown-clearable'])[1]");
    private final By txt_AmountValue = By.xpath("(//input[@placeholder='0.00'])[1]");
    private final By Btn_Sendforapproval = By.xpath("//button[@type=\"submit\"]");
    private final By Btn_CancelForMarkup = By.xpath("(//button[@type=\"button\"])[7]");
    private final By After = By.xpath("//span[contains(@class,'p-datepicker-next-icon')]");
    private final By Search = By.xpath("//input[contains(@class,'p-multiselect-filter')]");
    private final By txt_Search = By.xpath("//input[contains(@class,'p-multiselect-filter')]");
    private final By Btn_Checkbox = By.xpath("//div[@role='checkbox' and contains(@class,'p-checkbox-box') and @aria-checked='false']");
    private final By Btn_AllAgency = By.xpath("//li[contains(@class,'p-multiselect-item')]//span[normalize-space()='Test Egypt (AGN2)']");
    private final By Btn_CheckBoxSearch = By.xpath("(//p-checkbox[.//input[@value=\"All\"]])[1]");
    By editIcon = By.xpath("//i[contains(@class,'pi-pencil')]");
    By txt_Reamrks = By.xpath("//textarea[@name=\"remarks\"]");
    By Btn_Approve = By.xpath("(//button[@type=\"submit\"])[2]");
    By Reject = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Approve = By.xpath("//i[@class=\"pi pi-thumbs-up\"]");
    By RemarksAction = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_SubmitAction = By.xpath("(//button[@type=\"submit\"])[2]");
    By Btn_last = By.xpath("(//span[@class='last text-xs selected-last-page ng-star-inserted'])[1]");
    public By Table_FirstRow = By.xpath("//table//tbody/tr[1]");
    public By StatusCellInactive = By.xpath("//td[normalize-space()='Inactive']");
    public By StatusCellActive = By.xpath("//td[normalize-space()='Active']");
    public By Btn_Next = By.xpath("//button[contains(@class,'next')]//i[contains(@class,'pi-angle-right')]");
    private By rowMarkup(String markupname){
        return By.xpath("//td[normalize-space()='" + markupname + "']");
    }
    By Year = By.xpath("//button[normalize-space()='2026']");

    private By approveButton(String markupname){
        return By.xpath("//tr[td[normalize-space()='" + markupname + "']]//i[contains(@class,'pi-thumbs-up')]");
    }
    private By rejectButton(String markupname){
        return By.xpath("//tr[td[normalize-space()='" + markupname + "']]//i[contains(@class,'pi-thumbs-down')]");
    }








    public Markup_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    public Markup_Page navigateToRuleEngine(){
        driver.element().click(Btn_RuleEngine);
        return this;
    }

    public void searchMarkup(String Country, String branch) {
        driver.element().select(Lst_CountryPos, Country);
//        driver.element().click(Lst_Branch);
//        By item = By.xpath("//li[normalize-space()='" + branch + "']");
//
//// scroll inside the dropdown container
//        driver.element().scrollToElement(item);
//
//// then click
//        driver.element().click(item);
////        driver.element().type(txt_MarkupCode,markupcode);


    }
    public void goToNextPage() {
        // Scroll to button if needed
        if (!driver.getDriver().findElements(Btn_Next).isEmpty()){
        driver.element().scrollToElement(Btn_Next);

        // Click Next
        driver.element().click(Btn_Next);
        }
    }
    public void goToLastPage() {
        // Wait until Last button is visible and clickable
        driver.element().scrollToElement(Btn_last);

        // Click the Last button
        driver.element().click(Btn_last);
    }
    public void activestatus(){
        driver.element().click(Rbtn_Active);
        driver.element().click(Btn_Submit);
    }
    public void inactivestatus() {
        driver.element().click(Rbtn_Inactive);
        driver.element().click(Btn_Submit);

        if (!driver.getDriver().findElements(Btn_last).isEmpty()) {
            driver.element().click(Btn_last);
        }
    }
    public void bothstatus(){
        driver.element().click(Rbtn_Both);
        driver.element().click(Btn_Submit);
        driver.element().click(Btn_last);
    }

    public boolean findMarkupInPages(){
        int maxPages = 20; // safety limit

        for(int i = 0; i < maxPages; i++){
            // If found in current page, return true
            if(driver.element().isElementDisplayed(rowMarkup(markupName))){
                return true;
            }

            // If Next button disabled -> no more pages
            if(!driver.element().isElementDisplayed(Btn_Next)){
                break;
            }

            // Go to next page
            driver.element().scrollToElement(Btn_Next);
            driver.element().click(Btn_Next);

        }

        return false; // not found anywhere
    }

    public void rejectAction(String remark){
        if(!findMarkupInPages()){
            throw new RuntimeException("Markup not found: " + markupName);}
//
        driver.element().click(rejectButton(markupName));
        driver.element().type(RemarksAction,remark);
        driver.element().click(Btn_SubmitAction);
    }
    public void approveAction(String remark){
        if(!findMarkupInPages()){
            throw new RuntimeException("Markup not found: " + markupName);
        }
//
        driver.element().click(approveButton(markupName));
        driver.element().type(RemarksAction,remark);
        driver.element().click(Btn_SubmitAction);
    }

    public void addMarkup(String MarkupDis,String year,String month,String From, String year2,String month2,String From1,String Country,String Branch,String Agency,String Attribute,String Operator ,String Value,String Faretype,String Amounttype,String AmountValue) throws InterruptedException {
        driver.element().click(Btn_AddMarkup);
        driver.element().type(txt_MarkupName, FakerSingleton.PassengerFactory.firstName())
                .type(txt_MarkupDisc,MarkupDis);
        markupName = driver.element().getText(txt_MarkupName);
        driver.element().click(Dpick_Validityfrom);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        driver.element().click(Dpick_ValidityTo);

        driver.element().click(Year);
        By year3 = By.xpath("//span[normalize-space()='" + year2 + "']");
        driver.element().click(year3);
        By month3 = By.xpath("//span[normalize-space()='" + month2 + "']");
        driver.element().click(month3);
        By Day1 = By.xpath(String.format("(//span[text()='%s'])[1]", From1));
        driver.element().click(Day1);

        driver.element().click(Lst_CountryPosForAdd);
        Thread.sleep(3000);
        driver.element().type(Search,Country);
        Thread.sleep(3000);
        driver.element().click(Btn_Checkbox);
//                  .click(Lst_CountryPosForAdd);
//                        Thread.sleep(3000);
        driver.element().click(Lst_BranchForAdd);
        Thread.sleep(5000);
        driver.element().type(txt_Search,Branch);
        Thread.sleep(3000);
        driver.element().click(Btn_Checkbox)
//                .click(Lst_BranchForAdd)
                .click(Lst_AgencyForAdd);
        Thread.sleep(3000);
        driver.element().type(Search,Agency);
        Thread.sleep(3000);
        driver.element().click(Btn_AllAgency);
        driver.element().click(Lst_Attribute);
        By item = By.xpath("//li[normalize-space()='" + Attribute + "']");

// scroll inside the dropdown container
        driver.element().scrollToElement(item);

// then click
        driver.element().click(item);
        driver.element().select(Lst_Operator,Operator)
                .click(Btn_Search)
                .select(txt_SearchData,Value).click(Btn_CheckBoxSearch)
                .click(Btn_AddForAttribute)
                .click(Btn_Save)
                .select(Lst_FareType,Faretype)
                .select(Lst_AmountType,Amounttype)
                .type(txt_AmountValue,AmountValue)
                .click(Btn_Sendforapproval);
        driver.element().select(Lst_CountryPos, Country);
//        driver.element().select(Lst_Branch, Branch);
        driver.element().click(Rbtn_Inactive);
        driver.element().click(Btn_Submit);

    }
    public void updateMarkup(String name,String MarkupDis, String year,String month,String From, String year2,String month2,String From1, String Amounttype,
                             String AmountValue, String Remarks) throws InterruptedException {

        // 1. Build dynamic row locator
        By dynamicRow = By.xpath(String.format("//tr[td[normalize-space()='%s']]", name));

        // 2. Build dynamic edit icon inside that row
        By editButton = By.xpath(String.format(
                "//tr[td[normalize-space()='%s']]//i[contains(@class,'pi-pencil')]", markupName));

        // 3. Paginator next button
        By nextButton = By.xpath("//button[.//i[contains(@class,'pi-angle-right')]]");


        // 5. Click the correct edit button for that row
        driver.element().scrollToElement(editButton);
        driver.element().click(editButton);
        Thread.sleep(3000);

        // 6. Update fields
        driver.element().type(txt_MarkupName, FakerSingleton.PassengerFactory.firstName());
        markupName = driver.element().getText(txt_MarkupName);

        driver.element().clear(txt_MarkupDisc);
        driver.element().type(txt_MarkupDisc, MarkupDis);

        // Validity From
        driver.element().click(Dpick_Validityfrom);
        By yearr = By.xpath("//button[text()=" + year + "]");
        driver.element().click(yearr);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        // Validity To
        driver.element().click(Dpick_ValidityTo);
        By yearr1 = By.xpath("//button[text()=" + year2 + "]");
        driver.element().click(yearr1);
        By year3 = By.xpath("//span[normalize-space()='" + year2 + "']");
        driver.element().click(year3);
        By month3 = By.xpath("//span[normalize-space()='" + month2 + "']");
        driver.element().click(month3);
        By Day1 = By.xpath(String.format("(//span[text()='%s'])[1]", From1));
        driver.element().click(Day1);




        // Dropdowns
//        driver.element().select(Lst_FareType, Faretype);
//        driver.element().select(Lst_AmountType, Amounttype);
//
//        // Amount + Remarks
//        driver.element().clear(txt_AmountValue);
//        driver.element().type(txt_AmountValue, AmountValue);

        driver.element().type(txt_Reamrks, Remarks);

        // Approve/Update button
        driver.element().click(Btn_Approve);
    }


}
