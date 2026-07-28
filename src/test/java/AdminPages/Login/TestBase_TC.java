package AdminPages.Login;
import Drive_Factory.CommonMethod;
import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utilities.DataUtils;

public class TestBase_TC {

    public SHAFT.GUI.WebDriver driver ;

    @BeforeTest
    public void setupBrowse() throws InterruptedException {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
    }

//    @AfterTest
//    public void Quit() {
//        driver.quit();
//    }
}