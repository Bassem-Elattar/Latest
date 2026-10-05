package PortalPages.BookingMidOffice.Refund;

import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
import PortalPages.BookingMidOffice.Booking.Booking_TC;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.annotations.*;
import utilities.DataUtils;

// Agency/Portal online-refund flow, mirroring AdminPages.BookingMidOffice.Refund.Refund_TC's 4
// booking variants. Unlike Admin, the agent has no lock/take-control step and refunds directly;
// depending on the AGENCY_AUTO_REFUND_ENABLED admin setting the refund either posts immediately
// ("Refunded") or lands as "Pending" and has to be approved (or, in the Reject variants, rejected)
// from Admin's Refunded Request page before the Portal reflects the final state.
//
// The actual flow orchestration (Portal submission -> Admin cross-check/action -> Portal
// reflecting the final state) lives in RefundPage.runRefundFlow()/runRejectRefundFlow() -- this
// class only wires up each booking variant to one of those two flows.
public class Refund_TC {
    private SHAFT.GUI.WebDriver driver;

    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
    }

    @AfterMethod
    public void logout() {
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }

    @Test
    public void verifyRefundFlowOneWay() throws Exception {
        String bookingReference = new Booking_TC(driver).bookOneWay();
        new RefundPage(driver).runRefundFlow(bookingReference);
    }

    @Test
    public void verifyRefundFlowRoundTrip() throws Exception {
        String bookingReference = new Booking_TC(driver).bookRoundTrip();
        new RefundPage(driver).runRefundFlow(bookingReference);
    }

    @Test
    public void verifyRefundFlowMultiCity() throws Exception {
        String bookingReference = new Booking_TC(driver).bookMultiCity();
        new RefundPage(driver).runRefundFlow(bookingReference);
    }

    @Test
    public void verifyRefundFlowPayAfterHoldOneWay() throws Exception {
        String bookingReference = new Booking_TC(driver).payAfterHoldOneWay();
        new RefundPage(driver).runRefundFlow(bookingReference);
    }

    @Test
    public void verifyRejectRefundFlowOneWay() throws Exception {
        String bookingReference = new Booking_TC(driver).bookOneWay();
        new RefundPage(driver).runRejectRefundFlow(bookingReference);
    }

    @Test
    public void verifyRejectRefundFlowRoundTrip() throws Exception {
        String bookingReference = new Booking_TC(driver).bookRoundTrip();
        new RefundPage(driver).runRejectRefundFlow(bookingReference);
    }

    @Test
    public void verifyRejectRefundFlowMultiCity() throws Exception {
        String bookingReference = new Booking_TC(driver).bookMultiCity();
        new RefundPage(driver).runRejectRefundFlow(bookingReference);
    }

    @Test
    public void verifyRejectRefundFlowPayAfterHoldOneWay() throws Exception {
        String bookingReference = new Booking_TC(driver).payAfterHoldOneWay();
        new RefundPage(driver).runRejectRefundFlow(bookingReference);
    }
}
