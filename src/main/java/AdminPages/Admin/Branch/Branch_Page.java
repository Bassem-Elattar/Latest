package AdminPages.Admin.Branch;

import com.shaft.driver.SHAFT;
import io.cucumber.java.eo.Se;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.By.xpath;

//import org.python.antlr.ast.Str;

public class Branch_Page {
    public Branch_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    ///////////  Search&Action Branch  /////////

    By Btn_RouteBranch = By.xpath("//a[@href=\"/admin/branch\"]");
    By Txt_BranchCode = By.xpath("//input[@id=\"id-Branchcode\"]");
    By Txt_BranchName = By.xpath("//input[@id=\"id-Branchname\"]");
    By Lst_Country = By.xpath("//p-dropdown[.//input[@id=\"id-Country\"]]");
    By Lst_State = By.xpath("//p-dropdown[.//input[@id=\"id-State\"]]");
    By Lst_City = By.xpath("//p-multiselect[.//input[@id=\"id-City\"]]");
    By Btn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Btn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Btn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_Search = By.xpath("//button[@type=\"submit\"]");
    By Btn_ThumbUp = By.xpath("//i[@class=\"pi pi-thumbs-up\"]");
    By Txt_Remarks = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_SubmitApprove = By.xpath("(//button[@type=\"submit\"])[2]");
    //////// Create Branch ////////

    By Txt_OperatingCountry = By.xpath("//input[@id=\"id-Operatingcountry\"]");
    By Txt_Name = By.xpath("(//input[@id=\"id-Name\"])[1]");
    By Lst_StateCreate = By.xpath("//p-dropdown[.//input[@id=\"id-State\"]]");
    By Lst_CityCreate  = By.xpath("//p-dropdown[.//input[@id=\"id-City\"]]");
    By Txt_Address1 = By.xpath("(//label[@for='id-Address1']/following::div//textarea)[1]");
    By Txt_Address2 = By.xpath("//textarea[@class=\"p-inputtextarea p-inputtext p-component p-element ng-pristine ng-valid ng-touched\"]");
    By Txt_PostOffice = By.xpath("//input[@id=\"id-PostOfficeBox\"]");
    By Txt_PhoneNo = By.xpath("(//input[@id=\"Phone Number\"])[1]");
    By Txt_ServiceTaxNo = By.xpath("//input[@id=\"id-ServiceTaxNumber\"]");
    By Txt_ABTANo = By.xpath("//input[@id=\"id-ABTANumber\"]");
    By Txt_EmailID = By.xpath("(//input[@id=\"id-EmailID\"])[1]");
    By Txt_EmailPassword = By.xpath("//input[@id=\"id-EmailPassword\"]");
    By Txt_NameContact = By.xpath("(//input[@id=\"id-Name\"])[2]");
    By Txt_EmailIdContact = By.xpath("(//input[@id=\"id-EmailID\"])[2]");
    By Txt_PhoneNumber = By.xpath("(//input[@id=\"Phone Number\"])[2]");
    By Btn_AddContactInfo = By.xpath("//button[@class=\"p-element p-button-raised p-button-rounded mt-3 mb-3 p-button p-component ng-star-inserted\"]");
    By Txt_EnterTargetAmount = By.xpath("//input[@id=\"id-EnterTargetAmount\"]");
    By Lst_Month = By.xpath("//p-dropdown[.//input[@id=\"id-SetMonth\"]]");
    By Lst_Year = By.xpath("//p-dropdown[.//input[@id=\"id-SetYear\"]]");
    By Btn_AddTargetAmount = By.xpath("//button[@class=\"justify-content-center p-element p-ripple p-button-outlined p-button p-component mg-start-auto mg-end-10 ng-star-inserted\"]");
    By Txt_CreditLimit = By.xpath("//input[@id=\"id-CreditLimit\"]");
    By Txt_TopUpLimit = By.xpath("//input[@id=\"id-TopUpLimit\"]");
    By Txt_CreditTerms = By.xpath("//input[@id=\"id-CreditTerms(Days)\"]");
    By Txt_ReachingPercentage = By.xpath("//input[@id=\"id-ReachingPercentage\"]");
    By Txt_WarningPrompt = By.xpath("//input[@id=\"id-WarningPrompt(Days)\"]");
    By Cbox_Cash = By.xpath("//p-checkbox[.//input[@id=\"id-PaymentOption-Cash\"]]");
    By Cbox_Wallet = By.xpath("//p-checkbox[.//input[@id=\"id-PaymentOption-Wallet\"]]");
    By Cbox_CreditCard = By.xpath("//p-checkbox[.//input[@id=\"id-PaymentOption-CreditCard\"]]");
    By Cbox_Flight = By.xpath("//p-checkbox[.//input[@id=\"id--Flight\"]]");
    By Cbox_Other = By.xpath("//p-checkbox[.//input[@id=\"id--Others\"]]");
    By Cbox_AirArabiaEgypt = By.xpath("//p-checkbox[.//input[@name=\"123\"]]");
    By Cbox_AirArabia = By.xpath("//p-checkbox[.//input[@name=\"122\"]]");
  //  By Cbox_Amadeus = By.xpath("//p-checkbox[.//input[@name=\"120\"]]");
    By listOfStates =By.xpath("//ul//p-dropdownitem");
    By Cbox_Galileo = By.xpath("//p-checkbox[.//input[@name=\"119\"]]");
    By Cbox_ImportPNR = By.xpath("//p-checkbox[.//input[@id=\"id--ImportPNR\"]]");
    By Txt_SupplierDescription = By.xpath("//input[@id=\"id-Description\"]");
    By Btn_CreateBranch = By.xpath("//button[@routerlink=\"add\"]");
    By Txt_SearchSupplier = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    By CboxSupplier = By.xpath("//*[@id=\"p-tabpanel-1\"]/ndc-fg-form-generator/form/ndc-fg-input[1]/ndc-fg-dropdown-input/p-multiselect/div/p-overlay/div/div/div/div[2]/ul/p-multiselectitem/li/div/div");
    By Lst_SupplierCredential = By.xpath("//p-multiselect[.//input[@id=\"id-Genericsuppliercredentials(PCC)\"]]");
    By Btn_Submit = By.xpath("//button[@type=\"submit\"]");
    By Cbox_credential = By.xpath("//button[@type=\"submit\"]");
   // By Cbox_credential = By.xpath("//button[@type=\"submit\"]");
   By typeLocator =By.xpath("(//p-dropdown[contains(@class,'p-element')])[1]");
   By cityLocator =By.xpath("(//p-dropdown[contains(@class,'p-element')])[2]");
   By clearButtonForOperatingCountry =By.xpath("(//ndc-fg-input//span//i)[1]");


