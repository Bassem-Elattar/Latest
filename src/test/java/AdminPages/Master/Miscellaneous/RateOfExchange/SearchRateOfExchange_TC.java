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

public class SearchRateOfExchange_TC extends TestBase_TC {
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
    public void searchWithoutAnyData() {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.ClickSearchButton();
        // rateOfExchange.PaginationAutomation();
        rateOfExchange.paginateAndVerifyData();
    }
    @Test
    public void searchWithFromCurrency()   {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectFromCurrency("Egyptian Pound");
        rateOfExchange.ClickSearchButton();
        assertEquals("Egyptian Pound",rateOfExchange.TableColumnDataExtractor(1,"Egyptian Pound"));
    }
    @Test
    public void searchWithToCurrency() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectToCurrency("Egyptian Pound");
        rateOfExchange.ClickSearchButton();
        assertEquals("Egyptian Pound",rateOfExchange.TableColumnDataExtractor(2,"Egyptian Pound"));
    }
    @Test
    public void searchWithFromAndToCurrency() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectFromCurrency("United States Dollar");
        rateOfExchange.SelectToCurrency("Egyptian Pound");
        rateOfExchange.ClickSearchButton();
        assertEquals("United States Dollar",rateOfExchange.TableColumnDataExtractor(1,"United States Dollar"));
        assertEquals("Egyptian Pound",rateOfExchange.TableColumnDataExtractor(2,"Egyptian Pound"));

    }
    //inValid
    @Test
    public void searchWithFromAndToCurrencyNoData() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectFromCurrency("testCurremcy");
        rateOfExchange.SelectToCurrency("Euro");
        rateOfExchange.ClickSearchButton();
        String Actual =driver.element().getText(rateOfExchange.Txt_NoDataFounded);
        assertEquals("No data has been found!",Actual);

    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

