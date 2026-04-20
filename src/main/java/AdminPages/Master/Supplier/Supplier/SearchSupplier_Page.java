package AdminPages.Master.Supplier.Supplier;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class SearchSupplier_Page {
    public static final int MILLIS = 3000;

    public SearchSupplier_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_Supplier = By.linkText("Supplier");
    By Btn_Supplier1 = By.xpath("//a[@href=\"/master/supplier/supplier\"]");
//    By Supplier1 = By.xpath("//li[@req-url=\"/odeysysadmin/Master/searchSupplier\"]");
    By Txt_SupplierName = By.xpath("//input[@id=\"id-SupplierName\"]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_SearchGrid = By.xpath("//span[@class=\"p-button-label\"]");

    public void clickonsupplier(){
        driver.element().click(Btn_Supplier);
    }

    public void searchsupplierdata(String suppliername  ){
       // driver.element().click(Btn_Supplier1);
        driver.element().type(Txt_SupplierName,suppliername);


    }

    public void setInActiveBtn() throws InterruptedException {
        driver.element().click(Rbtn_Inactive);
        driver.element().click(Btn_SearchGrid);
    }
    public void setActiveBtn() throws InterruptedException {
        driver.element().click(Rbtn_Active);
        driver.element().click(Btn_SearchGrid);
    }
    public void setBoth(){
        driver.element().click(Rbtn_Both);
        driver.element().click(Btn_SearchGrid);
    }

    public void performAssertions() {
        String expectedHeaderStatus = "Type";

        String[] allowedStatusValues = {"GDS", "LCC","Both"};


        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[2]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Type' table header does not match the expected value.");


            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[2]"));

                Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                        "Row " + i + ": The 'Type' column data (" + actualDataStatus + ") does not match any of the allowed values.");

            }

        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }
    }

    public boolean isValueInArray(String value, String[] array) {
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