    By Lst_pcc = By.xpath("//p-dropdown[.//input[@id=\"id-State\"]]");


    //////// Search&Action Branch ///////

    public void RouteBranch(){
        driver.element().click(Btn_RouteBranch);
    }

    public void Txt_BranchCode(String TXT){
        driver.element().type(Txt_BranchCode,TXT);
    }

    public void Txt_BranchName(String TXT){
        driver.element().type(Txt_BranchName,TXT);
    }

    public void Lst_Country(String Select){
        driver.element().select(Lst_Country,Select);
    }

    public void Lst_State(String Select){
        driver.element().select(Lst_State,Select);
    }

    public void Lst_City(String Select){
        driver.element().select(Lst_City,Select);
    }

    public void Btn_Inactive(){
        driver.element().click(Btn_Inactive);
    }

    public void Btn_Active(){
        driver.element().click(Btn_Active);
    }

    public void Btn_Both(){
        driver.element().click(Btn_Both);
    }

    public void Btn_Search(){
        driver.element().click(Btn_Search);
    }

    public void Btn_ThumbUp(String Txt){
        driver.element().click(Btn_ThumbUp);
        driver.element().type(Txt_Remarks,Txt);
        driver.element().click(Btn_SubmitApprove);

    }

    ///// Create Branch /////

    public void Btn_CreateBranch(){
        driver.element().click(Btn_CreateBranch);
    }

    public void Txt_OperatingCountry(String Txt){

        driver.element().type(Txt_OperatingCountry,Txt);
        driver.element().select(Txt_OperatingCountry,Txt);
    }

    public void Txt_Name(String Txt){
        driver.element().type(Txt_Name,Txt);
    }

    public void Lst_StateCreate(String Select,String Txt) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(25));

        driver.element().click(typeLocator);

        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        boolean elementExists = (Boolean) js.executeScript(
                "return document.querySelector('li[role=\"option\"]') !== null;"
        );
     //   boolean hasResults = driver.getDriver().findElements(By.xpath("//li[@role='option']")).size() > 0;
    //  boolean hasEmptyMessage = driver.getDriver().findElements(By.xpath("//li[contains(@class,'p-dropdown-empty-message')]")).size() > 0;

    //    List<WebElement> listOfStatesDropdown = driver.getDriver().findElements(listOfStates);
        if (elementExists) {

            By option11 = By.xpath(String.format("//span[contains(text(), '%s')]", Select));
            driver.element().click(option11);}

        else{
            driver.element().click(clearButtonForOperatingCountry);
            driver.element().type(Txt_OperatingCountry, Txt);
            driver.element().select(Txt_OperatingCountry, Txt);
            driver.element().click(typeLocator);
        By option11 = By.xpath(String.format("//span[contains(text(), '%s')]", Select));
        driver.element().click(option11);}



