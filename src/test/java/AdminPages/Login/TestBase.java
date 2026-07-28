package AdminPages.Login;

import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import utilities.DataUtils;

public class TestBase {

    protected SHAFT.GUI.WebDriver driver;

    @BeforeTest
    public void setupBrowse() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        driver = new SHAFT.GUI.WebDriver(
                DriverFactory.DriverType.CHROME,
                options
        );

        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        Thread.sleep(5000);
    }

//    @AfterTest
//    public void Quit() {
//        driver.quit();
//    }
}