package PortalPages.Settings;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.asserts.SoftAssert;

public class ProfileManagemet {
    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private SoftAssert softAssert = new SoftAssert();


    public ProfileManagemet(SHAFT.GUI.WebDriver driver){
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("profilemanagement.json");
    }
    private final By Btn_Settings = By.xpath("(//tilde-theme-header//button)[2]");
    private final By Btn_Profilemanagement =    By.xpath("//ndc-settings-navbar//section[2]//a[2]");
    // ================= Add New Agent =================
    private final By Btn_AddNewAgent = By.xpath("//button[.//span[text()='Add New Agent']]");
    private final By Txt_FullName = By.xpath("//*[@id=\"id-FullName\"]");
    private final By Lst_Position = By.xpath("(//ndc-fg-dropdown-input//p-dropdown)[1]");
    private final By Btn_POSITION_Admin = By.cssSelector("li[aria-label='Admin']");
    private final By BtnPOSITION_Agent = By.cssSelector("li[aria-label='Agent']");
    private final By Txt_Contact_No = By.xpath("(//ngx-intl-tel-input//input)[1]");
    private final By Txt_Emaill = By.xpath("//*[@id=\"id\"]");
    private final By Txt_Date_of_Joining = By.xpath("//*[@id=\"id-DateofJoining\"]");
    private final By Txt_Address = By.xpath("(//ndc-fg-text-area-input//textarea)[1]");
    private final By Btn_Upload_Document = By.xpath("//ndc-fg-file-input//button");
    private final By Btn_Save = By.xpath("//button[contains(.,'Save')]");
    // ================= Travel Agent Information =================
    private final By Txt_Full_Name_search = By.xpath("//input[@id=\"id-FullName\"]");
    private final By Txt_Contact_No_search =By.xpath("//*[@id=\"Contact No\"]");
    private final By TxtEmail_ID =By.xpath("//*[@id=\"id-EmailID\"]");
    private final By Btn_Reset = By.xpath("(//ndc-profile-management//form//button)[1]");
    private final By Btn_Search = By.xpath("(//ndc-profile-management//form//button)[2]");
    private final By Btn_Action_edit = By.xpath("(//i[contains(@class,'pi-pencil')])[1]");
    private final By Rbtn_Action_active = By.xpath("(//i[contains(@class,'pi-circle-fill')])[1]");
    // ================= verify =================
    private final By Txt_Verify_AgentName = By.xpath("(//tr[contains(@class,'ng-star-inserted')]/td[1])[1]");
    private final By Txt_Verify_ContactNo = By.xpath("(//tr[contains(@class,'ng-star-inserted')]/td[2])[1]");
    private final By Txt_Verify_EmailID = By.xpath("(//tr[contains(@class,'ng-star-inserted')]/td[3])[1]");


    public  ProfileManagemet click_Settings (){
        driver.element().click(Btn_Settings);
        return this;
    }
    public  ProfileManagemet click_Profilemanagement (){
        driver.element().click(Btn_Profilemanagement);
        return this;
    }


    // ================= Add New Agent Action =================
    public  ProfileManagemet click_add_new_agent (){
        driver.element().click(Btn_AddNewAgent);
        return this;
    }

    public ProfileManagemet type_full_name (){
        driver.element().type(Txt_FullName, testData.getTestData("fullname"));
        return this;
    }
    public ProfileManagemet SELECT_POSITION (){
        driver.element().click(Lst_Position);
        return this;
    }
    public ProfileManagemet SELECT_POSITION_ADMIN (){
        driver.element().click(Btn_POSITION_Admin);
        return this;
    }
    public ProfileManagemet SELECT_POSITION_AGENT (){
        driver.element().click(BtnPOSITION_Agent);
        return this;
    }
    public ProfileManagemet TYPE_CONTACT_NO (){
        driver.element().type(Txt_Contact_No,testData.getTestData("ContactNo"));
        return this;
    }


    public ProfileManagemet TYPE_EMAIL(String email){
        driver.element().type(Txt_Emaill, email);
        return this;
    }
    public ProfileManagemet TYPE_Date_of_Joining (){
        driver.element().type(Txt_Date_of_Joining,testData.getTestData("Date_of_Joining"));
        return this;
    }

    public ProfileManagemet TYPE_Address() {

        driver.element().isElementClickable(Txt_Address);

        driver.element().scrollToElement(Txt_Address);

        driver.element().clear(Txt_Address);

        driver.element().type(Txt_Address, testData.getTestData("Address"));

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

        driver.element().isElementClickable(Btn_Save);

        driver.element().click(Btn_Save);

        return this;
    }


// ================= Travel Agent Information Action =================

    public ProfileManagemet type_full_name_search () throws InterruptedException {
      /*  driver.element().click(Full_Name_search);
        Thread.sleep(30000);*/
        driver.element().type(Txt_Full_Name_search, testData.getTestData("fullname"));
       // Thread.sleep(30000);
        return this;
    }
    public ProfileManagemet type_Contact_No_search (){
        driver.element().type(Txt_Contact_No_search, testData.getTestData("ContactNo"));
        return this;
    }
    public ProfileManagemet type_Email_ID (){
        driver.element().type(TxtEmail_ID, testData.getTestData("EMAIL"));
        return this;
    }
    public ProfileManagemet CLICK_Search(){
        driver.element().click(Btn_Search);
        return this;
    }
    public ProfileManagemet CLICK_Reset (){
        driver.element().click(Btn_Reset);
        return this;
    }
    public ProfileManagemet CLICK_Action_edit (){
        driver.element().click(Btn_Action_edit);
        driver.element().type(Txt_FullName, testData.getTestData("fullnameedit"));
        return this;
    }
    public ProfileManagemet CLICK_Action_active (){
        driver.element().click(Rbtn_Action_active);
        return this;
    }

    // ================= verify Action =================
    public ProfileManagemet Verify_AgentName_View () {

        softAssert.assertEquals(driver.element().getText(Txt_Verify_AgentName), testData.getTestData("Verify_AgentName_View"));
        softAssert.assertAll();
        return this;
    }

    public ProfileManagemet Verify_ContactNo_View () {

        softAssert.assertEquals(driver.element().getText(Txt_Verify_ContactNo), testData.getTestData("Verify_ContactNo_View"));
        softAssert.assertAll();
        return this;
    }


    public ProfileManagemet Verify_EmailID_View () {

        softAssert.assertEquals(driver.element().getText(Txt_Verify_EmailID), testData.getTestData("Verify_EmailID_View"));
        softAssert.assertAll();
        return this;
    }











}
