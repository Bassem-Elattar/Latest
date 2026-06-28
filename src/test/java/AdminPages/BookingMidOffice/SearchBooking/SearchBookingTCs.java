package AdminPages.BookingMidOffice.SearchBooking;

import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Login.TestBase_TC;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Time;

public class SearchBookingTCs extends TestBase_TC {
    private SHAFT.TestData.JSON testData;

    @BeforeTest
    public void login(){
        testData = new SHAFT.TestData.JSON("SearchBooking.json");
        new LogIn_Page(driver).ClickAdmin();
        new LogIn_Page(driver).ClickOnLoginButton();
    }


    @Test
    public void verifyThatUserCanSearchByBooking() throws InterruptedException {
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectBooking()
                .SelectBranch(testData.getTestData("ValidData.BranchName"))
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .ClickSearch()
                .verifyThatTheResultShowsSuccessfully();
    }

    @Test
    public void verifyThatUserCanSearchByOrderID(){
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectFlight()
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .EnterOrderID(testData.getTestData("ValidData.OrderID"))
                .ClickSearch()
                .verifyThatTheUserCanSearchByOrderID();
    }

    @Test
    public void verifyThatUserCanSearchByBookingReference(){
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectFlight()
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .EnterBookingReference(testData.getTestData("ValidData.BookingReference"))
                .ClickSearch()
                .verifyThatTheUserCanSearchByBookinReference();
    }

    @Test
    public void verifyThatUserCanSearchByTicketNo(){
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectFlight()
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .EnterTicketNo(testData.getTestData("ValidData.TicketNo"))
                .ClickSearch()
                .verifyThatTheUserCanSearchByTicketNo();
    }

    @Test
    public void verifyThatUserCanSearchByAirline_GDSPNR(){
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectFlight()
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .EnterGDSPNR(testData.getTestData("ValidData.GDSPNR"))
                .ClickSearch()
                .verifyThatTheUserCanSearchByAirline_GDSPNR();
    }

    @Test
    public void verifyThatUserCanSearchBySpecificAgency(){
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectBooking()
                .SelectBranch(testData.getTestData("ValidData.BranchName"))
                .SelectAgency()
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .ClickSearch()
                .verifyThatTheUserCanSearchByBranchAndSpecificAgency();
    }

    @Test
    public void verifyThatUserCanPaginateTheResult(){
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectBooking()
                .SelectBranch(testData.getTestData("ValidData.BranchName"))
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .ClickSearch()
                .ClickThePagination()
                .verifyThatTheUserCanPaginateTheResultSuccessfully();
    }

    @AfterMethod
    public void navigateBackToURL() {
        // Optionally close the browser
        // driver.quit();
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
