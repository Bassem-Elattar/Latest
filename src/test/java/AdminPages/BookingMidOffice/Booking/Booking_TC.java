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
import java.util.List;
import java.util.Map;

public class Booking_TC extends TestBase_TC {

    private SearchBookingBranch Booking;
    SHAFT.TestData.JSON testData;
    private LogIn_Page logIn;
    Faker faker = new Faker();
    String NumberOfAdults;
    String NumberOfChildren;
    String NumberOfInfants;
    String source;
    String destination;
    String BranchName;
    String dayOfFirstJourney;
    String monthOfFirstJourney;
    String yearOfFirstJourney;
    String QuoteNewUserFirstName;
    String QuoteNewUserLastName;
    String QuoteNewUserEmail;
    String QuoteNewUserPhone;
    String PassengerPaxTitle;
    String adultDob;
    String childDob;
    String infantDob;
    String PassengerPaxExpiryDate;
    String PassengerPaxNationality;
    String PassengerPaxEmail;
    String PassengerPaxPhone;



    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        Booking = new SearchBookingBranch(driver);
        testData = new SHAFT.TestData.JSON("searchBookingBrData.json");
        NumberOfAdults = testData.getTestData("NumberOfAdults");
        NumberOfChildren = testData.getTestData( "NumberOfChildren");
        NumberOfInfants = testData.getTestData("NumberOfInfants");
        source = testData.getTestData( "source");
        destination = testData.getTestData("destination");
        BranchName = testData.getTestData( "brName");
        dayOfFirstJourney = testData.getTestData( "JourneyDay");
        monthOfFirstJourney = testData.getTestData( "JourneyMonth");
        yearOfFirstJourney = testData.getTestData( "JourneyYear");
        testData = new SHAFT.TestData.JSON("QuoteData.json");
        QuoteNewUserFirstName = testData.getTestData("QuoteUserFirstName");
        QuoteNewUserLastName = testData.getTestData("QuoteUserLastName");
        QuoteNewUserEmail = testData.getTestData("QuoteNewUserEmail");
        QuoteNewUserPhone = testData.getTestData("QuoteNewUserPhone");
        testData = new SHAFT.TestData.JSON("PassengerPaxDetails.json");
        PassengerPaxTitle = testData.getTestData("Title");
        adultDob = testData.getTestData("DateOfBirth");
        childDob = testData.getTestData("ChildDateOfBirth");
        infantDob = testData.getTestData("InfantDateOfBirth");
        PassengerPaxExpiryDate = testData.getTestData("ExpiryDate");;
        PassengerPaxNationality = testData.getTestData("Nationality");;
        PassengerPaxEmail = testData.getTestData("Email");
        PassengerPaxPhone = testData.getTestData("Phone");
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
        //driver.browser().refreshCurrentPage();
        List<String> paxSegmentDetails = searchBookingBranch.PaxSegmentDetails();
        System.out.println(paxSegmentDetails);

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
    @Test
    public void Hold() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookingMidOffice();
        //new SearchBooking_Page(driver).
        searchBookingBranch.SelectBranch(BranchName).
                AddStartingFrom(source).AddGoingTo(destination)
                .SelectDateOfJourney(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        List<String> BaggageData = searchBookingBranch.BaggageInfo();
        searchBookingBranch.CloseTheSideMenuInfo();
        List<String> FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight().proceedIfBrandedFareExists();
        //driver.browser().refreshCurrentPage();
        List<String> paxSegmentDetails = searchBookingBranch.PaxSegmentDetails();
        System.out.println(paxSegmentDetails);
        new PaxDetailsPage(driver).fillOnePassengerDetails(PassengerPaxTitle,
                adultDob,
                childDob,
                infantDob,
                PassengerPaxEmail,
                PassengerPaxPhone,
                PassengerPaxExpiryDate,
                PassengerPaxNationality).SelectTermsAndConditions().clickOnHold().AssertThatTicketIsHoldSuccessfully();


    }
    @Test
    public void Book() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookingMidOffice();
        //new SearchBooking_Page(driver).
        searchBookingBranch.SelectBranch(BranchName).
                AddStartingFrom(source).AddGoingTo(destination)
                .SelectDateOfJourney(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        List<String> BaggageData = searchBookingBranch.BaggageInfo();
        searchBookingBranch.CloseTheSideMenuInfo();
        List<String> FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight().proceedIfBrandedFareExists();
        //driver.browser().refreshCurrentPage();
        List<String> paxSegmentDetails = searchBookingBranch.PaxSegmentDetails();
        System.out.println(paxSegmentDetails);
        new PaxDetailsPage(driver).fillOnePassengerDetails(PassengerPaxTitle,
                adultDob,
                childDob,
                infantDob,
                PassengerPaxEmail,
                PassengerPaxPhone,
                PassengerPaxExpiryDate,
                PassengerPaxNationality).SelectTermsAndConditions().payAndBook().AssertThatTicketIsHoldSuccessfully();


    }

}