package AdminPages.Admin.Branch;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.PaymentGateway.PaymentMethod.Add;
import Drive_Factory.CommonMethod;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;


public class CreateBranch_TC  {
//    private AddNewBranch_Page addNewBranch;
//    private LogIn_Page logIn;
//    private SearchBranch_Page searchBranch;
    String Branchcode = "";
    String Branchname = "";
    String Selectcountry = "";
    String Selectstate = "";
    String Selectcity = "";

//    @DataProvider(name = "JsonProvider")
//    public static Object[][] provideJsonData(Method method) throws IOException {
//        String fileName = method.getName();
//        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
//        return JsonDataUtil.readJsonData(filePath);

    private SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;
    @BeforeTest
    public void sign(){
        testData = new SHAFT.TestData.JSON("CreateBranch.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        // Admin login
        new LogIn_Page(driver).AdminLogin();


    }

//    @Test(dataProvider = "JsonProvider")
//    public void CreateBranch(Map<String, String> Branch) throws InterruptedException {
//        addNewBranch = new Branch_Page(driver);
//        searchBranch = new Branch_Page(driver);
//        searchBranch.RouteBranch();
//        addNewBranch.Btn_CreateBranch();
//        String OperatingCountry = Branch.get("OperatingCountry");
//        String BranchName = Branch.get("BranchName");
//        String State = Branch.get("State");
//        String City = Branch.get("City");
//        String Address = Branch.get("Address");
//        String PostOffice = Branch.get("PostOffice");
//        String PhoneNo = Branch.get("PhoneNo");
//        String EmailID = Branch.get("EmailID");
//        String Password = Branch.get("Password");
//        String Name = Branch.get("Name");
//        String Email = Branch.get("Email");
//        String Phone = Branch.get("Phone");
//        String Creditlimit = Branch.get("Creditlimit");
//        String TopupLimit = Branch.get("TopupLimit");
//        String Creditterms = Branch.get("Creditterms");
//        String SupplierCredential = Branch.get("SupplierCredential");
//        String Description = Branch.get("Description");
//        String Supplierdescription = Branch.get("Supplierdescription");

        @Test
        public void CreateBranch() throws InterruptedException {
//            addNewBranch = new AddNewBranch_Page(driver);
//            searchBranch = new SearchBranch_Page(driver);
            String Brand = testData.getTestData("Brand");
            Selectcountry = testData.getTestData("Country");
            String OperatingCountry = testData.getTestData("OperatingCountry");
            Branchname = testData.getTestData("BranchName");
            String state = testData.getTestData("State");
            String City = testData.getTestData("City");
            String Address = testData.getTestData("Address");
            String PostOffice = testData.getTestData("PostOffice");
            String PhoneNo = testData.getTestData("PhoneNo");
            String EmailID = testData.getTestData("EmailID");
            String Password = testData.getTestData("Password");
            String Name = testData.getTestData("Name");
            String Email = testData.getTestData("Email");
            String Phone = testData.getTestData("Phone");
            String Department = testData.getTestData("Department");
            String Creditlimit = testData.getTestData("Creditlimit");
            String Topup = testData.getTestData("Topup");
            String Creditterm = testData.getTestData("Creditterm");
            String Description = testData.getTestData("Description");
            String Supplierdescription = testData.getTestData("Supplierdescription");

//        addNewBranch.Txt_OperatingCountry(OperatingCountry);
//        addNewBranch.Txt_Name(BranchName);
//        addNewBranch.Lst_StateCreate(State);
//        addNewBranch.Lst_CityCreate(City);
//        addNewBranch.Txt_Address1(Address);
//        addNewBranch.Txt_PostOffice(PostOffice);
//        addNewBranch.Txt_PhoneNo(PhoneNo);
//        addNewBranch.Txt_EmailID(EmailID);
//        addNewBranch.Txt_EmailPassword(Password);
//        addNewBranch.Txt_NameContact(Name);
//        addNewBranch.Txt_EmailIdContact(Email);
//        addNewBranch.Txt_PhoneNumber(Phone);
//        addNewBranch.Txt_CreditLimit(Creditlimit);
//        addNewBranch.Txt_TopUpLimit(TopupLimit);
//        addNewBranch.Txt_CreditTerms(Creditterms);
//        addNewBranch.Cbox_Cash();
//        addNewBranch.Cbox_Amadeus();
//        addNewBranch.Txt_Description(Description);
//        addNewBranch.Lst_SupplierCredential(SupplierCredential);
//        addNewBranch.Txt_SupplierDescription(Supplierdescription);
//        addNewBranch.Btn_Submit();
//        String Expected = "Added Successfully";
//        Assert.assertEquals(addNewBranch.Actual(),Expected);
        new AddNewBranch_Page(driver).BranchDetails(Brand,Selectcountry,OperatingCountry,Branchname,state,City,Address,PostOffice,PhoneNo,EmailID,Password
                ,Name,Email,Phone,Creditlimit,Topup,Creditterm,Description,Supplierdescription);
        Thread.sleep(1000);
            new AddNewBranch_Page(driver).Alert();
        Thread.sleep(Long.parseLong("1000"));
        new SearchBranch_Page(driver).setsearchbranch(Branchcode,Branchname,Selectcountry,Selectstate,Selectcity);

        new SearchBranch_Page(driver).setInactive();
        new SearchBranch_Page(driver).search();
        new SearchBranch_Page(driver).setThumpUp("Approved");

//        addNewBranch.Alert();
//        Thread.sleep(Long.parseLong("1000"));
//        searchBranch.Txt_BranchName(BranchName);
//        searchBranch.Btn_Inactive();
//        searchBranch.Btn_Search();
//        searchBranch.Btn_ThumbUp("Approved");





    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}