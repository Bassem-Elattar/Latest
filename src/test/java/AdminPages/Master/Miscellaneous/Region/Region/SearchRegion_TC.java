package AdminPages.Master.Miscellaneous.Region.Region;
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

public class SearchRegion_TC extends TestBase_TC {
    private SearchCity_Page regionSearchCity;
    private SearchRegion_Page searchRegion;
    private LogIn_Page logIn;
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
    public void SearchRegion(Map<String, String> region) throws InterruptedException {
        regionSearchCity = new SearchCity_Page(driver);
        searchRegion = new SearchRegion_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        String RegionName = region.get("RegionName");
        String RegionCode = region.get("RegionCode");

        searchRegion.setRegionName(RegionName,RegionCode);
        searchRegion.setBoth();
        searchRegion.setSearch();
        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            searchRegion.performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }
    }
}
