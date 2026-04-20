package AdminPages.Admin.Agency.AgencyRegistrationRequest;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

import static org.openqa.selenium.By.xpath;

public class RegistrationRequest_Page {

    public RegistrationRequest_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver ;


    By Btn_Agency = By.linkText("Agency");
    By Btn_RegistrationRequest = By.xpath("//a[@href=\"/admin/registration-requests\"]");
    By Txt_AgencyName = By.xpath("//input[@id='id-Agencyname']");
    By Txt_ContactName = By.xpath("//input[@id='id-Contactname']");
    By Dpick_CleanderClick = By.xpath("//input[@id='id-Requestdate']");
    By Txt_Email = By.xpath("//input[@id='id-email']");
    By Rbtn_New = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-New\"]]");
    By Rbtn_inProgress = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inprogress\"]]");
    By Rbtn_Rejected = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Rejected\"]]");
    By Btn_Searchgrid = By.xpath("//button[@type='submit']");
    By Year = By.xpath("//button[normalize-space()='2026']");

    public void setAgency(){
        driver.element().click(Btn_Agency);
    }

    public void setRegistrationRequest(String agencyname, String contactname, String email){
        driver.element().type(Txt_AgencyName,agencyname);
        driver.element().type(Txt_ContactName,contactname);
        driver.element().type(Txt_Email,email);
    }
    public void setNew(){
        driver.element().click(Rbtn_New);
    }

    public void setInProgress(){
        driver.element().click(Rbtn_inProgress);
    }

    public void setRejected(){
        driver.element().click(Rbtn_Rejected);
    }

    public void setSearchgrid(){
        driver.element().click(Btn_Searchgrid);
    }

    public void searchValidDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(Dpick_CleanderClick);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }


}