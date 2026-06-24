package PortalPages.Settings;
import AdminPages.DashBoard.DashBoard_Page;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import utilities.FileUploadUtil;

public class ProfileManagemet {
    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private SoftAssert softAssert = new SoftAssert();


    public ProfileManagemet(SHAFT.GUI.WebDriver driver){
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("profilemanagement.json");
    }
    private final By Settings = By.xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/tilde-theme-header/header/div/div[2]/div[2]/button[2]");
    private final By Profilemanagement =    By.xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-settings/div/div/aside/ndc-settings-navbar/nav/div[2]/section[2]/div/a[2]");
    // ================= Add New Agent =================
    private final By AddNewAgent = By.xpath("//button[.//span[text()='Add New Agent']]");
    private final By FullName = By.xpath("//*[@id=\"id-FullName\"]");
    private final By Position = By.xpath("(//ndc-fg-dropdown-input//p-dropdown)[1]");
    private final By POSITION_Admin = By.cssSelector("li[aria-label='Admin']");
    private final By POSITION_Agent = By.cssSelector("li[aria-label='Agent']");
    private final By Contact_No = By.xpath("(//ngx-intl-tel-input//input)[1]");
    private final By Emaill = By.xpath("//*[@id=\"id\"]");
    private final By Date_of_Joining = By.xpath("//*[@id=\"id-DateofJoining\"]");
    private final By Address = By.xpath("(//ndc-fg-text-area-input//textarea)[1]");
    private final By Upload_Document = By.xpath("//ndc-fg-file-input//button");
    private final By Save = By.xpath("//button[contains(.,'Save')]");
    // ================= Travel Agent Information =================
    private final By Full_Name_search = By.xpath("//input[@id=\"id-FullName\"]");
    private final By Contact_No_search =By.xpath("//*[@id=\"Contact No\"]");
    private final By Email_ID =By.xpath("//*[@id=\"id-EmailID\"]");
    private final By Reset = By.xpath("(//ndc-profile-management//form//button)[1]");
    private final By Search = By.xpath("(//ndc-profile-management//form//button)[2]");
    private final By Action_edit = By.xpath("(//i[contains(@class,'pi-pencil')])[1]");
    private final By Action_active = By.xpath("(//i[contains(@class,'pi-circle-fill')])[1]");
    // ================= verify =================
    private final By Verify_AgentName = By.xpath("(//tr[contains(@class,'ng-star-inserted')]/td[1])[1]");
    private final By Verify_ContactNo = By.xpath("(//tr[contains(@class,'ng-star-inserted')]/td[2])[1]");
    private final By Verify_EmailID = By.xpath("(//tr[contains(@class,'ng-star-inserted')]/td[3])[1]");


    public  ProfileManagemet click_Settings (){
        driver.element().click(Settings);
        return this;
    }
    public  ProfileManagemet click_Profilemanagement (){
        driver.element().click(Profilemanagement);
        return this;
    }


    // ================= Add New Agent Action =================
    public  ProfileManagemet click_add_new_agent (){
        driver.element().click(AddNewAgent);
        return this;
    }

    public ProfileManagemet type_full_name (){
        driver.element().type(FullName, testData.getTestData("fullname"));
        return this;
    }
    public ProfileManagemet SELECT_POSITION (){
        driver.element().click(Position);
        return this;
    }
    public ProfileManagemet SELECT_POSITION_ADMIN (){
        driver.element().click(POSITION_Admin);
        return this;
    }
    public ProfileManagemet SELECT_POSITION_AGENT (){
        driver.element().click(POSITION_Agent);
        return this;
    }
    public ProfileManagemet TYPE_CONTACT_NO (){
        driver.element().type(Contact_No,testData.getTestData("ContactNo"));
        return this;
    }


    public ProfileManagemet TYPE_EMAIL(String email){
        driver.element().type(Emaill, email);
        return this;
    }
    public ProfileManagemet TYPE_Date_of_Joining (){
        driver.element().type(Date_of_Joining,testData.getTestData("Date_of_Joining"));
        return this;
    }

    public ProfileManagemet TYPE_Address() {

        driver.element().isElementClickable(Address);

        driver.element().scrollToElement(Address);

        driver.element().clear(Address);

        driver.element().type(Address, testData.getTestData("Address"));

        return this;
    }

    public ProfileManagemet UploadPDF(String filePath) {

        driver.getDriver()
                .findElement(By.xpath("//input[@type='file']"))
                .sendKeys(filePath);

        return this;
    }

    public ProfileManagemet CLICK_SAVE() {


        ((JavascriptExecutor) driver.getDriver())
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");


        driver.browser().alert();

        driver.element().isElementClickable(Save);

        driver.element().click(Save);

        return this;
    }


// ================= Travel Agent Information Action =================

    public ProfileManagemet type_full_name_search () throws InterruptedException {
      /*  driver.element().click(Full_Name_search);
        Thread.sleep(30000);*/
        driver.element().type(Full_Name_search, testData.getTestData("fullname"));
       // Thread.sleep(30000);
        return this;
    }
    public ProfileManagemet type_Contact_No_search (){
        driver.element().type(Contact_No_search, testData.getTestData("ContactNo"));
        return this;
    }
    public ProfileManagemet type_Email_ID (){
        driver.element().type(Email_ID, testData.getTestData("EMAIL"));
        return this;
    }
    public ProfileManagemet CLICK_Search(){
        driver.element().click(Search);
        return this;
    }
    public ProfileManagemet CLICK_Reset (){
        driver.element().click(Reset);
        return this;
    }
    public ProfileManagemet CLICK_Action_edit (){
        driver.element().click(Action_edit);
        driver.element().type(FullName, testData.getTestData("fullnameedit"));
        return this;
    }
    public ProfileManagemet CLICK_Action_active (){
        driver.element().click(Action_active);
        return this;
    }

    // ================= verify Action =================
    public ProfileManagemet Verify_AgentName_View () {

        softAssert.assertEquals(driver.element().getText(Verify_AgentName), testData.getTestData("Verify_AgentName_View"));
        softAssert.assertAll();
        return this;
    }

    public ProfileManagemet Verify_ContactNo_View () {

        softAssert.assertEquals(driver.element().getText(Verify_ContactNo), testData.getTestData("Verify_ContactNo_View"));
        softAssert.assertAll();
        return this;
    }


    public ProfileManagemet Verify_EmailID_View () {

        softAssert.assertEquals(driver.element().getText(Verify_EmailID), testData.getTestData("Verify_EmailID_View"));
        softAssert.assertAll();
        return this;
    }











}
