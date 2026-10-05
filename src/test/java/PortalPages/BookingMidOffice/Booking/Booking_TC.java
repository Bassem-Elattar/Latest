package PortalPages.BookingMidOffice.Booking;

import PortalPages.BookingMidOffice.Booking_Common;
import PortalPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import PortalPages.Login.Login_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.DataUtils;

import java.util.List;

import org.testng.asserts.SoftAssert;

// Portal clone of AdminPages.BookingMidOffice.Booking.Booking_TC, with every branch-selection
// step removed. Exposes bookRoundTrip()/bookMultiCity()/payAfterHoldOneWay() the same way Admin's
// does, for PortalPages.BookingMidOffice.Refund.Refund_TC to reuse when creating the booking to
// be refunded.
public class Booking_TC {

    SHAFT.GUI.WebDriver driver;
    SHAFT.TestData.JSON testData;
    SoftAssert softAssert = new SoftAssert();
    String NumberOfAdults;
    String NumberOfChildren;
    String NumberOfInfants;
    String source;
    String destination;
    String dayOfFirstJourney;
    String monthOfFirstJourney;
    String yearOfFirstJourney;
    String PassengerPaxTitle;
    String adultDob;
    String childDob;
    String infantDob;
    String PassengerPaxExpiryDate;
    String PassengerPaxNationality;
    String PassengerPaxEmail;
    String PassengerPaxPhone;
    String SecondDestination;

    public Booking_TC() {
    }

    public Booking_TC(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        loadTestData();
    }

