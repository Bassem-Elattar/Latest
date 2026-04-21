package AdminPages.Master.Miscellaneous.Region.State;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Miscellaneous.Region.Region.SearchRegion_Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class ActionState_TC extends TestBase_TC {
    private ActionState_Page actionState;
    private SearchCity_Page regionSearchCity;
    private SearchRegion_Page searchRegion;
    private SearchState_Page searchState;
    private CreateState_Page createState;
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

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void CreateState(Map<String, String> state){
        regionSearchCity = new SearchCity_Page(driver);
        searchRegion = new SearchRegion_Page(driver);
        searchState = new SearchState_Page(driver);
        createState = new CreateState_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        searchState.setStatebtn();
        String StateName = state.get("StateName");
        String StateCode = state.get("StateCode");
        String CountryName = state.get("CountryName");
        createState.setAddState(StateName,StateCode,CountryName);
        createState.verifyURLorErrorMessages();
    }

    @Test(priority = 2, dataProvider = "JsonProvider")
    public void SearchState(Map<String, String> state) throws InterruptedException {
        regionSearchCity = new SearchCity_Page(driver);
        searchRegion = new SearchRegion_Page(driver);
        searchState = new SearchState_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        searchState.setStatebtn();
        String StateName = state.get("StateName");
        String StateCode = state.get("StateCode");
        String CountryName = state.get("CountryName");
        searchState.setState(StateName,StateCode,CountryName);
        searchState.setBoth();
        searchState.setSearch();

        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            searchState.performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }
    }

//    @Test(priority = 3, dataProvider = "JsonProvider")
//    public void SearchStateAction(Map<String, String> state) throws InterruptedException {
//        regionSearchCity = new SearchCity_Page(driver);
//        searchRegion = new SearchRegion_Page(driver);
//        searchState = new SearchState_Page(driver);
//        actionState = new ActionState_Page(driver);
//        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
//        regionSearchCity.ClickonMaster();
//        regionSearchCity.ClickonMiscellanous();
//        regionSearchCity.ClickonRegion();
//        searchState.setStatebtn();
//        String StateName = state.get("StateName");
//        String StateCode = state.get("StateCode");
//        String CountryName = state.get("CountryName");
//        String CountryName1 = state.get("CountryName1");
//        String Remark = state.get("Remark");
//        searchState.setState(StateName,StateCode,CountryName);
//        searchState.setBoth();
//        searchState.setSearch();
//        actionState.setAddState(Remark,CountryName1);
//    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

