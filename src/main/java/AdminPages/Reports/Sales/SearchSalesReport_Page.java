package AdminPages.Reports.Sales;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

import static org.openqa.selenium.By.xpath;

public class SearchSalesReport_Page {
    public SearchSalesReport_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By Lst_BranchName = By.xpath("//p-multiselect[.//input[@name=\"Branch Name\"]]");
    By Lst_AgencyName = By.xpath("//p-multiselect[.//input[@id=\"id-AgencyName\"]]");
    By Txt_InvoiceNumber = By.xpath("//input[@placeholder=\"Invoice number\"]");
    By Txt_CustomerName = By.xpath("//input[@id=\"id-CustomerName\"]");
    By Dpick_PaymentDate = By.xpath("//input[@id='id-PaymentDate']");
    By Txt_TransactionID = By.xpath("//input[@id=\"id-TransactionID\"]");
    By Btn_Search = By.xpath("//button[@type=\"submit\"]");
    By InvoiceFromDate = xpath("//input[@id='id-InvoiceFromDate']");
    By InvoiceToDate = xpath("//input[@id='id-InvoiceToDate']");
    By Year = xpath("//button[normalize-space()='2026']");

    public void setBranchName(String branch){
        driver.element().select(Lst_BranchName,branch);
    }

    public void setAgencyName(String agency){
        driver.element().select(Lst_AgencyName, agency);
    }

    public void setDate(String From, String year, String month){
        driver.element().click(InvoiceFromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
    }

    public void setEndDate(String to, String year, String month){
        driver.element().click(InvoiceToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }

    public void setInvoiceNumber(String invoiceNumber){
        driver.element().type(Txt_InvoiceNumber,invoiceNumber);
    }

    public void setCustomerName(String customerName){
        driver.element().type(Txt_CustomerName,customerName);
    }

    public void setPaymentDate(String date){
        driver.element().click(Dpick_PaymentDate);
        By option4 = By.xpath(String.format("(//span[text()='%s'])[1]", date));
        driver.element().click(option4);
    }

    public void setTransactionID(String transactionID){
        driver.element().type(Txt_TransactionID,transactionID);
    }
    public void setSearch()
    {
      driver.element().click(Btn_Search);
    }

}


