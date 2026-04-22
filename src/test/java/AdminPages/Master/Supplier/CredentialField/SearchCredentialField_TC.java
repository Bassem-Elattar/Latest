package AdminPages.Master.Supplier.CredentialField;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Supplier.Supplier.SearchSupplier_Page;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchCredentialField_TC extends TestBase_TC {
    private SearchCredentialField_Page searchCredentialField;
    private LogIn_Page logIn;
    private AdminPages.Helper.PaginationHelper paginationHelper;

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
    public void SearchCredential(Map<String, String> credentialField) throws InterruptedException {
        searchCredentialField = new SearchCredentialField_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickCredentialField();
        String SupplierCredentialFieldName = credentialField.get("SupplierCredentialFieldName");
        String Supplier = credentialField.get("Supplier");
        searchCredentialField.setSupplierCredentialFieldName(SupplierCredentialFieldName,Supplier);
        searchCredentialField.setSearchGrid();
        searchCredentialField.performAssertions();
    }
}
