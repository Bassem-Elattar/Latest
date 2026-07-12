package AdminPages.Admin.Agency.Agency;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class SearchAgency_Page {

    public SearchAgency_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_Agency = By.xpath("//ndc-root/ndc-layout//div[1]/tilde-theme-side-menu/div/ul/li[3]/tilde-theme-accordion/header/a");
    By Btn_agency = By.xpath("//a[@href=\"/admin/agency\"]");
    By Txt_AgencyCode = By.xpath("//input[@id=\"id-Agencycode\"]");
    By Txt_AgencyName = By.xpath("//input[@id=\"id-Agencyname\"]");
    By Lst_SelectCountry = By.xpath("//p-multiselect[.//input[@id=\"id-Country\"]]");
    By Lst_SelectState = By.xpath("//p-multiselect[.//input[@id=\"id-State\"]]");
    By Lst_SelectCity = By.xpath("//p-multiselect[.//input[@id=\"id-City\"]]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_SearchGrid = By.xpath("//button[@type=\"submit\"]");


    public String setSearchAgency(String agencyname) throws InterruptedException {
        //driver.element().waitToBeReady(Btn_Agency);
        Thread.sleep(Long.parseLong("1000"));

        // driver.element().click(Btn_Agency);
        driver.element().click(Btn_agency);
        //  driver.element().type(Txt_AgencyCode,agencycode);

        driver.element().type(Txt_AgencyName,agencyname);
//        driver.element().click(Lst_SelectCountry);
//        By option = By.xpath(String.format("//span[text()='%s']", selectcountry));
//        driver.element().click(option);
//        driver.element().click(Lst_SelectState);
//        By option1 = By.xpath(String.format("//span[text()='%s']", selectstate));
//        driver.element().click(option1);
//        driver.element().click(Lst_SelectCity);
//        By option2 = By.xpath(String.format("//span[text()='%s']", selectcity));
//        driver.element().click(option2);
        return agencyname;
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

    public void setSearchGrid()
    {
        driver.element().click(Btn_SearchGrid);
    }



}