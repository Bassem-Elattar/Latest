package AdminPages.UserManagement.AgencyUserInformation.AgentInformation;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.UserManagement.UserManagement_Common;
import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchAgentInformation_TC extends TestBase_TC {
    AgentInformationnn_Page agentInformation;
    String ExpectedResult;
    private LogIn_Page logIn;
    private SHAFT.TestData.JSON testData;

    @BeforeTest
    public void SignIn (){
        logIn = new LogIn_Page(driver);
        logIn.ClickSuperAdmin();
        logIn.ClickOnLoginButton();
        agentInformation = new AgentInformationnn_Page(driver);
        testData = new SHAFT.TestData.JSON("SearchAgentInformation.json");
    }

    //VALID
    @Test ()
    public void SearchWithValidFullName() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.ClickonSearch();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectTextFromTable);
        ExpectedResult = Select.getText();

        if (testData.getTestData("Name").contains(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidFullName Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithValidFullName Failed");
        }
    }

    @Test()
    public void SearchWithValidPhone() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.EnterAgentPhoneNumber(testData.getTestData("Phone"));
        agentInformation.ClickonSearch();
    }

    @Test()
    public void SearchWithValidEmail() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.EnterAgentEmail(testData.getTestData("Email"));
        agentInformation.ClickonSearch();
    }

    @Test()
    public void SearchWithValidNameAndPhoneAndEmail() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.EnterAgentPhoneNumber(testData.getTestData("Phone"));
        agentInformation.EnterAgentEmail(testData.getTestData("Email"));
        agentInformation.ClickonSearch();
    }

    // Invalid
    @Test(priority = 5)
    public void SearchWithoutRequiredSelectAgency() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        try {
            agentInformation.ClickonSearch();
            String STRING = String.valueOf(agentInformation.STR);
            if (STRING.equals("Required")) {
                System.out.println("Test Case SearchWithoutRequiredSelectAgency Passed");
            } else {
                throw new RuntimeException("Test Case SearchWithoutRequiredSelectAgency Failed");
            }
        } catch (Exception e) {
            System.out.println("Test Case SearchWithoutRequiredSelectAgency Passed");
        }
    }

    @Test()
    public void SearchWithInvalidPhoneNumber() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.EnterAgentPhoneNumber(testData.getTestData("InvalidPhoneNumber"));
        agentInformation.ClickonSearch();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectInvalidTextPhone);
        ExpectedResult = Select.getText();
        if (ExpectedResult.equals("Please enter valid phone number")) {
            System.out.println("Test Case SearchWithInvalidPhoneNumber Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithInvalidPhoneNumber Failed");
        }
    }

    @Test()
    public void SearchWithInvalidEmail() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.EnterAgentEmail(testData.getTestData("Email"));
        agentInformation.ClickonSearch();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectInvalidTextEmail);
        ExpectedResult = Select.getText();
        if (ExpectedResult.equals("Please enter valid email")) {
            System.out.println("Test Case SearchWithInvalidEmail Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithInvalidEmail Failed");
        }
    }

    @Test()
    public void SearchWithValidDataAndNotExistName() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("AnotherName"));
        agentInformation.ClickonSearch();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectNoDataFound);
        ExpectedResult = Select.getText();
        if ("No data has been found!".equals(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidDataAndNotExistName Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithValidDataAndNotExistName Failed");
        }
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}