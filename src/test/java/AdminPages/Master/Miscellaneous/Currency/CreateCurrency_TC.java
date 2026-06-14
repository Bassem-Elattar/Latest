package AdminPages.Master.Miscellaneous.Currency;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateCurrency_TC extends TestBase_TC {
    private LogIn_Page logIn;
    private SearchCity_Page regionSearchCity;
    private SearchCurrency_Page searchCurrency;
    private CreateCurrency_Page createCurrency;
    private AdminPages.Helper.PaginationHelper paginationHelper;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
    }

    @Test(dataProvider = "JsonProvider")
    public void CreateCurrency(Map<String, String> create) throws InterruptedException {
        regionSearchCity = new SearchCity_Page(driver);
        searchCurrency = new SearchCurrency_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        createCurrency = new CreateCurrency_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickCurrency();
        String CurrencyName = create.get("CurrencyName");
        String CurrencyCode = create.get("CurrencyCode");
        String DecimalCount = create.get("DecimalCount");
        String Country = create.get("Country");
        createCurrency.CreateCurrencyy(CurrencyName,CurrencyCode,DecimalCount,Country);
        createCurrency.setSendforApproval();
        String Expected = "Added Successfully";
        Assert.assertEquals(createCurrency.Actual(),Expected);
    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
