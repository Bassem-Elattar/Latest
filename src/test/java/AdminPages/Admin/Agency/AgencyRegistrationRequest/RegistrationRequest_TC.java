package AdminPages.Admin.Agency.AgencyRegistrationRequest;

import AdminPages.Admin.AdminMenu;
import AdminPages.Admin.Staff_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class RegistrationRequest_TC extends TestBase_TC {
    private Staff_Page staff;
    private RegistrationRequest_Page registrationRequests;
    private LogIn_Page logIn;
    private List<WebElement> pagesLink;
    private By nextBtn;
    private AdminPages.Helper.PaginationHelper paginationHelper;


    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign() {
        logIn = new LogIn_Page(driver);
        new LogIn_Page(driver).ClickAdmin();
        new LogIn_Page(driver).ClickOnLoginButton();
        registrationRequests = new RegistrationRequest_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        staff = new Staff_Page(driver);

    }

    @Test(dataProvider = "JsonProvider", priority = 1)
    public void RegistrationRequest(Map<String, String> req) throws InterruptedException {
        new AdminMenu(driver).openSubAdmin().Agency().RegistrationRequest();
        String Agencyname = req.get("Agencyname");
        String Contactname = req.get("Contactname");
        String RequestDay = req.get("FromDate");
        String RequestMonth = req.get("FromMonth");
        String RequestYear = req.get("FromYear");
        String Email = req.get("Email");
        registrationRequests.setRegistrationRequest(Agencyname, Contactname, Email);
        registrationRequests.searchValidDate(RequestDay, RequestYear, RequestMonth);
        registrationRequests.setRejected();
        registrationRequests.setSearchgrid();
        Actions actions = new Actions(driver.getDriver());
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        paginationHelper.setPagination("100");
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }

    }


    @Test(dataProvider = "JsonProvider", priority = 2)
    public void RegistrationRequestInprogress(Map<String, String> req) throws InterruptedException {
        new AdminMenu(driver).openSubAdmin().Agency().RegistrationRequest();
        String Agencyname = req.get("Agencyname");
        String Contactname = req.get("Contactname");
        String RequestDay = req.get("FromDate");
        String RequestMonth = req.get("FromMonth");
        String RequestYear = req.get("FromYear");
        String Email = req.get("Email");
        registrationRequests.setRegistrationRequest(Agencyname, Contactname, Email);
        registrationRequests.searchValidDate(RequestDay, RequestYear, RequestMonth);
        registrationRequests.setInProgress();
        registrationRequests.setSearchgrid();
        Actions actions = new Actions(driver.getDriver());
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        paginationHelper.setPagination("100");
        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }

    }


    @Test(dataProvider = "JsonProvider", priority = 3)
    public void RegistrationRequestNew(Map<String, String> req) throws InterruptedException {
        new AdminMenu(driver).openSubAdmin().Agency().RegistrationRequest();
        String Agencyname = req.get("Agencyname");
        String Contactname = req.get("Contactname");
        String RequestDay = req.get("FromDate");
        String RequestMonth = req.get("FromMonth");
        String RequestYear = req.get("FromYear");
        String Email = req.get("Email");
        registrationRequests.setRegistrationRequest(Agencyname, Contactname, Email);
        registrationRequests.searchValidDate(RequestDay, RequestYear, RequestMonth);
        registrationRequests.setNew();
        registrationRequests.setSearchgrid();
        Actions actions = new Actions(driver.getDriver());
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        paginationHelper.setPagination("100");
        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }

    }

    private void performAssertions() {
        String expectedHeaderStatus = "Status";

        String[] allowedApprovalValues = {"New", "Rejected", "In Progress"};


        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[6]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");


            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {

                String actualDataApproval = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[6]"));


                Assert.assertTrue(isValueInArray(actualDataApproval, allowedApprovalValues),
                        "Row " + i + ": The 'Approval Status' column data (" + actualDataApproval + ") does not match any of the allowed values.");

                // Additional validation: If Approval Status is "Rejected", Status cannot be "Active"
                if (actualDataApproval.equals("Rejected")) {
                    Assert.assertNotEquals(actualDataApproval, "Active",
                            "Row " + i + ": The 'Status' cannot be 'Active' if the 'Approval Status' is 'Rejected'.");
                }
            }

        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }
    }

    private boolean isValueInArray(String value, String[] array) {
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @AfterMethod
    public void Reload() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

