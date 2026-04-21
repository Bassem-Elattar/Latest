package AdminPages.Master.Flight;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.openqa.selenium.By.xpath;

public class BSPCommission_Page {


    public BSPCommission_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;


    By Btn_AddCommission = By.xpath("//button[@routerlink=\"add\"]");
    By Txt_AirlineName = By.xpath("//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted']");
    By Txt_CommissionName = By.xpath("//input[@id='id-CommissionName']");
    By Lst_GDSSupplier = By.xpath("//p-multiselect[.//input[@id=\"id-GDSsupplier\"]]");
    By Lst_SupplierCredential = By.xpath("//p-multiselect[.//input[@id=\"id-Suppliercredential\"]]");
    By Lst_JourneyType = By.xpath("//p-multiselect[.//input[@id=\"id-Journeytype\"]]");
    By Txt_TicketDesignator = By.xpath("//input[@id=\"id-Ticketdesignator\"]");
    By Txt_TourCode = By.xpath("//input[@id=\"id-TourCode\"]");
    By Rbtn_Airport = By.xpath("//p-radiobutton[.//input[@id=\"Level :-Airport\"]]");
    By Txt_OriginAirport = By.xpath("(//div[@class=\"dropdown-wrapper\"])[1]");
    By SelectOrigin = By.xpath("(//input[@type='checkbox'])[2]");
    By Txt_DestinationAirport = By.xpath("//input[@placeholder='Search']");
    By Open = By.xpath("//div[@class=\"dropdown-wrapper open\"]");
    By Rbtn_City = By.xpath("//p-radiobutton[.//input[@id=\"Level :-City\"]]");
    By Txt_OriginCity = By.xpath("//input[@id=\"id-Origincities\"]");
    By Txt_DestinationCity = By.xpath("//div[@class='dropdown-wrapper']");
    By Rbtn_Country = By.xpath("//p-radiobutton[.//input[@id=\"Level :-Country\"]]");
    By Txt_OriginCountry = By.xpath("//input[@id=\"id-Origincountries\"]");
    By Txt_DestinationCountry = By.xpath("//input[@id=\"id-Destinationcountries\"]");
    By Cbox_Adult = By.xpath("//p-checkbox[.//input[@id=\"id-PAXtype:-Adult\"]]");
    By Cbox_Child = By.xpath("//p-checkbox[.//input[@id=\"id-PAXtype:-Child\"]]");
    By Cbox_ExcludeMultipleCarrier = By.xpath("//p-checkbox[.//input[@id=\"id--Excludemultiplecarriers\"]]");
    By Txt_FareBasisCode = By.xpath("//input[@placeholder=\"Enter values\"]");
    By Btn_ValidityPeriodFrom = By.xpath("//input[@id='id-ValidityPeriodFrom']");
    By Btn_ValidityPeriodTo = By.xpath("//input[@id='id-ValidityPeriodTo']");
    By Btn_OutboundPeriodFrom = By.xpath("//input[@id='id-OutboundPeriodFrom']");
    By Btn_OutboundPeriodTo = By.xpath("//input[@id='id-OutboundPeriodTo']");
    By Btn_InboundPeriodFrom = By.xpath("//button[@class=\"p-element p-ripple p-datepicker-trigger ng-tns-c48-48 p-button p-component p-button-icon-only ng-star-inserted\"]");
    By Btn_InboundPeriodTo = By.xpath("//button[@class=\"p-element p-ripple p-datepicker-trigger ng-tns-c48-49 p-button p-component p-button-icon-only ng-star-inserted\"]");
    By Btn_BlackoutInboundPeriodFrom = By.xpath("//button[@class=\"p-element p-ripple p-datepicker-trigger ng-tns-c48-50 p-button p-component p-button-icon-only ng-star-inserted\"]");
    By Btn_BlackoutInboundPeriodTo = By.xpath("//button[@class=\"p-element p-ripple p-datepicker-trigger ng-tns-c48-51 p-button p-component p-button-icon-only ng-star-inserted\"]");
    By Btn_BlackoutOutboundPeriodFrom = By.xpath("//button[@class=\"p-element p-ripple p-datepicker-trigger ng-tns-c48-52 p-button p-component p-button-icon-only ng-star-inserted\"]");
    By Btn_BlackoutOutboundPeriodTo = By.xpath("//button[@class=\"p-element p-ripple p-datepicker-trigger ng-tns-c48-53 p-button p-component p-button-icon-only ng-star-inserted\"]");
    By Cbox_Economy = By.xpath("//p-checkbox[.//input[@id=\"id-DefineCommission-Economy\"]]");
    By Cbox_PremiumEconomy = By.xpath("//p-checkbox[.//input[@id=\"id-DefineCommission-PremiumEconomy\"]]");
    By Cbox_Business = By.xpath("//p-checkbox[.//input[@id=\"id-DefineCommission-Business\"]]");
    By Cbox_FirstClass = By.xpath("//p-checkbox[.//input[@id=\"id-DefineCommission-FirstClass\"]]");
    By Rbtn_All = By.xpath("//p-radiobutton[.//input[@id=\"id--All\"]]");
    By Rbtn_Define = By.xpath("//p-radiobutton[.//input[@id=\"id--Define\"]]");
    By Txt_EnterValue = By.xpath("//input[@id=\"id-Entervalue\"]");
    By Lst_FareType = By.xpath("//p-dropdown[.//input[@id=\"id-Faretype\"]]");
    By Lst_Amount = By.xpath("//p-dropdown[.//input[@id=\"id-Amount\"]]");
    By Txt_A = By.xpath("//input[@id=\"id-A\"]");
    By Txt_B = By.xpath("//input[@id=\"id-B\"]");
    By Txt_C = By.xpath("//input[@id=\"id-C\"]");
    By Txt_D = By.xpath("//input[@id=\"id-D\"]");
    By Txt_E = By.xpath("//input[@id=\"id-E\"]");
    By Txt_F = By.xpath("//input[@id=\"id-F\"]");
    By Txt_G = By.xpath("//input[@id=\"id-G\"]");
    By Txt_H = By.xpath("//input[@id=\"id-H\"]");
    By Txt_I = By.xpath("//input[@id=\"id-I\"]");
    By Txt_J = By.xpath("//input[@id=\"id-J\"]");
    By Txt_K = By.xpath("//input[@id=\"id-K\"]");
    By Txt_L = By.xpath("//input[@id=\"id-L\"]");
    By Txt_M = By.xpath("//input[@id=\"id-M\"]");
    By Txt_N = By.xpath("//input[@id=\"id-N\"]");
    By Txt_O = By.xpath("//input[@id=\"id-O\"]");
    By Txt_P = By.xpath("//input[@id=\"id-P\"]");
    By Txt_Q = By.xpath("//input[@id=\"id-Q\"]");
    By Txt_R = By.xpath("//input[@id=\"id-R\"]");
    By Txt_S = By.xpath("//input[@id=\"id-S\"]");
    By Txt_U = By.xpath("//input[@id=\"id-U\"]");
    By Txt_V = By.xpath("//input[@id=\"id-V\"]");
    By Txt_W = By.xpath("//input[@id=\"id-W\"]");
    By Txt_X = By.xpath("//input[@id=\"id-X\"]");
    By Txt_Y = By.xpath("//input[@id=\"id-Y\"]");
    By Txt_Z = By.xpath("//input[@id=\"id-Z\"]");
    By Btn_Submit = By.xpath("//button[@type=\"submit\"]");
    By Submit = By.xpath("//button[@type=\"submit\"]");
    By Btn_Approve  = By.xpath( "(//button[@type=\"submit\"])[1]");
    By Btn_Reject  = By.xpath("(//button[@type=\"submit\"])[2]");
    By Btn_SendForApproval  = By.xpath("(//button[@type=\"submit\"])[3]");
    By Btn_UpdateButton = By.xpath("//i[@class=\"pi pi-pencil\"]");
    By Txt_Remarks = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    ////
    By Btn_Master = By.xpath("//a[@href=\"/master\"]");
    By Btn_Flight = By.linkText( "Flight");
    By Btn_BSP = By.xpath( "//a[@href=\"/master/flight/bsp\"]");
    By Txt_BSPCode = By.xpath("//input[@id=\"id-BSPCode\"]");
    By Btn_DateFrom = By.xpath("//span[@class=\"ng-tns-c48-11 p-calendar p-calendar-w-btn\"]");
    By Btn_DateTo = By.xpath("//span[@class=\"ng-tns-c48-12 p-calendar p-calendar-w-btn\"]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_SearchButton = By.xpath("//button[@type=\"submit\"]");
    By Year = By.xpath("//button[normalize-space()='2026']");

    public void AddCommission(){
        driver.element().click(Btn_AddCommission);
    }

    public void AirlineName(String airlinename) throws InterruptedException {
        driver.element().select(Txt_AirlineName,airlinename);
    }

    public void CommissionName(String commissionname){
        driver.element().type(Txt_CommissionName,commissionname);
    }

    public void GDSSupplier(String GDS){
        driver.element().select(Lst_GDSSupplier,GDS);
    }

    public void SupplierCredential(String suppliercredential){
        driver.element().select(Lst_SupplierCredential,suppliercredential);
    }

    public void JourneyType(String journeytype){
        driver.element().select(Lst_JourneyType,journeytype);
    }

    public void TicketDesignator(String tickerdesignator){
        driver.element().type(Txt_TicketDesignator,tickerdesignator);
    }

    public void TourCode(String tourcode){
        driver.element().type(Txt_TourCode,tourcode);
    }

    public void AirPort(){
        driver.element().click(Rbtn_Airport);
    }

    public void OriginAirPort(String originairport) throws InterruptedException {
        driver.element().click(Txt_OriginAirport);
        driver.element().type(Txt_DestinationAirport, originairport);
        driver.element().click(SelectOrigin);
    }

    public void DestinationAirPort(String destinationairport) throws InterruptedException {
        driver.element().click(Txt_DestinationCity);
        driver.element().type(Txt_DestinationAirport, destinationairport);
        driver.element().click(SelectOrigin);
    }

    public void City(){
        driver.element().click(Rbtn_City);
    }

    public void OriginCity(String origincity){
        driver.element().type(Txt_OriginCity,origincity).click(Txt_OriginCity);
    }

    public void DestinationCity(String destinationcity){
        driver.element().type(Txt_DestinationCity,destinationcity).click(Txt_DestinationCity);
    }

    public void Country(){
        driver.element().click(Rbtn_Country);
    }

    public void OriginCountry(String origincountry){
        driver.element().type(Txt_OriginCountry,origincountry).click(Txt_OriginCountry);
    }

    public void DestinationCountry(String destinationcountry){
        driver.element().type(Txt_DestinationCountry,destinationcountry).click(Txt_DestinationCountry);
    }

    public void Adult(){
        driver.element().click(Cbox_Adult);
    }

    public void Child(){
        driver.element().click(Cbox_Child);
    }

    public void Exclude(){
        driver.element().click(Cbox_ExcludeMultipleCarrier);
    }

    public void Fairebasis(String fairebasis){
        driver.element().type(Txt_FareBasisCode,fairebasis);
    }

    public void searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(Btn_ValidityPeriodFrom);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
    }

