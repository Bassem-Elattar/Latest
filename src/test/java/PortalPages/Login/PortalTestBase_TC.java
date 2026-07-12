package PortalPages.Login;

import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeTest;
import utilities.DataUtils;

public class PortalTestBase_TC {

    public SHAFT.GUI.WebDriver driver;

    @BeforeTest
    public void setupPortalBrowser() {
        driver = new SHAFT.GUI.WebDriver(DriverFactory.DriverType.CHROME);
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
    }
}