    @BeforeMethod
    public void sign() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));

        new Login_Page(driver).PortalLogin();
        loadTestData();
    }

    private void loadTestData() {
        testData = new SHAFT.TestData.JSON("searchBookingBrData.json");
        NumberOfAdults = testData.getTestData("NumberOfAdults");
        NumberOfChildren = testData.getTestData("NumberOfChildren");
        NumberOfInfants = testData.getTestData("NumberOfInfants");
        source = testData.getTestData("source");
        destination = testData.getTestData("destination");
        dayOfFirstJourney = testData.getTestData("JourneyDay");
        monthOfFirstJourney = testData.getTestData("JourneyMonth");
        yearOfFirstJourney = testData.getTestData("JourneyYear");
        SecondDestination = testData.getTestData("SecondDestination");
        testData = new SHAFT.TestData.JSON("PassengerPaxDetails.json");
        PassengerPaxTitle = testData.getTestData("Title");
        adultDob = testData.getTestData("DateOfBirth");
        childDob = testData.getTestData("ChildDateOfBirth");
        infantDob = testData.getTestData("InfantDateOfBirth");
        PassengerPaxExpiryDate = testData.getTestData("ExpiryDate");
        PassengerPaxNationality = testData.getTestData("Nationality");
        PassengerPaxEmail = testData.getTestData("Email");
        PassengerPaxPhone = testData.getTestData("Phone");
    }

    public String payAfterHoldOneWay() throws Exception {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookFlight();
        searchBookingBranch
                .AddStartingFrom(source).AddGoingTo(destination)
                .SelectDateOfJourney(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        searchBookingBranch.CloseTheSideMenuInfo();
        String FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight().proceedIfBrandedFareExists();
        String FareBreakDown = searchBookingBranch.FareBreakDown();

        searchBookingBranch.assertContains(SegmentData, FlightCard, softAssert);
        searchBookingBranch.assertContains(FareData, FareBreakDown, softAssert);

        new PaxDetailsPage(driver).fillOnePassengerDetails(PassengerPaxTitle,
                adultDob,
                childDob,
                infantDob,
                PassengerPaxEmail,
                PassengerPaxPhone,
                PassengerPaxExpiryDate,
                PassengerPaxNationality).SelectTermsAndConditions().clickNextIfDisplayed().handlePassengerAncillaries(NumberOfAdults, NumberOfChildren).clickOnHold().AssertThatTicketIsHoldSuccessfully();
        String bookingReference = searchBookingBranch.GetBookingReference();
        searchBookingBranch.addBookingReference(bookingReference);

        new Booking_Common(driver).clickMyBookings();
        new SearchBooking_Page(driver)
                .SelectCurrentStartDate()
                .SelectCurrentEndDate()
                .EnterBookingReference(bookingReference)
                .ClickSearch()
                .verifyThatTheUserCanSearchByBookinReference();
        searchBookingBranch.PayAfterHoldFlow();
        searchBookingBranch.SuccessPayAfterHoldAssertion();

        return bookingReference;
    }

    @Test
    public void PayAfterHoldOneWay() throws Exception {
        payAfterHoldOneWay();
    }

    public String bookRoundTrip() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookFlight();
        searchBookingBranch
                .SelectRoundTrip()
                .AddStartingFromRoundTrip(source).AddGoingToRoundTrip(destination)
                .SelectFirstDateOfTrip(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        searchBookingBranch.CloseTheSideMenuInfo();
        String FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight().proceedIfBrandedFareExists();
        String FareBreakDown = searchBookingBranch.FareBreakDown();

        searchBookingBranch.assertContains(SegmentData, FlightCard, softAssert);
        searchBookingBranch.assertContains(FareData, FareBreakDown, softAssert);

        new PaxDetailsPage(driver).fillOnePassengerDetails(PassengerPaxTitle,
                adultDob,
                childDob,
                infantDob,
                PassengerPaxEmail,
                PassengerPaxPhone,
                PassengerPaxExpiryDate,
                PassengerPaxNationality).SelectTermsAndConditions().clickNextIfDisplayed().handlePassengerAncillaries(NumberOfAdults, NumberOfChildren).payAndBook().AssertThatTicketIsHoldSuccessfully();

        return searchBookingBranch.GetBookingReference();
    }

    @Test
    public void BookRoundTrip() throws Exception {
        bookRoundTrip();
    }

    public String bookOneWay() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookFlight();
        searchBookingBranch
                .AddStartingFrom(source).AddGoingTo(destination)
                .SelectDateOfJourney(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        searchBookingBranch.CloseTheSideMenuInfo();
        String FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight().proceedIfBrandedFareExists();
        String FareBreakDown = searchBookingBranch.FareBreakDown();

        searchBookingBranch.assertContains(SegmentData, FlightCard, softAssert);
        searchBookingBranch.assertContains(FareData, FareBreakDown, softAssert);

        new PaxDetailsPage(driver).fillOnePassengerDetails(PassengerPaxTitle,
                adultDob,
                childDob,
                infantDob,
                PassengerPaxEmail,
                PassengerPaxPhone,
                PassengerPaxExpiryDate,
                PassengerPaxNationality).SelectTermsAndConditions().clickNextIfDisplayed().handlePassengerAncillaries(NumberOfAdults, NumberOfChildren).payAndBook().AssertThatTicketIsHoldSuccessfully();

        return searchBookingBranch.GetBookingReference();
    }

    @Test
    public void BookOneWay() throws Exception {
        bookOneWay();
    }

    public String bookMultiCity() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookFlight();
        searchBookingBranch
                .SelectMultiCity()
                .AddStartingFromMultiCity(source).AddGoingToMultiCity(destination).AddGoingToSecondDestinationMultiCity(SecondDestination)
                .SelectFirstDateOfTrip(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
        List<String> SegmentData = searchBookingBranch.SegmentDetails();
        List<String> FareData = searchBookingBranch.FareDetails();
        searchBookingBranch.CloseTheSideMenuInfo();
        String FlightCard = searchBookingBranch.FlightCard();
        searchBookingBranch.BookFirstFlight().proceedIfBrandedFareExists();
        String FareBreakDown = searchBookingBranch.FareBreakDown();

        searchBookingBranch.assertContains(SegmentData, FlightCard, softAssert);
        searchBookingBranch.assertContains(FareData, FareBreakDown, softAssert);

        new PaxDetailsPage(driver).fillOnePassengerDetails(PassengerPaxTitle,
                adultDob,
                childDob,
                infantDob,
                PassengerPaxEmail,
                PassengerPaxPhone,
                PassengerPaxExpiryDate,
                PassengerPaxNationality).SelectTermsAndConditions().clickNextIfDisplayed().handlePassengerAncillaries(NumberOfAdults, NumberOfChildren).payAndBook().AssertThatTicketIsHoldSuccessfully();

        return searchBookingBranch.GetBookingReference();
    }

    @Test
    public void BookMultiCity() throws Exception {
        bookMultiCity();
    }

    @AfterMethod
    public void Reload() {
        new AdminPages.Login.LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
