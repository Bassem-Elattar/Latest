package PortalPages;

import PortalPages.Reports.Booking.Sales.SalesReport;
import PortalPages.Reports.Booking.TotalDueToNDC.TotalDueToNDCReport;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class SideMenu {
    SHAFT.GUI.WebDriver driver ;
    private final By lnk_TravellersDetails = By.xpath("//a[@href='/travellers']");

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

    private final By salesButton = By.xpath("//ndc-card[.//h3[normalize-space()='Sales']]//button");
    private final By totalDueToNdcButton = By.xpath("//ndc-card[.//h3[normalize-space()='Total Due to NDC']]//button");


    public SideMenu openReports() {
        driver.element().click(reportsMenu);
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