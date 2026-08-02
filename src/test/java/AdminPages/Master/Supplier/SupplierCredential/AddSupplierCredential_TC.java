package AdminPages.Master.Supplier.SupplierCredential;
import AdminPages.Login.LogIn_Page;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Supplier.Supplier.SearchSupplier_Page;
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


public class AddSupplierCredential_TC {

    private LogIn_Page logIn;
    public SHAFT.GUI.WebDriver driver;
    private SearchCity_Page searchCity;
    private SearchSupplierCredential_Page searchSupplierCredential;
    private SearchSupplier_Page searchSupplier;
    private AddSupplierCredential_Page addSupplierCredential;
    private ReflectionForSupplierCredential_Page reflectionForSupplierCredential;
    private EditSupplierCredential_Page editSupplierCredential;

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

        new LogIn_Page(driver).superAdminLogin();
    }

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void CreateSupplierCredential(Map<String, String> add) throws InterruptedException {
        searchCity = new SearchCity_Page(driver);
        searchSupplierCredential = new SearchSupplierCredential_Page(driver);
        addSupplierCredential = new AddSupplierCredential_Page(driver);
        searchSupplier = new SearchSupplier_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplierCredencial();
        String Supplier = add.get("Supplier");
        String CredentialName = add.get("CredentialName");
        String CountryPOC = add.get("CountryPOC");
        String Currency = add.get("Currency");
        String CredentialType = add.get("CredentialType");
        String FlightSearchLimit = add.get("FlightSearchLimit");
        String ConnectionTime = add.get("ConnectionTime");
        String ReadTimeOut = add.get("ReadTimeOut");
        String EndpointURL = add.get("EndpointURL");
        String Password = add.get("Password");
        String DisplayName = add.get("DisplayName");
        String DevCreate = add.get("DevCreate");
        addSupplierCredential.setAddSupplierCredential(Supplier,CountryPOC,Currency,CredentialType);
        //addSupplierCredential.GDS(EndpointURL);
//        addSupplierCredential.RestrictedAirline();

        addSupplierCredential.setSaveBtn();
//        Thread.sleep(10000);
    }


                 // Check the Reflection on Supplier Credential then activate it //

//    @Test(priority = 2, dataProvider = "SearchSupplierCredential",dataProviderClass = JsonDataProvider.class)
//    public void setSearchSupplierCredential(Map<String, String> search) throws InterruptedException {
//        searchCity = new SearchCity(driver);
//        searchSupplierCredential = new SearchSupplierCredential(driver);
//        searchSupplier = new SearchSupplier(driver);
//        searchCity.ClickonMaster();
//        searchSupplier.clickonsupplier();
//        searchSupplierCredential.ClickonSupplierCredential();
//        String CredentialName = search.get("CredentialName");
//        String Supplier = search.get("Supplier");
//        searchSupplierCredential.setSearchCredential(CredentialName,Supplier);
//        searchSupplierCredential.setBoth();
//        searchSupplierCredential.setActiveCircle();
//
//
//    }



//    @Test(priority = 3,dataProvider = "JsonProvider")
//    public void SetReflectionOnBranch(Map<String, String> Reflection) throws InterruptedException {
//
//        reflectionForSupplierCredential = new ReflectionForSupplierCredential_Page(driver);
//        String OP = Reflection.get("OP");
//        String Country = Reflection.get("Country");
//        reflectionForSupplierCredential.setBranch(OP,Country);
//        String Expected = Reflection.get("Expected");
//        String Actual = ElementActions.getInstance().getText(By.xpath("/html/body/div/section/div/div[2]/div/div/div[4]/form/div[6]/div[3]/div/div[1]/div/div[2]/div/div[1]/select/option[2]"));
//        Assert.assertEquals(Actual,Expected);
//
//    }
//
//    @Test(priority = 4,dataProvider = "JsonProvider")
//    public void setReflectionOnAgency(Map<String, String> Reflection){
//        reflectionForSupplierCredential = new ReflectionForSupplierCredential_Page(driver);
//        String Branch = Reflection.get("Branch");
//        reflectionForSupplierCredential.SetAgency(Branch);
//        String Expected = Reflection.get("Expected");
//        String Actual = ElementActions.getInstance().getText(By.xpath("/html/body/div[1]/section/div/div[2]/div/div/div[4]/form[1]/div[10]/div[2]/div[1]/div/div[2]/div/div[1]/select/option[2]"));
//        Assert.assertEquals(Actual,Expected);
//
//    }

//    @Test(priority = 5, dataProvider = "SearchSupplierCredential",dataProviderClass = JsonDataProvider.class)
//    public void setEditSupplierCredential(Map<String, String> search){
//        searchCity = new SearchCity(driver);
//        searchSupplierCredential = new SearchSupplierCredential(driver);
//        searchSupplier = new SearchSupplier(driver);
//        editSupplierCredential = new EditSupplierCredential(driver);
//        searchCity.ClickonMaster();
//        searchSupplier.clickonsupplier();
//        searchSupplierCredential.ClickonSupplierCredential();
//        String CredentialName = search.get("CredentialName");
//        String Supplier = search.get("Supplier");
//        searchSupplierCredential.setSearchCredential(CredentialName,Supplier);
//        searchSupplierCredential.setInactive();
//        editSupplierCredential.setEditSuppliercredential("Amada");
//    }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
}
}


