package AdminPages.Admin;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class AdminMenu {
    public AdminMenu(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver;
    By Btn_Admin = By.xpath("//a[@id=\"p-tabpanel-1-label\"]");
    By Btn_Company = By.xpath("//span[normalize-space()='Company']");
    By Btn_Branch = By.xpath("(//a[@class=\"ng-star-inserted\"])[2]");
    By Btn_Agency = By.xpath("//li[3]//tilde-theme-accordion[1]//header[1]//a[1]");
    By Btn_Staff = By.xpath("(//a[@class=\"ng-star-inserted\"])[4]");
    By Btn_SubAgency = By.xpath("(//i[@class=\"pi pi-briefcase\"])[2]");
    By Btn_SearchLoginDetails = By.xpath("//a[@href=\"/admin/login-details\"]");
    By Btn_RegistrationRequests = By.xpath("//a[@href=\"/admin/registration-requests\"]");
    By Btn_OperatingCountry = By.xpath("//a[@href=\"/admin/operating-country\"]");
    By Btn_Role = By.xpath("(//li[@class=\"child ng-star-inserted\"])[2]");
    By Btn_Department = By.xpath("(//li[@class=\"child ng-star-inserted\"])[3]");
    By Btn_TopUp = By.xpath("(//li[@class=\"child ng-star-inserted\"])[4]");

    public AdminMenu openSubAdmin(){
        driver.element().click(Btn_Admin);
        return this;
    }
    public AdminMenu openBranch(){
        driver.element().click(Btn_Branch);
        return this;
    }
    public AdminMenu openStaff(){
        driver.element().click(Btn_Staff);
        return this;
    }
    public AdminMenu Agency(){
        driver.element().click(Btn_Agency);
        //driver.element().click(Btn_SubAgency);
        return this;
    }
    public AdminMenu SerachLoginDetails(){
        driver.element().click(Btn_SearchLoginDetails);
        return this;
    }
    public AdminMenu RegistrationRequest(){
        driver.element().click(Btn_RegistrationRequests);
        return this;
    }
    public AdminMenu Company(){
        driver.element().click(Btn_Company);
        return this;
    }
    public AdminMenu OperatingCountry(){
        driver.element().click(Btn_OperatingCountry);
        return this;
    }
    public AdminMenu Role(){
        driver.element().click(Btn_Role);
        return this;
    }
    public AdminMenu Department(){
        driver.element().click(Btn_Department);
        return this;
    }
    public AdminMenu TopUp(){
        driver.element().click(Btn_TopUp);
        return this;
    }
}
