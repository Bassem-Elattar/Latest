package PortalPages.Settings;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class ProfileManagementtest {
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private ProfileManagementtest profileManagementt;
    private Login_Page loginPage;


  /*  @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();


    }*/
  @BeforeClass
  public void setup() {
      CommonMethod.setupDriver(DataUtils.get("browser"));
      driver = CommonMethod.getDriver();

      driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
      new Login_Page(driver).PortalLogin();
  }


    @Test
    public void Tc1_ADD_NEW_AGENT_admin() throws InterruptedException {
        new ProfileManagemet(driver).click_Settings()
                .click_Profilemanagement()
                .click_add_new_agent()
                .type_full_name()
                .SELECT_POSITION()
                .SELECT_POSITION_ADMIN()
                .TYPE_CONTACT_NO()
                .TYPE_EMAIL("automation" + System.currentTimeMillis() + "@gmail.com")
                .TYPE_Date_of_Joining()
                .TYPE_Address()

                .UploadPDF(System.getProperty("user.dir") + "/src/test/resources/sendGridUsage.pdf")
                .CLICK_SAVE();


    }

    @Test
    public void Tc2_ADD_NEW_AGENT_AGENT() throws InterruptedException {
        new ProfileManagemet(driver).click_Settings()
                .click_Profilemanagement()
                .click_add_new_agent()
                .type_full_name()
                .SELECT_POSITION()
                .SELECT_POSITION_AGENT()
                .TYPE_CONTACT_NO()
                .TYPE_EMAIL("automation" + System.currentTimeMillis() + "@gmail.com")
                .TYPE_Date_of_Joining()
                .TYPE_Address()

                .UploadPDF(System.getProperty("user.dir") + "/src/test/resources/sendGridUsage.pdf")
                .CLICK_SAVE();


    }

    @Test
    public void Tc3_Sesrch() throws InterruptedException {
        new ProfileManagemet(driver).click_Settings()
                .click_Profilemanagement()
                .type_full_name_search()
                .type_Contact_No_search()
                .CLICK_Search()
                .Verify_AgentName_View()
                .Verify_ContactNo_View();

        //Thread.sleep(30000);
       // driver.quit();

    }
    @Test
    public void Tc4_edit_active() throws InterruptedException {
        new ProfileManagemet(driver).click_Settings()
                .click_Profilemanagement()
                .type_full_name_search()
                .type_Contact_No_search()
                .CLICK_Search()
                .CLICK_Action_edit()
                .CLICK_SAVE()

                .CLICK_Action_active();

                //.Verify_AgentName_View()
             //   .Verify_ContactNo_View();

        //Thread.sleep(30000);
     //   driver.quit();

    }


    @Test
    public void Tc5_end_to_end() throws InterruptedException {
        new ProfileManagemet(driver).click_Settings()
                .click_Profilemanagement()
                .click_add_new_agent()
                .type_full_name()
                .SELECT_POSITION()
                .SELECT_POSITION_AGENT()
                .TYPE_CONTACT_NO()
                .TYPE_EMAIL("automation" + System.currentTimeMillis() + "@gmail.com")
                .TYPE_Date_of_Joining()
                .TYPE_Address()
                //.UploadPDF("C:\\Users\\MediaTech\\NDC-Uplift-Automation\\src\\test\\resources\\tanga.pdf")
                .UploadPDF(System.getProperty("user.dir") + "/src/test/resources/sendGridUsage.pdf")
                .CLICK_SAVE();
        Thread.sleep(3000);
        //.type_full_name()
        new ProfileManagemet(driver).type_full_name_search()
                .type_Contact_No_search()
                .type_Contact_No_search()

                .CLICK_Search()
                .Verify_AgentName_View()
                .Verify_ContactNo_View()
                .CLICK_Action_edit()
                .CLICK_SAVE()
                .CLICK_Action_active();

        Thread.sleep(3000);
        driver.quit();

    }


}