package AdminPages.Master.Miscellaneous.Region.Region;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Miscellaneous.Region.Country.CreateCountry_Page;
import AdminPages.Master.Miscellaneous.Region.Country.SearchCountry_Page;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateRegion_TC extends TestBase_TC {
    private SearchCity_Page regionSearchCity;
    private SearchCountry_Page searchCountry;
    private CreateCountry_Page createCountry;
    private SearchRegion_Page searchRegion;
    private CreateRegion_Page createRegion;
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
    public void CreateRegion(Map<String, String> region) throws InterruptedException {
        regionSearchCity = new SearchCity_Page(driver);
        searchRegion = new SearchRegion_Page(driver);
        createRegion = new CreateRegion_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickMiscellaneous()
                .clickRegion();
        String RegionName = region.get("RegionName");
        String RegionCode = region.get("RegionCode");
        String CountryName = region.get("CountryName");
        String CountryName1 = region.get("CountryName1");
        createRegion.setRegionName(RegionName,RegionCode,CountryName, CountryName1);
        String Expected = "Added Successfully";
        Assert.assertEquals(createRegion.Actual(),Expected);
        createRegion.verifyURLorErrorMessages();
    }
}
