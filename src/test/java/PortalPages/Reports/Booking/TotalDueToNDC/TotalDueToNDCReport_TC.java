package PortalPages.Reports.Booking.TotalDueToNDC;

import AdminPages.Reports.Reports_Common;
import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class TotalDueToNDCReport_TC {
    public SHAFT.TestData.JSON testData;
    public SHAFT.GUI.WebDriver driver;
    TotalDueToNDCReport dueToNDC;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
        driver.element().click(By.xpath("//a[.//span[normalize-space()='Reports']]"));
        driver.element().click(By.xpath("//ndc-card[.//h3[normalize-space()='Total Due to NDC']]//button"));
        testData = new SHAFT.TestData.JSON("TotalDue.json");

        dueToNDC = new TotalDueToNDCReport(driver);
    }

    @Test
    public void validSearchForTotalDue() throws InterruptedException {
        dueToNDC
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .Submit()
                .verifyThatResultsIsDisplayed();
    }

    @Test
    public void validateThatUserCanSearchWithAllField() throws InterruptedException {
        dueToNDC
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .setInvoiceNumber()
                .setCustomerName()
                .setBookingRefernce()
                .Submit()
                .verifyThatResultsIsDisplayed();
    }

    @Test
    public void searchForTotalDueWithNoOutputAndSelectSameDayFromDate() throws InterruptedException {
        dueToNDC
                .searchValidFromDate(testData.getTestData("validDataNoOutput.From_Date"), testData.getTestData("validDataNoOutput.FromYear"), testData.getTestData("validDataNoOutput.FromMonth"))
                .searchValidToDate(testData.getTestData("validDataNoOutput.To_Date"), testData.getTestData("validDataNoOutput.ToYear"), testData.getTestData("validDataNoOutput.ToMonth"))
                .Submit()
                .verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput();
    }

    @Test
    public void validateThatUserCanExportTheFile() throws InterruptedException {
        dueToNDC
                .searchValidFromDate(testData.getTestData("validData.From_Date"), testData.getTestData("validData.FromYear"), testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.To_Date"), testData.getTestData("validData.ToYear"), testData.getTestData("validData.ToMonth"))
                .Submit()
                .VerifyThatTheExportButtonIsClickable();
    }






}
