package AdminPages.RuleEngine.CancellationCharge;
import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CancellationCharge_Page {

    private SHAFT.GUI.WebDriver driver ;
    public CancellationCharge_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }



    /////////////// RuleEngine Route /////////////

    By BtnRuleEngine = By.xpath("//span[normalize-space()='Rule Engine']");
    By BtnCancellationCharge = By.xpath("//span[normalize-space()='Cancellation Charge']");


    //////////////// Search CancellationCharge /////////////////

    By Txt_CancellationChargeName = By.xpath("//input[@id=\"id-CancellationChargeName\"]");
    By Lst_Country = By.xpath("//p-dropdown[.//input[@id=\"id-Country\"]]");
    By Txt_CancellationChargeCode = By.xpath("//input[@id=\"id-CancellationChargecode\"]");
    By Get_CancellationChargeCode = By.xpath("(//td[contains(normalize-space(), '1')])[1]");
    By Rbtn_Inactive = By.xpath("(//div[@class='p-radiobutton-box'])[1]");
    By Rbtn_Active = By.xpath("(//div[@class='p-radiobutton-box'])[1]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_Search = By.xpath("//button[@type='submit']");
    By SearchArea = By.xpath("//td[@class='message']");

    //////////////// Action CancellationCharge /////////////////

    By Btn_ThumpUp = By.xpath("//i[@class='pi pi-thumbs-up']");
    By Btn_ThumpDown = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Btn_Clone = By.xpath("//i[@class=\"pi pi-clone ng-star-inserted\"]");
    By Txt_NewCancellationChargeName = By.xpath("//input[@id=\"id-NewCancellationChargeName\"]");
    By Btn_Save = By.xpath("//span[normalize-space()='Submit']");
    By Btn_Delete = By.xpath("//i[@class='p-element pi pi-trash ng-star-inserted']");
    By Confirm_Delete = By.xpath("//button[normalize-space()='Delete']");

    //////////////// Create CancellationCharge /////////////////

    By BtnAddCancellationCharge = By.xpath("//button[@routerlink=\"add\"]");
    By TxtCancellationChargeDescription = By.xpath("//textarea[@placeholder=\"Type your Cancellation charge description...\"]");
    By Dpick_ValidityPeriodFrom = By.xpath("//p-calendar[.//input[@id=\"id-ValidityPeriodFrom\"]]");
    By Dpick_ValidityPeriodTo = By.xpath("//p-calendar[.//input[@id=\"id-ValidityPeriodTo\"]]");
    By Lst_CountryPos = By.xpath("//div[@class=\"selected-chips\"]");
    By Txt_SearchContainer = By.xpath("//input[@placeholder=\"Search\"]");
    By Btn_Select = By.xpath("(//input[@class='ng-star-inserted'])[1]");
    By BtnSendForApproval = By.xpath("//button[@type=\"submit\"]");

    //////////////// Create CancellationCharge /////////////////
    By CreationConfirmation = By.xpath("//div[@aria-label=\"Added Successfully\"]");
    By LstLeviedBy = By.xpath("//p-dropdown[.//input[@id=\"id-LeviedBy\"]]");
    By LstFareType = By.xpath("//p-dropdown[.//input[@id=\"id-FareType\"]]");

    //////////////// Create CancellationCharge PerPerson /////////////////

    By TxtAdult = By.xpath("//input[@id=\"id-Adult\"]");
    By TxtChild = By.xpath("//input[@id=\"id-Child\"]");
    By TxtInfant = By.xpath("//input[@id=\"id-Infant\"]");

    //////////////// Create CancellationCharge PerTrip /////////////////

    By TxtEnterValue = By.xpath("//input[@id=\"id-Entervalue\"]");

    //////////////// Update CancellationCharge  /////////////////

    By BtnUpdate = By.xpath("//i[@class='pi pi-pencil']");
    By TxtRemarks = By.xpath("//textarea[@placeholder='remarks...']");
    By BtnReject = By.xpath("(//button[@class=\"col-sm-12 col-md-6 col-lg-2 p-element p-ripple p-button p-component justify-content-center ng-star-inserted\"])[1]");
    By BtnApprove = By.xpath("//button[normalize-space()='Approve']");
    By BtnUpdateSendForApproval = By.xpath("(//button[@class=\"col-sm-12 col-md-6 col-lg-2 p-element p-ripple p-button p-component justify-content-center ng-star-inserted\"])[3]");


    public void Txt_CancellationChargeName(String Txt){
        driver.element().type(Txt_CancellationChargeName,Txt);
    }

    public void Lst_Country(String Select){
        driver.element().select(Lst_Country,Select);
    }

    public void Txt_CancellationChargeCode(String Txt){
        driver.element().type(Txt_CancellationChargeCode,Txt);
    }

    public String Get_CancellationChargeCode() {
        return driver.element().getText(Get_CancellationChargeCode);
    }

    public void Rbtn_Inactive(){
        driver.element().click(Rbtn_Inactive);
    }

    public void Rbtn_Active(){
        driver.element().click(Rbtn_Active);
    }

    public void Rbtn_Both(){
        driver.element().click(Rbtn_Both);
    }

    public void Btn_Search(){
        driver.element().click(Btn_Search);
    }

    public CancellationCharge_Page Btn_ThumpUp(){
        driver.element().click(Btn_ThumpUp);
        return this;
    }

    public void Btn_ThumpDown(){
        driver.element().click(Btn_ThumpDown);
    }

    public void Btn_Clone(){
        driver.element().click(Btn_Clone);
    }

    public void Btn_Save(){
        driver.element().click(Btn_Save);
    }

    public void Btn_Delete(){
        driver.element().click(Btn_Delete);
        driver.element().click(Confirm_Delete);
    }

    public void BtnAddCancellationCharge(){
        driver.element().click(BtnAddCancellationCharge);
    }

    public void TxtCancellationChargeDescription(String Txt){
        driver.element().type(TxtCancellationChargeDescription,Txt);
    }

    public void Dpick_ValidityPeriodFrom(String Select){
        driver.element().click(Dpick_ValidityPeriodFrom);
        By option11 = By.xpath(String.format("//span[normalize-space()='%s']", Select));
        driver.element().click(option11);
    }

    public void Dpick_ValidityPeriodTo(String Select){
        driver.element().click(Dpick_ValidityPeriodTo);
        By option11 = By.xpath(String.format("//span[normalize-space()='%s']", Select));
        driver.element().click(option11);
    }

    public void Lst_CountryPos(){
     driver.element().click(Lst_CountryPos);

    }

    public void Btn_Select(){
        driver.element().click(Btn_Select);
    }

    public void BtnSendForApproval(){
        driver.element().click(BtnSendForApproval);
    }

    public void LstFareType(String Select){
        driver.element().select(LstFareType,Select);
    }

    public void TxtAdult(String Txt){
        driver.element().type(TxtAdult,Txt);
    }

    public void TxtChild(String Txt){
        driver.element().type(TxtChild,Txt);
    }

    public void TxtInfant(String Txt){
        driver.element().type(TxtInfant,Txt);
    }

    public void BtnUpdate(){
        driver.element().click(BtnUpdate);
    }

    public CancellationCharge_Page TxtRemarks(String Txt){
        driver.element().type(TxtRemarks,Txt);
        return this;
    }

    public String EmptySearch(){
        return driver.element().getText(SearchArea);
    }

    public void BtnApprove(){
        driver.element().click(BtnApprove);
    }

    public void BtnUpdateSendForApproval(){
        driver.element().click(BtnUpdateSendForApproval);
    }

    public String ActualCreate() {
        return driver.element().getText(CreationConfirmation);
    }

}
