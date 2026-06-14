package AdminPages.Master.Miscellaneous.Region.Country;
import AdminPages.Helper.PaginationHelper;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchCountry_TC extends TestBase_TC {
    private SearchCity_Page regionSearchCity;
    private SearchCountry_Page searchCountry;
    private LogIn_Page logIn;
    private PaginationHelper paginationHelper;

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
    public void SearchCountry(Map<String, String> country) throws InterruptedException {
        regionSearchCity = new SearchCity_Page(driver);
        searchCountry = new SearchCountry_Page(driver);
        paginationHelper = new PaginationHelper(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        searchCountry.setCountry();
        String CountryName = country.get("CountryName");
        String CountryCode = country.get("CountryCode");
        searchCountry.setSearchcountry(CountryName, CountryCode);
        searchCountry.setBoth();
        searchCountry.setSearchGrid();

        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            searchCountry.performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }
    }
}








