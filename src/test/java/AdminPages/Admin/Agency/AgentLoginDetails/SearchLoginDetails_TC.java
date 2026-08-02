package AdminPages.Admin.Agency.AgentLoginDetails;

import AdminPages.Admin.AdminMenu;
import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SearchLoginDetails_TC {

    SearchLoginDetails_Page Search;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;


    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).superAdminLogin();
        new AdminMenu(driver).openSubAdmin().Agency().SerachLoginDetails();
    }

    @Test(dataProvider = "JsonProvider")
    public void SearchLoginDetails (Map<String,String> search) throws InterruptedException {
        Search = new SearchLoginDetails_Page(driver);
        String agn = search.get("agn");
        String FromDate = search.get("FromDate");
        String FromMonth = search.get("FromMonth");
        String FromYear = search.get("FromYear");
        String ToDate = search.get("ToDate");
        String ToMonth = search.get("ToMonth");
        String ToYear = search.get("ToYear");

        Search.ValidData(agn);
        Search.searchValidFromDate(FromDate,FromYear,FromMonth);
        Search.searchValidToDate(ToDate,ToYear,ToMonth);
        Search.SearchValid();
        Thread.sleep(3000);
        assertEquals("Test Egypt",Search.Table(0,"Test Egypt"));
    }


    @Test(priority = 2, dataProvider = "JsonProvider") //
    public void SearchLoginDetailsInvalid(Map<String,String> search) throws InterruptedException {
        new AdminMenu(driver).openSubAdmin().Agency().SerachLoginDetails();

        Search = new SearchLoginDetails_Page(driver);
        String agn = search.get("agn");
        String ToDate = search.get("ToDate");
        Search.InvalidStartDate(agn,ToDate);// Select date range
        String Actual=driver.element().getText(Search.StartDateError);
        String Expected="Required";
        Assert.assertEquals(Actual,Expected);

    }
    @Test(priority = 3, dataProvider = "JsonProvider") //
    public void SearchLoginDetailsEndDate(Map<String,String> search) throws InterruptedException {
        new AdminMenu(driver).openSubAdmin().Agency().SerachLoginDetails();

        Search = new SearchLoginDetails_Page(driver);
        String agn = search.get("agn");
        String FromDate = search.get("FromDate");
        Search.InvalidEndDate(agn,FromDate);// Select date range
        String Actual=driver.element().getText(Search.EndDateError);
        String Expected="Required";
        Assert.assertEquals(Actual,Expected);

    }
    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }

 }


