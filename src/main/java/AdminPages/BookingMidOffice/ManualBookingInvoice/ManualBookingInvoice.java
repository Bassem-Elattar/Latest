package AdminPages.BookingMidOffice.ManualBookingInvoice;
import  com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utilities.FileUploadUtil;

import static org.openqa.selenium.By.xpath;

public class ManualBookingInvoice {
    private SHAFT.GUI.WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private SHAFT.TestData.JSON testData;

    public ManualBookingInvoice(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("ManualInvoice.json");
    }

    // ================= Navigate to Manual Booking Invoice Page ================= //
    private final By btn_ManualBookingInvoice = By.xpath("//a[@href='/booking/manual-booking-invoice']");

    // ================= Branch & Agency Section ================= //
    private final By lst_BranchDropdown =
            By.xpath("(//div[@role='button'])[1]");

    private final By opt_TestBranch =
            By.xpath("//li[@aria-label='Test (BRN2)']");

    private final By lst_AgencyDropdown =
            By.xpath("//span[text()='Select Agency']");
    //   private final By s

    private final By opt_TestEgyptAgency =
            By.xpath("//li[@aria-label='Test Egypt']");
    private final By lst_AgentDropdown =
            By.xpath("//span[contains(text(),'Select Agent')]");
    private final By searchAgent =
            By.xpath("//input[contains(@class,'p-dropdown-filter')]");
    private final By opt_TestAgent =
            By.xpath("//li[@aria-label='TEST']");


    // ================= Trip Details Section ================= //
    private final By btn_FromField =
            By.xpath("//div[contains(text(),'From *')]");

    private final By txt_F_SearchField =
            By.xpath("(//input[@placeholder='Search'])[1]");

    private final By opt_F_Airport =
            By.xpath("(//div[@class='p-checkbox-box'])[1]");

    private final By btn_ToField =
            By.xpath("//span[normalize-space()='To *']");

    private final By txt_T_SearchField =
            By.xpath("(//input[@placeholder='Search'])[1]");

    private final By opt_T_Airport =
            By.xpath("//li[@role='option']//span[contains(@class,'city')]");

    private final By dp_DepartureDate =
            By.xpath("//button[contains(@class,'p-datepicker-trigger')]");

    private final By btn_PassengerSelection =
            By.xpath("//button[@class='passengers-btn ng-star-inserted']");
    private final By addChild =
            By.xpath("(//span[contains(@class,'pi-plus')]/parent::button)[2]");
    private final By addINF =
            By.xpath("(//span[contains(@class,'pi-plus')]/parent::button)[3]");


    By Year = xpath("//button[normalize-space()='2026']");

    private final By btn_SupplierDropdown =
            By.xpath("//span[contains(text(),'Supplier')]");
    private final By txt_Supplier =
            By.xpath("//input[contains(@class,'p-dropdown-filter')]");
    private final By galileoOption =
            By.xpath("//li[.//span[normalize-space()='Galileo']]");

    private final By btn_AirlineDropdown =
            By.xpath("//span[contains(text(),'Airline')]");
    private final By txt_Airline =
            By.xpath("//input[contains(@class,'p-dropdown-filter')]");
    private final By opt_Airline_Egyptair =
            By.xpath("//li[@role='option']//span[normalize-space()='Egyptair']");

    private final By btn_UploadPDF =
            By.xpath("//span[contains(text(),'Upload PDF')]");
    // ================= Price Details Section ================= //
    private final By txt_AdultBaseFare =
            By.xpath("//input[@formcontrolname='adultBase']");
    private final By txt_AdultTax =
            By.xpath("//input[@formcontrolname='adultTax']");
    private final By childBaseFare =
            By.xpath("//input[@formcontrolname='childBase']");
    private final By childTax =
            By.xpath("//input[@formcontrolname='childTax']");
    private final By infantBaseFare =
            By.xpath("//input[@formcontrolname='infantBase']");
    private final By infantTax =
            By.xpath("//input[@formcontrolname='infantTax']");
    private final By adultMarkup = By.xpath("//input[@formcontrolname='adultMarkup']");

