package AdminPages.Reports.Statement;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import static org.openqa.selenium.By.xpath;

public class State {
    public State( SHAFT.GUI.WebDriver driver)
    {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver;
    By Branch = xpath("//p-multiselect[.//input[@id=\"id-BranchName\"]]");
    By Agency = xpath("//p-multiselect[.//input[@id=\"id-AgencyName\"]]");
    By InvoiceFromDate = xpath("//input[@id='id-InvoiceFromDate']");
    By InvoiceToDate = xpath("//input[@id='id-InvoiceToDate']");
    By Submit = xpath("//button[@type=\"submit\"]");
    By Reports = xpath("//a[@href=\"/reports\"]");
    By BranchError = xpath("//span[@class='fg-error has-error']");
    By FromError = xpath("//span[@class='fg-error has-error']");
    By ToError = xpath("//span[@class='fg-error has-error']");
    By ErrorFromAfterTo = xpath("//span[@class='fg-error has-error']");
    By BeforeBTN = xpath("(//button[@class='p-ripple p-element p-datepicker-prev p-link ng-tns-c56-10 ng-star-inserted'])[1]");
    By BeforeError = xpath("//span[@class='fg-error has-error']");
    By Year = By.xpath("//button[normalize-space()='2026']");


    public void searchValidBranch(String branch){
        driver.element().select(Branch,branch);
        }

    public void searchValidAgency(String agency){
        driver.element().select(Agency,agency);
    }

    public void searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceFromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
    }

    public void searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }

    public void Submit(){
        driver.element().click(Submit);
    }

    public void SearchInvalidFromAfterTo(String branch,String From ,String To){
        driver.element().select(Branch,branch);
        driver.element().click(InvoiceFromDate);
        By option = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(option);
        driver.element().click(InvoiceToDate);
        By option1 = xpath(String.format("(//span[text()='%s'])[1]", To));
        driver.element().click(option1);
    }

    public void SearchInvalid60Days(String branch,String From ,String To){
        driver.element().select(Branch,branch);
        driver.element().click(InvoiceFromDate);
        for (int i=0; i<3; i++){
            driver.element().click(BeforeBTN);
        }
        By option = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(option);
        driver.element().click(InvoiceToDate);
        By option1 = xpath(String.format("(//span[text()='%s'])[1]", To));
        driver.element().click(option1);
    }
}



