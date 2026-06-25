package PortalPages;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class SideMenu {
    SHAFT.GUI.WebDriver driver ;
    private final By lnk_TravellersDetails = By.xpath("//a[@href='/travellers']");

    public SideMenu(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    public void ElementClick(By by){

        driver.element().click(by);
    }
    public void OpenAddTravellerPage(){
        ElementClick(lnk_TravellersDetails);
    }
}