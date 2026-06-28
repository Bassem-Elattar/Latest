package PortalPages.Settings;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utilities.DataUtils;

import java.util.List;

public class AccountDetailsAndSettings {

    private final SHAFT.GUI.WebDriver driver;

    private final By settingsIcon = By.cssSelector(".icon-button--settings");
    private final By pageTitle = By.xpath("//h1[contains(@class,'page-title') and normalize-space()='Account Details and Settings']");
    private final By agencyName = By.id("id-AgencyName");
    private final By websiteText = By.cssSelector(".website-text");
    private final By changeWebsiteButton = By.cssSelector(".change-btn");
    private final By websiteEditInput = By.cssSelector(".website-wrapper input, .website-wrapper textarea");
    private final By uploadLogoButton = By.xpath("//app-account-details-and-settings//span[normalize-space()='Upload logo']");
    private final By uploadLogoInput = By.cssSelector("input[name='logo']");
    private final By unsupportedFileErrorMessage = By.cssSelector("span.fg-error.has-error");
    private final By passCodeValue = By.cssSelector(".passcode-value");
    private final By regenerateButton = By.cssSelector(".regenerate-btn");
    private final By saveChangesButton = By.cssSelector(".save-main-btn");
    private final By successToastMessage = By.xpath("//*[contains(@class,'toast') and contains(normalize-space(),'Settings updated successfully')]");
    private final By validationMessages = By.xpath("//app-account-details-and-settings//*[contains(@class,'fg-error') and normalize-space()!='']");

    public AccountDetailsAndSettings(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    public AccountDetailsAndSettings openAccountDetailsPage() {
        hideChatWidget();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url").replace("/auth/login", "/settings/account"));
        waitForAccountDetailsForm();
        hideChatWidget();
        return this;
    }

    private void hideChatWidget() {
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "document.querySelectorAll('[data-id=\"zsalesiq\"], .zsiq_floatmain, .zsiq_cnt, .zls-sptwndw')" +
                        ".forEach(element => element.style.display = 'none');"
        );
    }

    public boolean isPageTitleDisplayed() {
        waitForPageTitle();
        return isAnyElementDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return driver.element().getText(pageTitle).trim();
    }

    private void waitForPageTitle() {
        for (int i = 0; i < 20; i++) {
            if (isAnyElementDisplayed(pageTitle)) {
                return;
            }

            sleep(250);
        }
    }

    private void waitForAccountDetailsForm() {
        for (int i = 0; i < 40; i++) {
            if (isAnyElementDisplayed(pageTitle) && isAnyElementDisplayed(agencyName)) {
                return;
            }

            sleep(250);
        }
    }

    public boolean isAgencyNameDisplayed() {
        return driver.element().isElementDisplayed(agencyName);
    }

    public boolean isAgencyNameReadOnly() {
        WebElement element = driver.getDriver().findElement(agencyName);
        return element.getAttribute("readonly") != null || element.getAttribute("disabled") != null || !element.isEnabled();
    }

    public String getAgencyNameValue() {
        return driver.getDriver().findElement(agencyName).getAttribute("value");
    }

