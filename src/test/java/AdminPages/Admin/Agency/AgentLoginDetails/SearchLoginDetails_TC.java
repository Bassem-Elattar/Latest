package AdminPages.Admin.Agency.AgentLoginDetails;

import AdminPages.Admin.AdminMenu;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SearchLoginDetails_TC extends TestBase_TC {

    SearchLoginDetails_Page Search;
    private LogIn_Page logIn;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign() {
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        new AdminMenu(driver).openSubAdmin().SerachLoginDetails();

    }

    @Test(dataProvider = "JsonProvider")
    public void SearchLoginDetails (Map<String,String> search) throws InterruptedException {
        Search = new SearchLoginDetails_Page(driver);
        String agn = search.get("agn");
        String FromDate = search.get("FromDate");
        String ToDate = search.get("ToDate");
        Search.ValidData(agn);
        Thread.sleep(3000);
        assertEquals("Test Egypt",Search.Table(0,"Test Egypt"));
    }


    @Test(priority = 2, dataProvider = "JsonProvider") //
    public void SearchLoginDetailsInvalid(Map<String,String> search) throws InterruptedException {

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

        Search = new SearchLoginDetails_Page(driver);
        String agn = search.get("agn");
        String FromDate = search.get("FromDate");
        Search.InvalidEndDate(agn,FromDate);// Select date range
        String Actual=driver.element().getText(Search.EndDateError);
        String Expected="start date must be greater than end date";
        Assert.assertEquals(Actual,Expected);

    }
    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }

 }


