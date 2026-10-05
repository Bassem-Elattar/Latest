package AdminPages.BookingMidOffice.Refund;

import AdminPages.BookingMidOffice.Booking.Booking_TC;
import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.*;
import utilities.DataUtils;

// The actual flow orchestration (open booking -> take control -> refund -> Refunded Requests ->
// AR Ledger) lives in RefundPage.runRefundFlow() -- this class only wires up each booking variant
// to it, matching PortalPages.BookingMidOffice.Refund.Refund_TC's structure.
public class Refund_TC {
    private SHAFT.GUI.WebDriver driver;

    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).AdminLogin();
    }

    @AfterMethod
    public void logout() {
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }

    @Test
    public void verifyRefundFlowOneWay() throws Exception {
        String bookingReference = new Booking_TC(driver).bookOneWay();
        new RefundPage(driver).runRefundFlow(bookingReference, true);
    }

    @Test
    public void verifyRefundFlowRoundTrip() throws Exception {
        String bookingReference = new Booking_TC(driver).bookRoundTrip();
        new RefundPage(driver).runRefundFlow(bookingReference, true);
    }

    @Test
    public void verifyRefundFlowMultiCity() throws Exception {
        String bookingReference = new Booking_TC(driver).bookMultiCity();
        new RefundPage(driver).runRefundFlow(bookingReference, true);
    }

    @Test
    public void verifyRefundFlowPayAfterHoldOneWay() throws Exception {
        String bookingReference = new Booking_TC(driver).payAfterHoldOneWay();
        // This booking starts unlocked -- no "Take Control" step needed.
        new RefundPage(driver).runRefundFlow(bookingReference, false);
    }
}
