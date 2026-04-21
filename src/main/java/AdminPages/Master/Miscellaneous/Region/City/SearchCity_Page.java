package AdminPages.Master.Miscellaneous.Region.City;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class SearchCity_Page {
    public static final int MILLIS1 = 1000;
    public static final int MILLIS = 1000;
    public SearchCity_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_Admin = By.xpath("//a[@href=\"/master\"]");
    By Btn_Miscellaneous = By.linkText("Miscellaneous");

//    By Region = By.xpath("//a[@href=\"/master/miscellaneous/region/region\"]");
    By Btn_Region = By.xpath("//a[@href=\"/master/miscellaneous/region/region\"]");
    By Btn_City = By.xpath("//button[@routerlink=\"city\"]");
    By Txt_StateName = By.xpath("//input[@id=\"id-Statename\"]");
    By Txt_StateCode = By.xpath("//input[@id=\"id-StateCode\"]");
    By Txt_CountryName = By.xpath("//input[@id=\"id-Countryname\"]");
    By Txt_CityName = By.xpath("//input[@id=\"id-Cityname\"]");
    By Txt_CityCode = By.xpath("//input[@id=\"id-CityCode\"]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_SearchBtn = By.xpath("//button[@type=\"submit\"]");
    By Btn_setinActive = By.xpath("(//i[@class='pi pi-circle-fill'])[1]");
    By Btn_setactive = By.xpath("(//i[@class='pi pi-circle'])[1]");

    public void ClickonMaster(){
        driver.element().click(Btn_Admin);
    }
    public void ClickonMiscellanous(){
        driver.element().click(Btn_Miscellaneous);
    }
    public void ClickonRegion(){
        driver.element().click(Btn_Region);
    }
    public void ClickonCity() throws InterruptedException {
        driver.element().click(Btn_City);
        Thread.sleep(MILLIS1);
    }
    public void SearchRegionCity(String statename , String statecode,String countryname ,String cityname , String citycode) throws InterruptedException {

        driver.element().type(Txt_StateName,statename);
        Thread.sleep(MILLIS1);
        driver.element().type(Txt_StateCode,statecode);
        Thread.sleep(MILLIS1);
        driver.element().type(Txt_CountryName,countryname);
        Thread.sleep(MILLIS1);
        driver.element().type(Txt_CountryName,countryname).select(Txt_CountryName,countryname);
        driver.element().type(Txt_CityName,cityname);
        Thread.sleep(MILLIS1);
        driver.element().type(Txt_CityCode,citycode);
    }

    public void setInActiveBtn() throws InterruptedException {
        driver.element().click(Rbtn_Inactive);
        Thread.sleep(MILLIS1);
        driver.element().click(Btn_SearchBtn);
    }
    public void setActiveBtn() throws InterruptedException {
        driver.element().click(Rbtn_Active);
        Thread.sleep(MILLIS1);
        driver.element().click(Btn_SearchBtn);
    }
    public void setBoth(){
        driver.element().click(Rbtn_Both);
        driver.element().click(Btn_SearchBtn);
    }

    public void setClickactive(){
        driver.element().click(Btn_setinActive);

    }
    public void setClickinactive(){
        driver.element().click(Btn_setactive);

    }

    public void performAssertions() {
        String expectedHeaderApproval = "Approval Status";
        String[] allowedApprovalValues = {"Pending for approval", "Approved", "Rejected"};

        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[4]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderApproval, "The 'Approval Status' table header does not match the expected value.");



            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[4]"));


                Assert.assertTrue(isValueInArray(actualDataStatus, allowedApprovalValues),
                        "Row " + i + ": The 'Approval Status' column data (" + actualDataStatus + ") does not match any of the allowed values.");


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