    private final By adultServiceCharge = By.xpath("//input[@formcontrolname='adultServiceCharge']");

    private final By adultDiscount = By.xpath("//input[@formcontrolname='adultDiscount']");

    // ================= PNR & Flight Info Section ================= //
    private final By txt_GDSPNR =
            By.xpath("//input[@formcontrolname='gdsPnr']");
    private final By txt_AirlinePNR =
            By.xpath("//input[@formcontrolname='airlinePnr']");
    private final By txt_FlightNo =
            By.xpath("//input[@formcontrolname='flightNo']");

    private final By btn_AddTraveler =
            By.xpath("//button[.//span[text()='Add Traveler']]");
    // =========================Passenger Details Section ========================= //
    private final By lst_Title =
            By.xpath("(//div[@role='button' and @aria-label='dropdown trigger'])[1]");
    private final By opt_Title_Mr =
            By.xpath("//li[@role='option']//span[normalize-space()='Mr']");

    private final By txt_FirstName =
            By.xpath("//input[@formcontrolname='firstName']");

    private final By txt_LastName =
            By.xpath("//input[@formcontrolname='lastName']");

    private final By txt_DateOfBirth =
            By.xpath("//input[@placeholder='Date of Birth']");

    private final By lst_Gender =
            By.xpath("//p-dropdown[@formcontrolname='gender']");
    private final By DOCType_ADT =
            By.xpath("(//div[@role='button' and @aria-label='dropdown trigger'])[3]");
    private final By DOCType_CHD =
            By.xpath("(//div[@role='button' and @aria-label='dropdown trigger'])[8]");
    private final By DOCType_INF =
            By.xpath("(//div[@role='button' and @aria-label='dropdown trigger'])[13]");

    private final By dropdownOption =
            By.xpath("//li[@role='option' and @aria-label='Passport']");

    private final By assignedToDropdown = By.xpath("//span[contains(text(),'Assigned to')]");
    private final By dropdown_assignedUser = By.xpath("//li[@role='option']");


    // ========================= Document Details Section ========================= //
    private final By lst_DocumentType =
            By.xpath("(//div[@role='button' and @aria-label='dropdown trigger'])[3]");

    private final By txt_DocumentNumber =
            By.xpath("//input[@formcontrolname='document']");

    private final By txt_DocumentExpiry =
            By.xpath("//input[@placeholder='Document Expiry']");

    private final By lst_Nationality =
            By.xpath("(//div[@role='button' and @aria-label='dropdown trigger'])[4]");

    private final By txt_AdTTicketNumber =
            By.xpath("(//input[@placeholder='Ticket Number'])[1]");
    private final By txt_CHDTicketNumber =
            By.xpath("(//input[@placeholder='Ticket Number'])[2]");
    private final By txt_INFTicketNumber =
            By.xpath("(//input[@placeholder='Ticket Number'])[3]");


    // ========================= Action Section ========================= //
    private final By btn_Pay =
            By.xpath("(//button[@type='button'])[7]");

    // ================= Actions ================= //
    public ManualBookingInvoice openPage() {
        driver.element().click(btn_ManualBookingInvoice);
        return this;
    }

    public ManualBookingInvoice selectBranch() {
        driver.element().click(lst_BranchDropdown);
        driver.element().click(opt_TestBranch);
        return this;
    }

    public ManualBookingInvoice selectAgency(String agency) {
        driver.element().click(lst_AgencyDropdown);
        driver.element().type(searchAgent, agency);
        driver.element().click(opt_TestEgyptAgency);
        return this;

    }

    public ManualBookingInvoice selectAgent(String agent) {
        driver.element().click(lst_AgentDropdown);
        driver.element().type(searchAgent, agent);
        driver.element().click(opt_TestAgent);
        return this;

    }

    public ManualBookingInvoice enterFrom(String from) {
        driver.element().click(btn_FromField);
        driver.element().type(txt_F_SearchField, from);
        driver.element().click(opt_F_Airport);
        return this;
    }