    public void searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(Btn_ValidityPeriodTo);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }

    public void OutboundPeriodFrom(String outboundperiodform){
        driver.element().select(Btn_OutboundPeriodFrom,outboundperiodform);
    }

    public void OutboundPeriodTo(String outboundperiodto){
        driver.element().select(Btn_OutboundPeriodTo,outboundperiodto);
    }

    public void InboundPeriodFrom(String inboundperiodfrom){
        driver.element().select(Btn_InboundPeriodFrom,inboundperiodfrom);
    }

    public void InboundPeriodTo(String inboundperiodto){
        driver.element().select(Btn_InboundPeriodTo,inboundperiodto);
    }

    public void BlackoutInboundPeriodFrom(String blackinfrom){
        driver.element().select(Btn_BlackoutInboundPeriodFrom,blackinfrom);

    }

    public void BlackoutInboundPeriodTo(String blackinto){
        driver.element().select(Btn_BlackoutInboundPeriodTo,blackinto);

    }

    public void BlackoutOutboundPeriodFrom(String blackout){
        driver.element().select(Btn_BlackoutOutboundPeriodFrom,blackout);
    }

    public void BlackoutOutboundPeriodTo(String blackout){
        driver.element().select(Btn_BlackoutOutboundPeriodTo,blackout);
    }

    public void Economy(){
        driver.element().click(Cbox_Economy);
    }

    public void PremiumEconomy(){
        driver.element().click(Cbox_PremiumEconomy);
    }

    public void Business(){
        driver.element().click(Cbox_Business);
    }

    public void FirstClass(){
        driver.element().click(Cbox_FirstClass);
    }

    public void All(){
        driver.element().click(Rbtn_All);
    }

    public void Define(){
        driver.element().click(Rbtn_Define);
    }

    public void EnterValue(String entervalue){
        driver.element().type(Txt_EnterValue,entervalue);
    }


    public void FareType(String faretype){
        driver.element().select(Lst_FareType,faretype);
    }

    public void Amount(String amount){
        driver.element().select(Lst_Amount,amount);
    }

    public void RBD(String a , String b, String c , String d , String e , String f , String g , String h , String i ,
                    String j , String k,String l , String m , String n , String o , String p , String q , String r ,String s
                   ,String u , String v , String w , String x , String y , String z)
    {
        driver.element().type(Txt_A,a);
        driver.element().type(Txt_B,b);
        driver.element().type(Txt_C,c);
        driver.element().type(Txt_D,d);
        driver.element().type(Txt_E,e);
        driver.element().type(Txt_F,f);
        driver.element().type(Txt_G,g);
        driver.element().type(Txt_H,h);
        driver.element().type(Txt_I,i);
        driver.element().type(Txt_J,j);
        driver.element().type(Txt_K,k);
        driver.element().type(Txt_L,l);
        driver.element().type(Txt_M,m);
        driver.element().type(Txt_N,n);
        driver.element().type(Txt_O,o);
        driver.element().type(Txt_P,p);
        driver.element().type(Txt_Q,q);
        driver.element().type(Txt_R,r);
        driver.element().type(Txt_S,s);
        driver.element().type(Txt_U,u);
        driver.element().type(Txt_V,v);
        driver.element().type(Txt_W,w);
        driver.element().type(Txt_X,x);
        driver.element().type(Txt_Y,y);
        driver.element().type(Txt_Z,z);


    }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }
    public void setSubmit(){
        driver.element().click(Btn_Submit);
    }

    ///Update///////////////////



    public void setRemarks(String remarks){
        driver.element().type(Txt_Remarks,remarks);
    }

    public void setApprove(){
        driver.element().click(Btn_Approve);
    }

    public void setReject(){
        driver.element().click(Btn_Reject);
    }

    public void setSendForApproval(){
        driver.element().click(Btn_SendForApproval);
    }

    public void setUpdateButton(){
        driver.element().click(Btn_UpdateButton);
    }
    public String Actualupdate()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Updated Successfully\"]"));
        return S;
    }

    ////Search///////////////

    public void setMaster() {
        driver.element().click(Btn_Master);
    }

    public void setFlight(){
        driver.element().click(Btn_Flight);
    }

    public void setBSP(){
        driver.element().click(Btn_BSP);
    }

    public void setAirlineName(String airlineName){
        driver.element().select(Txt_AirlineName,airlineName);
    }

    public void setCommissionName(String commissionName){
        driver.element().type(Txt_CommissionName,commissionName);
    }

    public void setGDSSupplier(String gdsSupplier){
        driver.element().select(Lst_GDSSupplier,gdsSupplier);
    }

    public void setSupplierCredential(String supplierCredential){
        driver.element().select(Lst_SupplierCredential,supplierCredential);
    }

    public void setBSP(String bsp) {
        driver.element().type(Txt_BSPCode,bsp);
    }

    public void setDateFrom(String date){
        driver.element().select(Btn_DateFrom,date);

    }

    public void setDateTo(String date){
        driver.element().select(Btn_DateTo,date);

    }

    public void setActive(){
        driver.element().click(Rbtn_Active);
    }

    public void setInactive(){
        driver.element().click(Rbtn_Inactive);
    }

    public void setBoth(){
        driver.element().click(Rbtn_Both);
    }

    public void setSearchButton(){
        driver.element().click(Btn_SearchButton);
    }


    public void performAssertions () {
        String expectedHeaderStatus = "Approval Status";
        String expectedHeaderApproval = "Payment Mode";
        String[] allowedStatusValues = {"Approved","Pending for approval","Rejected"};
        String[] allowedApprovalValues = {"Offline", "Online"};

        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[6]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");



            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[6]"));


                Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                        "Row " + i + ": The 'Status' column data (" + actualDataStatus + ") does not match any of the allowed values.");


            }

        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }
    }

    public void performAssertionsAirlineName () {
        String expectedHeaderStatus = "Airline Name";
        String[] allowedStatusValues = {"Air Burkina"};


        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[2]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");



            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[2]"));


                Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                        "Row " + i + ": The 'Status' column data (" + actualDataStatus + ") does not match any of the allowed values.");


            }

        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }
    }

    private boolean isValueInArray (String value, String[]array){
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
