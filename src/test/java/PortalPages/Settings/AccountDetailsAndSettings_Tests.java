package PortalPages.Settings;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

import java.nio.file.Paths;

public class AccountDetailsAndSettings_Tests {

    private SHAFT.GUI.WebDriver driver;
    private AccountDetailsAndSettings accountDetails;
    private SHAFT.TestData.JSON testData;

    @BeforeClass
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        testData = new SHAFT.TestData.JSON("AccountDetailsSettings.json");

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
    }

    @BeforeMethod
    public void openAccountDetailsAndSettingsPage(ITestResult result) {
        accountDetails = new AccountDetailsAndSettings(driver);

        if (result.getMethod().getMethodName().equals("verifyUserCanOpenAccountDetailsAndSettingsPage")) {
            driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
            accountDetails.openAccountDetailsPageBySettingsIcon();
        } else {
            accountDetails.openAccountDetailsPage();
        }
    }

    @Test
    public void verifyUserCanOpenAccountDetailsAndSettingsPage() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(accountDetails.isPageTitleDisplayed(), "Account Details and Settings page title should be displayed.");
        softAssert.assertEquals(accountDetails.getPageTitle(), testData.getTestData("Page.Title"));
        softAssert.assertAll();
    }

    @Test
    public void verifyAgencyNameFieldIsReadOnly() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(accountDetails.isAgencyNameDisplayed(), "Agency Name field should be displayed.");
        softAssert.assertTrue(accountDetails.isAgencyNameReadOnly(), "Agency Name field should be read-only.");
        softAssert.assertFalse(accountDetails.getAgencyNameValue().isEmpty(), "Agency Name field should have a saved agency value.");
        softAssert.assertAll();
    }

    @Test
    public void verifyUserCanUpdateAgencyWebsiteWithValidValue() {
        accountDetails
                .enterAgencyWebsite(testData.getTestData("Website.ValidUrl"))
                .clickSaveChanges();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                accountDetails.isSuccessToastDisplayed(testData.getTestData("Messages.Success")),
                "Success toast should be displayed after saving a valid website."
        );
        softAssert.assertAll();
    }

    @Test
    public void verifyUserCanSaveWithEmptyAgencyWebsite() {
        accountDetails
                .clearAgencyWebsite()
                .clickSaveChanges();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(accountDetails.hasValidationMessages(), "Agency Website should not be mandatory.");
        softAssert.assertAll();
    }

    @Test
    public void verifyUploadLogoButtonIsDisplayedAndClickable() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(accountDetails.isUploadLogoButtonDisplayed(), "Upload logo button should be displayed.");
        softAssert.assertTrue(accountDetails.isUploadLogoButtonClickable(), "Upload logo button should be clickable.");
        softAssert.assertAll();
    }

    @Test
    public void verifyUserCanUploadValidAgencyLogo() {
        accountDetails
                .uploadLogo(toAbsolutePath(testData.getTestData("Logo.ValidImagePath")))
                .clickSaveChanges();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(accountDetails.hasValidationMessages(), "Valid logo should be accepted after saving changes.");
        softAssert.assertAll();
    }

    @Test
    public void verifyUnsupportedLogoFileTypeDisplaysError() {
        accountDetails.uploadLogo(toAbsolutePath(testData.getTestData("Logo.UnsupportedFilePath")));

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                accountDetails.isUnsupportedFileErrorMessageDisplayed(),
                "Unsupported logo file type error message should be displayed."
        );
        softAssert.assertEquals(accountDetails.getUnsupportedFileErrorMessage(), testData.getTestData("Messages.UnsupportedFile"));
        softAssert.assertAll();
    }

    @Test
    public void verifyPassCodeIsDisplayedAndCanBeRegenerated() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(accountDetails.isPassCodeDisplayed(), "PassCode should be displayed.");
        softAssert.assertTrue(accountDetails.isRegenerateButtonClickable(), "Regenerate button should be clickable.");
        softAssert.assertAll();

        accountDetails.clickRegenerate();

        SoftAssert regenerateSoftAssert = new SoftAssert();

        regenerateSoftAssert.assertTrue(accountDetails.isSaveChangesButtonEnabled(), "Save Changes button should be enabled after regenerating PassCode.");
        regenerateSoftAssert.assertAll();
    }

    @Test
    public void verifyUserCanSaveAccountDetailsChanges() {
        accountDetails
                .enterAgencyWebsite(testData.getTestData("Website.ValidUrl"))
                .clickSaveChanges();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                accountDetails.isSuccessToastDisplayed(testData.getTestData("Messages.Success")),
                "Success toast should be displayed after saving account details."
        );
        softAssert.assertAll();
    }

    private String toAbsolutePath(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }
}
