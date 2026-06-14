package AdminPages.BookingMidOffice.MyQuotes;

import AdminPages.BookingMidOffice.Booking.PaxDetailsPage;
import AdminPages.BookingMidOffice.Booking.SearchBookingBranch;
import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class SaveQuote extends TestBase_TC {
    SHAFT.TestData.JSON testData;
    private LogIn_Page logIn;
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
    String PassengerPaxFirstName;
    String PassengerPaxLastName;
    String PassengerPaxDateOfBirth;
    String PassengerPaxDocumentNumber;
    String PassengerPaxExpiryDate;
    String PassengerPaxNationality;
    String PassengerPaxEmail;
    String PassengerPaxPhone;
    public SaveQuote() throws FileNotFoundException {
    }
    @Override
    @BeforeTest
    public void setupBrowse(){
    }
    @BeforeMethod
    public void Login(){
        driver = new SHAFT.GUI.WebDriver(
                DriverFactory.DriverType.CHROME
        );
        driver.browser().navigateToURL("http://192.168.1.70");
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

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
        PassengerPaxFirstName = testData.getTestData("FirstName");;
        PassengerPaxLastName = testData.getTestData("LastName");;
        PassengerPaxDateOfBirth = testData.getTestData("DateOfBirth");;
        PassengerPaxDocumentNumber = testData.getTestData("DocumentNumber");;
        PassengerPaxExpiryDate = testData.getTestData("ExpiryDate");;
        PassengerPaxNationality = testData.getTestData("Nationality");;
        PassengerPaxEmail = testData.getTestData("Email");
        PassengerPaxPhone = testData.getTestData("Phone");
    }
    @Test
    public void setSearchBookingAndSaveQuoteFromSearchPage() throws InterruptedException {
        //Open Search Page And Search For A Flight
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
            //Save Quote
            searchBookingBranch.selectFirstFlight().
                    SaveQuote().
                    ConfirmSaveQuote(QuoteNewUserFirstName,QuoteNewUserLastName,QuoteNewUserEmail,QuoteNewUserPhone).
                    AssertThatQuoteSaved();
        }
    }
    @Test
    public void setSearchBookingAndSaveQuoteFromPaxDetailsPage() throws InterruptedException {
        //Open Search Page And Search For A Flight
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
            //Save Quote
            searchBookingBranch.BookFirstFlight();
            new PaxDetailsPage(driver).
                    fillOnePassengerDetails(PassengerPaxTitle,
                                            PassengerPaxFirstName,
                                            QuoteNewUserLastName,
                                            PassengerPaxDateOfBirth,
                                            PassengerPaxEmail,
                                            PassengerPaxPhone,
                                            PassengerPaxDocumentNumber,
                                            PassengerPaxExpiryDate,
                                            PassengerPaxNationality)
                    .SaveQuote()
                    .AssertThatQuoteSaved();
        }
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
