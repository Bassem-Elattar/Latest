package AdminPages.UserManagement;

import AdminPages.Settings.AdminSettings.Setting_Common;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class UserManagement_Common {

    SHAFT.GUI.WebDriver driver;

    private final By btn_UserManagement= By.xpath("//span[normalize-space()='User Management']");
    private final By btn_UserAgencyManagement = By.xpath("//a[@class='ng-star-inserted']");
    private final By btn_AgentInformation = By.xpath("//a[@href='/management/agency/agency-info']");
    private final By btn_TravelDetails = By.xpath("//a[@href='/management/agency/travellers-details']");

    public UserManagement_Common(SHAFT.GUI.WebDriver driver){
        this.driver =driver;
    }

    public UserManagement_Common UserManagement(){
        driver.element().click(btn_UserManagement);
        return this;
    }
    public UserManagement_Common AgencyUserManagement(){
        driver.element().click(btn_UserAgencyManagement);
        return this;
    }
    public UserManagement_Common AgentInformation(){
        driver.element().click(btn_AgentInformation);
        return this;
    }
    public UserManagement_Common TravelDetails (){
        driver.element().click(btn_TravelDetails );
        return this;
    }
}
