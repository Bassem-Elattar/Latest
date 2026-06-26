package PortalPages.Settings;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Financial_Page {

    private SHAFT.GUI.WebDriver driver;

    // ================= Locators =================

    private final By Txt_ToastMessage = By.id("toast-container");
    private final By Btn_Settings           = By.xpath("(//tilde-theme-header//button)[2]");
    private final By Txt_DropDown           = By.xpath("//div[1]/div[2]/input");
    private final By Btn_Submit = By.xpath("//span[contains(@class,'p-button-label') and normalize-space()='Submit']");    private final By Btn_Financial          = By.xpath("//a[contains(@href,'/settings/financial')]");
    private final By Txt_MarkupHeader       = By.xpath("//*[normalize-space()='Markup']");
    private final By Lst_AgentName          = By.cssSelector("div.p-multiselect");
    private final By Btn_ForAllAirlines     = By.xpath("//*[normalize-space()='For All Airlines']");
    private final By Btn_ForEachAirline = By.xpath("//a[contains(@class,'p-accordion-header-link')][.//span[normalize-space()='For Each Airline']]");
    private final By Btn_Domestic           = By.xpath("//button[normalize-space()='Domestic']");
    private final By Btn_International      = By.xpath("//button[normalize-space()='International']");
    private final By Txt_SearchByName       = By.xpath("//input[contains(@placeholder,'Search by Name')]");
    private final By Btn_Search             = By.xpath("//input[contains(@placeholder,'Search by Name')]/following::button[1]");
    private final By Txt_FirstResult        = By.xpath("//tbody/tr/td[1]");
    private final By Txt_ResultsCounter     = By.xpath("//*[contains(text(),'Showing') and contains(text(),'results')]");
    private final By Btn_Edit = By.xpath("(//i[contains(@class,'pi-pencil')])[1]");
    private final By Txt_Value          = By.xpath("//*[@id='id-Value']");
    private final By Btn_SubmitForm     = By.xpath("//form/div/div[2]/button");
    private final String agentOptionXpath   = "(//li[contains(@class,'p-multiselect-item')][.//span[normalize-space()='%s']])[1]";

    public Financial_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    // ================= Actions =================
    public Financial_Page click_Settings() {
        driver.element().click(Btn_Settings);
        return this;
    }

    public Financial_Page click_Financial() {
        driver.element().click(Btn_Financial);
        return this;
    }

    public Financial_Page click_ForEachAirline() {
        driver.element().click(Btn_ForEachAirline);
        return this;
    }

    public Financial_Page click_Domestic() {
        driver.element().click(Btn_Domestic);
        return this;
    }

    public Financial_Page click_International() {
        driver.element().click(Btn_International);
        return this;
    }

    public Financial_Page select_AgentName(String agentName) {
        driver.element().click(Lst_AgentName);
        driver.element().type(Txt_DropDown, agentName);
        driver.element().click(By.xpath(String.format(agentOptionXpath, agentName)));
        driver.element().click(Btn_Submit);
        return this;
    }

    public Financial_Page type_SearchByName(String airlineName) {
        driver.element().type(Txt_SearchByName, airlineName);
        return this;
    }
    public Financial_Page verify_AirlineExistsInResults(String airlineName) throws InterruptedException {
        Thread.sleep(2000);
        driver.assertThat().element(Txt_FirstResult)
                .text().contains(airlineName)
                .withCustomReportMessage("Verify airline [" + airlineName + "] exists in search results")
                .perform();
        return this;
    }
    public Financial_Page click_Search() {
        driver.element().click(Btn_Search);
        return this;
    }

    // ================= Assertions =================
    public Financial_Page verify_MarkupPageIsDisplayed() {
        driver.assertThat().element(Txt_MarkupHeader)
                .isVisible()
                .withCustomReportMessage("Verify Markup page is displayed")
                .perform();
        return this;
    }

    public Financial_Page click_Edit() throws InterruptedException {
        Thread.sleep(2000);
        driver.element().click(Btn_Edit);
        return this;
    }

    public Financial_Page clear_And_Type_Value(String value) throws InterruptedException {
        Thread.sleep(2000);
        driver.element().clear(Txt_Value);
        driver.element().type(Txt_Value, value);
        return this;
    }

    public Financial_Page click_SubmitForm() {
        driver.element().click(Btn_SubmitForm);
        return this;
    }

    public Financial_Page verify_MarkupValueUpdated(String expectedValue) throws InterruptedException {
        Thread.sleep(2000);
        driver.assertThat().element(By.xpath("//table//tbody//tr[1]//td[2]"))
                .text().contains(expectedValue + " EGP")
                .withCustomReportMessage("Verify markup value updated to: " + expectedValue + " EGP")
                .perform();
        return this;
    }

    public Financial_Page verify_AgentExistsInResults(String agentName) {
        driver.assertThat().element(Txt_FirstResult)
                .text().contains(agentName)
                .withCustomReportMessage("Verify agent [" + agentName + "] exists in Financial search results")
                .perform();
        return this;
    }
    public Financial_Page verify_MarkupUpdatedToast() throws InterruptedException {
        Thread.sleep(1000);
        driver.assertThat().element(Txt_ToastMessage)
                .isVisible()
                .withCustomReportMessage("Verify 'Markup updated successfully' toast appears")
                .perform();
        return this;
    }
}