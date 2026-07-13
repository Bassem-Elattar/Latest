package AdminPages.Master.Flight.Airline;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import utilities.FakerSingleton;

public class CreateAirline_Page {
    public CreateAirline_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By Btn_AddAirline = By.xpath("//button[@routerlink=\"add\"]");
    By Btn_AirlineLogo = By.linkText("Airline Logo");
    By Btn_AirlinePriceMatrixLogo = By.linkText("Airline Price Matrix Logo");
    By Txt_AirlineCode = By.xpath("//input[@id=\"id-AirlineCode\"]");
    By Txt_AirlineName = By.xpath("//input[@id=\"id-AirlineName\"]");
    By Lst_Alliance = By.xpath("//p-dropdown[.//input[@id=\"id-Alliance\"]]");
    By Lst_AirlineType = By.xpath("//p-dropdown[.//input[@id=\"id-AirlineType\"]]");
    By Lst_NetworkType = By.xpath("//p-dropdown[.//input[@id=\"id-NetworkType\"]]");
    By Cbox_CodeSharing = By.xpath("//p-checkbox[.//input[@id=\"id--CodeSharing\"]]");
    By Cbox_AllowOnlineHold = By.xpath("//p-checkbox[.//input[@id=\"id--Allowonlinehold\"]]");
    By Lst_CountryPOS = By.xpath("//p-multiselect[.//input[@id=\"id-CountryPOS\"]]");
    By Lst_BranchPOS = By.xpath("//p-multiselect[.//input[@id=\"id-BranchPOS\"]]");
    By Lst_RestrictedAgencies = By.xpath("//p-multiselect[.//input[@id=\"id-RestrictedAgencies\"]]");
    By Cbox_SeatMappingInternational = By.xpath("(//p-checkbox[.//input[@id=\"id--International\"]])[1]");
    By Cbox_SeatMappingDomestic = By.xpath("(//p-checkbox[.//input[@id=\"id--Domestic\"]])[1]");
    By Cbox_OnlineTicketingRulesInternational = By.xpath("(//p-checkbox[.//input[@id=\"id--International\"]])[2]");
    By Cbox_OnlineTicketingRulesDomestic = By.xpath("(//p-checkbox[.//input[@id=\"id--Domestic\"]])[2]");
    By Lst_GDSTab = By.xpath("//a[@id=\"p-tabpanel-3-label\"]");
    By Btn_SendForApproval = By.xpath("(//button[@class='p-element col-sm-6 col-md-3 col-lg-2 mx-0 sm:mx-3 my-3 sm:my-0 justify-content-center p-ripple send p-button p-component ng-star-inserted'])[1]");
    By Btn_AllowOnHold = By.xpath("(//div[@class='p-checkbox-box'])[2]");
    By Lst_HoldCountryPOS = By.xpath("(//div[contains(text(),'Country Name')])[1]");
    By Btn_AllSelections = By.xpath("(//div[@role='checkbox'])[1]");
    By Lst_branchPOS = By.xpath("(//div[@class='p-multiselect-label p-placeholder'][normalize-space()='Select'])[1]");
    By Btn_OnlineTicketing = By.xpath("(//p-checkbox)[5]");
    By Lst_ticketingCountryPOS = By.xpath("(//div[@class='p-multiselect-label p-placeholder'])[3]");
    By Lst_ticketingBranchPOS = By.xpath("(//div[@id='p-tabpanel-13']//p-multiselect)[6]");


    public void setAddAirline(){
        driver.element().click(Btn_AddAirline);
    }

    public void AirlineLogo(){
        driver.element().click(Btn_AirlineLogo);
    }
    public void AirlineMatrix(){
        driver.element().click(Btn_AirlinePriceMatrixLogo);
    }
    public void GDS(){
        driver.element().click(Lst_GDSTab);
    }

    public String AirlineDetails(String airlinetype,String networktype){
        driver.element().type(Txt_AirlineCode,FakerSingleton.PassengerFactory.firstName());
        String Code = driver.element().getText(Txt_AirlineCode);
        driver.element().type(Txt_AirlineName, FakerSingleton.PassengerFactory.firstName());
        driver.element().select(Lst_AirlineType,airlinetype);
        driver.element().select(Lst_NetworkType,networktype);
        driver.element().click(Btn_AllowOnHold);
        driver.element().click(Lst_HoldCountryPOS);
        driver.element().click(Btn_AllSelections);
        driver.element().click(Lst_branchPOS);
        driver.element().click(Btn_AllSelections);
        driver.element().click(Btn_OnlineTicketing);
        driver.element().click(Lst_ticketingCountryPOS);
        driver.element().click(Btn_AllSelections);
        driver.element().click(Lst_ticketingBranchPOS);
        driver.element().click(Btn_AllSelections);
        return Code;
    }

    public void AllowOnlineHold(String countrypos,String branchpos,String restricted){
        driver.element().click(Cbox_AllowOnlineHold);
        driver.element().click(Lst_CountryPOS);
        By option1 = By.xpath(String.format("//span[contains(text(), '%s')]", countrypos));
        driver.element().click(option1);
        driver.element().click(Lst_BranchPOS);
        By option2 = By.xpath(String.format("//span[contains(text(), '%s')]", branchpos));
        driver.element().click(option2);
        driver.element().click(Lst_RestrictedAgencies);
        By option3 = By.xpath(String.format("//span[contains(text(), '%s')]", restricted));
        driver.element().click(option3);
    }

    public void SetMappingInternational(String countrypos,String branchpos,String restricted){
        driver.element().click(Cbox_SeatMappingInternational);
        driver.element().select(Lst_CountryPOS,countrypos);
        driver.element().select(Lst_BranchPOS,branchpos);
        driver.element().select(Lst_RestrictedAgencies,restricted);

    }

    public void SeatMappingDomestic(String countrypos,String branchpos,String restricted){
        driver.element().click(Cbox_SeatMappingDomestic);
        driver.element().select(Lst_CountryPOS,countrypos);
        driver.element().select(Lst_BranchPOS,branchpos);
        driver.element().select(Lst_RestrictedAgencies,restricted);

    }

    public void OnlineTicketingRulesInternational(String countrypos,String branchpos,String restricted){
        driver.element().click(Cbox_OnlineTicketingRulesInternational);
        driver.element().select(Lst_CountryPOS,countrypos);
        driver.element().select(Lst_BranchPOS,branchpos);
        driver.element().select(Lst_RestrictedAgencies,restricted);
    }

    public void OnlineTicketingRulesDomestic(String countrypos,String branchpos,String restricted){
        driver.element().click(Cbox_OnlineTicketingRulesDomestic);
        driver.element().select(Lst_CountryPOS,countrypos);
        driver.element().select(Lst_BranchPOS,branchpos);
        driver.element().select(Lst_RestrictedAgencies,restricted);
    }
   public void SendForApproval(){
        driver.element().click(Btn_SendForApproval);
   }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("(//span[@class=\"fg-error has-error\"])[1]"));
        return S;
    }
    public String Actual2()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }
}
