package AdminPages.RuleEngine.FareRules;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class FareRules_page {
    private SHAFT.GUI.WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private SHAFT.TestData.JSON testData;

    public FareRules_page(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
        this.testData = new SHAFT.TestData.JSON("SearchBooking.json");
    }

       ////Add Fare Rule/////////
    private final By btn_RuleEngine = By.xpath("//a[@href='/rule-engine']");
    //private final By btn_FareRule = By.xpath("//a[@href='/rule-engine/rule-master']");
    private final By btn_FareRule = By.xpath("//a[@href='/odeysysadmin/Rules/searchFareRuleMasterForm']");
    private final By btn_AddFareRule = By.xpath("//button[@routerlink='add']");
    private final By lst_addSupplier = By.xpath("//div[@role='button']");
    private final By btn_selectSupplier = By.xpath("//li[@aria-label='AirArbia']");
    private final By txt_selectFromDate = By.xpath("//input[@id='id-ValidityFrom']");
    private final By txt_selectToDate = By.xpath("//input[@id='id-ValidityTo']");
    private final By btn_ToDate = By.xpath("(//button[@type='button'][@tabindex='0'])[2]");
    private final By btn_AddCountryFrom = By.xpath("(//span[@class='dropdown-arrow'])[1]");
    private final By btn_AddCountryTo = By.xpath("(//span[@class='dropdown-arrow'])[2]");
    private final By btn_SelectCountryFrom = By.xpath("(//input[@type='checkbox'])[1]");
    private final By btn_SelectCountryTo = By.xpath("(//input[@type='checkbox'])[1]");
    private final By txt_FareName = By.xpath("//input[@id='id-fareRulename']");
    private final By txt_FareDescription = By.xpath("//div[@class='ql-editor ql-blank']");
    private final By btn_submit = By.xpath("//button[@type='submit']");

    ////search fare rule/////
    private final By btn_Inactive = By.xpath("(//div[@class='p-radiobutton-box'])[1]");
    private final By btn_AddSupplierType = By.xpath("(//div[@role='button'])[1]");
    private final By btn_SelectSupplierType = By.xpath("//li[@aria-label='AirArbia']");
    private final By btn_SearchInGrid= By.xpath("//button[@type='submit']");
    private final By btn_approve= By.xpath("(//div[@class='action exception-buttons ng-star-inserted'])[1]");
    //////remark///////
    private final By txt_remark= By.xpath("//textarea");
    private final By btn_SubmitRemark= By.xpath("(//button[@type='submit'])[2]");
    By Year = By.xpath("//button[normalize-space()='2026']");

    public FareRules_page navigateToRuleEngine(){
        driver.element().click(btn_RuleEngine);
        return this;
    }

    public FareRules_page navigateToFareRule(){
        driver.element().click(btn_FareRule);
        return this;
    }

    public FareRules_page ClickAtAddFareRule(){
        driver.element().click(btn_AddFareRule);
        return this;
    }

    public FareRules_page SelectSuplier(){
        driver.element().click(lst_addSupplier);
        driver.element().click(btn_selectSupplier);
        return this;
    }

    public FareRules_page SelectValidDate(String s1 , String s2){
        driver.element().type(txt_selectFromDate,s1);
        driver.element().type(txt_selectToDate,s2);
        driver.element().click(btn_ToDate);
        return this;
    }

    public FareRules_page SelectCountry(){
        driver.element().click(btn_AddCountryFrom);
        driver.element().click(btn_SelectCountryFrom);
        driver.element().click(btn_AddCountryTo);
        driver.element().click(btn_SelectCountryTo);
        return this;
    }

    public FareRules_page EnterFareName(String s){
        driver.element().type(txt_FareName,s);
        return this;
    }

    public FareRules_page EnterFareDescription(String s){
        driver.element().type(txt_FareDescription,s);
        return this;
    }

    public FareRules_page ClickSubmit() throws InterruptedException {
        driver.element().click(btn_submit);
        Thread.sleep(5000);
        return this;
    }
    ///////////////////////////////////////////////////////search////////////////////////////////////
    public FareRules_page ClickInactiveButton() {
        driver.element().click(btn_Inactive);

        return this;
    }

    public FareRules_page SelectSupplierType(){
        driver.element().click(btn_AddSupplierType);
        driver.element().click(btn_SelectSupplierType);
        return this;
    }

    public FareRules_page ClickSearch() throws InterruptedException {
        driver.element().click(btn_SearchInGrid);
        Thread.sleep(2000);
        return this;
    }
    public FareRules_page ApproveFareRule(){
        driver.element().click(btn_approve);
        return this;
    }

    public FareRules_page EnterRemarkText(String s){
        driver.element().type(txt_remark,s);
        return this;
    }
    public FareRules_page SubmitTheRemark(){
        driver.element().click(btn_SubmitRemark);
        return this;
    }
    public FareRules_page searchValidFromDate(String From, String year, String month) throws InterruptedException {
        driver.element().click(txt_selectFromDate);
        driver.element().click(Year);
    By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
    By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
    By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;

}

public FareRules_page searchValidToDate(String From, String year, String month) throws InterruptedException {

    driver.element().click(txt_selectToDate);
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
