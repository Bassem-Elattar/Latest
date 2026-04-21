package AdminPages.Master.Flight.BSPCommission;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Flight.BSPCommission_Page;
import AdminPages.Master.Master_Common;
import org.testng.annotations.*;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

public class SearchBSPCommission_TC extends TestBase_TC {
    private BSPCommission_Page createBSPCommission;
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
        createBSPCommission = new BSPCommission_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
    }

    @Test
    public void setSearchBSPCommission() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickBSP();
        createBSPCommission.setAirlineName("Air Burkina");
        createBSPCommission.setBoth();
        createBSPCommission.setSearchButton();
        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            createBSPCommission.performAssertionsAirlineName();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }
        Thread.sleep(5000);
    }


    @Test
    public void setGDSSearchBSPCommission() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickBSP();
        createBSPCommission.setGDSSupplier("Galileo");
        createBSPCommission.setBoth();
        createBSPCommission.setSearchButton();
        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            createBSPCommission.performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }

    }

    @Test
    public void setGDSsSearchBSPCommission() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickBSP();
        createBSPCommission.setGDSSupplier("Amadeus");
        createBSPCommission.setBoth();
        createBSPCommission.setSearchButton();
        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            createBSPCommission.performAssertions();

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
