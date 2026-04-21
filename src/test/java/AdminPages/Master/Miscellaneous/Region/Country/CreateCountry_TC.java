package AdminPages.Master.Miscellaneous.Region.Country;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateCountry_TC extends TestBase_TC {
    private SearchCity_Page regionSearchCity;
    private SearchCountry_Page searchCountry;
    private CreateCountry_Page createCountry;
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
    public void CreateCountry(Map<String, String> country) throws InterruptedException {
        regionSearchCity = new SearchCity_Page(driver);
        searchCountry = new SearchCountry_Page(driver);
        createCountry = new CreateCountry_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        searchCountry.setCountry();
        createCountry.setAddCountry();
        String CountryName = country.get("CountryName");
        String CountryCode = country.get("CountryCode");
        String Airline = country.get("Airline");
        createCountry.setOperatingCountryName(CountryName, CountryCode, Airline);
        String Expected = "Added Successfully";
        Assert.assertEquals(createCountry.Actual(),Expected);
        createCountry.verifyURLorErrorMessages();
    }
}




