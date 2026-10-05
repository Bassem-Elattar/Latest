package AdminPages.BookingMidOffice;

import AdminPages.Master.Master_Common;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class Booking_Common {
    SHAFT.GUI.WebDriver driver;
    private final By btn_BookingMidOffice = By.xpath("//span[text()='Booking-Mid Office']");
    private final By btn_Sub_BookingMidOffice = By.xpath("//a[@class='ng-star-inserted']");
    private final By btn_SearchBooking = By.xpath("//a[@href='/booking/search-booking']");
    private final By btn_Booking = By.xpath("//a[@href='/booking/booking']");
    private final By btn_MyQuotes = By.xpath("//a[@href='/booking/search-my-quotes']");
    private final By btn_ManualBookingInvoice = By.xpath("//a[@href='/booking/manual-booking-invoice']");
    private final By ShowMoreBtn = By.xpath("(//button[@type='button'])[1]");

    public Booking_Common(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
    }
    public Booking_Common clickBookingMidOffice(){
        driver.element().click(btn_BookingMidOffice);
        return this;
    }
    public Booking_Common ShowMoreMenu(){
        driver.element().click(ShowMoreBtn);
        return this;
    }
    public Booking_Common click_Sub_BookingMidOffice (){
        driver.element().click(btn_Sub_BookingMidOffice );
        return this;
    }
    public Booking_Common clickSearchBooking(){
        driver.element().click(btn_SearchBooking);
        return this;
    }
    public Booking_Common clickBooking(){
        driver.element().click(btn_Booking);
        return this;
    }
    public Booking_Common clickMyQuotes(){
        driver.element().click(btn_MyQuotes);
        return this;
    }
    public Booking_Common clickMyManualBookingInvoice(){
        driver.element().click(btn_ManualBookingInvoice);
        return this;
    }
}
