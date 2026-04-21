package AdminPages.Master.Miscellaneous.Region.State;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CreateState_Page {
    public CreateState_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_AddState = By.xpath("//button[@routerlink=\"add\"]");
    By Txt_StateName = By.xpath("//input[@id=\"id-Statename\"]");
    By Txt_StateCode = By.xpath("//input[@id=\"id-StateCode\"]");
    By Lst_CountryName = By.xpath("//p-dropdown[.//input[@id=\"id-CountryName\"]]");
    By Btn_SendForApproval = By.xpath("//button[@type=\"submit\"]");

    public void setAddState(String statename, String statecode , String countryname){
        driver.element().click(Btn_AddState);
        driver.element().type(Txt_StateName,statename);
        driver.element().type(Txt_StateCode,statecode);
        driver.element().click(Lst_CountryName);
        By option = By.xpath(String.format("//span[text()='%s']", countryname));
        driver.element().click(option);
        driver.element().click(Btn_SendForApproval);
    }

    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }

    public void verifyURLorErrorMessages() {
        String expectedURL = "http://192.168.1.70/master/miscellaneous/region/state";
        String expectedError2 = "unique validation error";
        String expectedError = "required validation error";
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
