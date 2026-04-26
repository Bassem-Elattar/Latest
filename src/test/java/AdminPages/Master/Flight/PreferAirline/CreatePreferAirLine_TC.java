package AdminPages.Master.Flight.PreferAirline;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;

public class CreatePreferAirLine_TC extends TestBase_TC {
    // Valid
    private PreferAirLine_Page preferAirLine ;
    private LogIn_Page logIn;

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
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();


    }


//    @Test(dataProvider = "JsonProvider")
//    public void testCreateForPreferAirLine1(String AirLineName, String supplierName,String SupplierPcc) throws InterruptedException {
//        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
//
//        preferAirLine.clickAddAirLineButton();
//        preferAirLine.AddAirlineName("Aero Lanka",AirLineName);
//        preferAirLine.clickOnSupplierList();
//        preferAirLine.SelectAddSupplierName(supplierName);
//        preferAirLine.clickOnPCCSupplierList();
//        preferAirLine.SelectAddPCCSupplierName(SupplierPcc);
//        preferAirLine.SendForApprovalButton();
//        preferAirLine.clickOnCancelButton();
//        preferAirLine.EnterAirlineName(AirLineName);
//        preferAirLine .SelectSupplierName(supplierName);
//        preferAirLine.clickBothButton()
//                .clickOnSearchInGrid();
//        assertEquals(AirLineName,preferAirLine.TableColumnDataExtractor(0,AirLineName));
//        assertEquals(supplierName,preferAirLine.TableColumnDataExtractor(1,supplierName));
//
//
//    }
    // Valid
    @Test
    public void testCreateForPreferAirLine() throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();

        preferAirLine.clickAddAirLineButton();
        preferAirLine.AddAirlineName("Aeropelican Air Services","Aeropelican Air Services");
        preferAirLine.clickOnSupplierList();
        preferAirLine.SelectAddSupplierName("Amadeus");
        preferAirLine.clickOnPCCSupplierList();
        preferAirLine.SelectAddPCCSupplierName("Amadeus Live");
        preferAirLine.SendForApprovalButton();
        String Acual =driver.element().getText(By.xpath("//div[@aria-label=\"Prefer Airline already exists.\"]"));
        String Expected="Prefer Airline already exists.";
        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");
        preferAirLine.clickOnCancelButton();
        preferAirLine.EnterAirlineName("Aeropelican Air Services");
        preferAirLine.clickOnSupplierList();
        preferAirLine.SelectSupplierName("Amadeus");
        preferAirLine.clickBothButton()
                .clickOnSearchInGrid();
        assertEquals("Aeropelican Air Services",preferAirLine.TableColumnDataExtractor(0,"Aeropelican Air Services"));
        assertEquals("Amadeus",preferAirLine.TableColumnDataExtractor(1,"Amadeus"));
    }
    @Test(dataProvider = "CreatePreferAirLineWithValidDataAlreadyExist", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testCreateForPreferAirLine2(String AirLineName, String supplierName ,String SupplierPcc) throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();

        preferAirLine.clickAddAirLineButton();
        preferAirLine.AddAirlineName("Aer", AirLineName);
        preferAirLine.clickOnSupplierList();
        preferAirLine.SelectAddSupplierName(supplierName);
        preferAirLine.clickOnPCCSupplierList();
        preferAirLine.SelectAddPCCSupplierName(SupplierPcc);
        preferAirLine.SendForApprovalButton();
        Thread.sleep(1000);
        String Acual =driver.element().getText(By.xpath("//div[@aria-label=\"Prefer Airline already exists.\"]"));
        String Expected="Prefer Airline already exists.";
        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");


    }
    @Test
    public void testCreateForPreferAirLineWithRestractedAirLine( ) throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();

        preferAirLine.clickAddAirLineButton();
        preferAirLine.AddAirlineName("Dev","Dev Create Five");
        preferAirLine.clickOnSupplierList();
        preferAirLine.SelectAddSupplierName("Galileo");
        preferAirLine.clickOnPCCSupplierList();
        preferAirLine.SelectAddPCCSupplierName("Live EGY PCC");
        preferAirLine.SendForApprovalButton();
        String Acual =driver.element().getText(preferAirLine.RestricatedAirLine);
        String Expected="Remove the restricted airlines to add prefer airline";
        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");
        Thread.sleep(1000);

    }

//    @Test(dataProvider = "CreatePreferAirLineWithValidDataWithSupplierPCC", dataProviderClass = PreferAirLineDataProvider_TC.class)
//    public void testCreateForPreferAirLine3(String AirLineName, String supplierName,String supplierPCC) throws InterruptedException {
//        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
//
//        preferAirLine.clickAddAirLineButton();
//        preferAirLine.AddAirlineName("40", AirLineName);
//        preferAirLine.clickOnSupplierList();
//        preferAirLine.SelectAddSupplierName(supplierName);
//        preferAirLine.clickOnPCCSupplierList();
//        preferAirLine.SelectAddPCCSupplierName(supplierPCC);
//        preferAirLine.SendForApprovalButton();
//        Thread.sleep(1000);
//        String Acual =driver.element().getText(preferAirLine.RestricatedAirLine);
//        String Expected="Remove the restricted airlines to add prefer airline";
//        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");
//
//    }

    @Test(dataProvider = "CreatePreferAirLineWithINValidName", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testCreateForPreferAirLine5(String AirLineName, String supplierName,String supplierPCC) throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();

        preferAirLine.clickAddAirLineButton();
        preferAirLine.AddAirLineName2(AirLineName);
        preferAirLine.clickOnSupplierList();
        preferAirLine.SelectAddSupplierName(supplierName);
        preferAirLine.clickOnPCCSupplierList();
        preferAirLine.SelectAddPCCSupplierName(supplierPCC);
        preferAirLine.SendForApprovalButton();
        Assert.assertEquals(driver.element().getText(preferAirLine.ValidationOnAirLineName),"Required");
    }
    @Test(dataProvider = "CreatePreferAirLineWithoutName", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testCreateForPreferAirLine6(String AirLineName, String supplierName) throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();

        preferAirLine.clickAddAirLineButton();
        preferAirLine.AddAirlineName("40",AirLineName);
        preferAirLine.clickOnSupplierList();
        preferAirLine.SelectAddSupplierName(supplierName);
        preferAirLine.SendForApprovalButton();
        Assert.assertEquals(driver.element().getText(preferAirLine.ValidationOnPCCName),"Required");

    }
    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }



}
