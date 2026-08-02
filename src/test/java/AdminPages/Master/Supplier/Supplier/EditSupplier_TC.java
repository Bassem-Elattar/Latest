package AdminPages.Master.Supplier.Supplier;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class EditSupplier_TC  {
    private SearchSupplier_Page searchSupplier;
    private ActionSupplier_Page actionSupplier;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    SHAFT.TestData.JSON testData;


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

        new LogIn_Page(driver).ClickSuperAdmin();
        testData = new SHAFT.TestData.JSON("src/test/resources/testDataFiles/EditSupplier.json");

    }
    @Test( dataProvider = "JsonProvider")
    public void EditSupplier(Map<String, String> search) throws InterruptedException {
        searchSupplier = new SearchSupplier_Page(driver);
        actionSupplier = new ActionSupplier_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplier();
        String SupplierName = search.get("SupplierName");
        String ProductType = search.get("ProductType");
        String Country = search.get("Country");
        String Email = search.get("Email");
        String City = search.get("City");
        String PinCode = search.get("PinCode");
        String WhiteListBoard = search.get("WhiteListBoard");
        String WhiteListBoard2 = search.get("WhiteListBoard2");
        String Remark = search.get("Remarks");
        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        actionSupplier.setEditBtn(ProductType,Country,Email,City,PinCode,WhiteListBoard,WhiteListBoard2,Remark);
    }
    @Test
    public void EditSupplierCICD() throws InterruptedException {
        searchSupplier = new SearchSupplier_Page(driver);
        actionSupplier = new ActionSupplier_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplier();
        String SupplierName = testData.getTestData("SupplierNameCICD");

        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        actionSupplier.setEdit();
    }
    @AfterMethod
    public void navigateBackToURL() {
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }

}
