package AdminPages.BookingMidOffice.MyQuotes;

import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class QuoteSearch {
    SHAFT.TestData.JSON testData;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    String QuoteID;
    String QuoteBranchName;
    String QuoteAgency;
    String QuotePassangerName;
    String QuoteEmailID;
    String CreationDateFrom;
    String CreationDateTo;
    String TravelDateFrom;
    String TravelDateTo;
    String QuoteStatus;
    String BranchNameToRejectRelatedQuote;
    String QuoteIdToBeRejected;
    @BeforeClass
    public void Login(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        logIn = new LogIn_Page(driver);
        logIn.AdminLogin();

        testData = new SHAFT.TestData.JSON("QuoteSearchData.json");
        QuoteBranchName = testData.getTestData( "Branch");
        QuoteAgency = testData.getTestData( "Agency");
        QuoteID = testData.getTestData( "QuoteID");
        QuotePassangerName = testData.getTestData( "PassengerName");
        QuoteEmailID = testData.getTestData( "EmailID");
        CreationDateFrom = testData.getTestData( "CreationDateFrom");
        CreationDateTo = testData.getTestData( "CreationDateTo");
        TravelDateFrom = testData.getTestData( "TravelDateFrom");
        TravelDateTo = testData.getTestData( "TravelDateTo");
        QuoteStatus = testData.getTestData( "QuoteStatus");
        BranchNameToRejectRelatedQuote = testData.getTestData("BranchNameToRejectRelatedQuote");
        QuoteIdToBeRejected = testData.getTestData("QuoteIdToBeRejected");
        new Booking_Common(driver).
                clickBookingMidOffice().
                ShowMoreMenu().
                click_Sub_BookingMidOffice().
                clickMyQuotes();
    }
    @Test(priority = 1)
    public void SearchByBranchNameOnly() {

        boolean result =
                new MyQuotesPage(driver)
                        .SelectBranch(QuoteBranchName)
                        .QuoteSearch()
                        .isQuoteGridDisplayed();

        Assert.assertTrue(result);
    }
    @Test(priority = 2)
    public void SearchByBranchNameAndAgencyName(){
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndAgentName(QuoteBranchName,QuoteAgency).
                        VerifyThatOnlySelectedAgencyDataAreDisplayed("Agent Name",QuoteAgency);
        Assert.assertTrue(result);
    }
    @Test(priority = 3)
    public void SearchByBranchNameAndQuoteNumber()
    {
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndQuoteNumber(QuoteBranchName,QuoteID).
                        VerifyThatQuoteDisplayed(QuoteID);
        Assert.assertTrue(result);
    }
    @Test(priority = 4)
    public void SearchByBranchNameAndPassengerName()
    {
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndPassengerName(QuoteBranchName,QuotePassangerName).
                        VerifyThatQuoteDisplayed(QuotePassangerName);
        Assert.assertTrue(result);
    }
    @Test(priority = 5)
    public void SearchByBranchNameAndEmail()
    {
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndPassengerEmail(QuoteBranchName,QuoteEmailID).
                        VerifyThatQuoteDisplayed(QuoteEmailID);
        Assert.assertTrue(result);
    }
    @Test(priority = 6)
    public void SearchByBranchNameAndCreationDate(){
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndCreationData(QuoteBranchName,CreationDateFrom,CreationDateTo).
                        VerifyThatOnlyQuotesWithinCreationOrTravelDateAreDisplayed("Date Created",CreationDateFrom,CreationDateTo);
        Assert.assertTrue(result);
    }
    @Test(priority = 7)
    public void SearchByBranchNameAndTravelDate()
    {
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndTravelData(QuoteBranchName,TravelDateFrom,TravelDateTo).
                        VerifyThatOnlyQuotesWithinCreationOrTravelDateAreDisplayed("Travel Date",TravelDateFrom,TravelDateTo);
        Assert.assertTrue(result);
    }
    @Test(priority = 8)
    public void SearchByBranchNameAndQuoteStatus()
    {
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndStatus(QuoteBranchName,QuoteStatus).
                        VerifyThatOnlySelectedStatusDataAreDisplayed("Status",QuoteStatus);
        Assert.assertTrue(result);
    }
    @Test(priority = 9)
    public void SearchByAllFiltersAtOnceWithCreationDate() {

        MyQuotesPage myQuotesPage =
                new MyQuotesPage(driver)
                        .SearchByAllFiltersWithCreationDate(
                                QuoteBranchName,
                                QuoteAgency,
                                QuoteID,
                                QuotePassangerName,
                                QuoteEmailID,
                                CreationDateFrom,
                                CreationDateTo,
                                QuoteStatus
                        );

        boolean result = myQuotesPage.VerifyThatOnlySelectedAgencyDataAreDisplayed("Agent Name",QuoteAgency)
                && myQuotesPage.VerifyThatQuoteDisplayed(QuoteID)
                && myQuotesPage.VerifyThatQuoteDisplayed(QuotePassangerName)
                && myQuotesPage.VerifyThatQuoteDisplayed(QuoteEmailID)
                && myQuotesPage.VerifyThatOnlyQuotesWithinCreationOrTravelDateAreDisplayed("Date Created",CreationDateFrom,CreationDateTo)
                && myQuotesPage.VerifyThatOnlySelectedStatusDataAreDisplayed("Status",QuoteStatus);
        Assert.assertTrue(result);
    }
    @Test(priority = 10)
    public void SearchByAllFiltersAtOnceWithTravelDate() {

        MyQuotesPage myQuotesPage =
                new MyQuotesPage(driver)
                        .SearchByAllFiltersWithTravelDate(
                                QuoteBranchName,
                                QuoteAgency,
                                QuoteID,
                                QuotePassangerName,
                                QuoteEmailID,
                                TravelDateFrom,
                                TravelDateTo,
                                QuoteStatus
                        );

        boolean result = myQuotesPage.VerifyThatOnlySelectedAgencyDataAreDisplayed("Agent Name",QuoteAgency)
                && myQuotesPage.VerifyThatQuoteDisplayed(QuoteID)
                && myQuotesPage.VerifyThatQuoteDisplayed(QuotePassangerName)
                && myQuotesPage.VerifyThatQuoteDisplayed(QuoteEmailID)
                && myQuotesPage.VerifyThatOnlyQuotesWithinCreationOrTravelDateAreDisplayed("Date Created",CreationDateFrom,CreationDateTo)
                && myQuotesPage.VerifyThatOnlySelectedStatusDataAreDisplayed("Status",QuoteStatus);
        Assert.assertTrue(result);
    }
    @Test(priority = 11)
    public void CheckThatQuotePopupIsOpened() {
        boolean result =
                new MyQuotesPage(driver)
                        .SelectBranch(QuoteBranchName)
                        .QuoteSearch()
                        .VerifyThatQuotePopupDisplayed(QuoteID);

        Assert.assertTrue(result);
    }
    @Test(priority = 12)
    public void RejectQuote(){
        boolean result =
                new MyQuotesPage(driver).
                        SearchByBranchAndQuoteNumber(BranchNameToRejectRelatedQuote,QuoteIdToBeRejected).
                        RejectQuote(QuoteIdToBeRejected).
                        ValidateQuoteStatus(QuoteIdToBeRejected,"Status","Reject");
        Assert.assertTrue(result);
    }
    @AfterMethod
    public void navigateBackToURL() {
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}