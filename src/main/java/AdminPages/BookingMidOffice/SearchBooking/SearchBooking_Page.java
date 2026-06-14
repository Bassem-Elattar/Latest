package AdminPages.BookingMidOffice.SearchBooking;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class SearchBooking_Page {
    private SHAFT.GUI.WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private SHAFT.TestData.JSON testData;

    public SearchBooking_Page(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
        this.testData = new SHAFT.TestData.JSON("SearchBooking.json");
    }

        /////select search booking////
    private final By Btn_SearchBooking = By.xpath("//a[@href = '/booking']");
        /////select module////
    private final By Btn_Flight = By.xpath("(//li[@role='presentation'])[15]");
    private final By ShowMoreBtn = By.xpath("(//button[@type='button'])[1]");
    private final By Btn_Booking = By.xpath("(//li[@role='presentation'])[14]");
     /////general fields//////
    private final By Dpick_BookingStartDate = By.xpath("//input[@id='id-BookingStartDate']");
    private final By Btn_SelectBookingStartDate = By.xpath("(//button[@tabindex='0'])[1]");
    private final By Dpick_BookingEndDate = By.xpath("//input[@id='id-BookingEndDate']");
    private final By Btn_SelectBookingEndDate = By.xpath("(//button[@tabindex='0'])[2]");
    private final By lst_OpenBranch = By.xpath("(//div[@role='button'])[1]");
    private final By Btn_SelectBranch = By.xpath("//li[@aria-label='Test']");
    private final By lst_OpenAgency = By.xpath("//div[@class='p-multiselect-trigger']");
    private final By Btn_SelectAgency = By.xpath("//li[@aria-label='Test Egypt']");
    private final By Btn_CloseAgency = By.xpath("//li[@aria-label='Test Egypt']");
        /////flight////
    private final By txt_Airline_GDSPNR = By.xpath("//input[@id='id-Airline/GDSPNR']");
    private final By txt_BookingReference = By.xpath("//input[@id='id-BookingReference']");
    private final By txt_TicketNo = By.xpath("//input[@id='id-TicketNo.']");
    private final By txt_OrderID = By.xpath("//input[@id='id-OrderId']");
        ////Booking///////
    private final By lst_OpenPaymentStatus = By.xpath("(//div[@role='button'])[2]");
    private final By lst_SelectPaymentStatus = By.xpath("//li[@aria-label='To be Authorized']");
    private final By lst_OpenBookingStatus = By.xpath("(//div[@role='button'])[3]");
    private final By lst_SelectBookingStatus = By.xpath("//li[@aria-label='New Booking']");
    private final By txt_RefundID = By.xpath("//input[@placeholder='Refund Id']");
    private final By txt_PaxFirstName = By.xpath("(//input[@placeholder='Name'])[1]");
    private final By txt_PaxLastName = By.xpath("(//input[@placeholder='Name'])[2]");
    private final By txt_PaxClientName = By.xpath("(//input[@placeholder='Name'])[3]");
    private final By txt_EmailID = By.xpath("//input[@placeholder='Email']");
    private final By txt_InvoiceNo = By.xpath("//input[@placeholder='Invoice No.']");
        /////buttons////
    private final By Btn_Search = By.xpath("//button[@type='submit']");
    private final By Btn_Reset = By.xpath("//button[@type='reset']");
        ////Columns///
    private final By orderID_Column    = By.xpath("//tbody/tr[1]/td[2]");
    private final By BranchName_Column = By.xpath("//tbody/tr[1]/td[3]");
    private final By AgencyName_Column = By.xpath("//tbody/tr[1]/td[4]");
    private final By ClientName_Column = By.xpath("//tbody/tr[1]/td[5]");

    private final By Results = By.xpath("//p[contains(text(),'Showing 10 results')]");
    private final By PaginateResults = By.xpath("//p[contains(text(),'Showing 20 results')]");
    private final By Btn_paginate = By.xpath("//button[@class='next']");


        public SearchBooking_Page NavigateToSearchBooking() {
        driver.element().click(Btn_SearchBooking);
        return this;
    }

    public SearchBooking_Page SelectFlight() {
        driver.element().click(Btn_Flight);
        return this;
    }

    public SearchBooking_Page SelectBooking() {
        driver.element().click(Btn_Booking);
        return this;
    }

    public SearchBooking_Page SelectValidStartDate(String s) {
        driver.element().type(Dpick_BookingStartDate, s);
        driver.element().type(Dpick_BookingStartDate, s);
        driver.element().click(Btn_SelectBookingStartDate);
        return this;
    }
    public SearchBooking_Page SelectValidEndDate(String s){
        driver.element().type(Dpick_BookingEndDate,s);
        driver.element().type(Dpick_BookingEndDate,s);
        driver.element().click(Btn_SelectBookingEndDate);
        return this;
    }

    public SearchBooking_Page EnterGDSPNR(String s) {
        driver.element().type(txt_Airline_GDSPNR,s);
        return this;
    }
    public SearchBooking_Page EnterBookingReference(String s) {
        driver.element().type(txt_BookingReference,s);
        return this;
    }
    public SearchBooking_Page EnterTicketNo(String s) {
        driver.element().type(txt_TicketNo,s);
        return this;
    }
    public SearchBooking_Page EnterOrderID(String s) {
        driver.element().type(txt_OrderID,s);
        return this;
    }

    public SearchBooking_Page SelectBranch() {
        driver.element().click(lst_OpenBranch);
        driver.element().click(Btn_SelectBranch);
        return this;
    }

    public SearchBooking_Page SelectAgency() {
        driver.element().click(lst_OpenAgency);
        driver.element().click(Btn_SelectAgency);
        //driver.element().click(Btn_CloseAgency);
        return this;
    }

    public SearchBooking_Page SelectPaymentStatus() {
        driver.element().click(lst_OpenPaymentStatus);
        driver.element().click(lst_SelectPaymentStatus);
        return this;
    }

    public SearchBooking_Page SelectBookingStatus() {
        driver.element().click(lst_OpenBookingStatus);
        driver.element().click(lst_SelectBookingStatus);
        return this;
    }

    public SearchBooking_Page EnterRefundID(String s) {
        driver.element().type(txt_RefundID,s);
        return this;
    }

    public SearchBooking_Page EnterPaxFirstName(String s) {
        driver.element().type(txt_PaxFirstName,s);
        return this;
    }

    public SearchBooking_Page EnterPaxLastName(String s) {
        driver.element().type(txt_PaxLastName,s);
        return this;
    }

    public SearchBooking_Page EnterClientName(String s) {
        driver.element().type(txt_PaxClientName,s);
        return this;
    }

    public SearchBooking_Page EnterClientEmailID(String s) {
        driver.element().type(txt_EmailID,s);
        return this;
    }

    public SearchBooking_Page EnterInvoiceNo(String s) {
        driver.element().type(txt_InvoiceNo,s);
        return this;
    }

    public SearchBooking_Page ClickSearch() {
        driver.element().click(Btn_Search);
        return this;
    }

    public SearchBooking_Page ClickReset() {
        driver.element().click(Btn_Reset);
        return this;
    }

    public SearchBooking_Page ClickThePagination() {
        driver.element().click(Btn_paginate);
        return this;
    }

    public SearchBooking_Page verifyThatTheResultShowsSuccessfully() {
        softAssert.assertTrue(driver.element().getText(Results).contains(testData.getTestData("ValidData.Result")),
                "Actual text does not contain expected value");
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanSearchByOrderID() {
        softAssert.assertEquals(driver.element().getText(orderID_Column),testData.getTestData("ValidData.OrderID"));
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanSearchByBookinReference() {
        softAssert.assertEquals(driver.element().getText(orderID_Column),testData.getTestData("ValidData.OrderID"));
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanSearchByTicketNo() {
        softAssert.assertEquals(driver.element().getText(orderID_Column),testData.getTestData("ValidData.OrderID"));
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanSearchByAirline_GDSPNR() {
        softAssert.assertEquals(driver.element().getText(orderID_Column),testData.getTestData("ValidData.OrderID"));
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanSearchByBranch() {
        softAssert.assertEquals(driver.element().getText(BranchName_Column),testData.getTestData("ValidData.BranchName"));
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanSearchByBranchAndSpecificAgency() {
        softAssert.assertEquals(driver.element().getText(AgencyName_Column),testData.getTestData("ValidData.AgencyName"));
        return this;
    }

    public SearchBooking_Page verifyThatTheUserCanPaginateTheResultSuccessfully() {
        softAssert.assertTrue(driver.element().getText(PaginateResults).contains(testData.getTestData("ValidData.PaginationResult")),
                "Actual text does not contain expected value");
        return this;
    }
}
