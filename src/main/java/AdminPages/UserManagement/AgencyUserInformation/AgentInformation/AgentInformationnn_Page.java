package AdminPages.UserManagement.AgencyUserInformation.AgentInformation;

import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class AgentInformationnn_Page {
    //constant
    private final SoftAssert softAssert;

    public AgentInformationnn_Page(SHAFT.GUI.WebDriver driver) {

        this.softAssert = new SoftAssert();

        this.driver = driver;
    }
    static SHAFT.GUI.WebDriver driver;
    public final By Txt_Username = By.id("id-Username");
    public final By Txt_Password = By.id("id-Password");
    public final By Btn_LogINButton = By.xpath("// button[@loadingicon=\"pi pi-spin pi-spinner\"]");

    public AgentInformationnn_Page EnterUserName(String username) {
        driver.element().type(Txt_Username, username);
        return this;
    }
    public AgentInformationnn_Page EnterPassword(String password) {
        driver.element().type(Txt_Password, password);
        return this;
    }
    public AgentInformationnn_Page ClickOnLoginButton() {
        driver.element().click(Btn_LogINButton);
        return this;
    }

    //constant

    public final By SearchAgencyName = By.xpath("//input[@id='id-Agency']");
    public final By SelectTextFromTable = By.xpath("//td[normalize-space()='Essam Saady']");
    public final By SelectInvalidTextPhone = By.xpath("//span[normalize-space()='Please enter valid phone number']");
    public final By SelectInvalidTextEmail = By.xpath("//span[normalize-space()='Please enter valid email']");
    public final By STR = By.xpath("//span[@class='fg-error has-error']");
    public final By SelectNoDataFound = By.xpath("//td[@class='message']");
    public final By SelectEditbutton = By.xpath("//i[@class=\"pi pi-pencil\"]");

    private final By Fullname = By.xpath("//input[@id='id-Fullname']");
    private final By Phone = By.xpath("//input[@id='phone number']");
    private final By Email = By.xpath("//input[@id='id-Email']");
    private final By Search = By.cssSelector("button[type='submit']");
    private final By UserManagement = By.linkText("User Management");
    private final By AgencyUserInfo = By.xpath("//a[@class=\"ng-star-inserted\"]");
    private final By AGENTINFORMATION = By.xpath("//a[@class=\"active-link\"]");
    By Select = By.xpath("//li[@role='option']");


    public AgentInformationnn_Page NavigateToAgencyInfo()
    {
        driver.element().click(UserManagement);
        return this;

    }
    public AgentInformationnn_Page OpenAgentInformation()
    {
        driver.element().click(AgencyUserInfo);
        return this;

    }
    public AgentInformationnn_Page OpenAGENTINFORMATION()
    {
        driver.element().click(AGENTINFORMATION);
        return this;

    }
    public AgentInformationnn_Page SelectAgency(String Selection) throws InterruptedException {
        driver.element().type(SearchAgencyName,Selection);
        Thread.sleep(2000);
        driver.element().click(Select);
        return this;
    }

    public AgentInformationnn_Page FullName(String Namee) {
        driver.element().type(Fullname, Namee);
        return this;
    }
    public AgentInformationnn_Page ClickonSearch() {
        driver.element().click(Search);
        return this;
    }
    public AgentInformationnn_Page EnterAgentPhoneNumber(String Phone1) {
        driver.element().type(Phone, Phone1);
        return this;
    }
    public AgentInformationnn_Page EnterAgentEmail(String Emaiil) {
        driver.element().type(Email, Emaiil);
        return this;
    }
    public AgentInformationnn_Page Reload() {
        driver.browser().navigateToURL("http://192.168.1.70/management/agency/agency-info");
        return this;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// View
    public final By SelectTextFrompopup=By.xpath("//span[normalize-space()='Essam Saady']");
    public final By Viewbutton = By.xpath("(//i[@class='pi pi-eye'])[1]");
    private final By Closebutton = By.xpath("/html/body/ndc-root/ndc-layout/div/div[3]/div[1]/div/ndc-agent-information/p-dialog/div/div/div[2]/div/button");
    private final By Unlock = By.xpath("//button[@class='p-element p-button p-component']");
    private final By Yes = By.xpath("//span[normalize-space()='Yes']");
    private final By No = By.xpath("//span[normalize-space()='No']");
    public final By SuccessfullAlert = By.xpath("(//div[@class='ng-tns-c7-49 ng-star-inserted ng-trigger ng-trigger-flyInOut ngx-toastr toast-success'])[1]");
    public final By SelectInvalidTextEmailForView = By.xpath("//span[contains(text(), 'Please enter a valid email')]");
// (//div[@class='ng-tns-c7-98 ng-star-inserted ng-trigger ng-trigger-flyInOut ngx-toastr toast-success'])[1]
    public AgentInformationnn_Page ClickonClose() {
        driver.element().click(Closebutton);
        return this;
    }
    public AgentInformationnn_Page ClickonViewAndYes() {
        driver.element().click(Unlock);
        driver.element().click(Yes);
        return this;
    }

    public AgentInformationnn_Page ClickonViewAndNo() {
        driver.element().click(Unlock);
        driver.element().click(No);

        return this;
    }
    protected AgentInformationnn_Page agentInformation1;
    private final By AngelDownForLogOut=By.xpath("//i[@style=\"font-size: 1rem;\"]");
    private final By LogOutButton = By.xpath("//span[@class=\"p-menuitem-icon pi pi-sign-out ng-star-inserted\"]");


    public AgentInformationnn_Page LoginAsSuperAdmin() {

        driver.element().click(AngelDownForLogOut);
        driver.element().click(LogOutButton);

        driver.element().type(Txt_Username, "odeysysadmin");
        driver.element().type(Txt_Password, "qqE6)Cxp6>B8");
        driver.element().click(Btn_LogINButton);
        agentInformation1 = new AgentInformationnn_Page(driver);
        agentInformation1.NavigateToAgencyInfo();
        agentInformation1.OpenAgentInformation();
        agentInformation1.OpenAGENTINFORMATION();
        return this;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Portal
    private final By Txt_UsernamePortal = By.xpath("//input[@id=\"agencyCodeMain\"]");
    private final By Txt_emailPortal = By.xpath("//input[@id=\"userAlias\"]");
    private final By Txt_PasswordPortal = By.xpath("//input[@id=\"password_password\"]");
    private final By Btn_LoginPortal = By.xpath("//input[@type=\"submit\"]");
    public final By Notification = By.xpath("//img[@style=\"margin-right: 12px;top: 0px;margin-left: -6px;\"]");
    public final By ProfileManagement = By.xpath("//img[@src=\"../static/images/icons/profile1.png\"]");
    public final By FullNamePortal = By.xpath("//input[@id=\"firstName\"]");
    public final By SelectTextFromMobilePortal = By.xpath("//span[contains(text(),'010')]");
    String urlforportal = "http://192.168.1.70:8080/odeysysportal/login/loginForm";
    public String urlfordashboardportal ="http://192.168.1.70:8080/odeysysportal/dashboard/dashboardHome";

    public AgentInformationnn_Page EnterNDCPortal() {
        driver.browser().navigateToURL(urlforportal);
        driver.element().type(Txt_UsernamePortal,"AGN2");
        driver.element().type(Txt_emailPortal,"e.saady@ndceg.com");
        driver.element().type(Txt_PasswordPortal,"qqE6)Cxp6>B8");
        driver.element().click(Btn_LoginPortal);
        return this;
    }
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Edit
    public final  By Btn_Editbutton = By.xpath("(//i[@class='pi pi-pencil'])[1]");
    public final By Txt_EditAgencyOwner = By.xpath("//tr[1]//i[@class=\"pi pi-pencil\"]");
    public final By Txt_EditFullName = By.xpath("//input[@id=\"id-FullName\"]");
    private final By Txt_EditEmail = By.xpath("//input[@id='id-Email']");
    private final By Txt_EditPhoneNumber = By.xpath("//input[@id=\"ContactNo\"]");
    public final By Btn_UpdateButton = By.xpath("//button[@type='submit']");
    private final By Btn_CancelButton =By.xpath("//button[@type=\"reset\"]");
    public final By InvalidEmail =By.xpath("//span[contains(text(), 'Please enter a valid email')]");
    public final By InvalidPhone =By.xpath("//span[@class='fg-error has-error']");
    public final By Btn_SearchKey = By.xpath("//div[@class='iti__selected-flag dropdown-toggle']");
    public final By SelectTextFromKey = By.xpath("//span[contains(text(),'Egypt')]");
    public final By EditFullName = By.xpath("(//span[@class='p-input-icon-right'])[1]");
//    public String ActualCancelButtonIsClickable(){
//        String S = ElementActions.getInstance().getText(By.xpath("//td[contains(text(),'safy')]"));
//        return S;
//    }
//    public String ActualReflectionAfterSuccessfullyUpdate(){
//        String S = ElementActions.getInstance().getText(By.xpath("//span[contains(text(),'010')]"));
//        return S;
//    }
    public AgentInformationnn_Page EnterEmail (String Check)
    {
        driver.element().type(Txt_EditEmail,Check);
        return this;
    }
    public AgentInformationnn_Page EnterPhoneNumber (String Check)
    {
        driver.element().type(Txt_EditPhoneNumber,Check);
        return this;
    }
    public AgentInformationnn_Page ClickOnCancel(){
        driver.element().click(Btn_CancelButton);
        return this;
    }
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Action
    public final By Rbtn_ActiveButtonForAgencyOwner = By.xpath("(//i[@class=\"pi pi-circle-fill\"])[1]");
    public final By Rbtn_ActiveButtonForAgencyOwner2 = By.xpath("//i[@class=\"pi pi-circle\"]");
    //tr[2]//div[3]//i[@class="pi pi-circle"]
    public final By Rbtn_ActiveButtonForAgentOrAdmin = By.xpath("(//i[@class=\"pi pi-circle-fill\"])[2]");
    public final By Rbtn_ActiveButtonForAgentOrAdmin2 = By.xpath("//tbody/tr[1]/td[6]/div[1]/div[2]/i[1]");

    public AgentInformationnn_Page ClickOnActiveButtonAO(){
        driver.element().click(Rbtn_ActiveButtonForAgencyOwner);
        return this;
    }
    public AgentInformationnn_Page ClickOnActiveButtonAO2(){
        driver.element().click(Rbtn_ActiveButtonForAgencyOwner2);
        return this;
    }
    public AgentInformationnn_Page ClickOnActiveButtonAdmin(){
        driver.element().click(Rbtn_ActiveButtonForAgentOrAdmin);
        return this;
    }
    public AgentInformationnn_Page ClickOnActiveButtonAdmin2(){
        driver.element().click(Rbtn_ActiveButtonForAgentOrAdmin2);
        return this;
    }

}