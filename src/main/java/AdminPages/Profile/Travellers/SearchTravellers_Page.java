package AdminPages.Profile.Travellers;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class SearchTravellers_Page {
    public SearchTravellers_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
   // By Btn_Profile = By.xpath("//a[@href=\"/profile\"]");
    By Lst_BranchName = By.xpath("//p-dropdown[.//input[@id=\"id-BranchName\"]]");
    By Txt_ClientID = By.xpath("//input[@id=\"id-ClientID\"]");
    By Txt_ClientName = By.xpath("//input[@id=\"id-Clientname\"]");
    By Txt_EmailID = By.xpath("//input[@id=\"id-EmailID\"]");
    By Txt_PhoneNo = By.xpath("//input[@id=\"phone number\"]");
    By Lst_Country = By.xpath("//p-dropdown[.//input[@id=\"id-Country\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_Search = By.xpath("//button[@type=\"submit\"]");


    public void setSearchTravellers(String branch , String clientid, String clientname,String emailid,String phone , String country){
//        driver.element().click(Lst_BranchName);
//        By option = By.xpath(String.format("//span[text()='%s']", branch));
//        driver.element().click(option);
        driver.element().type(Txt_ClientID,clientid);
        driver.element().type(Txt_ClientName,clientname);
        driver.element().type(Txt_EmailID,emailid);
        driver.element().type(Txt_PhoneNo,phone);
//        driver.element().click(Country);
//        By option1 = By.xpath(String.format("//span[text()='%s']", country));
//        driver.element().click(option1);


    }
    public void setActive(){
        driver.element().click(Rbtn_Active);
    }
    public void setInactive(){
        driver.element().click(Rbtn_Inactive);
    }
    public void setBoth(){
        driver.element().click(Rbtn_Both);
    }
    public void setSearch(){
        driver.element().click(Btn_Search);

    }
}
