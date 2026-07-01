package AdminPages.Master.Flight.PreferAirline;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class SearchPreferAirLine_TC extends TestBase_TC {
    //Valid
    PreferAirLine_Page preferAirLine;
    LogIn_Page logIn;


    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }
    @BeforeTest
    public void sign(){
        preferAirLine = new PreferAirLine_Page(driver);
        logIn = new LogIn_Page(driver);
        logIn.ClickSuperAdmin();
        logIn.ClickOnLoginButton();
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
    }

    @Test(dataProvider = "SearchWith ValidDataPreferAirLine with Name and Supplier name", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testSearchForPreferAirLine1(String AirLineName ,String supplierName) throws InterruptedException {

        preferAirLine.EnterAirlineName(AirLineName);
        preferAirLine .SelectSupplierName(supplierName);
        preferAirLine.clickBothButton();
        preferAirLine .clickOnSearchInGrid();
        Thread.sleep(1000);
      //  assertEquals("Air Cairo",preferAirLine.TableColumnDataExtractor(0,"Air Cairo"));
        assertEquals(supplierName,preferAirLine.TableColumnDataExtractor(1,supplierName));
    }
    @Test(dataProvider = "SearchWithValidDataPreferAirLine Name only and active status", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testSearchForPreferAirLine2(String AirLineName ,String Expected) throws InterruptedException {

        preferAirLine.EnterAirlineName(AirLineName);
        preferAirLine.clickInActiveButton()
                .clickOnSearchInGrid();
        Thread.sleep(1000);
        assertEquals(AirLineName,preferAirLine.TableColumnDataExtractor(0,AirLineName));
       assertEquals(Expected,preferAirLine.TableColumnDataExtractor(3,Expected));

    }



    @Test(dataProvider = "SearchWithValidDataPreferAirLine Supplier only and inActive Status", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testSearchForPreferAirLine3(String supplierName,String Expected) throws InterruptedException {


        preferAirLine.SelectSupplierName(supplierName);
        preferAirLine.clickInActiveButton()
                .clickOnSearchInGrid();
        Thread.sleep(1000);
        assertEquals( supplierName,preferAirLine.TableColumnDataExtractor(1,supplierName));
        assertEquals(Expected,preferAirLine.TableColumnDataExtractor(3,Expected));
        Thread.sleep(1000);
    }


    @Test// SearchBookingTC With  InActive   Status
    public void testSearchForPreferAirLine5() throws InterruptedException {

        preferAirLine.clickInActiveButton();
        preferAirLine.clickOnSearchInGrid();
        Thread.sleep(1000);
        assertEquals("Inactive",preferAirLine.TableColumnDataExtractor(3,"Inactive"));
    }
    @Test // SearchBookingTC With  Active   Status
    public void testSearchForPreferAirLine6() throws InterruptedException {

        preferAirLine.clickActiveButton();
        preferAirLine.clickOnSearchInGrid();
        assertEquals("Active",preferAirLine.TableColumnDataExtractor(3,"Active"));
    }
    @Test // SearchBookingTC With Both Status
    public void testSearchForPreferAirLine7() throws InterruptedException {

        // logIn.ClickOnLoginButton();
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();

    }
    @Test(dataProvider = "SearchWith ValidDataPreferAirLine with Name,Supplier name and Both Status", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testSearchForPreferAirLine8(String AirLineName,String supplierName) throws InterruptedException {
        preferAirLine.EnterAirlineName(AirLineName);
        preferAirLine.SelectSupplierName(supplierName);
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
    }

    //Invalid Data
    @Test(dataProvider = "SearchWith ValidDataPreferAirLine with Name and Supplier name DataNotFounded", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testSearchForPreferAirLine11(String AirLineName ,String supplierName,String Expected) throws InterruptedException {
        preferAirLine.EnterAirlineName(AirLineName);
        preferAirLine .SelectSupplierName(supplierName)
                .clickOnSearchInGrid();
        String ActualResult=  driver.getDriver().findElement(By.xpath("//td[@class=\"message\"]")).getText();
        assertEquals(Expected, ActualResult);

    }

}
