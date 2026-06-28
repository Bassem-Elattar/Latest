package PortalPages.Settings;

import PortalPages.Login.Login_Page;
import PortalPages.Login.PortalTestBase_TC;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class AccountDetailsAndSettings_Tests extends PortalTestBase_TC {

    private AccountDetailsAndSettings accountDetails;
    private SHAFT.TestData.JSON testData;

    @BeforeClass
    public void loginToPortal() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("AccountDetailsSettings.json");
        loginIfNeeded();
    }

    @BeforeMethod
    public void openAccountDetailsAndSettingsPage() {
        accountDetails = new AccountDetailsAndSettings(driver);
        accountDetails.openAccountDetailsPage();
    }

    private void loginIfNeeded() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            if (isElementPresent(By.id("id-AgencyCode"))) {
                new Login_Page(driver).PortalLogin();
                waitUntilLoginCompletes();
                return;
            }

            if (isElementPresent(By.xpath("//tilde-theme-side-menu"))) {
                return;
            }

            Thread.sleep(500);
        }
    }

    private void waitUntilLoginCompletes() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            if (!isElementPresent(By.id("id-AgencyCode"))) {
                return;
            }

            Thread.sleep(500);
        }
    }

    private boolean isElementPresent(By locator) {
        return !driver.getDriver().findElements(locator).isEmpty();
    }

    @Test
    public void verifyUserCanOpenAccountDetailsAndSettingsPage() {
        Assert.assertTrue(accountDetails.isPageTitleDisplayed(), "Account Details and Settings page title should be displayed.");
        Assert.assertEquals(accountDetails.getPageTitle(), testData.getTestData("Page.Title"));
    }

    @Test
    public void verifyAgencyNameFieldIsReadOnly() {
        Assert.assertTrue(accountDetails.isAgencyNameDisplayed(), "Agency Name field should be displayed.");
        Assert.assertTrue(accountDetails.isAgencyNameReadOnly(), "Agency Name field should be read-only.");
        Assert.assertFalse(accountDetails.getAgencyNameValue().isEmpty(), "Agency Name field should have a saved agency value.");
    }

    @Test
    public void verifyUserCanUpdateAgencyWebsiteWithValidValue() {
        accountDetails
                .enterAgencyWebsite(testData.getTestData("Website.ValidUrl"))
                .clickSaveChanges();

        Assert.assertTrue(
                accountDetails.isSuccessToastDisplayed(testData.getTestData("Messages.Success")),
                "Success toast should be displayed after saving a valid website."
        );
    }

    @Test
    public void verifyUserCanSaveWithEmptyAgencyWebsite() {
        accountDetails
                .clearAgencyWebsite()
                .clickSaveChanges();

        Assert.assertFalse(accountDetails.hasValidationMessages(), "Agency Website should not be mandatory.");
    }

    @Test
    public void verifyUploadLogoButtonIsDisplayedAndClickable() {
        Assert.assertTrue(accountDetails.isUploadLogoButtonDisplayed(), "Upload logo button should be displayed.");
        Assert.assertTrue(accountDetails.isUploadLogoButtonClickable(), "Upload logo button should be clickable.");
    }

    @Test
    public void verifyUserCanUploadValidAgencyLogo() {
        accountDetails
                .uploadLogo(toAbsolutePath(testData.getTestData("Logo.ValidImagePath")))
                .clickSaveChanges();

        Assert.assertFalse(accountDetails.hasValidationMessages(), "Valid logo should be accepted after saving changes.");
    }

    @Test
    public void verifyUnsupportedLogoFileTypeDisplaysError() {
        accountDetails.uploadLogo(toAbsolutePath(testData.getTestData("Logo.UnsupportedFilePath")));

        Assert.assertTrue(
                accountDetails.isUnsupportedFileErrorMessageDisplayed(),
                "Unsupported logo file type error message should be displayed."
        );
        Assert.assertEquals(accountDetails.getUnsupportedFileErrorMessage(), testData.getTestData("Messages.UnsupportedFile"));
    }

    @Test
    public void verifyPassCodeIsDisplayedAndCanBeRegenerated() {
        Assert.assertTrue(accountDetails.isPassCodeDisplayed(), "PassCode should be displayed.");
        Assert.assertTrue(accountDetails.isRegenerateButtonClickable(), "Regenerate button should be clickable.");

        accountDetails.clickRegenerate();

        Assert.assertTrue(accountDetails.isSaveChangesButtonEnabled(), "Save Changes button should be enabled after regenerating PassCode.");
    }

    @Test
    public void verifyUserCanSaveAccountDetailsChanges() {
        accountDetails
                .enterAgencyWebsite(testData.getTestData("Website.ValidUrl"))
                .clickSaveChanges();

        Assert.assertTrue(
                accountDetails.isSuccessToastDisplayed(testData.getTestData("Messages.Success")),
                "Success toast should be displayed after saving account details."
        );
    }

    private String toAbsolutePath(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }
}
