package AdminPages.Master.Miscellaneous.RateOfExchange;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class CreateRateOfExchange_TC extends TestBase_TC {
    private LogIn_Page logIn;
    private RateOfExchange_Page rateOfExchange;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }
    //Valid TestCases
    @Test
    public void CreateRateWithValidData() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("Saudi Riyal");
        rateOfExchange.AddToCurrency("Egyptian Pound");
        rateOfExchange.AddRateOfExchange("12345.05");
        rateOfExchange.ClickSendForApprovel();
        rateOfExchange.SelectFromCurrency("Saudi Riyal");
        rateOfExchange.SelectToCurrency("Egyptian Pound");
        rateOfExchange.ClickSearchButton();
        assertEquals("Saudi Riyal",rateOfExchange.TableColumnDataExtractor(1,"Saudi Riyal"));
        assertEquals("Egyptian Pound",rateOfExchange.TableColumnDataExtractor(2,"Egyptian Pound"));
        assertEquals("12345.05",rateOfExchange.TableColumnDataExtractor(3,"12345.05"));

    }
    @Test
    public void CreateRateWithValidDataWithSameCurrency() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("Saudi Riyal");
        rateOfExchange.AddToCurrency("Egyptian Pound");
        rateOfExchange.AddRateOfExchange("47");
        rateOfExchange.ClickSendForApprovel();
        rateOfExchange.SelectFromCurrency("Saudi Riyal");
        rateOfExchange.SelectToCurrency("Egyptian Pound");
        rateOfExchange.ClickSearchButton();
        assertEquals("Saudi Riyal",rateOfExchange.TableColumnDataExtractor(1,"Saudi Riyal"));
        assertEquals("Egyptian Pound",rateOfExchange.TableColumnDataExtractor(2,"Egyptian Pound"));
        assertEquals("47",rateOfExchange.TableColumnDataExtractor(3,"47"));
    }
    //update on change of Currency
    @Test
    public void CreateRateWithValidDataUpdate(){
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("United States Dollar");
        rateOfExchange.AddToCurrency("Egyptian Pound");
        rateOfExchange.AddRateOfExchange("44");
        rateOfExchange.ClickSendForApprovel();
        rateOfExchange.SelectFromCurrency("United States Dollar");
        rateOfExchange.SelectToCurrency("Egyptian Pound");
        rateOfExchange.ClickSearchButton();
        assertEquals("United States Dollar",rateOfExchange.TableColumnDataExtractor(1,"United States Dollar"));
        assertEquals("Egyptian Pound",rateOfExchange.TableColumnDataExtractor(2,"Egyptian Pound"));
        assertEquals("44",rateOfExchange.TableColumnDataExtractor(3,"44"));

    }
    @Test
    public void CancelRateWithValidDataUpdate(){
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("United States Dollar");
        rateOfExchange.AddToCurrency("Egyptian Pound");
        rateOfExchange.AddRateOfExchange("44");
        rateOfExchange.ClickCancel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationOnCancelButton);
        assertEquals("Search Rate of Exchange",Actual);


    }

    //Invalid testcases

    @Test
    public void CreateRateWithoutFromCurrency()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddToCurrency("Euro");
        rateOfExchange.AddRateOfExchange("44");
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorFromCurrency);
        assertEquals("Required",Actual);

    }
    @Test
    public void CreateRateWithoutToCurrency()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("United States Dollar");
        rateOfExchange.AddRateOfExchange("44");
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorToCurrency);
        assertEquals("Required",Actual);

    }
    @Test
    public void CreateRateWithoutRateCurrency()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("United States Dollar");
        rateOfExchange.AddToCurrency("Egyptian Pound");
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorForRate);
        assertEquals("Required",Actual);

    }

    // issue under solving
    @Test
    public void CreateRateWithInvalidRateCurrencyZero()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency("United States Dollar");
        rateOfExchange.AddToCurrency("Egyptian Pound");
        rateOfExchange.AddRateOfExchange("0");
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorForRate);
        assertEquals("Invalid Exchange Rate",Actual);

    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