    public AccountDetailsAndSettings clickChangeWebsite() {
        WebElement element = getFirstDisplayedElement(changeWebsiteButton);
        ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);
        return this;
    }

    public AccountDetailsAndSettings enterAgencyWebsite(String website) {
        clickChangeWebsite();
        setInputValue(websiteEditInput, website);
        return this;
    }

    public AccountDetailsAndSettings clearAgencyWebsite() {
        clickChangeWebsite();
        setInputValue(websiteEditInput, "");
        return this;
    }

    private void setInputValue(By input, String value) {
        WebElement element = driver.getDriver().findElement(input);
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "const element = arguments[0];" +
                        "const value = arguments[1];" +
                        "element.focus();" +
                        "element.value = '';" +
                        "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "element.value = value;" +
                        "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "element.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "element.blur();",
                element,
                value
        );
    }

    public String getAgencyWebsiteText() {
        return driver.element().getText(websiteText);
    }

    public boolean isUploadLogoButtonDisplayed() {
        return driver.element().isElementDisplayed(uploadLogoButton);
    }

    public boolean isUploadLogoButtonClickable() {
        return driver.element().isElementClickable(uploadLogoButton);
    }

    public AccountDetailsAndSettings uploadLogo(String filePath) {
        driver.getDriver().findElement(uploadLogoInput).sendKeys(filePath);
        return this;
    }

    public boolean isLogoFileSelected(String fileName) {
        String selectedFilePath = driver.getDriver().findElement(uploadLogoInput).getAttribute("value");
        return selectedFilePath != null && selectedFilePath.contains(fileName);
    }

    public boolean isUnsupportedFileErrorMessageDisplayed() {
        for (int i = 0; i < 20; i++) {
            if (isAnyElementDisplayed(unsupportedFileErrorMessage)) {
                return true;
            }

            sleep(250);
        }

        return isAnyElementDisplayed(unsupportedFileErrorMessage);
    }

    public String getUnsupportedFileErrorMessage() {
        return getFirstDisplayedElement(unsupportedFileErrorMessage).getText().trim().replaceAll("\\s+", " ");
    }

    public String getPassCodeValue() {
        waitForPassCodeValue();
        return driver.element().getText(passCodeValue).trim();
    }

    private void waitForPassCodeValue() {
        for (int i = 0; i < 20; i++) {
            if (!driver.element().getText(passCodeValue).trim().isEmpty()) {
                return;
            }

            sleep(250);
        }
    }

    public boolean isPassCodeDisplayed() {
        return driver.element().isElementDisplayed(passCodeValue);
    }

    public AccountDetailsAndSettings clickRegenerate() {
        driver.element().click(regenerateButton);
        return this;
    }

    public boolean isRegenerateButtonClickable() {
        return driver.element().isElementClickable(regenerateButton);
    }

    public boolean isPassCodeChangedFrom(String oldPassCode) {
        for (int i = 0; i < 20; i++) {
            String currentPassCode = driver.element().getText(passCodeValue).trim();

            if (!currentPassCode.isEmpty() && !currentPassCode.equals(oldPassCode)) {
                return true;
            }

            sleep(250);
        }

        return false;
    }

    public boolean isSaveChangesButtonDisplayed() {
        return driver.element().isElementDisplayed(saveChangesButton);
    }

    public boolean isSaveChangesButtonEnabled() {
        return driver.getDriver().findElement(saveChangesButton).isEnabled();
    }

    public AccountDetailsAndSettings clickSaveChanges() {
        hideChatWidget();
        WebElement element = driver.getDriver().findElement(saveChangesButton);
        ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);
        return this;
    }

    public boolean isSuccessToastDisplayed() {
        waitForSuccessToast();
        return isAnyElementDisplayed(successToastMessage);
    }

    public boolean isSuccessToastDisplayed(String message) {
        By successMessage = By.xpath("//*[contains(@class,'toast') and contains(normalize-space(),'" + message + "')]");
        waitForElement(successMessage, 60);
        return isAnyElementDisplayed(successMessage);
    }

    private void waitForSuccessToast() {
        waitForElement(successToastMessage, 60);
    }

    public boolean hasValidationMessages() {
        return isAnyElementDisplayed(validationMessages);
    }

    private boolean isAnyElementDisplayed(By locator) {
        List<WebElement> elements = driver.getDriver().findElements(locator);

        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return true;
            }
        }

        return false;
    }

    private void waitForElement(By locator, int attempts) {
        for (int i = 0; i < attempts; i++) {
            if (isAnyElementDisplayed(locator)) {
                return;
            }

            sleep(250);
        }
    }

    private WebElement getFirstDisplayedElement(By locator) {
        List<WebElement> elements = driver.getDriver().findElements(locator);

        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return element;
            }
        }

        throw new AssertionError("No displayed element was found for locator: " + locator);
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
