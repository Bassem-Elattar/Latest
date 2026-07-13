package AdminPages.Master.Flight.Airline;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class E2EAirLine_TC {
    private Airline_Page airline;
    private CreateAirline_Page createAirline;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void sign() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        // Admin login
        new LogIn_Page(driver).AdminLogin();
        testData = new SHAFT.TestData.JSON("CreateAirlineLCC.json");
    }

    @Test()
    public void E2EAirline() {
        airline = new Airline_Page(driver);
        createAirline = new CreateAirline_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickAirline();
        createAirline.setAddAirline();
        String AirlineType = testData.getTestData("AirlineType");
        String NetworkType = testData.getTestData("NetworkType");
        String Code = createAirline.AirlineDetails(AirlineType, NetworkType);
        createAirline.SendForApproval();
        airline.ClickOnInactive();
        airline.EnterAirLineCode(Code);
        airline.SearchButton();
        airline.ClickOnThumbsUp();
        airline.ClickOnActive();
        airline.EnterAirLineCode(Code);
        airline.SearchButton();
//        String Expected = "This name already exists";
//        Assert.assertEquals(createAirline.Actual(), Expected);
    }
}