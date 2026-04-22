package AdminPages.Master.Supplier.SupplierCredential;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class AddSupplierCredential_Page {
    public AddSupplierCredential_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_Admin = By.xpath("//a[@href=\"/admin\"]");
    By Btn_AddSupplierCredential = By.xpath("//button[@routerlink=\"add\"]");
    By Lst_Supplier = By.xpath("//p-dropdown[.//input[@id=\"id-Supplier\"]]");
    By Lst_CredentialName = By.xpath("//input[@id=\"id-CredentialName\"]");
    By Lst_CountryPOC = By.xpath("//p-multiselect[.//input[@id=\"id-CountryPOC\"]]");
    By Lst_Currency = By.xpath("//p-dropdown[.//input[@id=\"id-Currency\"]]");
    By Lst_CredentialType = By.xpath("//p-dropdown[.//input[@id=\"id-CredentialType\"]]");
    By Lst_FlightSearchLimit = By.xpath("//p-dropdown[.//input[@id=\"id-FlightSearchLimit\"]]");
    By Lst_ConnectionTime = By.xpath("//p-dropdown[.//input[@id=\"id-ConnectionTimeout\"]]");
    By Lst_ReadTimeOut = By.xpath("//body[1]/ndc-root[1]/ndc-layout[1]/div[1]/div[3]/div[1]/div[1]/ndc-add-supplier-credentials[1]/div[1]/div[1]/ndc-fg-form-container[1]/div[1]/ndc-fg-form-generator[1]/form[1]/ndc-fg-input[9]/ndc-fg-dropdown-input[1]/p-dropdown[1]/div[1]");
    By Txt_EndpointURL = By.xpath("//input[@id=\"id-EndpointURL\"]");
    By Password = By.xpath("//input[@id=\"id-password\"]");
    By DisplayName = By.xpath("//input[@id=\"id-displayname\"]");
    By Cbox_DevCreate = By.xpath("(//p-checkbox[.//input[@type=\"checkbox\"]])[1]");
    By Btn_Save = By.xpath("//button[@type=\"submit\"]");

    public void setAddSupplierCredential(String supplier, String credentialname, String countrypoc, String currency
    , String credentialtype,String flightsearchlimit,String connectiontime,String readtimeout) throws InterruptedException {
        driver.element().click(Btn_AddSupplierCredential);
        driver.element().click(Lst_Supplier);
        By option = By.xpath(String.format("//span[text()='%s']", supplier));
        driver.element().click(option);
        driver.element().type(Lst_CredentialName,credentialname);
        driver.element().click(Lst_CountryPOC);
        By option1 = By.xpath(String.format("//span[text()='%s']", countrypoc));
        driver.element().click(option1);
        driver.element().click(Lst_Currency);
        By option2 = By.xpath(String.format("//span[text()='%s']", currency));
        driver.element().click(option2);
        driver.element().click(Lst_CredentialType);
        By option3 = By.xpath(String.format("//span[text()='%s']", credentialtype));
        driver.element().click(option3);
        driver.element().click(Lst_FlightSearchLimit);
        By option4 = By.xpath(String.format("//span[text()='%s']", flightsearchlimit));
        driver.element().click(option4);
//        driver.element().select(ConnectionTime,connectiontime);
//        driver.element().select(ReadTimeOut,readtimeout);
        driver.element().click(Lst_ConnectionTime);
        By option5 = By.xpath(String.format("//span[text()='%s']", connectiontime));
        driver.element().click(option5);
        Thread.sleep(1000);
        driver.element().click(Lst_ReadTimeOut);
        By option6 = By.xpath(String.format("//span[text()='%s']", readtimeout));
        driver.element().click(option6);

    }

    public void GDS(String endpointurl  ){

        driver.element().type(Txt_EndpointURL,endpointurl);
//        driver.element().type(Password,password);
//        driver.element().type(DisplayName,displayname);

    }

    public void RestrictedAirline(){
        driver.element().click(Cbox_DevCreate);
    }

    public void setSaveBtn(){
        driver.element().click(Btn_Save);
    }
    public void setAdmin(){
        driver.element().click(Btn_Admin);
    }

}
