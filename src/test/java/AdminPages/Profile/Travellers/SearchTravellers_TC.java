package AdminPages.Profile.Travellers;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Profile.Profile_Common;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchTravellers_TC extends TestBase_TC {
    private SearchTravellers_Page searchTravellers;
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
    public void SearchTraveller(Map<String, String> search){
        searchTravellers = new SearchTravellers_Page(driver);
        new Profile_Common(driver).clickProfile().clickTraveller();
        String BranchName = search.get("BranchName");
        String ClientID = search.get("ClientID");
        String ClientName = search.get("ClientName");
        String EmailID = search.get("EmailID");
        String PhoneNo = search.get("PhoneNo");
        String Country = search.get("Country");
        searchTravellers.setSearchTravellers(BranchName,ClientID,ClientName,EmailID,PhoneNo,Country);
        searchTravellers.setBoth();
        searchTravellers.setSearch();

    }
}