//        Thread.sleep(1000);
//        driver.element().select(Lst_StateCreate,Select);
    }

    public void Lst_CityCreate(String Select) throws InterruptedException {
        driver.element().click(cityLocator);
        By option11 = By.xpath(String.format("//span[contains(text(), '%s')]", Select));
        driver.element().click(option11);
    }

    public void Txt_Address1(String Txt){
        driver.element().type(Txt_Address1,Txt);
    }

    public void Txt_Address2(String Txt){
        driver.element().type(Txt_Address2,Txt);
    }

    public void Txt_PostOffice(String Txt){
        driver.element().type(Txt_PostOffice,Txt);
    }

    public void Txt_PhoneNo(String Txt){
        driver.element().type(Txt_PhoneNo,Txt);
    }

    public void Txt_ServiceTaxNo(String Txt){
        driver.element().type(Txt_ServiceTaxNo,Txt);
    }

    public void Txt_ABTANo(String Txt){
        driver.element().type(Txt_ABTANo,Txt);
    }

    public void Txt_EmailID(String Txt){
        driver.element().type(Txt_EmailID,Txt);
    }

    public void Txt_EmailPassword(String Txt){
        driver.element().type(Txt_EmailPassword,Txt);
    }

    public void Txt_NameContact(String Txt){
        driver.element().type(Txt_NameContact,Txt);
    }

    public void Txt_EmailIdContact(String Txt){
        driver.element().type(Txt_EmailIdContact,Txt);
    }

    public void Txt_PhoneNumber(String Txt){
        driver.element().type(Txt_PhoneNumber,Txt);
    }

    public void Btn_AddContactInfo(){
        driver.element().click(Btn_AddContactInfo);
    }

    public void Txt_EnterTargetAmount(String Txt){
        driver.element().type(Txt_EnterTargetAmount,Txt);
    }

    public void Lst_Month(String Select){
        driver.element().select(Lst_Month,Select);
    }

    public void Lst_Year(String Select){
        driver.element().select(Lst_Year,Select);
    }

    public void Btn_AddTargetAmount(){
        driver.element().click(Btn_AddTargetAmount);
    }

    public void Txt_CreditLimit(String Txt){
        driver.element().type(Txt_CreditLimit,Txt);
    }

    public void Txt_TopUpLimit(String Txt) throws InterruptedException {
        Thread.sleep(1500);
        driver.element().type(Txt_TopUpLimit,Txt);
    }

    public void Txt_CreditTerms(String Txt){
        driver.element().type(Txt_CreditTerms,Txt);
    }

    public void Txt_ReachingPercentage(String Txt){
        driver.element().type(Txt_ReachingPercentage,Txt);
    }

    public void Txt_WarningPrompt(String Txt){
        driver.element().type(Txt_WarningPrompt,Txt);
    }

    public void Cbox_Cash(){
        driver.element().click(Cbox_Cash);
    }

    public void Cbox_Wallet(){
        driver.element().click(Cbox_Wallet);
    }

    public void Cbox_CreditCard(){
        driver.element().click(Cbox_CreditCard);
    }

    public void Cbox_Flight(){
        driver.element().click(Cbox_Flight);
    }

    public void Cbox_Other(){
        driver.element().click(Cbox_Other);
    }

    public void Cbox_AirArabiaEgypt(){
        driver.element().click(Cbox_AirArabiaEgypt);
    }

    public void Cbox_AirArabia(){
        driver.element().click(Cbox_AirArabia);
    }

    public void selectSupplier(String supplier ){

        driver.element().click(By.xpath("//tr[td[contains(text(), '"+supplier+"')]]//p-checkbox"));
    }
    public void addPccForGds(){
        driver.element().click(Cbox_credential);


    }

    public void addCredForLcc(String supplier,String Cred){
        By dropdownTrigger = By.xpath("//tr[td[contains(text(),'"+supplier+"')]]//p-dropdown//div[@role='button']");
        driver.element().click(dropdownTrigger);
        By option = By.xpath("//tr[td[contains(text(),'"+supplier+"')]]//p-dropdownitem/li/span[contains(text(),'"+Cred+"')]");
        driver.getDriver().findElement(option).click();


    }







    public void Cbox_Galileo(){
        driver.element().click(Cbox_Galileo);
    }

    public void Cbox_ImportPNR(){
        driver.element().click(Cbox_ImportPNR);
    }
    public void Txt_Description(String TXT,String supplier){
        By Txt_Description = By.xpath("(//tr[td[contains(text(),'"+supplier+"')]]//td//input[@type='text'])[last()]");
        driver.element().type(Txt_Description,TXT);
    }
    public void Txt_SupplierDescription(String TXT){
        driver.element().type(Txt_SupplierDescription,TXT);
    }

    public void Lst_SupplierCredential(String Select){
        driver.element().click(Lst_SupplierCredential);
        driver.element().select(Txt_SearchSupplier,Select);
//        driver.element().click(CboxSupplier);
    }
    public void Btn_Submit(){
        driver.element().click(Btn_Submit);
    }

    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }
}
