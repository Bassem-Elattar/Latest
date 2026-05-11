package AdminPages.DashBoard;

import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class DashBoard_Tc {
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private DashBoard_Page dashboard;


    @BeforeClass
    public void setup() {
        testData = new SHAFT.TestData.JSON("DashBoard.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        dashboard = new DashBoard_Page(driver);

        new LogIn_Page(driver).AdminLogin();
        new DashBoard_Common(driver).clickDashboard();
    }


    @Test
    public void Tc1_Search_Flights_Dashboard() throws InterruptedException {
        new DashBoard_Page(driver).searchValidFromDate(testData.getTestData("FromDate"),testData.getTestData("FromYear"),testData.getTestData("FromMonth"))
                .searchValidToDate(testData.getTestData("ToDate"),testData.getTestData("ToYear"),testData.getTestData("ToMonth"))
                .click_search()
                .verify_New_Booking_View();




    }
    @Test
    public void Tc2_search_using_Booking_Reference() throws InterruptedException {
        new DashBoard_Page(driver).searchValidFromDate(testData.getTestData("FromDate"),testData.getTestData("FromYear"),testData.getTestData("FromMonth"))
                .searchValidToDate(testData.getTestData("ToDate"),testData.getTestData("ToYear"),testData.getTestData("ToMonth"))
                .Booking_Reference()
                .click_search()
                .click_TicketedDocument()

                .verify_booking_reference();
        //Thread.sleep(30000);
    }
    @Test
    public void Tc3_search_using_Booking_Reference_Navigates_to_Itinerary()throws InterruptedException{
        new DashBoard_Page(driver).searchValidFromDate(testData.getTestData("FromDate"),testData.getTestData("FromYear"),testData.getTestData("FromMonth"))
                .searchValidToDate(testData.getTestData("ToDate"),testData.getTestData("ToYear"),testData.getTestData("ToMonth"))
                .Booking_Reference()
                .click_search()
                .click_TicketedDocument()
                .click_Booking_Reference();
        //driver.verifyThat().browser().url().isEqualTo("http://192.168.1.70/booking/booking-details/BRN103KIKORD260408ZWVJLH");
        driver.verifyThat().browser().url().isEqualTo(testData.getTestData("bookingDetailsURL_Itinerary"));

    }


    }