package AdminPages.BookingMidOffice.Booking;

import com.shaft.driver.SHAFT;
import org.junit.Assert;
import org.openqa.selenium.By;

public class SearchBookingBranch {
    public SearchBookingBranch(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By BookingMidOffice = By.xpath("//a[@class='mid-office-1'and contains(text(),'Booking-Mid Office')]");
    By Booking = By.xpath("//li[@id='Booking']");
    By BranchList = By.xpath( "//p-dropdown[@formcontrolname='branch']");
    By StartingFrom = By.xpath("//div[contains(text(),'From *')]");
    By selectFrom = By.xpath("//input[@placeholder='Search']");
    By fromCheckBox = By.xpath("(//div[@class='p-checkbox-box'])[1]");
    By GoingTo = By.xpath("//span[normalize-space()='To *']");
    By selectTo = By.xpath("//div[contains(@class,'p-dropdown-header')]//input[@placeholder='Search']");
    By DataPicker = By.xpath("//input[@placeholder='YYYY-MM-DD *']");
    By noOfAdults = By.xpath("(//p-button[@icon='pi pi-plus'])[1]");
    By noOfChildren = By.xpath("(//p-button[@icon='pi pi-plus'])[2]");
    By noOfInfants = By.xpath("(//p-button[@icon='pi pi-plus'])[3]");
    By SearchButton = By.xpath("//button[@class='p-element btn search-btn p-button p-component']");
    By WhiteMarkup = By.xpath("(//button[@class='flight-action-btn mr-2'])[1]");
    By DiscountLabel = By.xpath("(//p[contains(text(),'Save EGP ')])[1]");
    By ServiceChargeLabel = By.xpath("(//p[contains(text(),'EGP ')])[2]");
    By CancelChargeLabel = By.xpath("(//p[contains(text(),'EGP ')])[2]");
    By ServiceChargeOnFareDetails = By.xpath("(//strong[text()='Service Charge']/following-sibling::span[@data-currency='EGP'])[1]");
    By FareDetails = By.xpath("//li[@id='fare_0_0']");
    By noSearchResultsMsg = By.xpath("//p[contains(text(),'No, search results available')]");
    By Year = By.xpath("//button[normalize-space()='2026']");
    By PassengersList = By.xpath("//button[@class='passengers-btn ng-star-inserted']");
    By firstFlicghtSelector = By.xpath("(//div[contains(@class,'left-side-card')]//p-checkbox//div[contains(@class,'p-checkbox-box')])[1]");
    By saveQuoteBtn = By.xpath("//button[contains(@class,'flight-action-btn') and contains(.,'Save Quote')]");
    By NewUserCheckForQuoteTxt = By.xpath("//label[normalize-space()='New user']");
    By QuoteNewUserFirstNameTxt = By.id("id-FirstName");
    By QuoteNewUserLastNameTxt = By.id("id-LastName");
    By QuoteNewUserEmailTxt = By.id("id-Email");
    By QuoteNewUserAddEmailBtn = By.xpath("//button[.//span[normalize-space()='Add Email']]");
    By QuoteNewUserPhone = By.id("Phone");
    By ConfirmSaveQuoteBtn = By.xpath("//button[@type='submit']//span[normalize-space()='Save Quote']/ancestor::button");
    By QuoteSavedMsg = By.xpath("//span[normalize-space()='Quote Saved']");
    By firstBookFlightButton = By.xpath("(//button[.//span[normalize-space()='Book Flight']])[1]");

    public void BookFirstFlight() {
        driver.element().click(firstBookFlightButton);
    }
    public SearchBookingBranch selectFirstFlight(){
        driver.element().click(firstFlicghtSelector);
        return new SearchBookingBranch(driver);
    }
    public SearchBookingBranch SaveQuote(){
        driver.element().click(saveQuoteBtn);
        return new SearchBookingBranch(driver);
    }
    public SearchBookingBranch ConfirmSaveQuote(String NewUserFirstName,String NewUserLastName,String NewUserEmail,String NewUserPhone){

        driver.element().click(NewUserCheckForQuoteTxt);
        driver.element().type(QuoteNewUserFirstNameTxt,NewUserFirstName);
        driver.element().type(QuoteNewUserLastNameTxt,NewUserLastName);
        driver.element().type(QuoteNewUserEmailTxt,NewUserEmail);
        driver.element().click(QuoteNewUserAddEmailBtn);
        driver.element().type(QuoteNewUserPhone,NewUserPhone);
        driver.element().click(ConfirmSaveQuoteBtn);
        return new SearchBookingBranch(driver);
    }
    public void AssertThatQuoteSaved(){
        driver.verifyThat()
                .element(QuoteSavedMsg)
                .isVisible();
    }

    public SearchBookingBranch ClickOnBookingMidOffice() {
        driver.element().click(BookingMidOffice);
        return new SearchBookingBranch(driver);
    }

    public Boolean ReturnNoSearchResultsMsg() {
        return !driver.getDriver().findElements(noSearchResultsMsg).isEmpty();

    }

    public SearchBookingBranch ClickOnBooking() {
        driver.element().click(Booking);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch SelectBranch(String branch) {

        // open dropdown
        driver.element().click(BranchList);

        // select option
        By branchOption = By.xpath(
                "//li[@role='option']//span[normalize-space()='" + branch + "']"
        );

        driver.element().waitToBeReady(branchOption);

        driver.element().click(branchOption);

        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch AddStartingFrom(String Place) {
        driver.element().click(StartingFrom);
        driver.element().type(selectFrom, Place);
        driver.element().click(fromCheckBox);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch AddGoingTo(String Place) {
        driver.element().click(GoingTo);
        driver.element().type(selectTo, Place);
        By selectTo = By.xpath("//li[@aria-label='" + Place + "']");
        driver.element().click(selectTo);
        return new SearchBookingBranch(driver);
    }

    //    public SearchBookingBranch SelectDateOfJourney(String Months, String Year, String Day) {
//        driver.element().click(DataPicker);
//        driver.element().select(By.xpath("//select[@class='ui-datepicker-year']"), Year);
//        driver.element().select(By.xpath("//select[@class='ui-datepicker-month']"), Months);
//        driver.element().click(By.xpath("//a[contains(@class, 'ui-state-default') and text()='" + Day + "']"));
//
//        return new SearchBookingBranch(driver);
//    }
    public SearchBookingBranch SelectDateOfJourney(String to, String year, String month) throws InterruptedException {

        driver.element().click(DataPicker);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch passengersDropDown() {
        driver.element().click(PassengersList);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch SelectNumberOfAdult(int Number) {
        for (int j = 1; j < Number; j++) {
            driver.element().click(noOfAdults);
        }
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch SelectNumberOfChildren(int Number) {
        for (int j = 0; j < Number; j++) {
            driver.element().click(noOfChildren);
        }
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch SelectNumberOfInfant(int Number) {
        for (int j = 0; j < Number; j++) {
            driver.element().click(noOfInfants);
        }
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch clickOnSearchButton() {
        driver.element().click(SearchButton);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch clickOnWhiteMarkupButton() {
        driver.element().click(WhiteMarkup);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch ValidateTheDiscountIsApplied(int DiscountAdded) {
        String Discount = driver.getDriver().findElement(DiscountLabel).getText();
        String processedText = Discount.replace("Save EGP ", "").replace(".00", "").replace(",", "").trim();

        Assert.assertEquals(processedText, String.valueOf(DiscountAdded));
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch ValidateTheServiceChargeIsApplied(int ServiceCharge) {
        String Service = driver.getDriver().findElement(ServiceChargeLabel).getText();
        String processedText = Service.replace("EGP ", "").replace(".00", "").replace(",", "").trim();
        Assert.assertEquals(processedText, String.valueOf(ServiceCharge));
        driver.element().click(FareDetails);
        String ServiceOnFareDetails = driver.getDriver().findElement(ServiceChargeOnFareDetails).getText();
        String ServiceOnFareDetailsProcessedText = ServiceOnFareDetails.replace(".00", "");
        Assert.assertEquals(ServiceOnFareDetailsProcessedText, String.valueOf(ServiceCharge));


        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch ValidateTheCancellationChargeIsApplied(int CancelCharge) {
        String Discount = driver.getDriver().findElement(CancelChargeLabel).getText();
        String processedText = Discount.replace("EGP", "").replace(".0", "");
        Assert.assertEquals(processedText, String.valueOf(CancelCharge));

        return new SearchBookingBranch(driver);
    }
}
