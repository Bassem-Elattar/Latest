package AdminPages.BookingMidOffice.Booking;
import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import AdminPages.Reports.Statement.State;
import com.github.javafaker.Faker;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

import java.io.FileNotFoundException;
import java.util.Map;

public class SearchBooking_TC extends TestBase_TC {

   private SearchBookingBranch Booking;
   private LogIn_Page logIn;
    //DashboardPage dashObj;
    Faker faker = new Faker();
    SHAFT.TestData.JSON testData;

    String NumberOfAdults = DataUtils.getJsonData("searchBookingBrData", "NumberOfAdults");
    String NumberOfChildren = DataUtils.getJsonData("searchBookingBrData", "NumberOfChildren");
    String NumberOfInfants = DataUtils.getJsonData("searchBookingBrData", "NumberOfInfants");
    String source = DataUtils.getJsonData("searchBookingBrData", "source");
    String destination = DataUtils.getJsonData("searchBookingBrData", "destination");
    String BranchName = DataUtils.getJsonData("searchBookingBrData", "brName");
    String dayOfFirstJourney = DataUtils.getJsonData("searchBookingBrData", "JourneyDay");
    String monthOfFirstJourney = DataUtils.getJsonData("searchBookingBrData", "JourneyMonth");
    String yearOfFirstJourney = DataUtils.getJsonData("searchBookingBrData", "JourneyYear");
    String searchLimit = DataUtils.getJsonData("SearchBooking", "searchLimit");

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
        searchBookingBranch.SelectBranch(BranchName).
                AddStartingFrom(source).AddGoingTo(destination)
                .SelectDateOfJourney(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton();

        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();

// This script returns true if an element containing that exact text exists on the page
        Thread.sleep(9000);
        boolean noResults = (Boolean) js.executeScript(
                "return Array.from(document.querySelectorAll('p')).some(p => p.textContent.includes('No, search results available.'));"
        );

        if (noResults) {
            System.out.println("No search results message");
            searchBookingBranch.clickOnSearchButton();
        } else {
            System.out.println("There is search results");
        }
    }
}
