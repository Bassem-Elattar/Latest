package AdminPages.Reports.ImportPNRRepo;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;


public class Import_TC {
    ImportPNRReport_Page Importobj;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeClass
    public void sign() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();
    }

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void ImportPNRWithValidData(Map<String,String> search) throws InterruptedException {
        new Reports_Common(driver).clickReports().clickImportPNR();
        Importobj = new ImportPNRReport_Page(driver);
        String Branch = search.get("Branch");
        String FromDay = search.get("FromDate");
        String FromMonth = search.get("FromMonth");
        String FromYear = search.get("FromYear");
        String ToDay = search.get("ToDate");
        String ToMonth = search.get("ToMonth");
        String ToYear = search.get("ToYear");
        Importobj.SearchValidBranch(Branch);
        Importobj.searchValidFromDate(FromDay, FromYear, FromMonth);
        Importobj.searchValidToDate(ToDay, ToYear, ToMonth);
        Importobj.Submit();
    }

    @Test(priority = 2, dataProvider = "JsonProvider") //
    public void ImportPNRInvalidToDate(Map<String,String> search) throws InterruptedException {
        new Reports_Common(driver).clickReports().clickImportPNR();
        Importobj = new ImportPNRReport_Page(driver);
        String Branch = search.get("Branch");
        String FromDay = search.get("FromDate");
        String FromMonth = search.get("FromMonth");
        String FromYear = search.get("FromYear");
        String Expected = search.get("ExpectedError");
        Importobj.SearchValidBranch(Branch);
        Importobj.searchValidFromDate(FromDay, FromYear, FromMonth);
        Importobj.Submit();
        String Actual=driver.element().getText(Importobj.Txt_DateError);
        Assert.assertEquals(Actual,Expected);
    }

    @Test(priority = 3, dataProvider = "JsonProvider") //
    public void ImportPNRInvalidFromDate(Map<String,String> search) throws InterruptedException {
        new Reports_Common(driver).clickReports().clickImportPNR();
        Importobj = new ImportPNRReport_Page(driver);
        String Branch = search.get("Branch");
        String Agency = search.get("AGN");
        String ToDay = search.get("ToDate");
        String ToMonth = search.get("ToMonth");
        String ToYear = search.get("ToYear");
        String Expected = search.get("ExpectedError");
        Importobj.SearchValidBranch(Branch);
        Importobj.SearchValidAgency(Agency);
        Importobj.searchValidToDate(ToDay, ToYear, ToMonth);
        Importobj.Submit();
        String Actual=driver.element().getText(Importobj.Txt_DateError);
        Assert.assertEquals(Actual,Expected);
    }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}

