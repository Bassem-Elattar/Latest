package Drive_Factory;

import com.shaft.driver.SHAFT;

public class CommonMethod {

    private static SHAFT.GUI.WebDriver driver;

    public static void setupDriver(String browser) {

        if (driver == null) {
            com.shaft.driver.DriverFactory.DriverType type =
                    com.shaft.driver.DriverFactory.DriverType.valueOf(browser.toUpperCase());

            driver = new SHAFT.GUI.WebDriver(type);
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