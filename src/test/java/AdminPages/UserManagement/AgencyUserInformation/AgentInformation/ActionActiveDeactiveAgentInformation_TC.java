package AdminPages.UserManagement.AgencyUserInformation.AgentInformation;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.UserManagement.UserManagement_Common;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class ActionActiveDeactiveAgentInformation_TC extends TestBase_TC {
    AgentInformationnn_Page agentInformation;
    private LogIn_Page logIn;
    private SHAFT.TestData.JSON testData;

   @BeforeTest
    public void SignIn (){
       logIn = new LogIn_Page(driver);
       logIn.ClickAdmin();
       logIn.ClickOnLoginButton();
       agentInformation = new AgentInformationnn_Page(driver);
       testData = new SHAFT.TestData.JSON("ActionAgentInformation.json");
   }

    @Test ()
      public void VerifyClickingOnActiveButtonForAgentOrAdmin() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        agentInformation.ClickOnActiveButtonAdmin();
    }

    @Test ()
    public void VerifyClickingOnInActiveButtonForAgentOrAdminToActive() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        agentInformation.ClickOnActiveButtonAdmin2();
    }

    @Test ()
    public void VerifyClickingOnActiveButtonForAgencyOwner() throws InterruptedException {
        agentInformation = new AgentInformationnn_Page(driver);
        new UserManagement_Common(driver).UserManagement();
        agentInformation.SelectAgency(testData.getTestData("AgencyName"));
        agentInformation.ClickonSearch();
        agentInformation.ClickOnActiveButtonAO();
    }

    @Test ()
    public void VerifyThatAgencyOwnerCanLoginPortal() {
        agentInformation.EnterNDCPortal();
        String Select = driver.getDriver().getCurrentUrl();
        if (Select.equals(agentInformation.urlfordashboardportal)) {
        System.out.println("Test Case VerifyThatAgencyOwnerCanLoginPortal Passed");
        }
        else {
        throw new RuntimeException("Test Case VerifyThatAgencyOwnerCanLoginPortal Failed");
        }
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
