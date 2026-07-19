package AdminPages.Master.DealCode;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

public class CreateDealCode{
    SHAFT.TestData.JSON testData;
    SoftAssert softAssert = new SoftAssert();
    public SHAFT.GUI.WebDriver driver;
    @BeforeClass
    public void SetupBrowser (){
        testData = new SHAFT.TestData.JSON("src/test/resources/testDataFiles/DealCode.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();
    }

    @Test(priority = 1) // TODO : End To End Scenario Create By Sector
    public void Create_DEALCODE () throws InterruptedException {
        // SoftAssert softAssert = new SoftAssert();
        new Master_Common(driver).clickMaster().clickFlight().clickDealCode();
        new Create.CreateDEalCode(driver).clickONAddDealCodeButton().choseSupplier(testData.getTestData("supplier"))
                .choseSupplierCredential(testData.getTestData("supplierCredential"))
                .choseAireLine(testData.getTestData("AireLine"))
                .SelectFrom().writeFirstSectorFrom(testData.getTestData("firstSectorFrom"))
                .clickONCheckBox1().
                //.SelectFrom().SelectFrom().writeSecondSectorFrom(testData.getTestData("secondSectorFrom")).clickONCheckBox2()//TODO: بضغط مرتين  + اكن ضغطة منهم ضغطها بره والتانية ع نفس المكان

                        selectTo().writeFirstSectorTo(testData.getTestData("FirstSectorTO"))  // TODO : ضغط تانى ع select from  عشان يخفى القايمه اكن ضغط  ف  اى مكان بره

                .clickONCheckBox1().writeDealCode()
                .salesValidityFrom(testData.getTestData("SalesFrom"))
                .salesValidityTO(testData.getTestData("SalesTO"))
                .travelValidityFrom(testData.getTestData("travelFrom"))
                .travelValidityTO(testData.getTestData("travelTO"));

//TODO :  Assert for sector From is not empty have a sector
//  String sectorData = createDealCode.chosenSector();
//  Assert.assertFalse(sectorData.isEmpty());
        new Create.CreateDEalCode(driver).clickOnSendForAPPROVEL();

        //TODO Assert For create a deal code well
  String actualadded = new Create.CreateDEalCode(driver).returnActualMsg();
  String expectedadd = "Deal Code Added Successfully";
 softAssert.assertEquals(actualadded,expectedadd);

        new Master.SearchAction(driver).choseinactive().clickONSearch();

        new Create.CreateDEalCode(driver).clickONapprovelAction().writeRemark(testData.getTestData("Remark"))
                .clickONSubmit();

//TODO Assert For change status for deal code from  - Pending for approval - to approval
        String actualApproved = new Create.CreateDEalCode(driver).returnapprovedMsg();
        String expectedApproved = "Deal Code Approved";
        softAssert.assertEquals(actualApproved,expectedApproved);

        new Master.SearchAction(driver).choseactive().clickONSearch().clickOnExportExcel();
        softAssert.assertAll();
    }

    @Test(priority = 2) // TODO : End To End Scenario Create By Country
    public void CreateDealCode_Country () throws InterruptedException {
        new Master_Common(driver).clickMaster().clickFlight().clickDealCode();
        new Create.CreateDEalCode(driver).clickONAddDealCodeButton().choseSupplier(testData.getTestData("supplier"))
                .choseSupplierCredential(testData.getTestData("supplierCredential"))
                .choseAireLine(testData.getTestData("AireLine"))
                .ChoseCountry().SelectCountryFrom().writeFirstSectorFrom(testData.getTestData("FirstCountryFrom"))
                .clickONCountryCheckBox1().SelectCountryTo()
                .writeFirstSectorTo(testData.getTestData("FirstCountryTO"))
                .clickONCountryCheckBox2().writeDealCode()
                .salesValidityFrom(testData.getTestData("SalesFrom"))
                .salesValidityTO(testData.getTestData("SalesTO"))
                .travelValidityFrom(testData.getTestData("travelFrom"))
                .travelValidityTO(testData.getTestData("travelTO"));

//TODO :  Assert for Country From is not empty
//        String sectorData = createDealCode.chosenSector();
//        Assert.assertFalse(sectorData.isEmpty());
        new Create.CreateDEalCode(driver).clickOnSendForAPPROVEL();

//TODO Assert For create a deal code well
        String actualadded = new Create.CreateDEalCode(driver).returnActualMsg();
        String expectedadd = "Deal Code Added Successfully";
        softAssert.assertEquals(actualadded,expectedadd);

        new Master.SearchAction(driver).choseinactive().clickONSearch();
        new Create.CreateDEalCode(driver).clickONapprovelAction().writeRemark(testData.getTestData("Remark"))
                .clickONSubmit();

//TODO Assert For change status for deal code from  - Pending for approval - to approval
        String actualApproved = new Create.CreateDEalCode(driver).returnapprovedMsg();
        String expectedApproved = "Deal Code Approved";
        softAssert.assertEquals(actualApproved,expectedApproved);

        new Master.SearchAction(driver).choseactive().clickONSearch().clickOnExportExcel();
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void EditDealCode(){
        new Master_Common(driver).clickMaster().clickFlight().clickDealCode();
        new Create.CreateDEalCode(driver).Edit();

        //String supplierData = createDealCode.EditePage();
        //TODO : Assert For all field not empty
        try {

            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_Supplier).isBlank());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_supplierCredential).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_AirLine).isEmpty());
            Assert.assertFalse(driver.element().getText (new Create.CreateDEalCode(driver).Lst_SelectFrom).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Lst_SelectTO).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_DealCode).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_SalesFrom).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_SalesTO).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_TravelFrom).isEmpty());
            Assert.assertFalse(driver.element().getText(new Create.CreateDEalCode(driver).Txt_TravelTO).isEmpty());
            //TODO : Assert for button  "  approve - reject - send for approval "
//            Assert.assertTrue(driver.element().isElementClickable(new Create.CreateDEalCode(driver).Btn_Approve));
//            Assert.assertTrue(driver.element().isElementClickable(new Create.CreateDEalCode(driver).Btn_Reject));
//            Assert.assertTrue(driver.element().isElementClickable(new Create.CreateDEalCode(driver).Btn_sendForApproval));
        }
        finally {
            driver.browser().captureScreenshot();
        }
        new Create.CreateDEalCode(driver).EditeDealCode(testData.getTestData("editDEalCoe")).approveButton();
        Assert.assertEquals(driver.element().getText(By.xpath("//div[@aria-label='Deal Code Updated Successfully']")),"Deal Code Updated Successfully");
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit (){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
