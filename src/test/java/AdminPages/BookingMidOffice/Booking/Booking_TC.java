package AdminPages.BookingMidOffice.Booking;
import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.BookingMidOffice.SearchBooking.SearchBookingTCs;
import AdminPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import AdminPages.Reports.Statement.State;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.asserts.SoftAssert;

public class Booking_TC extends TestBase_TC {

    private SearchBookingBranch Booking;
    SHAFT.TestData.JSON testData;
    private LogIn_Page logIn;
    SoftAssert softAssert = new SoftAssert();
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
    String BookingReference;

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
        BookingReference = testData.getTestData("BookingReference");
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

//    @Test
//    public void SearchOneWay() throws InterruptedException {
//        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
//        new Booking_Common(driver).clickBookingMidOffice();
//        searchBookingBranch.SelectBranch(BranchName).
//                AddStartingFrom(source).AddGoingTo(destination)
//                .SelectDateOfJourney(dayOfFirstJourney, yearOfFirstJourney, monthOfFirstJourney)
//                .passengersDropDown()
//                .SelectNumberOfAdult(Integer.parseInt(NumberOfAdults)).SelectNumberOfChildren(Integer.parseInt(NumberOfChildren)).SelectNumberOfInfant(Integer.parseInt(NumberOfInfants)).clickOnSearchButton().OpenSideMenuInfo();
//
//        List<String> SegmentData = searchBookingBranch.SegmentDetails();
//        List<String> FareData = searchBookingBranch.FareDetails();
//        searchBookingBranch.CloseTheSideMenuInfo();
//        String FlightCard = searchBookingBranch.FlightCard();
//        searchBookingBranch.BookFirstFlight();
//        String FareBreakDown = searchBookingBranch.FareBreakDown();
//
//        searchBookingBranch.assertContains(SegmentData, FlightCard, softAssert);
//        searchBookingBranch.assertContains(FareData, FareBreakDown, softAssert);
//    }

    @Test
    public void Hold() throws Exception {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookingMidOffice();
        searchBookingBranch.SelectBranch(BranchName).
                AddStartingFrom(source).AddGoingTo(destination)
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
                PassengerPaxNationality).SelectTermsAndConditions().clickOnHold().AssertThatTicketIsHoldSuccessfully();
        String BookingReference = searchBookingBranch.GetBookingReference();
        searchBookingBranch.addBookingReference(BookingReference);
    }

    @Test
    public void PayAfterHold() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectFlight()
                .SelectBranch(BranchName)
                .SelectCurrentStartDate()
                .SelectCurrentEndDate()
                .EnterBookingReference(BookingReference)
                .ClickSearch()
                .verifyThatTheUserCanSearchByBookinReference();
        searchBookingBranch.PayAfterHoldFlow();
        searchBookingBranch.SuccessPayAfterHoldAssertion();
    }

    @Test
    public void Book() throws InterruptedException {
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookingMidOffice();
        searchBookingBranch.SelectBranch(BranchName).
                AddStartingFrom(source).AddGoingTo(destination)
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
                PassengerPaxNationality).SelectTermsAndConditions().payAndBook().AssertThatTicketIsHoldSuccessfully();
    }
}