package AdminPages.Master.Supplier.CredentialField;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Supplier.Supplier.SearchSupplier_Page;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class ActionCredentialField_TC extends TestBase_TC {

    private SearchCredentialField_Page searchCredentialField;
    private LogIn_Page logIn;
    private ActionCredentialField_Page actionCredentialField;
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        searchCredentialField = new SearchCredentialField_Page(driver);
        actionCredentialField = new ActionCredentialField_Page(driver);
        testData = new SHAFT.TestData.JSON("ActionCredentialField.json");
    }

    @Test(priority = 1)
    public void setActionCredentialField() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickCredentialField();
        searchCredentialField.setSupplierCredentialFieldName(testData.getTestData("SupplierCredentialFieldName1"),testData.getTestData("Supplier1"));
        searchCredentialField.setSearchGrid();
        actionCredentialField.setSupplierCredential(testData.getTestData("SupplierCredentialFieldName"),testData.getTestData("Supplier"));
        String Expected = "Updated Successfully";
        String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Updated Successfully\"]"));
        Assert.assertEquals(Actual,Expected);
        }

    @Test(priority = 2)
    public void setSearchCredentialField() throws InterruptedException {
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickCredentialField();
        searchCredentialField.setSupplierCredentialFieldName(testData.getTestData("SupplierCredentialFieldName"),testData.getTestData("Supplier"));
        searchCredentialField.setSearchGrid();

        // Perform assertions
        performAssertions();
    }

    private void performAssertions() {
        String expectedHeaderStatus = "Supplier";

        String[] allowedStatusValues = {"Galileo", "Amadeus","GDI","JazeeraAirwaysEG","AirArabiaEgypt","DOT","NDCsbr"};


        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[2]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Supplier' table header does not match the expected value.");


            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[2]"));

                Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                        "Row " + i + ": The 'Supplier' column data (" + actualDataStatus + ") does not match any of the allowed values.");

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
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
