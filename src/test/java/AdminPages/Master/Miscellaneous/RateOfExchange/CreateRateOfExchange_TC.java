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
import static org.junit.Assert.assertFalse;


public class CreateRateOfExchange_TC extends TestBase_TC {
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
    public void CreateRateWithValidData() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("ValidData.RateOfExchange"));
        rateOfExchange.ClickSendForApprovel();
        rateOfExchange.SelectFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.SelectToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.ClickSearchButton();
        assertEquals(testData.getTestData("ValidData.FromCurrency"),rateOfExchange.TableColumnDataExtractor(1,testData.getTestData("ValidData.FromCurrency")));
        assertEquals(testData.getTestData("ValidData.ToCurrency"),rateOfExchange.TableColumnDataExtractor(2,testData.getTestData("ValidData.ToCurrency")));
        assertEquals(testData.getTestData("ValidData.RateOfExchange"),rateOfExchange.TableColumnDataExtractor(3,testData.getTestData("ValidData.RateOfExchange")));
    }

    @Test
    public void CreateRateWithValidDataWithSameCurrency() throws InterruptedException {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("ValidData.RateOfExchange"));
        rateOfExchange.ClickSendForApprovel();
        rateOfExchange.SelectFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.SelectToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.ClickSearchButton();
        assertEquals(testData.getTestData("ValidData.FromCurrency"),rateOfExchange.TableColumnDataExtractor(1,testData.getTestData("ValidData.FromCurrency")));
        assertEquals(testData.getTestData("ValidData.ToCurrency"),rateOfExchange.TableColumnDataExtractor(2,testData.getTestData("ValidData.ToCurrency")));
        assertEquals(testData.getTestData("ValidData.RateOfExchange"),rateOfExchange.TableColumnDataExtractor(3,testData.getTestData("ValidData.RateOfExchange")));
    }
    //update on change of Currency
    @Test
    public void CreateRateWithValidDataUpdate(){
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("ValidData.RateOfExchange"));
        rateOfExchange.ClickSendForApprovel();
        rateOfExchange.SelectFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.SelectToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.ClickSearchButton();
        assertEquals(testData.getTestData("ValidData.FromCurrency"),rateOfExchange.TableColumnDataExtractor(1,testData.getTestData("ValidData.FromCurrency")));
        assertEquals(testData.getTestData("ValidData.ToCurrency"),rateOfExchange.TableColumnDataExtractor(2,testData.getTestData("ValidData.ToCurrency")));
        assertEquals(testData.getTestData("ValidData.RateOfExchange"),rateOfExchange.TableColumnDataExtractor(3,testData.getTestData("ValidData.RateOfExchange")));

    }
    @Test
    public void CancelRateWithValidDataUpdate(){
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("ValidData.RateOfExchange"));
        rateOfExchange.ClickCancel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationOnCancelButton);
        assertEquals(testData.getTestData("ValidData.ExpectedError"),Actual);
    }

    //Invalid testcases
    @Test
    public void CreateRateWithoutFromCurrency()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("ValidData.RateOfExchange"));
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorFromCurrency);
        assertEquals(testData.getTestData("ValidData.RequiredError"),Actual);

    }
    @Test
    public void CreateRateWithoutToCurrency()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("ValidData.RateOfExchange"));
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorToCurrency);
        assertEquals(testData.getTestData("ValidData.RequiredError"),Actual);

    }
    @Test
    public void CreateRateWithoutRateCurrency()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorForRate);
        assertEquals(testData.getTestData("ValidData.RequiredError"),Actual);
    }

    // issue under solving
    @Test
    public void CreateRateWithInvalidRateCurrencyZero()  {
        rateOfExchange= new RateOfExchange_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRateOfExchange();
        rateOfExchange.AddRateButton();
        rateOfExchange.AddFromCurrency(testData.getTestData("ValidData.FromCurrency"));
        rateOfExchange.AddToCurrency(testData.getTestData("ValidData.ToCurrency"));
        rateOfExchange.AddRateOfExchange(testData.getTestData("InValidData.RateOfExchange1"));
        rateOfExchange.ClickSendForApprovel();
        String Actual=driver.element().getText(rateOfExchange.Txt_ValidationErrorForRate);
        assertEquals(testData.getTestData("InValidData.ExpectedError1"),Actual);

    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
