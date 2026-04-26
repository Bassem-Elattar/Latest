package AdminPages.Master.Supplier.CredentialField;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class SearchCredentialField_Page {
    public SearchCredentialField_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By Btn_Credintial = By.xpath("//a[@href=\"/master/supplier/credentials\"]");
//    By Btn_Credintial = By.xpath("//li[@req-url=\"/odeysysadmin/Master/searchSupplierCredentialField\"]");
    By Btn_SupplierCredintialFieldName = By.xpath("//input[@id=\"id-SupplierCredentialFieldName\"]");
   // By Product = By.xpath("//p-dropdown[.//input[@id=\"id-Product\"]]");
    By Lst_Supplier = By.xpath("//p-dropdown[.//input[@id=\"id-Supplier\"]]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_SearchGrid = By.xpath("//button[@type=\"submit\"]");

    public void setCredintial(){
        driver.element().click(Btn_Credintial);
    }

    public void setSupplierCredentialFieldName(String supplierCredintialFieldName,String supplier){
        driver.element().type(Btn_SupplierCredintialFieldName,supplierCredintialFieldName);
        driver.element().click(Lst_Supplier);
        By option1 = By.xpath(String.format("//span[text()='%s']", supplier));
        driver.element().click(option1);
    }
    public void setActive(){
        driver.element().click(Rbtn_Active);
    }

    public void setInactive(){
        driver.element().click(Rbtn_Inactive);
    }

    public void setBoth(){
        driver.element().click(Rbtn_Both);
    }

    public void setSearchGrid()
    {
        driver.element().click(Btn_SearchGrid);
    }

    public void performAssertions() {
        String expectedHeaderStatus = "Supplier";

        String[] allowedStatusValues = {"MIK", "Amadeus","GDI","JazeeraAirwaysEG","AirArabiaEgypt","DOT","NDCsbr"};


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
}
