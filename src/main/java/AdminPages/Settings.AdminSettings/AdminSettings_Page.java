package AdminPages.Settings.AdminSettings;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class AdminSettings_Page {
    //constant
    private final SoftAssert softAssert;

    public AdminSettings_Page(SHAFT.GUI.WebDriver driver) {

        this.softAssert = new SoftAssert();

        this.driver = driver;
    }
    static SHAFT.GUI.WebDriver driver;
    public final By Username = By.id("id-Username");
    public final By Password = By.id("id-Password");
    public final By LogINButton = By.xpath("// button[@loadingicon=\"pi pi-spin pi-spinner\"]");
    private final By Master = By.xpath("//span[normalize-space()='Master']");
    private final By Supplier = By.xpath("//span[normalize-space()='Supplier']");
    private final By FlightSearchLimitationButton = By.xpath("//a[@href='/master/supplier/flight-search-limitation']");
    By Settings = By.xpath("//span[normalize-space()='Settings']");
    By AdminSettings = By.xpath("//a[@class='ng-star-inserted']");


    public final By EditButton = By.xpath("//body[1]/ndc-root[1]/ndc-layout[1]/div[1]/div[3]/div[1]/div[1]/ndc-flight-search-limitation[1]/div[1]/tilde-data-table[1]/div[1]/div[1]/div[1]/div[1]/div[2]/p-table[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[4]/div[1]/div[1]/i[1]");

    public final By LimitSupplier = By.xpath("//p-dropdown[@class=\"p-element p-inputwrapper fg-input " +
            "ng-untouched ng-pristine ng-valid ng-star-inserted p-inputwrapper-filled\"]");
    public final By ApproveButton = By.xpath("//button[@type=\"submit\"]");
    public final By GetLimit = By.xpath("(//div//table//tbody//tr//td)[3]");
    public AdminSettings_Page NavigateToFlightLimitation(String Check)
    {
        driver.element().click(Master);
        driver.element().click(Supplier);
        driver.element().click(FlightSearchLimitationButton);
        driver.element().click(EditButton);
        driver.element().click(LimitSupplier);
        final By SelectIndex = By.xpath("(//p-dropdown//div//ul//p-dropdownitem[@class=\"p-element ng-star-inserted\"])["+Check+"]");
        driver.element().click(SelectIndex);
        driver.element().click(ApproveButton);
        return this;

    }
    public final By ClickOnSupplier = By.xpath("//span[@class=\"p-dropdown-label p-inputtext p-placeholder ng-star-inserted\"]");
    public AdminSettings_Page ClickOnSupplierAndChooseIndex(String Check){


        driver.element().click(ClickOnSupplier);
        final By SelectIndex = By.xpath("//p-dropdown//p-overlay//div//div//ul//p-dropdownitem["+ Check+"]");
        driver.element().click(SelectIndex);
        return this;
    }
    public AdminSettings_Page NavigateToAdminSetting()
    {
        driver.element().click(Settings);
        driver.element().click(AdminSettings);

        return this;

    }

    public AdminSettings_Page EditNewFlightNum(String S)
    {
        driver.element().click(EditFlight);
        driver.element().clear(EditBox);
        driver.element().type(EditBox, S);
        driver.element().click(Save);

        return this;

    }
    By BookingMidOffice = By.xpath("//span[normalize-space()='Booking-Mid Office']");
    By ExpandMenu = By.xpath("//body/ndc-root/ndc-layout[@class='ng-star-inserted']/div[@class='content']/div[@class='side-menu collapsed-sidebar']/button[1]");
    By DropDownBookingMidOffice = By.xpath("//a[@class='ng-star-inserted']//span[contains(text(),'Booking-Mid Office')]");
    By Booking = By.xpath("//span[normalize-space()='Booking']");
    By BranchList = By.xpath("//span[normalize-space()='Branch*']");
    By Up_Selling = By.xpath("//p-checkbox[@formcontrolname='isUpSelling']//div[@class='p-checkbox-box']");
    public AdminSettings_Page NavigateToBooking()
    {
        driver.element().click(BookingMidOffice);
        driver.element().click(BranchList);
        final By SelectIndex = By.xpath("//li[@aria-label='Test']");
        driver.element().click(SelectIndex);
        driver.element().click(Up_Selling);

        return this;

    }

    public AdminSettings_Page EditNewUpsellingNum(String S)
    {
        driver.element().click(EditUpSelling);
        driver.element().clear(EditBox);
        driver.element().type(EditBox, S);
        driver.element().click(Save);

        return this;

    }
    public AdminSettings_Page EditNewRefundNum(String S)
    {
        driver.element().click(Pages);
        driver.element().click(EditRefund);
        driver.element().clear(EditBox);
        driver.element().type(EditBox, S);
        driver.element().click(Save);

        return this;

    }


    By EditFlight = By.xpath("(//i[@class=\"pi pi-pencil\"])[10]");
    By EditUpSelling = By.xpath("(//i[@class=\"pi pi-pencil\"])[10]");
    By Pages = By.xpath("//button[@class=\"page-no ng-star-inserted\"]");
    By EditRefund = By.xpath("(//i[@class=\"pi pi-pencil\"])[3]");
    By EditBox = By.xpath("//input[@id=\"id-Value\"]");
    By Save = By.xpath("//button[@type=\"submit\"]");
    protected AdminSettings_Page adminsetting1;

    private final By AngelDownForLogOut=By.xpath("//i[@style=\"font-size: 1rem;\"]");
    private final By LogOutButton = By.xpath("//span[@class=\"p-menuitem-icon pi pi-sign-out ng-star-inserted\"]");

    public AdminSettings_Page LoginAsAdmin() {

        driver.element().click(AngelDownForLogOut);
        driver.element().click(LogOutButton);

        driver.element().type(Username, "e.saady");
        driver.element().type(Password, "qqE6)Cxp6>B8");
        driver.element().click(LogINButton);
        adminsetting1 = new AdminSettings_Page(driver);

        return this;
    }
    private final By AngelDownForLogOut1=By.xpath("//img[@src=\"../static/img/dropdown_arrow_icon.png\"]");
    private final By LogOutButton1 = By.xpath("(//a[@href=\"/odeysysadmin/logout\"])[1]");

    public AdminSettings_Page LoginAsSuperAdmin() {

        driver.element().click(AngelDownForLogOut1);
        driver.element().click(LogOutButton1);

        driver.element().type(Username, "odeysysadmin");
        driver.element().type(Password, "qqE6)Cxp6>B8");
        driver.element().click(LogINButton);
        adminsetting1 = new AdminSettings_Page(driver);

        return this;
    }

}