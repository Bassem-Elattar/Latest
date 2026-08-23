package PortalPages.ImportPnr;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

/** Portal - Import PNR: validation and happy-path retrieval tests. */
public class ImportPnr_TC {

    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private ImportPnr_Page importPnrPage;

    @BeforeClass
    public void beforeClass() {
        testData = new SHAFT.TestData.JSON("PortalImportPNR.json");
    }

    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();

        new Login_Page(driver)
                .navigateToLoginPage(DataUtils.get("Portal_Url"))
                .enterAgencyCode(DataUtils.get("Portal_AgencyCode"))
                .enterEmail(DataUtils.get("Portal_Email"))
                .enterPassword(DataUtils.get("Portal_Password"))
                .clickLoginButton()
                .waitUntilLoggedIn();

        importPnrPage = new ImportPnr_Page(driver);
        importPnrPage.navigateToImportPnrPage(DataUtils.get("Portal_Url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        CommonMethod.quitDriver();
    }

    /** Verifies a valid PNR is imported successfully and shown as Not Ticketed. */
    @Test
    public void TC01_verifyThatPnrImportsSuccessfully() {
        importPnrPage.enterPnrCode(testData.getTestData("pnr.unticketed"))
                     .selectSupplier(testData.getTestData("supplier"))
                     .selectCredential(testData.getTestData("supplierCredential"))
                     .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(
                importPnrPage.areResultsDisplayed(),
                "A valid PNR for this agency should retrieve and display a fare total");
        softAssert.assertEquals(
                importPnrPage.getStatusText().trim(),
                testData.getTestData("expected.statusNotTicketed"),
                "A freshly imported PNR should show status 'Not Ticketed'");
        softAssert.assertTrue(
                importPnrPage.isPayButtonEnabled(),
                "An unticketed PNR should have the Pay button enabled");
        softAssert.assertAll();
    }

    /** Verifies an empty PNR Code shows the Required error. */
    @Test
    public void TC02_verifyThatEmptyPnrShowsRequiredError() {
        importPnrPage.enterPnrCodeAndBlur("");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                importPnrPage.getFieldErrorText().trim(),
                testData.getTestData("expected.requiredMessage"),
                "An empty PNR Code field should display the Required validation message");
        softAssert.assertTrue(
                importPnrPage.isPnrCodeInputInvalid(),
                "An empty PNR Code field should be marked invalid (ng-invalid)");
        softAssert.assertAll();
    }

    /** Verifies a PNR Code under 6 characters shows a length error. */
    @Test
    public void TC03_verifyThatShortPnrShowsLengthError() {
        importPnrPage.enterPnrCodeAndBlur(testData.getTestData("pnr.lessThanSixChars"));

        Assert.assertEquals(
                importPnrPage.getFieldErrorText().trim(),
                testData.getTestData("expected.lessThanSixCharsMessage"),
                "A PNR Code shorter than 6 characters should display the less-than-six-chars message");
    }

    /** Verifies a PNR Code over 9 characters shows a length error. */
    @Test
    public void TC04_verifyThatLongPnrShowsLengthError() {
        importPnrPage.enterPnrCodeAndBlur(testData.getTestData("pnr.moreThanNineChars"));

        Assert.assertEquals(
                importPnrPage.getFieldErrorText().trim(),
                testData.getTestData("expected.moreThanNineCharsMessage"),
                "A 10-character PNR Code should display the cannot-exceed-nine-characters message");
    }

    /** Verifies typing more than 10 characters into PNR Code gets truncated to 10. */
    @Test
    public void TC05_verifyThatPnrTruncatesAtTenChars() {
        importPnrPage.enterPnrCodeAndBlur(testData.getTestData("pnr.twelveChars"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                importPnrPage.getPnrCodeMaxLength(),
                testData.getTestData("expected.pnrCodeMaxLength"),
                "The PNR Code field should declare maxlength=10");
        softAssert.assertEquals(
                importPnrPage.getPnrCodeValue(),
                testData.getTestData("pnr.truncatedToTen"),
                "Typing 12 characters should be truncated to 10 by the maxlength attribute");
        softAssert.assertEquals(
                importPnrPage.getFieldErrorText().trim(),
                testData.getTestData("expected.moreThanNineCharsMessage"),
                "The truncated 10-character value should still fail the 9-character rule");
        softAssert.assertAll();
    }

    /** Verifies searching without picking a Supplier returns no results. */
    @Test
    public void TC06_verifyThatSearchWithoutSupplierFindsNothing() {
        importPnrPage.enterPnrCode(testData.getTestData("pnr.exactlySixChars"))
                     .clickSearch();

        Assert.assertFalse(
                importPnrPage.areResultsDisplayed(),
                "Searching without a supplier should not retrieve PNR results");
    }

    /** Verifies searching without picking a Supplier Credential returns no results. */
    @Test
    public void TC07_verifyThatSearchWithoutCredentialFindsNothing() {
        importPnrPage.enterPnrCode(testData.getTestData("pnr.exactlySixChars"))
                     .selectSupplier(testData.getTestData("supplier"))
                     .clickSearch();

        Assert.assertFalse(
                importPnrPage.areResultsDisplayed(),
                "Searching without a supplier credential should not retrieve PNR results");
    }

    /** Verifies searching for a PNR that doesn't exist returns no results. */
    @Test
    public void TC08_verifyThatUnknownPnrFindsNothing() {
        importPnrPage.enterPnrCode(testData.getTestData("pnr.nonExistent"))
                     .selectSupplier(testData.getTestData("supplier"))
                     .selectCredential(testData.getTestData("supplierCredential"))
                     .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(
                importPnrPage.areResultsDisplayed(),
                "A non-existent PNR should not render a fare total");
        softAssert.assertFalse(
                importPnrPage.isStatusDisplayed(),
                "A non-existent PNR should not render a status badge");
        softAssert.assertFalse(
                importPnrPage.isPayButtonDisplayed(),
                "A non-existent PNR should not render a Pay button");
        softAssert.assertAll();
    }

    /** Verifies an expired session redirects to the login page. */
    @Test
    public void TC09_verifyThatExpiredSessionRedirectsToLogin() {
        ((org.openqa.selenium.JavascriptExecutor) driver.getDriver())
                .executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
        importPnrPage.navigateToImportPnrPageExpectingPossibleRedirect(DataUtils.get("Portal_Url"));
        new Login_Page(driver).waitUntilLoaded();

        Assert.assertTrue(
                importPnrPage.getCurrentUrl().contains(testData.getTestData("expected.loginUrlFragment")),
                "Opening Import PNR without a valid session should redirect to the login page. Actual URL: "
                        + importPnrPage.getCurrentUrl());
    }
}
