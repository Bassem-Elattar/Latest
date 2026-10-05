package PortalPages;

import PortalPages.Reports.Booking.Sales.SalesReport;
import PortalPages.Reports.Booking.TotalDueToNDC.TotalDueToNDCReport;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SideMenu {
    SHAFT.GUI.WebDriver driver ;
    private final By lnk_TravellersDetails = By.xpath("//a[@href='/travellers']");

    // Confirmed live: sidebar markup is
    // <div class="agent-details ..."><h2>Automation Agency</h2><p>AGN10066</p></div>
    // <div class="wallet-balance-section">...<h1 class="balance-amount"> 974,274.400 <span class="currency">EGP</span></h1>
    // Both now render twice in the DOM (a hidden duplicate, confirmed live -- e.g. an off-canvas
    // mobile drawer copy of the sidebar), so an un-indexed locator throws
    // MultipleElementsFoundException. The visible one is always the first match.
    private final By agencyNameLabel = By.xpath("(//div[contains(@class,'agent-details')]/h2)[1]");
    private final By walletBalanceLabel = By.xpath("(//h1[contains(@class,'balance-amount')])[1]");

    public String getAgencyName() {
        return driver.element().getText(agencyNameLabel).trim();
    }

    public BigDecimal getWalletBalance() {
        String raw = driver.element().getText(walletBalanceLabel);
        String numeric = raw.replaceAll("[^0-9.]", "");
        // Wallet is displayed to 3 decimals (e.g. "974,274.400 EGP"); keep that precision rather
        // than rounding to 2, which would risk a false failure against the real credited amount.
        return new BigDecimal(numeric).setScale(3, RoundingMode.HALF_UP);
    }

    public SideMenu(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    public void ElementClick(By by){

        driver.element().click(by);
    }
    public void OpenAddTravellerPage(){
        ElementClick(lnk_TravellersDetails);
    }

    private final By reportsMenu = By.xpath("//a[.//span[normalize-space()='Reports']]");
    private final By bookingButton = By.xpath("//ndc-card[.//h3[normalize-space()='Bookings']]//button");
    private final By salesButton = By.xpath("//ndc-card[.//h3[normalize-space()='Sales']]//button");
    private final By totalDueToNdcButton = By.xpath("//ndc-card[.//h3[normalize-space()='Total Due to NDC']]//button");


    public SideMenu openReports() {
        driver.element().click(reportsMenu);
        return this;
    }

    public SideMenu openBookingReport() {
        driver.element().click(bookingButton);
        return this;
    }

    public SideMenu openSalesReport() {
        driver.element().click(salesButton);
        return this;
    }

    public SideMenu openTotalDueToNdcReport() {
        driver.element().click(totalDueToNdcButton);
        return this;
    }


}