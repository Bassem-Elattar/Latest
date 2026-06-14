package AdminPages.BookingMidOffice.SearchBooking;

import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Login.TestBase_TC;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.*;
import utilities.DataUtils;

import java.sql.Time;

public class SearchBookingTCs  {
    private SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;

    @BeforeMethod
    public void login(){
        testData = new SHAFT.TestData.JSON("SearchBooking.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).AdminLogin();
    }

    @Test
    public void verifyThatUserCanSearchByBooking() throws InterruptedException {
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        Thread.sleep(300);
        new SearchBooking_Page(driver)
                .SelectBooking()
                .SelectBranch()
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
                .SelectBranch()
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
                .SelectBranch()
                .SelectValidStartDate(testData.getTestData("ValidData.BookingStartDate"))
                .SelectValidEndDate(testData.getTestData("ValidData.BookingEndDate"))
                .ClickSearch()
                .ClickThePagination()
                .verifyThatTheUserCanPaginateTheResultSuccessfully();
    }

    @AfterMethod
    public void navigateBackToURL() {
         new LogIn_Page(driver).ClickOnLogOuTButton();
//         driver.quit();

    }
}
