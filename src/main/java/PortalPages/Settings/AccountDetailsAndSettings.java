package PortalPages.Settings;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utilities.DataUtils;

import java.util.List;

public class AccountDetailsAndSettings {

    private final SHAFT.GUI.WebDriver driver;

    private final By Icn_Settings = By.cssSelector(".icon-button--settings");
    private final By Txt_PageTitle = By.xpath("//h1[contains(@class,'page-title') and normalize-space()='Account Details and Settings']");
    private final By Txt_AgencyName = By.id("id-AgencyName");
    private final By Txt_Website = By.cssSelector(".website-text");
    private final By Btn_ChangeWebsite = By.cssSelector(".change-btn");
    private final By Txt_WebsiteEdit = By.cssSelector(".website-wrapper input, .website-wrapper textarea");
    private final By Btn_UploadLogo = By.xpath("//app-account-details-and-settings//span[normalize-space()='Upload logo']");
    private final By Txt_UploadLogo = By.cssSelector("input[name='logo']");
    private final By Txt_UnsupportedFileErrorMessage = By.cssSelector("span.fg-error.has-error");
    private final By Txt_PassCode = By.cssSelector(".passcode-value");
    private final By Btn_Regenerate = By.cssSelector(".regenerate-btn");
    private final By Btn_SaveChanges = By.cssSelector(".save-main-btn");
    private final By Txt_SuccessToastMessage = By.xpath("//*[contains(@class,'toast') and contains(normalize-space(),'Settings updated successfully')]");
    private final By Txt_ValidationMessages = By.xpath("//app-account-details-and-settings//*[contains(@class,'fg-error') and normalize-space()!='']");

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

    public AccountDetailsAndSettings openAccountDetailsPageBySettingsIcon() {
        hideChatWidget();
        driver.element().click(Icn_Settings);
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
        return isAnyElementDisplayed(Txt_PageTitle);
    }

    public String getPageTitle() {
        return driver.element().getText(Txt_PageTitle).trim();
    }

    private void waitForPageTitle() {
        for (int i = 0; i < 20; i++) {
            if (isAnyElementDisplayed(Txt_PageTitle)) {
                return;
            }

            sleep(250);
        }
    }

    private void waitForAccountDetailsForm() {
        for (int i = 0; i < 40; i++) {
            if (isAnyElementDisplayed(Txt_PageTitle) && isAnyElementDisplayed(Txt_AgencyName)) {
                return;
            }

            sleep(250);
        }
    }

    public boolean isAgencyNameDisplayed() {
        return driver.element().isElementDisplayed(Txt_AgencyName);
    }

    public boolean isAgencyNameReadOnly() {
        WebElement element = driver.getDriver().findElement(Txt_AgencyName);
        return element.getAttribute("readonly") != null || element.getAttribute("disabled") != null || !element.isEnabled();
    }

    public String getAgencyNameValue() {
        return driver.getDriver().findElement(Txt_AgencyName).getAttribute("value");
    }

    public AccountDetailsAndSettings clickChangeWebsite() {
        waitForElement(Btn_ChangeWebsite, 20);
        WebElement element = getFirstDisplayedElement(Btn_ChangeWebsite);
        ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);
        return this;
    }

    public AccountDetailsAndSettings enterAgencyWebsite(String website) {
        openWebsiteEditModeIfNeeded();
        setInputValue(Txt_WebsiteEdit, website);
        return this;
    }

    public AccountDetailsAndSettings clearAgencyWebsite() {
        openWebsiteEditModeIfNeeded();
        setInputValue(Txt_WebsiteEdit, "");
        return this;
    }

    private void openWebsiteEditModeIfNeeded() {
        if (!isAnyElementDisplayed(Txt_WebsiteEdit)) {
            clickChangeWebsite();
        }

        waitForElement(Txt_WebsiteEdit, 20);
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
        return driver.element().getText(Txt_Website);
    }

    public boolean isUploadLogoButtonDisplayed() {
        return driver.element().isElementDisplayed(Btn_UploadLogo);
    }

    public boolean isUploadLogoButtonClickable() {
        return driver.element().isElementClickable(Btn_UploadLogo);
    }

    public AccountDetailsAndSettings uploadLogo(String filePath) {
        driver.getDriver().findElement(Txt_UploadLogo).sendKeys(filePath);
        return this;
    }

    public boolean isLogoFileSelected(String fileName) {
        String selectedFilePath = driver.getDriver().findElement(Txt_UploadLogo).getAttribute("value");
        return selectedFilePath != null && selectedFilePath.contains(fileName);
    }

    public boolean isUnsupportedFileErrorMessageDisplayed() {
        for (int i = 0; i < 20; i++) {
            if (isAnyElementDisplayed(Txt_UnsupportedFileErrorMessage)) {
                return true;
            }

            sleep(250);
        }

        return isAnyElementDisplayed(Txt_UnsupportedFileErrorMessage);
    }

    public String getUnsupportedFileErrorMessage() {
        return getFirstDisplayedElement(Txt_UnsupportedFileErrorMessage).getText().trim().replaceAll("\\s+", " ");
    }

    public String getPassCodeValue() {
        waitForPassCodeValue();
        return driver.element().getText(Txt_PassCode).trim();
    }

    private void waitForPassCodeValue() {
        for (int i = 0; i < 20; i++) {
            if (!driver.element().getText(Txt_PassCode).trim().isEmpty()) {
                return;
            }

            sleep(250);
        }
    }

    public boolean isPassCodeDisplayed() {
        return driver.element().isElementDisplayed(Txt_PassCode);
    }

    public AccountDetailsAndSettings clickRegenerate() {
        driver.element().click(Btn_Regenerate);
        return this;
    }

    public boolean isRegenerateButtonClickable() {
        return driver.element().isElementClickable(Btn_Regenerate);
    }

    public boolean isPassCodeChangedFrom(String oldPassCode) {
        for (int i = 0; i < 20; i++) {
            String currentPassCode = driver.element().getText(Txt_PassCode).trim();

            if (!currentPassCode.isEmpty() && !currentPassCode.equals(oldPassCode)) {
                return true;
            }

            sleep(250);
        }

        return false;
    }

    public boolean isSaveChangesButtonDisplayed() {
        return driver.element().isElementDisplayed(Btn_SaveChanges);
    }

    public boolean isSaveChangesButtonEnabled() {
        return driver.getDriver().findElement(Btn_SaveChanges).isEnabled();
    }

    public AccountDetailsAndSettings clickSaveChanges() {
        hideChatWidget();
        WebElement element = driver.getDriver().findElement(Btn_SaveChanges);
        ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);
        return this;
    }

    public boolean isSuccessToastDisplayed() {
        waitForSuccessToast();
        return isAnyElementDisplayed(Txt_SuccessToastMessage);
    }

    public boolean isSuccessToastDisplayed(String message) {
        By successMessage = By.xpath("//*[contains(@class,'toast') and contains(normalize-space(),'" + message + "')]");
        waitForElement(successMessage, 60);
        return isAnyElementDisplayed(successMessage);
    }

    private void waitForSuccessToast() {
        waitForElement(Txt_SuccessToastMessage, 60);
    }

    public boolean hasValidationMessages() {
        return isAnyElementDisplayed(Txt_ValidationMessages);
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
