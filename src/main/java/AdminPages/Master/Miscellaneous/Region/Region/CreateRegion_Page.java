package AdminPages.Master.Miscellaneous.Region.Region;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CreateRegion_Page {
    public CreateRegion_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_AddRegion = By.xpath("//button[@routerlink=\"add\"]");
    By Txt_RegionName = By.xpath("//input[@id=\"id-Region\"]");
    By Txt_RegionCode = By.xpath("//input[@id=\"id-Regioncode\"]");
    By Lst_CountryName = By.xpath("//input[@id=\"id-Countryname\"]");
    By Btn_SendforAprroval = By.xpath("//button[@type=\"submit\"]");



    public void setRegionName(String regionName , String regionCode , String countryName, String countryName1) throws InterruptedException {
        driver.element().click(Btn_AddRegion);
        driver.element().type(Txt_RegionName,regionName);
        driver.element().type(Txt_RegionCode,regionCode);
        driver.element().type(Lst_CountryName,countryName);
        driver.element().select(Lst_CountryName,countryName);
        driver.element().type(Lst_CountryName,countryName1);
        driver.element().select(Lst_CountryName,countryName1);
        driver.element().click(Btn_SendforAprroval);
    }

    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }

    public void verifyURLorErrorMessages() {
        String expectedURL = "http://192.168.1.70/master/miscellaneous/region/region";
        String expectedError2 = "unique validation error";
        String expectedError = "Required";
        String actualURL = driver.browser().getCurrentURL();

        // First check: Verify the URL
        if (actualURL.equals(expectedURL)) {
            System.out.println("URL verification passed.");
            driver.verifyThat().browser().url().isEqualTo(expectedURL).perform();
            Assert.assertEquals(actualURL, expectedURL, "URL does not match the expected value.");
        } else {
            System.out.println("URL verification failed. Attempting to check for an error message...");

            // Initialize variables to hold error messages
            String errorText1 = "";
            String errorText2 = "";

            // Try to find the first error message
            try {
                errorText1 = driver.getDriver().findElement(By.xpath("//span[contains(text(),' required validation error ')]")).getText();
            } catch (Exception e) {
                System.out.println("First error message element not found.");
            }

            // Try to find the second error message
            try {
                errorText2 = driver.getDriver().findElement(By.xpath("//span[contains(text(),'unique validation error')]")).getText();
            } catch (Exception e) {
                System.out.println("Second error message element not found.");
            }

            // Check if the first error text matches the expected error
            if (!errorText1.isEmpty() && errorText1.equals(expectedError)) {
                System.out.println("First error message verification passed.");
                Assert.assertEquals(errorText1, expectedError);
            }
            // Check if the second error text matches the expected error
            else if (!errorText2.isEmpty() && errorText2.equals(expectedError2)) {
                System.out.println("Second error message verification passed.");
                Assert.assertEquals(errorText2, expectedError2);
            }
            // If neither error message matches
            else {
                System.out.println("Neither the URL nor any error messages matched the expected values.");
                Assert.fail("Test failed: Neither the URL nor any error messages matched the expected values.");
            }
        }
    }


}
