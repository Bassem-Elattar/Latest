package AdminPages.BookingMidOffice.Booking;

import com.shaft.driver.SHAFT;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import org.testng.asserts.SoftAssert;

public class SearchBookingBranch {
    public SearchBookingBranch(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    SoftAssert softAssert = new SoftAssert();

    By BookingMidOffice = By.xpath("//a[@class='mid-office-1'and contains(text(),'Booking-Mid Office')]");
    By Booking = By.xpath("//li[@id='Booking']");
    By BranchList = By.xpath( "//span[normalize-space()='Branch*']");
    By StartingFrom = By.xpath("//div[contains(text(),'From *')]");
    By selectFrom = By.xpath("//input[@role='textbox']");
    By fromCheckBox = By.xpath("(//div[@class='p-checkbox-box'])[1]");
    By GoingTo = By.xpath("//span[normalize-space()='To *']");
    By selectTo = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component']");
    By DataPicker = By.xpath("//input[@placeholder='DD/MM/YYYY *']");
    By noOfAdults = By.xpath("(//p-button[@icon='pi pi-plus'])[1]");
    By noOfChildren = By.xpath("(//p-button[@icon='pi pi-plus'])[2]");
    By noOfInfants = By.xpath("(//p-button[@icon='pi pi-plus'])[3]");
    By SearchButton = By.xpath("//button[@class='p-element btn search-btn p-button p-component']");
    By WhiteMarkup = By.xpath("(//button[@aria-label='Apply markup'])[1]");
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
    By BookFlight_BTN = By.xpath("(//span[contains(text(),'Book Flight')])[1]");
    By inputField = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component']");
    By FlightDetails_Btn = By.xpath("(//button[contains(text(),'Flight Details')])[1]");
    By FlightDetails_Txt = By.xpath("(//div[@class='p-tabview-panels'])[2]");
    By FareDetails_Btn = By.xpath("(//a[normalize-space()='Fare Details'])[1]");
    By BaggageInfo_Btn = By.xpath("(//a[normalize-space()='Baggage Info'])[1]");
    By SegmentExpand_Btn = By.xpath("//a[@id='p-accordiontab-0']");
    By SegmentExpand_Txt = By.xpath("(//div[@class='flight-route flex align-items-center justify-content-between'])[1]");
    By FlightCard_Txt = By.xpath("(//div[@class='journey-row'])[1]");
    By Close_Btn = By.xpath("(//div[@class='p-component-overlay p-sidebar-mask p-component-overlay-enter'])[1]");
    By TotalPrice_Txt = By.xpath("(//div[@class='price-total'])[1]");
    By SupplierName_Txt = By.xpath("(//div[@class='badges-row'])[1]");
    By FareBreakDown_Txt = By.xpath("//div[@class='fare-breakdown-container ng-star-inserted']");
    By MarkUp_Inpt = By.xpath("(//input[@placeholder='Markup'])[1]");
    By FareDetails_Txt = By.xpath("(//div[@class='fare-details-block ng-star-inserted'])[1]");

    public String FareBreakDown() {
        return driver.element().getText(FareBreakDown_Txt);
    }

    public SearchBookingBranch BookFirstFlight() {
        driver.element().click(BookFlight_BTN);
        return new SearchBookingBranch(driver);
    }
    public SearchBookingBranch selectFirstFlight(){
        driver.element().click(firstFlicghtSelector);
        return new SearchBookingBranch(driver);
    }
    public SearchBookingBranch SaveQuote(){
        driver.element().click(saveQuoteBtn);
        return new SearchBookingBranch(driver);
    }

    public SearchBookingBranch OpenSideMenuInfo(){
        driver.element().click(FlightDetails_Btn);
        return new SearchBookingBranch(driver);
    }

    public List<String> SegmentDetails() {
        for (int i=1; i<6; i++) {
            By expandButton = By.xpath("(//button[@class='expand-btn'])["+ i +"]");
            if (!driver.getDriver().findElements(expandButton).isEmpty()) {
                driver.element().click(expandButton);
            }
        }
        List<WebElement> elements = driver.getDriver().findElements(FlightDetails_Txt);
        List<String> Segment = new ArrayList<>();

        for (WebElement element : elements) {
            Segment.add(element.getText());
        }
        return Segment;
    }

    public List<String> FareDetails() {
        driver.element().click(FareDetails_Btn);
        for (int i=2; i<6; i++) {
            By expandButton = By.xpath("(//button[@class='toggle-icon'])["+ i +"]");
            if (!driver.getDriver().findElements(expandButton).isEmpty()) {
                driver.element().click(expandButton);
            }
        }

        List<WebElement> elements = driver.getDriver().findElements(FlightDetails_Txt);
        List<String> Fare = new ArrayList<>();

        for (WebElement element : elements) {
            Fare.add(element.getText());
        }
        return Fare;
    }

    public List<String> BaggageInfo() {
        driver.element().click(BaggageInfo_Btn);
        for (int i=2; i<6; i++) {
            By expandButton = By.xpath("(//button[@class='expand-toggle'])["+ i +"]");
            if (!driver.getDriver().findElements(expandButton).isEmpty()) {
                driver.element().click(expandButton);
            }
        }
        List<WebElement> elements = driver.getDriver().findElements(FlightDetails_Txt);
        List<String> Baggage = new ArrayList<>();

        for (WebElement element : elements) {
            Baggage.add(element.getText());
        }
        return Baggage;
    }

    public List<String> FlightCard() {
        List<WebElement> elements = driver.getDriver().findElements(FlightCard_Txt);
        List<String> flightCard = new ArrayList<>();

        for (WebElement element : elements) {
            flightCard.add(element.getText());
        }
        return flightCard;
    }

    public List<String> PaxSegmentDetails() {
        driver.element().click(SegmentExpand_Btn);
        List<WebElement> elements = driver.getDriver().findElements(SegmentExpand_Txt);
        List<String> Segment = new ArrayList<>();

        for (WebElement element : elements) {
            Segment.add(element.getText());
        }
        return Segment;
    }

    public SearchBookingBranch CloseTheSideMenuInfo(){
     driver.element().click(Close_Btn);
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
        driver.element().click(BranchList);
        driver.element().type(inputField, branch);
        By branchOption = By.xpath("(//li[contains(@aria-label,'" + branch + "')])[1]");
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