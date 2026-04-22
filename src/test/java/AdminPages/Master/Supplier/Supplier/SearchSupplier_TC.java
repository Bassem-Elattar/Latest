package AdminPages.Master.Supplier.Supplier;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchSupplier_TC extends TestBase_TC {
   private SearchSupplier_Page searchSupplier;
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
    public void SearchSupplier(Map<String, String> search) throws InterruptedException {
       searchSupplier = new SearchSupplier_Page(driver);
       paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
       new Master_Common(driver).clickMaster()
               .clickSupplierMenue()
               .clickSupplier();
       String SupplierName = search.get("SupplierName");
       searchSupplier.searchsupplierdata(SupplierName);
       searchSupplier.setBoth();
       // Handle pagination and assertions separately
       int totalPages = paginationHelper.getTotalPages();
       for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
           System.out.println("Processing page: " + currentPage);

           // Perform assertions
           searchSupplier.performAssertions();

           // Navigate to the next page if not on the last page
           if (currentPage < totalPages) {
               paginationHelper.navigateToNextPage();
           }
       }
   }
}