    public ManualBookingInvoice enterTo(String to) {
        driver.element().click(btn_ToField);
        driver.element().type(txt_T_SearchField, to);
        driver.element().click(opt_T_Airport);
        return this;
    }

    //    public ManualBookingInvoice_Page1 selectDate(String departureDate) {
//        driver.element().click(dp_DepartureDate);
//        driver.element().type(dp_DepartureDate, departureDate);
//        return this;
//    }
    public ManualBookingInvoice clickAddPassenger() {
        driver.element().click(btn_PassengerSelection);
        driver.element().click(addChild);
        driver.element().click(addINF);
        return this;
    }

    public ManualBookingInvoice selectSupplier(String supplierName) {
        driver.element().click(btn_SupplierDropdown);
        driver.element().type(txt_Supplier, supplierName);
        driver.element().click(galileoOption);
        return this;
    }

    public ManualBookingInvoice selectAirline(String airline) {
        driver.element().click(btn_AirlineDropdown);
        driver.element().type(txt_Airline, airline);
        driver.element().click(opt_Airline_Egyptair);
        return this;
    }

    public ManualBookingInvoice UploadPDF(String filePath) {
        By fileInputLocator = By.xpath("(//input[@type='file'])[1]");

        //   driver.element().click(btn_UploadPDF);


        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath);

