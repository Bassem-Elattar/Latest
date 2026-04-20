package AdminPages.UserManagement.AgencyUserInformation.AgentInformation;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.UserManagement.UserManagement_Common;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class ViewAgentInformation_TC extends TestBase_TC {
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
        testData = new SHAFT.TestData.JSON("ViewAgentInformation.json");
    }

    @Test ()
    public void ValidView() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Viewbutton);
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectTextFrompopup);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").contains(ExpectedResult)) {
            System.out.println("Test Case ValidView Passed");

        } else {
            throw new RuntimeException("Test Case ValidView Failed");
        }
    }

    @Test ()
    public void ValidCloseView() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Viewbutton);
        agentInformation.ClickonClose();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectTextFromTable);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").contains(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidFullName Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithValidFullName Failed");
        }
    }

    @Test ()
    public void UnlockClickableAndClickYes() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Viewbutton);
        agentInformation.ClickonViewAndYes();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectTextFromTable);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").contains(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidFullName Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithValidFullName Failed");
        }
    }

    @Test ()
    public void UnlockClickableAndClickNoClickable() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Viewbutton);
        agentInformation.ClickonViewAndNo();
        WebElement Select = driver.getDriver().findElement(agentInformation.SelectTextFromTable);
        ExpectedResult = Select.getText();
        if (testData.getTestData("Name").contains(ExpectedResult)) {
            System.out.println("Test Case SearchWithValidFullName Passed");

        } else {
            throw new RuntimeException("Test Case SearchWithValidFullName Failed");
        }
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}