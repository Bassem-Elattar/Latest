package PortalPages.BookingMidOffice;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

// Confirmed live against the real Portal dashboard/sidebar (Portal has no "Booking-Mid Office"
// mega-menu like Admin's -- these are the persistent left-sidebar nav links).
public class Booking_Common {
    SHAFT.GUI.WebDriver driver;
    // The dashboard also has a "Book Flight" quick-access widget with the same visible text but a
    // different href ("/booking"), so matching on href against the sidebar link avoids
    // MultipleElementsFoundException.
    private final By btn_BookFlight = By.xpath("//a[@href='/booking/flight-search']");
    private final By btn_MyBookings = By.xpath("//a[@href='/myBooking']");

    public Booking_Common(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    public Booking_Common clickBookFlight() {
        driver.element().click(btn_BookFlight);
        return this;
    }

    public Booking_Common clickMyBookings() {
        driver.element().click(btn_MyBookings);
        return this;
    }
}
