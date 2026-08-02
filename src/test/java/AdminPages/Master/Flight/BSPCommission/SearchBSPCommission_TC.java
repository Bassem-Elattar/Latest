package AdminPages.Master.Flight.BSPCommission;

import AdminPages.Login.LogIn_Page;
import AdminPages.Master.Flight.BSPCommission_Page;
import AdminPages.Master.Master_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.*;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

public class SearchBSPCommission_TC {
    private BSPCommission_Page createBSPCommission;
    private LogIn_Page logIn;
    private AdminPages.Helper.PaginationHelper paginationHelper;
    public SHAFT.GUI.WebDriver driver;
    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeClass
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).superAdminLogin();
    }

    @Test(priority  = 1)
    public void setSearchBSPCommission() throws InterruptedException {
        createBSPCommission = new BSPCommission_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickBSP();

        createBSPCommission.setAirlineName("Egyptair");
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


    @Test(priority  = 2)
    public void setGDSSearchBSPCommission() throws InterruptedException {
        createBSPCommission = new BSPCommission_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
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

    @Test(priority  = 3)
    public void setGDSsSearchBSPCommission() throws InterruptedException {
        createBSPCommission = new BSPCommission_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
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
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
