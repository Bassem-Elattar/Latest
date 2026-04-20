package AdminPages.Admin.Branch;
import AdminPages.Admin.AdminMenu;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchBranch_TC extends TestBase_TC {
    private Branch_Page1 searchBranch;
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
        new LogIn_Page(driver).ClickAdmin();
        new LogIn_Page(driver).ClickOnLoginButton();
        logIn.ClickOnLoginButton();


    }
    @Test(dataProvider = "JsonProvider")
    public void SearchBranch(Map<String,String>branch) throws InterruptedException {
        searchBranch = new Branch_Page1(driver);
        new AdminMenu(driver).openSubAdmin().openBranch();
        String Branchcode = branch.get("Branchcode");
        String Branchname = branch.get("Branchname");
        String Selectcountry = branch.get("Selectcountry");
        String Selectstate = branch.get("Selectstate");
        String Selectcity = branch.get("Selectcity");
        searchBranch.RouteBranch();
        searchBranch.Txt_BranchCode(Branchcode);
        searchBranch.Btn_Active();
        searchBranch.Btn_Search();
        Thread.sleep(1500);
    }

    @Test(dataProvider = "JsonProvider")
    public void setSearchBranchInactive(Map<String,String>branch){
        searchBranch = new Branch_Page1(driver);
        new AdminMenu(driver).openSubAdmin().openBranch();
        String Branchcode = branch.get("Branchcode");
        String Branchname = branch.get("Branchname");
        String Selectcountry = branch.get("Selectcountry");
        String Selectstate = branch.get("Selectstate");
        String Selectcity = branch.get("Selectcity");
        searchBranch.RouteBranch();
        searchBranch.Txt_BranchCode(Branchcode);
        searchBranch.Lst_Country(Selectcountry);
        searchBranch.Btn_Inactive();
        searchBranch.Btn_Search();
    }

    @Test(dataProvider = "JsonProvider")
    public void setSearchBranchBoth(Map<String,String>branch){
        searchBranch = new Branch_Page1(driver);
        new AdminMenu(driver).openSubAdmin().openBranch();
        String Branchcode = branch.get("Branchcode");
        String Branchname = branch.get("Branchname");
        String Selectcountry = branch.get("Selectcountry");
        String Selectstate = branch.get("Selectstate");
        String Selectcity = branch.get("Selectcity");
        searchBranch.RouteBranch();
        searchBranch.Txt_BranchCode(Branchcode);
        searchBranch.Btn_Both();
        searchBranch.Btn_Search();
    }
    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
