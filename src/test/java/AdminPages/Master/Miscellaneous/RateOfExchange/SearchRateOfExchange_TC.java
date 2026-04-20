package AdminPages.Master.Miscellaneous.RateOfExchange;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import com.shaft.driver.SHAFT;
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
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        testData = new SHAFT.TestData.JSON("RateOfExchange.json");
    }
    //Valid TestCases
    @Test
    public void searchWithoutAnyData() {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.ClickSearchButton();
        rateOfExchange.paginateAndVerifyData();
    }

    @Test
    public void searchWithFromCurrency()   {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.ClickSearchButton();
        assertEquals(testData.getTestData("ValidData.FromCurrency"),rateOfExchange.TableColumnDataExtractor(1,testData.getTestData("ValidData.FromCurrency")));
    }

    @Test
    public void searchWithToCurrency() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.ClickSearchButton();
        assertEquals(testData.getTestData("ValidData.ToCurrency"),rateOfExchange.TableColumnDataExtractor(2,testData.getTestData("ValidData.ToCurrency")));
    }
    @Test
    public void searchWithFromAndToCurrency() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.SelectToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.ClickSearchButton();
        assertEquals(testData.getTestData("ValidData.FromCurrency"),rateOfExchange.TableColumnDataExtractor(1,testData.getTestData("ValidData.FromCurrency")));
        assertEquals(testData.getTestData("ValidData.ToCurrency"),rateOfExchange.TableColumnDataExtractor(2,testData.getTestData("ValidData.ToCurrency")));

    }
    //inValid
    @Test
    public void searchWithFromAndToCurrencyNoData() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.SelectFromCurrency(testData.getTestData("InValidData.InvalidFromCurrency"));
        rateOfExchange.SelectToCurrency(testData.getTestData("InValidData.InvalidToCurrency"));
        rateOfExchange.ClickSearchButton();
        String Actual =driver.element().getText(rateOfExchange.Txt_NoDataFounded);
        assertEquals(testData.getTestData("InValidData.NoResultsError"),Actual);
    }

    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

