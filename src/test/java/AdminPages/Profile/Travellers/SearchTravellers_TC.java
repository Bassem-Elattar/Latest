package AdminPages.Profile.Travellers;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Profile.Profile_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchTravellers_TC {
    private SearchTravellers_Page searchTravellers;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).AdminLogin();

    }
    @Test(dataProvider = "JsonProvider")
    public void SearchTraveller(Map<String, String> search){
        searchTravellers = new SearchTravellers_Page(driver);
        new Profile_Common(driver).clickProfile().clickTraveller();
        String BranchName = search.get("BranchName");
        String ClientID = search.get("ClientID");
        String ClientName = search.get("FirstName");
        String EmailID = search.get("EmailID");
        String PhoneNo = search.get("PhoneNo");
        String Country = search.get("Country");
        searchTravellers.setSearchTravellers(BranchName,ClientID,ClientName,EmailID,PhoneNo,Country);
        searchTravellers.setBoth();
        searchTravellers.setSearch();

    }
}
