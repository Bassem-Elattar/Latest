package AdminPages.Master.Flight.Airport;

import AdminPages.RuleEngine.CancellationCharge.CancellationCharge_Page;
import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class Airport_Page {
    private final SoftAssert softAssert;
    private final SHAFT.GUI.WebDriver driver;

    public Airport_Page(SHAFT.GUI.WebDriver driver) {
        this.softAssert = new SoftAssert();
        this.driver = driver;

    }
////////////////////////////////////////// Search ///////////////////////////////////////////////////////////////////
    private final By Master = By.xpath("//a[@id=\"p-tabpanel-2-label\"]");
    private final By Flight = By.xpath("//a[.//span[normalize-space()='Flight']]\n");
    private final By Airport = By.xpath("//a[.//span[normalize-space()='Airport']]\n");
    private final By IATACode = By.xpath("//input[@id='id-IATACode']");
    private final By AirportName = By.xpath("//input[@id='id-AirportName']");
    private final By Country = By.xpath("//input[@id='id-Country']");
    private final By City = By.xpath("//input[@id='id-City']");
    private final By Type = By.xpath("(//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted'][normalize-space()='Select'])[1]");
    private final By Station = By.xpath("//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted']");
    private final By Search = By.xpath("//button[@type=\"submit\"]");
    private final By IATAAssert =  By.xpath("//td[normalize-space()][1]");
    private final By NameAssert =  By.xpath("//td[normalize-space()][2]");

    //////////////////////////////////////////////////  Add  ///////////////////////////////////////////////////////////////
    private final By AddButton = By.xpath("//span[@style=\"margin-inline-end: 10px;\"]");
    private final By Domestic = By.xpath("(//div[@class='p-checkbox-box'])[1]");
    private final By International = By.xpath("(//p-checkbox)[2]");
    private final By BothOfType = By.xpath("(//div[@class=\"p-checkbox p-component\"])[3]");
    private final By Latitude = By.xpath("//input[@id='id-Latitude']");
    private final By Longitude = By.xpath("//input[@id='id-Longitude']");
    private final By DontDisplayToPOS = By.xpath("//span[normalize-space()='Ex: Egypt']");
    private final By NearByAirport = By.xpath("//span[normalize-space()='Ex: Cairo International Airport']");
    private final By SendForApproval  = By.xpath("//button[normalize-space()='Send For Approval']");
    private final By Right = By.xpath("//i[@class=\"pi pi-arrow-right\"]");
    private final By Left = By.xpath("//i[@class=\"pi pi-arrow-left\"]");
    private final By Airportname = By.xpath("//input[@id=\"id-AirportName\"]");
    private final By station = By.xpath("//p-dropdown[.//input[@id=\"id-Station\"]]");

    //////////////////////////////////////////////////////  Edit  ///////////////////////////////////////////////////////////
    private final By Editbutton = By.xpath("(//i[@class=\"pi pi-pencil\"])[1]");
    private final By UpdateApprove = By.xpath("//button[normalize-space()='Approve']");
    private final By UpdateConfirmation = By.xpath("//div[@aria-label=\"Updated Successfully\"]");

    /////////////////////////////////////////////////////  Action  ////////////////////////////////////////////////////////////

    private final By Active = By.xpath("//p-radiobutton[ .//input[@ id=\"id-Status-Active\"]]");
    private final By InActive = By.xpath("//p-radiobutton[ .//input[@ id=\"id-Status-Inactive\"]]");
    private final By Approve = By.xpath("//i[@class='pi pi-thumbs-up']");
    private final By CreationConfirmation = By.xpath("//div[@aria-label=\"Added Successfully\"]");
    private final By TxtRemarks = By.xpath("//textarea[@placeholder='remarks...']");
    private  final By Btn_Save = By.xpath("//span[normalize-space()='Submit']");

    public void navigateToAirportPage() {
                driver.element().click(Master);
                driver.element().click(Flight);
                driver.element().click(Airport);

    }
    public Airport_Page EnterIATACode(String IATA){
        driver.element().type(IATACode,IATA);
        return this;
    }

    public Airport_Page clearFields() throws InterruptedException {
        Thread.sleep(1000);
        driver.element().clear(IATACode);
        driver.element().clear(AirportName);
        Thread.sleep(500);
        return this;
    }

    public String random3Letters() {
        return "" +
                (char) ('A' + Math.random() * 26) +
                (char) ('A' + Math.random() * 26) +
                (char) ('A' + Math.random() * 26);
    }

    public void EnterAirportName(String Name){
        driver.element().type(AirportName,Name);
    }
    public void EnterCountry(String Name) throws InterruptedException {
        driver.element().click(Country);
        driver.element().type(Country,Name);
        Thread.sleep(500);
        final By DropdownCountry_City = xpath("(//ul//li[@role=\"option\"])[1]");
        Thread.sleep(500);
        driver.element().click(DropdownCountry_City);
    }
    public void EnterCity(String Name) throws InterruptedException {
        driver.element().click(City);
        driver.element().type(City,Name);
        Thread.sleep(500);
        final By DropdownCountry_City = xpath("(//ul//li[@role=\"option\"])[1]");
        Thread.sleep(500);
        driver.element().click(DropdownCountry_City);
    }
    public Airport_Page ClickOnTypeAndChooseIndex(String Check) throws InterruptedException {
        driver.element().click(Type);
        Thread.sleep(500);
        final By SelectIndex = xpath("//p-dropdown//p-overlay//div//div//ul//p-dropdownitem["+ Check+"]");
        driver.element().click(SelectIndex);
        return this;
    }
    public Airport_Page ClickOnStationAndChooseIndex(String Check) throws InterruptedException {

        driver.element().click(Station);
        Thread.sleep(500);
        final By SelectIndex = xpath("//p-dropdown//p-overlay//div//div//ul//p-dropdownitem["+ Check+"]");
        driver.element().click(SelectIndex);
        return this;
    }
    public Airport_Page ClickOnSearch (){
        driver.element().click(Search);
        return this;
    }

    public Airport_Page ClickOnStation(String Check) throws InterruptedException {

        driver.element().click(station);
        Thread.sleep(500);
        final By SelectIndex = xpath("(//p-dropdown//p-overlay//div//div//ul//p-dropdownitem//li)["+ Check+"]");
        driver.element().click(SelectIndex);
        return this;
    }

    public Airport_Page EnterAirportname(String S){
        driver.element().type(Airportname,S);
        return this;
    }

    public Airport_Page ClickOnSendForApproval(){
        driver.element().click(SendForApproval);
        return this;
    }
    public Airport_Page ClickOnAddAirport(){
        driver.element().click(AddButton);
        return this;
    }
    public Airport_Page ClickOnDomestic() throws InterruptedException {
        driver.element().click(Domestic);
        Thread.sleep(500);
        return this;
    }
    public Airport_Page ClickOnInternational(){
        driver.element().click(International);
        return this;
    }
    public Airport_Page ClickOnBoth(){
        driver.element().click(BothOfType);
        return this;
    }
    public Airport_Page TypeOnLatitude(String s){
        driver.element().type(Latitude,s);
        return this;
    }
    public Airport_Page TypeOnLongitude(String s){
        driver.element().type(Longitude,s);
        return this;
    }
    public Airport_Page ClickOnRight(){
        driver.element().click(Right);
        return this;
    }
    public Airport_Page ClickOnLeft(){
        driver.element().click(Left);
        return this;
    }
    public void DontDisplayToPOS (String Name) throws InterruptedException {
        driver.element().click(DontDisplayToPOS);
        driver.element().type(DontDisplayToPOS,Name);
        Thread.sleep(500);
        final By DontDisplayToPOS = xpath("(//ul//li[@role=\"option\"])[1]");
        Thread.sleep(500);
        driver.element().click(DontDisplayToPOS);
    }
    public void NearByAirport(String Name) throws InterruptedException {
        driver.element().click(NearByAirport);
        driver.element().type(NearByAirport,Name);
        Thread.sleep(500);
        final By NearByAirport = xpath("(//ul//li[@role=\"option\"])[1]");
        Thread.sleep(500);
        driver.element().click(NearByAirport);
    }

    public Airport_Page ClickOnEdit(){
        driver.element().click(Editbutton);
        return this;
    }

    public void ClickOnThumbsUp(){
        driver.element().click(Approve);
    }

    public void ClickOnInActiveStatus(){
        driver.element().click(InActive);
    }

    public void ClickOnActiveStatus() throws InterruptedException {
        driver.element().click(Active);
    }

    public String ActualCreate() {
        return driver.element().getText(CreationConfirmation);
    }

    public String ActualUpdate() {
        return driver.element().getText(UpdateConfirmation);
    }

    public Airport_Page TxtRemarks(String Txt){
        driver.element().type(TxtRemarks,Txt);
        return this;
    }

    public void Btn_Save(){
        driver.element().click(Btn_Save);
    }

    public void UpdateApprove(){
        driver.element().click(UpdateApprove);
    }

    public String AssertIATA(){
        return driver.element().getText(IATAAssert);
    }

    public String AssertName(){
       return driver.element().getText(NameAssert);
    }

 /*  public Integer NumberOfSupplier() {
       String S = GetSupplierText();
       char array[] = S.toCharArray();
       StringBuilder Array = null;
       int Res;
       for (int i = 1; i <= 5; i++) {
           if (array[i]=='0' || array[i]=='1' ||array[i]=='2' ||array[i]=='3' ||array[i]=='4' ||array[i]=='5' ||array[i]=='6'
                   ||array[i]=='7' ||array[i]=='8' ||array[i]=='9' ){
               Array.append(array[i]);
           }

       }
       String s = Array.toString();
       Res = Integer.parseInt(s);
        return Res;
   }
    public Integer NumberOfSupplier2() {
        String S = GetSupplierText2();
        char array[] = S.toCharArray();
        StringBuilder Array = null;
        int Res;
        for (int i = 1; i <= 5; i++) {
            if (array[i]=='0' || array[i]=='1' ||array[i]=='2' ||array[i]=='3' ||array[i]=='4' ||array[i]=='5' ||array[i]=='6'
                    ||array[i]=='7' ||array[i]=='8' ||array[i]=='9' ){
                Array.append(array[i]);
            }

        }
        String s = Array.toString();
        Res = Integer.parseInt(s);
        return Res;
    }*/
}