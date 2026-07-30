package Drive_Factory;

import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class CommonMethod {

    private static SHAFT.GUI.WebDriver driver;

    public static void setupDriver(String browser) {
        if (driver != null) {
            return;
        }

        DriverFactory.DriverType driverType =
                DriverFactory.DriverType.valueOf(browser.trim().toUpperCase());

        if (driverType == DriverFactory.DriverType.EDGE) {

            driver = new SHAFT.GUI.WebDriver(driverType);

        } else if (driverType == DriverFactory.DriverType.CHROME) {

            ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);

            options.setExperimentalOption("prefs", prefs);

            // Common Options
            options.addArguments("--disable-features=PasswordLeakDetection");
            options.addArguments("--disable-features=PasswordCheck");
            options.addArguments("--disable-save-password-bubble");

            // Linux Only (No GUI)
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("linux")) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
            }

            driver = new SHAFT.GUI.WebDriver(driverType, options);

        } else {

            driver = new SHAFT.GUI.WebDriver(driverType);

        }
    }

    public static SHAFT.GUI.WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}