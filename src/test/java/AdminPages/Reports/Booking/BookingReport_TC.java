package AdminPages.Reports.Booking;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Reports.Reports_Common;
import com.shaft.validation.Validations;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import org.openqa.selenium.interactions.Actions;
import utilities.JsonDataUtil;

public class BookingReport_TC extends TestBase {

    BookingReport bookingReport;
    private LogIn_Page logIn;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
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
            Thread.sleep(2000);
            String BranchName = Search.get("BranchName");
            //String AgencyName = Search.get("AgencyName");
            String Email = Search.get("Email");
            String FromBookingDate = Search.get("FromBookingDate");
            String ToBookingDate = Search.get("ToBookingDate");
            String FromYear = Search.get("FromYear");
            String FromMonth = Search.get("FromMonth");
            String ToYear = Search.get("ToYear");
            String ToMonth = Search.get("ToMonth");
            String ClientName = Search.get("ClientName");
            String PhoneNumber = Search.get("PhoneNumber");
            String TripsStartDate = Search.get("TripsStartDate");
            String TripsReturnDate = Search.get("TripsReturnDate");
            String InvoiceNumber = Search.get("InvoiceNumber");
            String BookingReference = Search.get("BookingReference");
            String AirlinePNR = Search.get("AirlinePNR");
            String TicketNumber = Search.get("TicketNumber");
            String TicketStatus = Search.get("TicketStatus");
            Thread.sleep(2000);
            bookingReport.searchValidBranch(BranchName);
            //bookingReport.searchValidAgency(AgencyName);
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
            Thread.sleep(2000);

        }
    }



