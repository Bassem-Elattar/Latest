package AdminPages.Master.Miscellaneous.RateOfExchange;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class SearchRateOfExchange_TC{
    private LogIn_Page logIn;
    private RateOfExchange_Page rateOfExchange;
    SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;

    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        // Admin login
        new LogIn_Page(driver).AdminLogin();
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
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}

