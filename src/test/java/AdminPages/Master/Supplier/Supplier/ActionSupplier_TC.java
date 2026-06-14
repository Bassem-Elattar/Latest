package AdminPages.Master.Supplier.Supplier;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class ActionSupplier_TC extends TestBase_TC {
    private SearchCity_Page searchCity;
    private SearchSupplier_Page searchSupplier;
    private ActionSupplier_Page actionSupplier;
    private LogIn_Page logIn;


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
    public void ActionSupplier(Map<String, String> supplier){
        searchSupplier = new SearchSupplier_Page(driver);
        searchCity = new SearchCity_Page(driver);
        actionSupplier = new ActionSupplier_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplier();
        String SupplierName = supplier.get("SupplierName");
        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        actionSupplier.setApprove("yes");

    }

    @Test(priority = 2, dataProvider = "JsonProvider")
    public void SearchSupplier(Map<String, String> search) throws InterruptedException {
        searchSupplier = new SearchSupplier_Page(driver);
        searchCity = new SearchCity_Page(driver);
//        new Master_Common(driver).clickMaster()
//                .clickSupplierMenue()
//                .clickSupplier();
        String SupplierName = search.get("SupplierName");
        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        String expectedHeader = "Status";
        String expectedData = "Active";

        ///////////////////////Check TableData///////////////////////
        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(3000);

            // Find and verify the table header
            String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
            Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

            // Find and verify the data in the first row under the "Operating Country" header
            String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
            Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");
        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }

    }

    @Test(priority = 3, dataProvider = "JsonProvider")
    public void SearchSupplierReject(Map<String, String> supplier){
        searchSupplier = new SearchSupplier_Page(driver);
        searchCity = new SearchCity_Page(driver);
        actionSupplier = new ActionSupplier_Page(driver);
//        new Master_Common(driver).clickMaster()
//                .clickSupplierMenue()
//                .clickSupplier();
        String SupplierName = supplier.get("SupplierName");
        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        actionSupplier.setReject("No");

    }


    @Test(priority = 4, dataProvider = "JsonProvider")
    public void SearchSupplierRejected(Map<String, String> search) throws InterruptedException {
        searchSupplier = new SearchSupplier_Page(driver);
        searchCity = new SearchCity_Page(driver);
//        new Master_Common(driver).clickMaster()
//                .clickSupplierMenue()
//                .clickSupplier();
        String SupplierName = search.get("SupplierName");
        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        String expectedHeader = "Status";
        String expectedData = "Inactive";

        ///////////////////////Check TableData///////////////////////
        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(3000);

            // Find and verify the table header
            String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
            Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

            // Find and verify the data in the first row under the "Operating Country" header
            String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
            Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");
        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }

    }


}
