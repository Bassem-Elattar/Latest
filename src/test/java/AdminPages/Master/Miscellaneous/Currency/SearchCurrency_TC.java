package AdminPages.Master.Miscellaneous.Currency;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchCurrency_TC extends TestBase_TC {
    private LogIn_Page logIn;
    private SearchCity_Page regionSearchCity;
    private SearchCurrency_Page searchCurrency;
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
    public void SearchCurrency(Map<String, String> search) throws InterruptedException {
     regionSearchCity = new SearchCity_Page(driver);
     searchCurrency = new SearchCurrency_Page(driver);
     paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickCurrency();
     String CurrencyName = search.get("CurrencyName");
     String CurrencyCode = search.get("CurrencyCode");
     searchCurrency.SearchCurrency(CurrencyName,CurrencyCode);
     searchCurrency.setBoth();
     searchCurrency.setSearchGrid();

        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            searchCurrency.performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }
    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }

}
