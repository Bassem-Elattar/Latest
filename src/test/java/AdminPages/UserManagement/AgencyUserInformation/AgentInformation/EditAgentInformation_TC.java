package AdminPages.UserManagement.AgencyUserInformation.AgentInformation;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.UserManagement.UserManagement_Common;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class EditAgentInformation_TC extends TestBase_TC {
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
        testData = new SHAFT.TestData.JSON("EditAgentInformation.json");
    }

    @Test ()
      public void EditButtonExistForSuperAdmin() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        Validations.verifyThat().element(driver.getDriver(), agentInformation.Btn_Editbutton).exists();
    }

    @Test ()
    public void FullNameCannotBeChanged() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        Validations.verifyThat().element(driver.getDriver(), agentInformation.Txt_EditFullName).isVisible();
        Validations.assertThat().element(driver.getDriver(), agentInformation.EditFullName).isDisabled();
    }
    //Invalid cases
    @Test ()
    public void EmailIsClickableAndChangeableInvalid() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Btn_Editbutton);
        agentInformation.EnterEmail(testData.getTestData("InvalidMail"));
        driver.element().click(agentInformation.Btn_UpdateButton);
        WebElement Select = driver.getDriver().findElement(agentInformation.InvalidEmail);
        ExpectedResult = Select.getText();
        String InvalidMessageEmail = "Please enter a valid email";
        if (InvalidMessageEmail.equals(ExpectedResult)) {
            System.out.println("Test Case EmailIsClickableAndChangeableInvalid Passed");
        }
        else
        {
            throw new RuntimeException("Test Case EmailIsClickableAndChangeableInvalid Failed");
        }
    }

    @Test ()
    public void PhoneIsClickableAndChangeableInvalid() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Btn_Editbutton);
        agentInformation.EnterPhoneNumber(testData.getTestData("phoneExample"));
        driver.element().click(agentInformation.Btn_UpdateButton);
        WebElement Select = driver.getDriver().findElement(agentInformation.InvalidPhone);
        ExpectedResult = Select.getText();
        String InvalidMessagePhone = "Please enter valid phone number";
        if (InvalidMessagePhone.equals(ExpectedResult)) {
            System.out.println("Test Case PhoneIsClickableAndChangeableInvalid Passed");
        }
        else
        {
            throw new RuntimeException("Test Case PhoneIsClickableAndChangeableInvalid Failed");
        }
    }

    @Test ()
    public void KeyPhoneIsClickableAndChangeableInvalid() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Btn_Editbutton);
        driver.element().click(agentInformation.Btn_SearchKey);
        //driver.element().click(agentInformation.SelectTextFromKey);
        agentInformation.EnterPhoneNumber(testData.getTestData("phoneExample"));
        driver.element().click(agentInformation.Btn_UpdateButton);
        WebElement Select = driver.getDriver().findElement(agentInformation.InvalidPhone);
        ExpectedResult = Select.getText();
        String InvalidMessagePhone = "Please enter valid phone number";
        if (InvalidMessagePhone.equals(ExpectedResult)) {
            System.out.println("Test Case PhoneIsClickableAndChangeableInvalid Passed");
        }
        else
        {
            throw new RuntimeException("Test Case PhoneIsClickableAndChangeableInvalid Failed");
        }
    }

    @Test ()
    public void CancelButtonIsClickable() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Btn_Editbutton);
        agentInformation.ClickOnCancel();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.FullName(testData.getTestData("Name"));
        agentInformation.ClickonSearch();
    }

    //Reflection
    @Test ()
    public void ReflectionAfterSuccessfullyUpdate() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        driver.element().click(agentInformation.Btn_Editbutton);
        agentInformation.EnterPhoneNumber(testData.getTestData("ValidPhone"));
        driver.element().click(agentInformation.Btn_UpdateButton);
        WebElement Select = driver.getDriver().findElement(agentInformation.SuccessfullAlert);
        ExpectedResult = Select.getText();
        String Alert = "Updated Successfully";
        if (Alert.equals(ExpectedResult)) {
            agentInformation.EnterNDCPortal();
            driver.element().click(agentInformation.Notification);
            driver.element().click(agentInformation.ProfileManagement);
        }
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
