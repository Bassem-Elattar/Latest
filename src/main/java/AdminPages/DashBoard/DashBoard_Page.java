package AdminPages.DashBoard;
import com.shaft.driver.SHAFT;
import junit.framework.TestCase;
import net.bytebuddy.asm.MemberSubstitution;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import static org.openqa.selenium.By.xpath;
public class DashBoard_Page {
    public SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private SoftAssert softAssert = new SoftAssert();

    public DashBoard_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("DashBoard.json");
    }


    private final By from_booking_date = By.id("id-FromBookingDate");
    private final By to_booking_date = By.xpath("//input[@id='id-ToBookingDate']");
    private final By txt_Booking_Reference = By.xpath("//*[@id=\"id-BookingReference\"]");
    private final By Search_in_grid = By.xpath("//button[contains(@class, \"p-button-raised\")]");
    private final By TicketedDocument = By.xpath("//div[@class='label' and normalize-space()='Ticketed Document']/following-sibling::div");
    private final By Booking_Reference = By.xpath("//*[@id=\"pr_id_4-table\"]/tbody/tr[1]/td[2]");
    //private final By click_from_data = By.xpath("/html/body/ndc-root/ndc-layout/div/div[3]/div[1]/div/ndc-dashboard/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/ndc-fg-input[3]/ndc-fg-date-picker-input/span/p-calendar/span/button");
    private final By New_BookingTEXT = By.xpath("//div[text()=\"New Booking\"]");
    private final By Cli_toBookingReference = By.xpath("//*[@role=\"table\"]/tbody/tr/td[2]/a[1]");
    By Year = xpath("//button[normalize-space()='2026']");

    /*public DashBoard_Page SELECT_FROM_DATE() {

        WebElement dateField = driver.getDriver().findElement(from_booking_date);

        dateField.click();
        dateField.sendKeys(Keys.CONTROL + "a");
        dateField.sendKeys(Keys.DELETE);
        dateField.sendKeys(testData.getTestData("fromdate"));
        driver.element().click(click_from_data);
        return this;
    }*/

    public DashBoard_Page click_search() {
        driver.element().click(Search_in_grid);
        return this;
    }
    public DashBoard_Page verify_New_Booking_View () {
        //softAssert.assertEquals(driver.element().getText(New_BookingTEXT),"New Booking");
        softAssert.assertEquals(driver.element().getText(New_BookingTEXT), testData.getTestData("verify_New_Booking_View"));
        softAssert.assertAll();


        return this;

    }
    public DashBoard_Page Booking_Reference(){
        //driver.element().type(txt_Booking_Reference ,"NDCEG-BR-F260408iKY5df");
        driver.element().type(txt_Booking_Reference, testData.getTestData("bookingReference"));
        return this;

    }
    public DashBoard_Page verify_booking_reference(){
        // softAssert.assertEquals(driver.element().getText(Booking_Reference),"NDCEG-BR-F260408iKY5df");
        softAssert.assertEquals(driver.element().getText(Booking_Reference), testData.getTestData("bookingReference"));
        softAssert.assertAll();
        return this;
    }
    public DashBoard_Page click_TicketedDocument(){
        driver.element().click(TicketedDocument);
        return this;
    }
    public DashBoard_Page click_Booking_Reference(){
        driver.element().click(Cli_toBookingReference);
        return this;
    }
    public DashBoard_Page searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(from_booking_date);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;
    }

    public DashBoard_Page searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(to_booking_date);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
        return this;
    }

}



