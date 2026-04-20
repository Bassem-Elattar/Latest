package AdminPages.Master.Flight.PreferAirline;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class EditPreferAirLine_TC extends TestBase_TC {
    //INValid Cases
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
    @Test  // Prefer Airline Already exists
    public void testUpdateForPreferAirLine1( ) throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
        preferAirLine.EnterAirlineName("40MileAir");
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage2();
        preferAirLine.AirLineNameEdit("Air Algerie");
        Thread.sleep(1000);
        preferAirLine.SupplierNameEdit("GDI");
        preferAirLine.SelectPCCForSupplierEdit("GDI Egypt PCC");
        preferAirLine.SendForApprovalButton();
        String Acual =driver.element().getText(By.xpath("//div[@aria-label=\"Prefer Airline already exists.\"]"));
        String Expected="Prefer Airline already exists.";
        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");
        Thread.sleep(2000);

    }
//    @Test//Prefer Airline With the restricted airlines to add prefer airline
//    public void testUpdateForPreferAirLine2( ) throws InterruptedException {
//        preferAirLine.EnterAirlineName("40-Mile Air");
//        preferAirLine.clickBothButton();
//        preferAirLine.clickOnSearchInGrid();
//        preferAirLine.clickEditPenPage2();
//        preferAirLine.AirLineNameEdit(" Dev Create");
//       //  preferAirLine.SupplierNameEdit("Galileo");
//        preferAirLine.SelectPCCForSupplierEdit("importpnr PCC");
//        preferAirLine.SendForApprovalButton();
//        String Acual =driver.element().getText(preferAirLine.RestricatedAirLine);
//        String Expected="Remove the restricted airlines to add prefer airline";
//        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");
//        Thread.sleep(2000);
//
//    }
    @Test // Validation On Required Pcc  for Supplier
    public void testUpdateForPreferAirLine3() throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
        preferAirLine.EnterAirlineName("Aires");
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage();
        preferAirLine.AirLineNameEdit(" Dev Create");
        preferAirLine.SupplierNameEdit("GDI");
        preferAirLine.SendForApprovalButton();
       String Actual =driver.element().getText(preferAirLine.ValidtionInPcc);
       String Expected="required validation error";
      Assert.assertEquals(Actual,Expected,"this issue !!! Pcc Is Not Required  ");
        Thread.sleep(2000);
    }
//    @Test  // Validation  On Required  Supplier
//    public void testUpdateForPreferAirLine4( ) throws InterruptedException {
//        preferAirLine.EnterAirlineName("Aires");
//        preferAirLine.clickBothButton();
//        preferAirLine.clickOnSearchInGrid();
//        preferAirLine.clickEditPenPage();
//        preferAirLine.AirLineNameEdit(" Dev Create");
//        preferAirLine.SendForApprovalButton();
//        String Actual =driver.element().getText(preferAirLine.ValidtionInPcc);
//        String Expected="required validation error";
//        Assert.assertEquals(Actual,Expected,"this issue !!! Pcc Is Not Required  ");
//        Thread.sleep(2000);
//    }
//    @Test// Validation  On Required  AirLineName
//    public void testUpdateForPreferAirLine5( ) throws InterruptedException {
//        preferAirLine.EnterAirlineName("Aires");
//        preferAirLine.clickBothButton();
//        preferAirLine.clickOnSearchInGrid();
//        preferAirLine.clickEditPenPage();
//        preferAirLine.SupplierNameEdit("Galileo");
//        preferAirLine.SelectPCCForSupplierEdit("Live Egypt PCC");
//        preferAirLine.SendForApprovalButton();
//        String Actual =driver.element().getText(preferAirLine.ValidtionInPcc);
//        String Expected="required validation error";
//        Assert.assertEquals(Actual,Expected,"this issue !!! Pcc Is Not Required  ");
//        Thread.sleep(2000);
//
//    }
    @Test
    public void testUpdateForPreferAirLine6( ) throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
        preferAirLine.EnterAirlineName("Aereonautica militare");
        preferAirLine.SelectSupplierName("GDI");
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage();
        preferAirLine.AirLineNameEdit("Aereonautica militare");
        preferAirLine.SupplierNameEdit("Galileo");
        preferAirLine.SelectPCCForSupplierEdit("Galileo Live UAE");
        preferAirLine.SendForApprovalButton();
        String Actual=driver.element().getText(preferAirLine.UpdatedSuccessfully);
        String Expected="Updated Successfully";
        Assert.assertEquals(Actual,Expected);
        Thread.sleep(2000);
        preferAirLine.EnterAirlineName("Aereonautica militare");
        preferAirLine.SelectSupplierName("Galileo");
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        assertEquals("Aereonautica militare",preferAirLine.TableColumnDataExtractor(0,"Aereonautica militare"));
        assertEquals("Galileo",preferAirLine.TableColumnDataExtractor(1,"Galileo"));
        assertEquals("Galileo Live UAE",preferAirLine.TableColumnDataExtractor(2,"Galileo Live UAE"));
        Thread.sleep(2000);
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage();
        preferAirLine.AirLineNameEdit("Aereonautica militare");
        preferAirLine.SupplierNameEdit("GDI");
        preferAirLine.SelectPCCForSupplierEdit("GDI Egypt PCC");
        preferAirLine.SendForApprovalButton();
        String Actual2=driver.element().getText(preferAirLine.UpdatedSuccessfully);
        String Expected2="Updated Successfully";
        Assert.assertEquals(Actual2,Expected2);
        preferAirLine.EnterAirlineName("Aereonautica militare");
        preferAirLine.SelectSupplierName("GDI");
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        Thread.sleep(2000);
        assertEquals("Aereonautica militare",preferAirLine.TableColumnDataExtractor(0,"Aereonautica militare"));
        assertEquals("GDI",preferAirLine.TableColumnDataExtractor(1,"GDI"));
        Thread.sleep(2000);
    }




}
