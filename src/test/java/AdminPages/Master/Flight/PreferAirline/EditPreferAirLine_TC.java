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
    PreferAirLine_Page preferAirLine;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }
    @BeforeTest
    public void sign(){
        preferAirLine = new PreferAirLine_Page(driver);
        LogIn_Page logIn = new LogIn_Page(driver);
        logIn.ClickSuperAdmin();
        logIn.ClickOnLoginButton();
        new Master_Common(driver).clickMaster().clickFlight().clickPreferSAirline();
    }

      // Prefer Airline Already exists
    @Test(dataProvider = "UpdatePreferAirLineWithCValidDataAlreadyExist", dataProviderClass = PreferAirLineDataProvider_TC.class)
    public void testUpdateForPreferAirLine1(String AirLineName,String EditAirline,String supplierName,String EditSupplier,String Remark,String Expected ) throws InterruptedException {
        preferAirLine.EnterAirlineName(AirLineName);
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage2();
        preferAirLine.AirLineNameEdit(EditAirline);
        Thread.sleep(1000);
        preferAirLine.SupplierNameEdit(supplierName);
        preferAirLine.SelectPCCForSupplierEdit(EditSupplier);
        preferAirLine.EnterRemarksType(Remark);
        preferAirLine.SendForApprovalButton();
        String Acual =driver.element().getText(By.xpath("//div[@aria-label=\"Prefer Airline already exists.\"]"));
        Assert.assertEquals(Acual,Expected,"This Behaviour not correct ");
        Thread.sleep(2000);
//        preferAirLine.Cancel();

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
    @Test(dataProvider = "UpdatePreferAirLineWithCValidDataWithOUtRemark", dataProviderClass = PreferAirLineDataProvider_TC.class) // Validation On Required Pcc  for Supplier
    public void testUpdateForPreferAirLine3(String AirLineName,String EditAirline,String supplierName,String Expected) throws InterruptedException {
        preferAirLine.EnterAirlineName(AirLineName);
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage();
        preferAirLine.AirLineNameEdit(EditAirline);
        preferAirLine.SupplierNameEdit(supplierName);
        preferAirLine.SendForApprovalButton();
       String Actual =driver.element().getText(preferAirLine.ValidtionInPcc);
      Assert.assertEquals(Actual,Expected,"this issue !!! Pcc Is Not Required  ");
        Thread.sleep(2000);
//        preferAirLine.Cancel();
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
//   }
    @Test(dataProvider = "UpdatePreferAirLineWithCValidDataWithOUtName", dataProviderClass = PreferAirLineDataProvider_TC.class) // Validation  On Required  AirLineName
    public void testUpdateForPreferAirLine6(String AirlineName,String supplierName,String EditAirline,String EditSupplier,String EditPcc,String Remarks,String Expected,String Editpcc2) throws InterruptedException {
        preferAirLine.EnterAirlineName(AirlineName);
        preferAirLine.SelectSupplierName(supplierName);
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage();
        preferAirLine.AirLineNameEdit(EditAirline);
        preferAirLine.SupplierNameEdit(EditSupplier);
        preferAirLine.SelectPCCForSupplierEdit(EditPcc);

        preferAirLine.EnterRemarksType(Remarks);
        preferAirLine.SendForApprovalButton();
        String Actual=driver.element().getText(preferAirLine.UpdatedSuccessfully);
        Assert.assertEquals(Actual,Expected);
        Thread.sleep(2000);
        preferAirLine.EnterAirlineName(EditAirline);
        preferAirLine.SelectSupplierName(EditSupplier);
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
//        assertEquals(AirlineName,preferAirLine.TableColumnDataExtractor(0,AirlineName));
//        assertEquals(supplierName,preferAirLine.TableColumnDataExtractor(1,supplierName));
//        assertEquals(EditPcc,preferAirLine.TableColumnDataExtractor(2,EditPcc));
        Thread.sleep(2000);
//        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickEditPenPage();
        preferAirLine.AirLineNameEdit(AirlineName);
        preferAirLine.SupplierNameEdit(supplierName);
        preferAirLine.SelectPCCForSupplierEdit(Editpcc2);
        preferAirLine.SendForApprovalButton();
        String Actual2=driver.element().getText(preferAirLine.UpdatedSuccessfully);
        Assert.assertEquals(Actual2,Expected);
        preferAirLine.EnterAirlineName(AirlineName);
        preferAirLine.SelectSupplierName(supplierName);
        preferAirLine.clickBothButton();
        preferAirLine.clickOnSearchInGrid();
        Thread.sleep(2000);
        assertEquals(AirlineName,preferAirLine.TableColumnDataExtractor(0,AirlineName));
        assertEquals(supplierName,preferAirLine.TableColumnDataExtractor(1,supplierName));
        Thread.sleep(2000);
    }




}
