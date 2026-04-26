package AdminPages.Master;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class Master_Common {
    private SHAFT.GUI.WebDriver driver;

    private final By btn_master = By.xpath("//a[@id=\"p-tabpanel-2-label\"]");

    /////////////flight//////////
    private final By btn_Flight = By.xpath("(//a[@class=\"ng-star-inserted\"])[1]");
    private final By btn_Airport = By.xpath("//a[@href=\"/master/flight/airport\"]");
    private final By btn_Airline = By.xpath("//a[@href='/master/flight/airline']");
    private final By btn_RBD = By.xpath("//a[@href='/master/flight/rbd']");
    private final By btn_PreferSAirline = By.xpath("//a[@href='/master/flight/preferAirline']");
    private final By btn_BSP = By.xpath("//a[@href='/master/flight/bsp']");
    private final By btn_DealCode = By.xpath("//a[@href='/master/flight/deal-code']");

    /////////////supplier/////////////////
    private final By btn_SupplierMenue = By.xpath("(//a[@class=\"ng-star-inserted\"])[2]");
    private final By btn_Supplier = By.xpath("//a[@href='/master/supplier/supplier']");
    private final By btn_SupplierCredencial = By.xpath("//a[@href='/master/supplier/supplier-credentials']");
    private final By btn_FlightSearchLimitation = By.xpath("//a[@href='/master/supplier/flight-search-limitation']");
    private final By btn_CredentialsField = By.xpath("//span[normalize-space()='Credential Field']");

    //////////////miscellaneous//////////
    private final By btn_Miscellaneous = By.xpath("(//a[@class=\"ng-star-inserted\"])[3]");
    private final By btn_Region = By.xpath("//a[@href='/master/miscellaneous/region/region']");
    private final By btn_Currency = By.xpath("//a[@href='/master/miscellaneous/currency']");
    private final By btn_RateOfExchange = By.xpath("//a[@href='/master/miscellaneous/rate']");
    private final By btn_CredentialRateOfExchange = By.xpath("//a[@href='/master/miscellaneous/roe-per-cred']");

    /////////////////paymentGateWay/////////////
    private final By btn_paymentGateWay = By.xpath("(//a[@class=\"ng-star-inserted\"])[4]");
    private final By btn_PaymentMethod = By.xpath("//a[@href='/master/gateway/gateway-method']");

    public Master_Common(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
    }

    public Master_Common clickMaster(){
        driver.element().click(btn_master);
        return this;
    }
    public Master_Common clickFlight(){
        driver.element().click(btn_Flight);
        return this;
    }
    public Master_Common clickAirport(){
        driver.element().click(btn_Airport);
        return this;
    }
    public Master_Common clickAirline(){
        driver.element().click(btn_Airline);
        return this;
    }
    public Master_Common clickRBD(){
        driver.element().click(btn_RBD);
        return this;
    }
    public Master_Common clickPreferSAirline(){
        driver.element().click(btn_PreferSAirline);
        return this;
    }
    public Master_Common clickBSP(){
        driver.element().click(btn_BSP);
        return this;
    }
    public Master_Common clickDealCode(){
        driver.element().click(btn_DealCode);
        return this;
    }
    public Master_Common clickSupplierMenue(){
        driver.element().click(btn_SupplierMenue);
        return this;
    }
    public Master_Common clickSupplier(){
        driver.element().click(btn_Supplier);
        return this;
    }
    public Master_Common clickSupplierCredencial(){
        driver.element().click(btn_SupplierCredencial);
        return this;
    }
    public Master_Common clickCredentialField(){
        driver.element().click(btn_CredentialsField);
        return this;
    }
    public Master_Common clickFlightSearchLimitation(){
        driver.element().click(btn_FlightSearchLimitation);
        return this;
    }
    public Master_Common clickMiscellaneous(){
        driver.element().click(btn_Miscellaneous);
        return this;
    }
    public Master_Common clickRegion(){
        driver.element().click(btn_Region);
        return this;
    }
    public Master_Common clickCurrency(){
        driver.element().click(btn_Currency);
        return this;
    }
    public Master_Common clickRateOfExchange(){
        driver.element().click(btn_RateOfExchange);
        return this;
    }
    public Master_Common clickCredentialRateOfExchange(){
        driver.element().click(btn_CredentialRateOfExchange);
        return this;
    }
    public Master_Common clickpaymentGateWay(){
        driver.element().click(btn_paymentGateWay);
        return this;
    }
    public Master_Common clickPaymentMethod(){
        driver.element().click(btn_PaymentMethod);
        return this;
    }

}
