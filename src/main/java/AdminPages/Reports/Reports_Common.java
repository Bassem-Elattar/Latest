package AdminPages.Reports;

import AdminPages.RuleEngine.RuleEngine_Common;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class Reports_Common {
    SHAFT.GUI.WebDriver driver;
    private final By btn_Reports = By.xpath("//a[@id='p-tabpanel-8-label']");

                 ////////////////subs////////////////
    private final By btn_AgencyWallet = By.xpath("//a[@href='/reports/agency-wallet']");
    private final By btn_Booking = By.xpath("//a[@href='/reports/booking']");
    private final By btn_ImportPNR = By.xpath("//a[@href='/reports/pnr']");
    private final By btn_ARLedger = By.xpath("//a[@href='/reports/ar-ledger']");
    private final By btn_Quotation= By.xpath("//a[@href='/reports/quotation']");
    private final By btn_ReleasedPNR= By.xpath("//a[@href='/reports/release']");
    private final By btn_Sales = By.xpath("//a[@href='/reports/sales']");
    private final By btn_Statement = By.xpath("//a[@href='/reports/statement']");
    private final By btn_SyncPNR = By.xpath("//a[@href='/reports/sync']");
    private final By btn_TotalDueToNDC = By.xpath("//a[@href='/reports/total-ndc']");
    private final By btn_WalletHistory = By.xpath("//a[@href='/reports/wallet-history']");
    private final By btn_Finance = By.xpath("//a[@href='/reports/financial']");
    private final By btn_DailySales = By.xpath("//a[@href='/reports/daily-sales']");
    private final By btn_TransferPNR = By.xpath("//a[@href='/reports/transfer-pnr']");

    public Reports_Common(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
    }

    public Reports_Common clickReports(){
        driver.element().click(btn_Reports);
        return this;
    }
    public Reports_Common clickAgencyWallet(){
        driver.element().click(btn_AgencyWallet);
        return this;
    }
    public Reports_Common clickBooking(){
        driver.element().click(btn_Booking);
        return this;
    }
    public Reports_Common clickImportPNR(){
        driver.element().click(btn_ImportPNR);
        return this;
    }
    public Reports_Common clickARLedger(){
        driver.element().click(btn_ARLedger);
        return this;
    }
    public Reports_Common clickQuotation(){
        driver.element().click(btn_Quotation);
        return this;
    }
    public Reports_Common clickReleasedPNR(){
        driver.element().click(btn_ReleasedPNR);
        return this;
    }
    public Reports_Common clickSales(){
        driver.element().click(btn_Sales);
        return this;
    }
    public Reports_Common clickStatement(){
        driver.element().click(btn_Statement);
        return this;
    }
    public Reports_Common clickSyncPNR(){
        driver.element().click(btn_SyncPNR);
        return this;
    }
    public Reports_Common clickTotalDueToNDC(){
        driver.element().click(btn_TotalDueToNDC);
        return this;
    }
    public Reports_Common clickWalletHistory(){
        driver.element().click(btn_WalletHistory);
        return this;
    }
    public Reports_Common clickFinance(){
        driver.element().click(btn_Finance);
        return this;
    }public Reports_Common clickDailySales(){
        driver.element().click(btn_DailySales);
        return this;
    }
    public Reports_Common clickTransferPNR(){
        driver.element().click(btn_TransferPNR);
        return this;
    }



}
