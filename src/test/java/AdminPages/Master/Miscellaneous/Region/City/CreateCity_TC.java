package AdminPages.Master.Miscellaneous.Region.City;

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

public class CreateCity_TC extends TestBase_TC {
    private SearchCity_Page regionSearchCity;
    private CreateCity_Page regionAddCity;
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

    @Test(dataProvider = "JsonProvider")
    public void CreateCity(Map<String,String> city) throws InterruptedException {
        regionAddCity = new CreateCity_Page(driver);
        regionSearchCity = new SearchCity_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        regionSearchCity.ClickonCity();
        String CountryName = city.get("CountryName");
        String StateName = city.get("StateName");
        String CityName  = city.get("CityName");
        String CityCode = city.get("CityCode");
        regionAddCity.AddcityDetails(CountryName,StateName,CityName,CityCode);
        String Expected = "Added Successfully";
        Assert.assertEquals(regionAddCity.Actual(),Expected);
        regionAddCity.verifyURLorErrorMessages();

    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

