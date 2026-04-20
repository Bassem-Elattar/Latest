package AdminPages.Reports.WalletHistoryReport;

import AdminPages.Reports.Quotation.Quotation_Page;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

import static org.openqa.selenium.By.xpath;

public class WalletHistoryReport_Page {


    public WalletHistoryReport_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;


    public static final String P_DROPDOWN_INPUT_ID_ID_AGENCY_NAME = "//p-dropdown[.//input[@id=\"id-AgencyName\"]]";
    public static final String BUTTON_TYPE_SUBMIT = "//button[@type=\"submit\"]";
    public static final String A_HREF_REPORTS = "//a[@href=\"/reports\"]";
    public static final String IMG_SRC_STATIC_IMAGES_ICONS_ARROW_7_PNG_6 = "(//img[@src=\"../static/images/icons/arrow_7.png\"])[6]";
    private final By selectBranch = By.xpath("//li[@aria-label='Test']");
    private final By Lst_branchName = By.xpath("//span[normalize-space()='Branch Name']");
    By Btn_Reports = By.xpath(A_HREF_REPORTS);
    By Btn_WalletReport = By.xpath(IMG_SRC_STATIC_IMAGES_ICONS_ARROW_7_PNG_6);
    By Lst_AgencyName = By.xpath(P_DROPDOWN_INPUT_ID_ID_AGENCY_NAME);
    By Dpick_FromDate = By.xpath("//input[@id='id-InvoiceFromDate']");
    By Dpick_ToDate = By.xpath("//input[@id='id-InvoiceToDate']");
    By Btn_SearchButton = By.xpath(BUTTON_TYPE_SUBMIT);
    By Year = xpath("//button[normalize-space()='2026']");


    public void SelectBranch(){
        driver.element().click(Lst_branchName);
        driver.element().click(selectBranch);
    }


    public void setAgencyName(String agency){
        driver.element().click(Lst_AgencyName);
        By option1 = By.xpath(String.format("//span[contains(text(), '%s')]",agency ));
        driver.element().click(option1);
    }

    public void searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(Dpick_FromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
    }

    public void searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(Dpick_ToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }

    public void setReports(){
        driver.element().click(Btn_Reports);
    }
    public void setWalletReport(){
        driver.element().click(Btn_WalletReport);
    }
    public void setSearchButton(){
        driver.element().click(Btn_SearchButton);
    }
}
