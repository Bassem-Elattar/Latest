package AdminPages.importPNR;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class ImportPNR_Page {

    SHAFT.GUI.WebDriver driver;

    public ImportPNR_Page(SHAFT.GUI.WebDriver driver) {
        this.driver=driver;
    }

    // Navigation
    private final By Btn_importPNR=By.xpath("//*[text()='Import PNR']");

    // PNR Code
    private final By pnrCodeInput=By.id("id-PNRCode");

    // Branch Name dropdown
    private final By branchNameDropdown=By.xpath("//*[contains(text(),'Select Branch')]");
    private final By branchNameDropdownSearchInput=By.xpath("//input[@aria-activedescendant=\"p-highlighted-option\"]");

    // Agency Name dropdown
    private final By agencyNameDropdown=By.xpath("//*[text()='Select Agency']");
    private final By agencyNameDropdownSearchInput=By.xpath("//input[@aria-activedescendant=\"p-highlighted-option\"]");

    // Agent Name dropdown
    private final By agentNameDropdown=By.xpath("//*[text()='Select Agent']");;
    private final By agentNameDropdownSearchInput=By.xpath("//input[@aria-activedescendant=\"p-highlighted-option\"]");

    // Supplier dropdown
    private final By supplierDropdown=By.xpath("//*[text()='Select Supplier ']");

    // Supplier Credential dropdown
    private final By supplierCredentialDropdown=By.xpath("//*[text()='Select Credential']");

    // Search button
    private final By Btn_search=By.xpath("//*[text()='Search']");

    // Terms and Conditions checkbox
    private final By termsAndConditionsCheckbox =
            By.xpath("//*[contains(@class,'p-checkbox') and contains(@class,'p-component')]");

    //Find a button with class pay-btn But not inside a popup dialogAnd its label is Pay
    private final By mainPayButton =
            By.xpath("//button[contains(@class,'pay-btn') and not(ancestor::*[contains(@class,'p-dialog')])]//span[normalize-space()='Pay']/parent::button");

    //Find the popup that contains Pay PNR
    private final By confirmPayPopupPayButton =
            By.xpath("//*[normalize-space()='Pay PNR']/ancestor::*[contains(@class,'p-dialog')][1]//button[.//span[normalize-space()='Pay']]");

    // Final success message
    private final By ticketConfirmedSuccessMessage =
            By.xpath("//p[contains(normalize-space(.),'Your ticket has been successfully Confirmed. Thank you for choosing NDC')]");

    private By dropdownOption(String optionText) {
            return By.xpath("//li[normalize-space(@aria-label)='"+ optionText +"']");
    }

    public ImportPNR_Page navigateToImportPNRPage(){
        driver.element().click(Btn_importPNR);

        return this;
    }

    public ImportPNR_Page enterPNRCode(String pnrCode){
        driver.element().type(pnrCodeInput,pnrCode);

        return this;
    }

    public ImportPNR_Page selectBranchName(String branchName){
        driver.element().click(branchNameDropdown);
        driver.element().type(branchNameDropdownSearchInput,branchName);
        driver.element().click(dropdownOption(branchName));

        return this;
    }

    public ImportPNR_Page selectAgencyName(String agencyName){
        driver.element().click(agencyNameDropdown);
        driver.element().type(agencyNameDropdownSearchInput,agencyName);
        driver.element().click(dropdownOption(agencyName));

        return this;
    }

    public ImportPNR_Page selectAgentName(String agentName){
        driver.element().click(agentNameDropdown);
        driver.element().type(agentNameDropdownSearchInput,agentName);
        driver.element().click(dropdownOption(agentName));

        return this;
    }

    public ImportPNR_Page selectSupplier(String supplierName) {
        driver.element().click(supplierDropdown);
        driver.element().click(dropdownOption(supplierName));

        return this;
    }

    public ImportPNR_Page selectSupplierCredential(String supplierCredential) {
        driver.element().click(supplierCredentialDropdown);
        driver.element().click(dropdownOption(supplierCredential));

        return this;
    }

    public ImportPNR_Page clickSearchButton(){
        driver.element().click(Btn_search);

        return this;
    }

    public ImportPNR_Page checkTermsAndConditionsCheckbox() {
        driver.element().click(termsAndConditionsCheckbox);
        return this;
    }

    public ImportPNR_Page clickMainPayButton() {
        driver.element().click(mainPayButton);
        return this;
    }

    public ImportPNR_Page clickConfirmPayPopupPayButton() {
        driver.element().click(confirmPayPopupPayButton);
        return this;
    }

    public By getTicketConfirmedSuccessMessage() {
        return ticketConfirmedSuccessMessage;
    }


















}
