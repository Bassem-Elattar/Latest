package PortalPages.Settings;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.github.javafaker.Faker;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.DataUtils;

import java.util.Random;

public class FinancialTest {
    private SHAFT.TestData.JSON testData;
    private final Random random = new Random();

    private SHAFT.GUI.WebDriver driver;
    private static String agentName;
    private static String agentEmail;
    private final Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
        testData = new SHAFT.TestData.JSON("financial.json");
    }


    @Test(priority = 1, description = "Create new agent using Faker data")
    public void Tc1_Create_Agent() throws InterruptedException {
        agentName = faker.name().firstName() + " " + faker.name().lastName();
        agentEmail = "automation" + System.currentTimeMillis() + "@gmail.com";

        new ProfileManagemet(driver)
                .click_Settings()
                .click_Profilemanagement()
                .click_add_new_agent()
                .type_full_name_dynamic(agentName)
                .SELECT_POSITION()
                .SELECT_POSITION_AGENT()
                .TYPE_CONTACT_NO()
                .TYPE_EMAIL(agentEmail)
                .TYPE_Date_of_Joining()
                .TYPE_Address()
                .UploadPDF(System.getProperty("user.dir") + "/src/test/resources/sendGridUsage.pdf")
                .CLICK_SAVE();

        Thread.sleep(3000);
    }

    @Test(priority = 2, description = "Navigate to Financial and verify airline exists in search results")
    public void Tc2_Verify_Airline_In_Financial() throws InterruptedException {
        new Financial_Page(driver)
                .click_Settings()
                .click_Financial()
                .select_AgentName(testData.getTestData("agentName"))
                .click_ForEachAirline()
                .type_SearchByName(testData.getTestData("airlineName"))
                .click_Search()
                .verify_AirlineExistsInResults(testData.getTestData("airlineName"))
                ;
    }
    @Test(priority = 3, description = "Edit markup value and verify it updated in search results")
    public void Tc3_Edit_Markup_Value() throws InterruptedException {
        String markupValue = String.valueOf(random.nextInt(91) + 10);
        new Financial_Page(driver)
                .click_Settings()
                .click_Financial()
                .select_AgentName(testData.getTestData("agentName"))
                .click_ForEachAirline()
                .type_SearchByName(testData.getTestData("airlineName"))
                .click_Search()
                .click_Edit()
                .clear_And_Type_Value(markupValue)
                .click_SubmitForm()
                .verify_MarkupUpdatedToast()
                .click_ForEachAirline()
                .type_SearchByName(testData.getTestData("airlineName"))
                .click_Search()
                .verify_MarkupValueUpdated(markupValue);
    }
}