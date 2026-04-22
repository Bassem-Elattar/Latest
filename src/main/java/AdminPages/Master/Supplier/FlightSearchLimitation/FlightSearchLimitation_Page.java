package AdminPages.Master.Supplier.FlightSearchLimitation;

import com.shaft.driver.SHAFT;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class FlightSearchLimitation_Page {
    //constant
    private final SoftAssert softAssert;

    public FlightSearchLimitation_Page(SHAFT.GUI.WebDriver driver) {

        this.softAssert = new SoftAssert();

        this.driver = driver;
    }
    static SHAFT.GUI.WebDriver driver;
    public final By Txt_Username = By.id("id-Username");
    public final By Txt_Password = By.id("id-Password");
    public final By Btn_LogINButton = By.xpath("// button[@loadingicon=\"pi pi-spin pi-spinner\"]");

    private final By Btn_Master = By.xpath("//a[@href=\"/master\"]");
    private final By Btn_Supplier = By.xpath("(//a[@class=\"ng-star-inserted\"])[2]");
    private final By Btn_FlightSearchLimitationButton = By.xpath("(//a[@href=\"/master/supplier/flight-search-limitation\"])[1]");
    public static By LogInNavigateToUrl() {
        driver.browser().maximizeWindow();
        String url = "http://192.168.1.90/auth/login";
        driver.browser().navigateToURL(url);
        driver.browser().maximizeWindow();  // Initialize the driver here

        return null;
    }

    public FlightSearchLimitation_Page EnterUserName(String username) {
        driver.element().type(Txt_Username, username);
        return this;
    }
    public FlightSearchLimitation_Page EnterPassword(String password) {
        driver.element().type(Txt_Password, password);
        return this;
    }
    public FlightSearchLimitation_Page ClickOnLoginButton() {
        driver.element().click(Btn_LogINButton);
        return this;
    }

    //constant



    public FlightSearchLimitation_Page NavigateToMaster()
    {
        driver.element().click(Btn_Master);
        return this;

    }
    public FlightSearchLimitation_Page OpenSupplier()
    {
        driver.element().click(Btn_Supplier);
        return this;

    }
    public FlightSearchLimitation_Page OpenFlightSearchLimitation()
    {
        driver.element().click(Btn_FlightSearchLimitationButton);
        return this;

    }
    public FlightSearchLimitation_Page Reload() {
        driver.browser().navigateToURL("http://192.168.1.90/master/supplier/flight-search-limitation");
        driver.element().click(Btn_Master);
        driver.element().click(Btn_Supplier);
        driver.element().click(Btn_FlightSearchLimitationButton);

        return this;
    }
    public final By Lst_ClickOnSupplier = By.xpath("//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted']");
    public final By Txt_NameOfSupplier = By.xpath("//input[@id='id-CredentialName']");
    public final By Rbtn_ActiveButton = By.xpath("(//p-radiobutton//div//span[@class=\"p-radiobutton-icon\"])[2]");
    public final By Rbtn_InactiveButton = By.xpath("(//div[@class='p-radiobutton-box'])[1]");
    public final By Rbtn_BothButton = By.xpath("(//div[@class='p-radiobutton-box'])[2]");
    public final By Btn_SearchButton = By.xpath("//button[@type='submit']");
    public final By Lst_SelectTextFromTable = By.xpath("(//td[@class='ng-star-inserted'][normalize-space()='AirArabiaEgypt'])[2]");
    public final By Lst_SelectNoData = By.xpath("//td[contains(text(),'No')]");
    public final By Btn_EditButton = By.xpath("(//i[@class=\"pi pi-pencil\"])[1]");
    public final By Pagination = By.xpath("//span[@class=\"p-element p-dropdown-label p-inputtext ng-star-inserted\"]");
    public final By pagination20 = By.xpath("(//div//span[@class=\"ng-star-inserted\"])[2]");
    public final By SuppliersList = By.xpath("//li[@aria-label='AirArabiaEgypt']");

    public FlightSearchLimitation_Page ClickAndTypeName(String Name){
        driver.element().type(Txt_NameOfSupplier, Name);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnSupplierAndChooseIndex(String Check){
        driver.element().click(Lst_ClickOnSupplier);
        driver.element().click(SuppliersList);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnActive(){
        driver.element().click(Rbtn_ActiveButton);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnInactive(){
        driver.element().click(Rbtn_InactiveButton);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnBoth(){
        driver.element().click(Rbtn_BothButton);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnSearch(){
        driver.element().click(Btn_SearchButton);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnPagination(){
        driver.element().click(Pagination);
        driver.element().click(pagination20);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnEditButton(){
        driver.element().click(Btn_EditButton);
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// View


    protected FlightSearchLimitation_Page agentInformation1;
    private final By AngelDownForLogOut=By.xpath("//i[@style=\"font-size: 1rem;\"]");
    private final By LogOutButton = By.xpath("//span[@class=\"p-menuitem-icon pi pi-sign-out ng-star-inserted\"]");


    public FlightSearchLimitation_Page LoginAsSuperAdmin() {

        driver.element().click(AngelDownForLogOut);
        driver.element().click(LogOutButton);

        driver.element().type(Txt_Username, "odeysysadmin");
        driver.element().type(Txt_Password, "qqE6)Cxp6>B8");
        driver.element().click(Btn_LogINButton);
        agentInformation1 = new FlightSearchLimitation_Page(driver);
        agentInformation1.NavigateToMaster();
        agentInformation1.OpenSupplier();
        agentInformation1.OpenFlightSearchLimitation();
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Portal
    private final By UsernamePortal = By.xpath("//input[@id=\"agencyCodeMain\"]");
    private final By emailPortal = By.xpath("//input[@id=\"userAlias\"]");
    private final By PasswordPortal = By.xpath("//input[@id=\"password_password\"]");
    private final By LoginPortal = By.xpath("//input[@type=\"submit\"]");
    public final By Notification = By.xpath("//img[@style=\"margin-right: 12px;top: 0px;margin-left: -6px;\"]");
    public final By ProfileManagement = By.xpath("//img[@src=\"../static/images/icons/profile1.png\"]");
    public final By FullNamePortal = By.xpath("//input[@id=\"firstName\"]");
    public final By SelectTextFromMobilePortal = By.xpath("//span[@class=\"ng-binding ng-scope\"]");
    String urlforportal = "http://192.168.1.90:8080/odeysysportal/login/loginForm";
    public String urlfordashboardportal ="http://192.168.1.90:8080/odeysysportal/dashboard/dashboardHome";
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///Edit
    public final By EditSupplierName = By.xpath("//input[@placeholder=\"Supplier Name\"]");
    public final By EditSupplierCredentials = By.xpath("//input[@placeholder=\"Supplier Credential\"]");
    public final By LimitSupplier = By.xpath("//p-dropdown[@class=\"p-element p-inputwrapper fg-input " +
            "ng-untouched ng-pristine ng-valid ng-star-inserted p-inputwrapper-filled\"]");
    public final By ApproveButton = By.xpath("//button[@type=\"submit\"]");
    public final By CancelButton = By.xpath("//button[@type=\"reset\"]");
    public final By SuccessfullAlert = By.xpath("//div[@role=\"alert\"]");
    public final By GetLimit = By.xpath("(//div//table//tbody//tr//td)[3]");
    private final By SupplierCredential = By.xpath("//a[@href='/master/supplier/supplier-credentials']");
    private final By LimitSupplierInSupplierCredential = By.xpath("(//p-dropdown[@class=\"p-element p-inputwrap" +
            "per fg-input ng-untouched ng-pristine ng-star-inserted p-inputwrapper-filled ng-valid\"])[3]");
    private final By UpdateButtonInSupplierCredential = By.xpath("//button[@type=\"submit\"]");
    private final By SupplierLimit = By.xpath("//li[@aria-label='2']");
    By SupplierSearchLimit = By.xpath("//*[@id=\"pr_id_96_label\"]");

    public FlightSearchLimitation_Page ClickonLimitDropDown()
    {
        driver.element().click(LimitSupplier);
        driver.element().click(SupplierLimit);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnUpdate(){
        driver.element().click(ApproveButton);
        return this;
    }

    public FlightSearchLimitation_Page ClickOnCancel()
    {
        driver.element().click(CancelButton);
        return this;
    }

    public FlightSearchLimitation_Page NavigateToSupplierCredential()
    {
        driver.element().click(SupplierCredential);
        return this;

    }

    public FlightSearchLimitation_Page ClickOnLimitOnSupplierCredentialAndChoose(String Check){
        driver.element().click(LimitSupplierInSupplierCredential);
        final By SelectIndex = By.xpath("(//p-dropdown//div//ul//p-dropdownitem[@class=\"p-element ng-star-inserted\"])["+Check+"]");
        driver.element().click(SelectIndex);
        return this;

    }

    public FlightSearchLimitation_Page ClickOnSaveInSupplierCredential(){
        driver.element().click(UpdateButtonInSupplierCredential);
        return this;
    }
}