        return this;
    }

    public ManualBookingInvoice enterBaseADT(String baseFare) {
        driver.element().click(txt_AdultBaseFare);
        driver.element().type(txt_AdultBaseFare, baseFare);
        return this;
    }

    public ManualBookingInvoice enterTaxADT(String tax) {

        driver.element().click(txt_AdultTax);
        driver.element().type(txt_AdultTax, tax);
        return this;
    }

    public ManualBookingInvoice enterBaseCHD(String baseFare) {
        driver.element().click(childBaseFare);
        driver.element().type(childBaseFare, baseFare);
        return this;
    }

    public ManualBookingInvoice enterTaxCHD(String tax) {

        driver.element().click(childTax);
        driver.element().type(childTax, tax);
        return this;
    }

    public ManualBookingInvoice enterBaseINF(String baseFare) {
        driver.element().click(infantBaseFare);
        driver.element().type(infantBaseFare, baseFare);
        return this;
    }

    public ManualBookingInvoice enterTaxINF(String tax) {

        driver.element().click(infantTax);
        driver.element().type(infantTax, tax);
        return this;
    }

    public ManualBookingInvoice addRuleEngine(String markup, String serviceCharge, String discount) {
        driver.element().click(adultMarkup);
        driver.element().type(adultMarkup, markup);
        driver.element().click(adultServiceCharge);
        driver.element().type(adultServiceCharge, serviceCharge);
        driver.element().click(adultDiscount);
        driver.element().type(adultDiscount, discount);

        return this;
    }

    public ManualBookingInvoice enterPNR(String gdsPnr) {
        driver.element().click(txt_GDSPNR);
        driver.element().type(txt_GDSPNR, gdsPnr);
        return this;
    }

    public ManualBookingInvoice enterAirLine(String airlinePnr) {
        driver.element().click(txt_AirlinePNR);
        driver.element().type(txt_AirlinePNR, airlinePnr);
        return this;
    }

    public ManualBookingInvoice enterFlightNo(String flightNo) {
        driver.element().click(txt_FlightNo);
        driver.element().type(txt_FlightNo, flightNo);
        return this;
    }

    public ManualBookingInvoice addTraveler() {
        driver.element().click(btn_AddTraveler);
        return this;
    }

    public ManualBookingInvoice searchValidDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(dp_DepartureDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;
    }

    public ManualBookingInvoice selectTitle(String title) {

        driver.element().click(lst_Title);
        driver.element().click(opt_Title_Mr);

//        driver.element().click(
//                By.xpath("//li[@aria-label='" + title + "']"));


        return this;
    }

    public ManualBookingInvoice enterFirstName(String firstName) {
        driver.element().click(txt_FirstName);
        driver.element().type(txt_FirstName, firstName);
        return this;
    }

    public ManualBookingInvoice enterLastName(String lastName) {
        driver.element().click(txt_LastName);
        driver.element().type(txt_LastName, lastName);
        return this;
    }

    public ManualBookingInvoice enterDateOfBirth(String dob) {
        driver.element().click(txt_DateOfBirth);
        driver.element().type(txt_DateOfBirth, dob);

        return this;
    }

    public ManualBookingInvoice enterGenderType() {
        driver.element().click(lst_Gender);
        return this;

    }

    public ManualBookingInvoice enterDocumentNumber(String documentNumber) {
        driver.element().click(txt_DocumentNumber);
        driver.element().type(txt_DocumentNumber, documentNumber);
        return this;
    }

    public ManualBookingInvoice enterDocumentExpiry(String expiryDate) {
        driver.element().click(txt_DocumentExpiry);
        driver.element().type(txt_DocumentExpiry, expiryDate);
        return this;
    }

    public ManualBookingInvoice selectADTDocumentType() {
        driver.element().click(DOCType_ADT);
        driver.element().click(dropdownOption);
        return this;
    }

    public ManualBookingInvoice selectCHDDocumentType() {
        driver.element().click(DOCType_CHD);
        driver.element().click(dropdownOption);
        return this;
    }

    public ManualBookingInvoice selectINFDocumentType() {
        driver.element().click(DOCType_INF);
        driver.element().click(dropdownOption);
        return this;
    }

    public ManualBookingInvoice enterADTTicketNumber(String ticketNumber) {
        driver.element().click(txt_AdTTicketNumber);
        driver.element().type(txt_AdTTicketNumber, ticketNumber);
        return this;
    }

    public ManualBookingInvoice enterCHDTicketNumber(String ticketNumber) {
        driver.element().click(txt_CHDTicketNumber);
        driver.element().type(txt_CHDTicketNumber, ticketNumber);
        return this;
    }

    public ManualBookingInvoice enterINFTicketNumber(String ticketNumber) {
        driver.element().click(txt_INFTicketNumber);
        driver.element().type(txt_INFTicketNumber, ticketNumber);
        return this;
    }

    public ManualBookingInvoice selectAssignedUser() {
        driver.element().click(assignedToDropdown);
        driver.element().click(dropdown_assignedUser);
        return this;
    }

    public ManualBookingInvoice clickPay() {
        driver.element().click(btn_Pay);
        return this;
    }



    private final By btnSegmentsDetails =
            By.xpath("//span[contains(.,'Segments Details')]");
    private final By lblRoute =
            By.xpath("//b[contains(.,'CAI') and contains(.,'DXB')]");

    private final By lblPassengers =
            By.xpath("(//*[contains(normalize-space(.),'Passengers')])[15]");

    private final By lblTripType =
            By.xpath("//span[contains(text(),'OneWay')]");
    private final By lblBaseFare =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[1]");

    private final By lblTax =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[2]");
    private final By lblTotal =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[3]");

    private final By lblBaseFare_ADT =
            By.xpath("(//span[contains(text(),'EGP')])[2]");

    private final By lblTax_ADT =
            By.xpath("(//span[contains(text(),'EGP')])[5]");


    private final By lblBaseFare_CHD =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[2]");

    private final By lblTax_CHD =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[5]");


    private final By lblBaseFare_INF =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[3]");

    private final By lblTax_INF =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[6]");

    private final By lblTotal_all =
            By.xpath("(//span[contains(@class,'value') and contains(text(),'EGP')])[7]");
    private final By lblDiscount = By.xpath("(//span[contains(text(),'EGP')])[4]");
    private final By lblServiceCharge = By.xpath(
            "(//span[contains(@class,'text-orange-600')])[2]");
    private final By lblTotalWithRule = By.xpath("(//span[contains(@class,'font-semibold')])[3]");

    // ================= HELPERS ================= //

    private int extractAmount(By locator) {

        String text = driver.element().getText(locator);

        return Integer.parseInt(
                text.replace("EGP", "")
                        .replace(",", "")
                        .trim()
        );
    }

    public ManualBookingInvoice OpenSegDetails() {
        driver.element().click(btnSegmentsDetails);
        return this;
    }

    public ManualBookingInvoice assertRouteVisible() {

        driver.verifyThat()
                .element(lblRoute)
                .exists()
                .perform();

        return this;
    }

    public ManualBookingInvoice assertPassengerCountVisible() {

        driver.verifyThat()
                .element(lblPassengers)
                .exists()
                .perform();

        return this;
    }

    public ManualBookingInvoice assertTripTypeVisible() {

        driver.verifyThat()
                .element(lblTripType)
                .exists()
                .perform();

        return this;
    }

    public ManualBookingInvoice openSegmentsDetails() {

        driver.element().click(btnSegmentsDetails);
        driver.element()
                .waitToBeReady(btnSegmentsDetails);

        driver.element()
                .waitToBeReady(lblPassengers);

        return this;
    }

    public ManualBookingInvoice assertBaseFare(String expected) {

        int actual = extractAmount(lblBaseFare);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Base Fare Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Base Fare match"
        );

        return this;
    }

    public ManualBookingInvoice assertTax(String expected) {

        int actual = extractAmount(lblTax);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Tax Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Tax match"
        );

        return this;
    }

    public ManualBookingInvoice assertBaseFare_ADT(String expected) {

        int actual = extractAmount(lblBaseFare_ADT);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Base Fare Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Base Fare match"
        );

        return this;
    }

    public ManualBookingInvoice assertTax_ADT(String expected) {

        int actual = extractAmount(lblTax_ADT);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Tax Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Tax match"
        );

        return this;
    }

    public ManualBookingInvoice assertBaseFarecCHD(String expected) {

        int actual = extractAmount(lblBaseFare_CHD);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Base Fare Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Base Fare match"
        );

        return this;
    }

    public ManualBookingInvoice assertTaxCHD(String expected) {

        int actual = extractAmount(lblTax_CHD);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Tax Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Tax match"
        );

        return this;
    }

    public ManualBookingInvoice assertBaseFare_INF(String expected) {

        int actual = extractAmount(lblBaseFare_INF);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Base Fare Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Base Fare match"
        );

        return this;
    }

    public ManualBookingInvoice assertTax_INF(String expected) {

        int actual = extractAmount(lblTax_INF);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Tax Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Tax match"
        );

        return this;
    }

    public ManualBookingInvoice assertTotalADT(String expected) {

        int actual = extractAmount(lblTotal);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Total Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");
        Assert.assertEquals(
                actual,
                expectedValue,
                "Total match"
        );

        return this;
    }

    public ManualBookingInvoice assertTotal(String expected) {

        int actual = extractAmount(lblTotal_all);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Total Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");
        Assert.assertEquals(
                actual,
                expectedValue,
                "Total match"
        );

        return this;
    }

    public ManualBookingInvoice assertDiscount(String expected) {

        int actual = extractAmount(lblDiscount);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Discount Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Discount match"
        );

        return this;
    }

    public ManualBookingInvoice assertServiceCharge(String expected) {

        int actual = extractAmount(lblServiceCharge);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Service Charge Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");

        Assert.assertEquals(
                actual,
                expectedValue,
                "Servic Charge match"
        );
        return this;
    }


    public ManualBookingInvoice assertTotalwithRule(String expected) {

        int actual = extractAmount(lblTotalWithRule);
        int expectedValue = Integer.parseInt(expected);
        System.out.println("==================================");
        System.out.println("✔ Total Assertion");
        System.out.println("Expected: " + expectedValue);
        System.out.println("Actual: " + actual);
        System.out.println("==================================");
        Assert.assertEquals(
                actual,
                expectedValue,
                "Total match"
        );

        return this;
    }
}



