package AdminPages.DashBoard;

import AdminPages.BookingMidOffice.Booking_Common;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class DashBoard_Common {
    SHAFT.GUI.WebDriver driver;
    private final By btn_DashBoard = By.xpath("//a[@id='p-tabpanel-0-label']");

    public DashBoard_Common(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
    }
    public DashBoard_Common clickBookingMidOffice() {
        driver.element().click(btn_DashBoard);
        return this;
    }

    public void clickDashboard() {
    }
}
