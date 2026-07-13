package AdminPages.Master.Flight.Airline;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateAirline_TC extends TestBase_TC {
    private Airline_Page airline;
    private CreateAirline_Page createAirline;
    private LogIn_Page logIn;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void CreateAirline(Map<String,String> Create){
        airline = new Airline_Page(driver);
        createAirline = new CreateAirline_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickAirline();
        createAirline.setAddAirline();
        String AirlineCode = Create.get("AirlineCode");
        String AirlineName = Create.get("AirlineName");
        String Alliance = Create.get("Alliance");
        String AirlineType = Create.get("AirlineType");
        String NetworkType = Create.get("NetworkType");
        String CountryPOS = Create.get("CountryPOS");
        String BranchPOS = Create.get("BranchPOS");
        String RestrictedAgencies = Create.get("RestrictedAgencies");
        createAirline.AirlineDetails(AirlineType,NetworkType);
        createAirline.SendForApproval();
        String Expected = "This name already exists";
        Assert.assertEquals(createAirline.Actual(),Expected);
    }

    @Test(priority = 2, dataProvider = "JsonProvider")
    public void CreateAirlineLCC(Map<String,String> Create){
        airline = new Airline_Page(driver);
        createAirline = new CreateAirline_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickAirline();
        createAirline.setAddAirline();
        String AirlineCode = Create.get("AirlineCode");
        String AirlineName = Create.get("AirlineName");
        String Alliance = Create.get("Alliance");
        String AirlineType = Create.get("AirlineType");
        String NetworkType = Create.get("NetworkType");
        String CountryPOS = Create.get("CountryPOS");
        String BranchPOS = Create.get("BranchPOS");
        String RestrictedAgencies = Create.get("RestrictedAgencies");
        createAirline.AirlineDetails(AirlineType,NetworkType);
        createAirline.AllowOnlineHold(CountryPOS,BranchPOS,RestrictedAgencies);
        createAirline.SendForApproval();
        String Expected = "Added Successfully";
        Assert.assertEquals(createAirline.Actual2(),Expected);
    }

    @Test(priority = 3, dataProvider = "JsonProvider")
    public void CreateAirlineInvalidLCC(Map<String,String> Create){
        airline = new Airline_Page(driver);
        createAirline = new CreateAirline_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickAirline();
        createAirline.setAddAirline();
        String AirlineCode = Create.get("AirlineCode");
        String AirlineName = Create.get("AirlineName");
        String Alliance = Create.get("Alliance");
        String AirlineType = Create.get("AirlineType");
        String NetworkType = Create.get("NetworkType");
        String CountryPOS = Create.get("CountryPOS");
        String BranchPOS = Create.get("BranchPOS");
        String RestrictedAgencies = Create.get("RestrictedAgencies");
        createAirline.AirlineDetails(AirlineType,NetworkType);
        createAirline.AllowOnlineHold(CountryPOS,BranchPOS,RestrictedAgencies);
        createAirline.SendForApproval();
        String Expected = "Required";
        Assert.assertEquals(createAirline.Actual(),Expected);
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
