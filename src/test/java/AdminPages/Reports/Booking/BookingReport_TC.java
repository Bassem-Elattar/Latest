package AdminPages.Reports.Booking;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Reports.Reports_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import org.openqa.selenium.interactions.Actions;
import utilities.DataUtils;
import utilities.JsonDataUtil;

public class BookingReport_TC {

    BookingReport bookingReport;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();
        bookingReport = new BookingReport(driver);
    }

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

        @Test(dataProvider = "JsonProvider")
        public void BookingReport(Map <String, String> Search) throws InterruptedException {
            new Reports_Common(driver).clickReports().clickBooking();
            bookingReport = new BookingReport(driver);
            String BranchName = Search.get("BranchName");
            String Email = Search.get("Email");
            String FromBookingDate = Search.get("FromBookingDate");
            String ToBookingDate = Search.get("ToBookingDate");
            String FromYear = Search.get("FromYear");
            String FromMonth = Search.get("FromMonth");
            String ToYear = Search.get("ToYear");
            String ToMonth = Search.get("ToMonth");
            String ClientName = Search.get("ClientName");
            String PhoneNumber = Search.get("PhoneNumber");
            String InvoiceNumber = Search.get("InvoiceNumber");
            String BookingReference = Search.get("BookingReference");
            String AirlinePNR = Search.get("AirlinePNR");
            String TicketStatus = Search.get("TicketStatus");
            bookingReport.SearchValidBranch(BranchName);
            bookingReport.FillMail(Email);
            bookingReport.searchValidFromDate(FromBookingDate,FromYear,FromMonth);
            bookingReport.searchValidToDate(ToBookingDate,ToYear,ToMonth);
            bookingReport.FillClientName(ClientName);
            bookingReport.FillPhoneNumber(PhoneNumber);
            bookingReport.ShowAdvance();
            bookingReport.FillBookingReference(BookingReference);
            bookingReport.FillInvoiceNumber(InvoiceNumber);
            bookingReport.FillAirlinePNR(AirlinePNR);
            bookingReport.ChooseBookingStatus(TicketStatus);
            Actions a = new Actions(driver.getDriver());
            WebElement an=driver.getDriver().findElement( bookingReport.Locator);
            a.moveToElement(an).click().build().perform();
            bookingReport.Submit();
            bookingReport.performAssertions();
            Validations.verifyThat().element(bookingReport.DataReturn).isVisible();
        }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
    }



