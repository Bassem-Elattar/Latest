package AdminPages.Master.Flight.PreferAirline;

import AdminPages.Login.LogIn_Page;
import AdminPages.Master.Master_Common;
import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class ActionPreferAirLine_TC {
    protected SHAFT.GUI.WebDriver driver;
    protected PreferAirLine_Page preferAirLine;
    protected LogIn_Page logIn;

    private final String baseURL = "http://192.168.1.70/master/flight/preferAirline";

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void setUpClass() throws InterruptedException {
        System.out.println("Initializing WebDriver...");
        driver = new SHAFT.GUI.WebDriver(DriverFactory.DriverType.FIREFOX);
        driver.browser().maximizeWindow();  // Initialize the driver here
        logIn =new LogIn_Page(driver);
        logIn.LogInNavigateToUrl();
        logIn.EnterUserName("e.saady");
        logIn.EnterPassword("qqE6)Cxp6>B8");
        logIn.ClickOnLoginButton();
        preferAirLine = new PreferAirLine_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickPreferSAirline();
    }
    @Test (priority = 1)//Aer Arann must be inactive in first and Approved
    public void Actions1() throws InterruptedException {
        preferAirLine.EnterAirlineName("Aer Arann");
        preferAirLine.SelectSupplierName("Galileo");
        preferAirLine.clickOnSearchInGrid();

        preferAirLine.clickInActiveButton();
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickActiveSign();
        Thread.sleep(Long.parseLong("2000"));
        preferAirLine.clickActiveButton();
        preferAirLine.clickOnSearchInGrid();
        assertEquals("Active", preferAirLine.TableColumnDataExtractor(3, "Active"));
    }
    @Test (priority = 2)
    public void Actions2() throws InterruptedException
    {
        preferAirLine.clickDisLike();
        preferAirLine.EnterRemarksType("Asmaa");
        preferAirLine.RemarkSubmitButton();
        Thread.sleep(2000);
         //preferAirLine.EnterAirlineName("Aer Arann");
        preferAirLine.clickInActiveButton();
        preferAirLine.clickOnSearchInGrid();
        assertEquals("Rejected", preferAirLine.TableColumnDataExtractor(4, "Rejected"));
    }
    @Test (priority = 3)
    public void Actions3() throws InterruptedException {
        preferAirLine.clickLike();
        preferAirLine.EnterRemarksType("Asmaa");
        preferAirLine.RemarkSubmitButton();
        Thread.sleep(2000);
       // preferAirLine.EnterAirlineName("Aer Arann");
        preferAirLine.clickOnSearchInGrid();
        preferAirLine.clickActiveButton();
        preferAirLine.clickOnSearchInGrid();
        Thread.sleep(3000);
        assertEquals("Approved", preferAirLine.TableColumnDataExtractor(4, "Approved"));
    }
    @Test (priority = 4)
    public void Actions4() throws InterruptedException {
       // assertEquals("Active",preferAirLine.TableColumnDataExtractor(4,"Active"));
        preferAirLine.clickDisActiveSign();
        preferAirLine.clickInActiveButton();
        preferAirLine.clickOnSearchInGrid();
        assertEquals("Approved",preferAirLine.TableColumnDataExtractor(4,"Approved"));
       // assertEquals("Inactive",preferAirLine.TableColumnDataExtractor(4,"Inactive"));

    }

    @AfterTest
    public void teardown() {
        System.out.println("Quitting the driver...");
        driver.quit();
    }

}
