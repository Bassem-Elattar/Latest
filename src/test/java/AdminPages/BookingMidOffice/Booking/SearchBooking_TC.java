package AdminPages.BookingMidOffice.Booking;
import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import AdminPages.Reports.Statement.State;
import com.github.javafaker.Faker;
import com.shaft.driver.SHAFT;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import org.testng.asserts.SoftAssert;

public class SearchBooking_TC extends TestBase_TC {

    private SearchBookingBranch Booking;
    private LogIn_Page logIn;
    Faker faker = new Faker();
    SHAFT.TestData.JSON testData;
    SoftAssert softAssert = new SoftAssert();

    public SearchBooking_TC() throws FileNotFoundException {
    }

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        Booking = new SearchBookingBranch(driver);
        testData = new SHAFT.TestData.JSON("searchBookingBrData.json");
    }

    @Test
    public void SearchOneWay() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookingMidOffice();
        //new SearchBooking_Page(driver).
        searchBookingBranch.SelectBranch(testData.getTestData("brName")).
                AddStartingFrom(testData.getTestData("source")).AddGoingTo(testData.getTestData("destination"))
                .SelectDateOfJourney(testData.getTestData("JourneyDay"), testData.getTestData("JourneyYear"), testData.getTestData("JourneyMonth"))
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(testData.getTestData("NumberOfAdults"))).SelectNumberOfChildren(Integer.parseInt(testData.getTestData("NumberOfChildren"))).SelectNumberOfInfant(Integer.parseInt(testData.getTestData("NumberOfInfants"))).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        List<String> BaggageData = searchBookingBranch.BaggageInfo();
        searchBookingBranch.CloseTheSideMenuInfo();
        List<String> FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight();
        String FareBreakDown = searchBookingBranch.FareBreakDown();
        System.out.println(FareBreakDown);
        softAssert.assertTrue(
                FareData.contains(FareBreakDown),
                "Actual value [" + FareData + "] does not contain expected value [" + FareBreakDown + "]"
        );
//        List<String> paxSegmentDetails = searchBookingBranch.PaxSegmentDetails();
//        System.out.println(paxSegmentDetails);

//        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
//
//// This script returns true if an element containing that exact text exists on the page
//        Thread.sleep(9000);
//        boolean noResults = (Boolean) js.executeScript(
//                "return Array.from(document.querySelectorAll('p')).some(p => p.textContent.includes('No, search results available.'));"
//        );
//
//        if (noResults) {
//            System.out.println("No search results message");
//            searchBookingBranch.clickOnSearchButton();
//        } else {
//            System.out.println("There is search results");
//        }
    }
}