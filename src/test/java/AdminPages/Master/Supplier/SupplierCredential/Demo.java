package AdminPages.Master.Supplier.SupplierCredential;
import AdminPages.Admin.Branch.AddNewBranch_Page;
import AdminPages.BookingMidOffice.Booking.SearchBookingBranch;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import AdminPages.Master.Miscellaneous.Region.City.SearchCity_Page;
import AdminPages.Master.Supplier.CredentialField.CreateCredentialField_Page;
import AdminPages.Master.Supplier.CredentialField.SearchCredentialField_Page;
import AdminPages.Master.Supplier.Supplier.ActionSupplier_Page;
import AdminPages.Master.Supplier.Supplier.SearchSupplier_Page;
import com.shaft.gui.element.ElementActions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Map;

public class Demo extends TestBase_TC {

    private SearchBookingBranch searchBooking;
    private SearchCity_Page searchCity;
    private SearchSupplier_Page searchSupplier;
    private ActionSupplier_Page actionSupplier;
    private AddNewBranch_Page addNewBranch;
    private LogIn_Page logIn;
    private SearchSupplierCredential_Page searchSupplierCredential;
    private AddSupplierCredential_Page addSupplierCredential;
    private ReflectionForSupplierCredential_Page reflectionForSupplierCredential;
    private EditSupplierCredential_Page editSupplierCredential;
    private SearchSupplier_Page supplier;
    private SearchCredentialField_Page searchCredentialField;
    private SearchCity_Page regionSearchCity;
    private CreateCredentialField_Page createCredentialField;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }


    //Update In Branch
    @Test( dataProvider = "SearchSupplierData")
    public void setSearchSupplier(Map<String, String> search) throws InterruptedException {
        searchSupplier = new SearchSupplier_Page(driver);
        searchCity = new SearchCity_Page(driver);
        actionSupplier = new ActionSupplier_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplierCredencial();
        String SupplierName = search.get("SupplierName");
        searchSupplier.searchsupplierdata(SupplierName);
        searchSupplier.setBoth();
        actionSupplier.setEditBtn("GDS","egy","mahmd@gmail.com","cai","1125","ada","cai","approve");

    }

    @Test(dataProvider = "CreateCredintial")
    public void setCreateCredentialField(Map<String, String> credentialField){
        supplier = new SearchSupplier_Page(driver);
        searchCredentialField = new SearchCredentialField_Page(driver);
        regionSearchCity = new SearchCity_Page(driver);
        createCredentialField = new CreateCredentialField_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplierCredencial();
        String SupplierCredintial = credentialField.get("SupplierCredintial");
        String Supplier = credentialField.get("Supplier");
        createCredentialField.setSupplierCredintial(SupplierCredintial,Supplier);
        String Expected = "Added Successfully";
        String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        Assert.assertEquals(Actual,Expected);
    }


    @Test(dataProvider = "CreateSupplierCredential")
    public void setAddSupplierCredential(Map<String, String> add) throws InterruptedException {
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
        String DevCreate = add.get("DevCreate");
        addSupplierCredential.setAddSupplierCredential(Supplier,CountryPOC,Currency,CredentialType
                ,FlightSearchLimit,ConnectionTime,ReadTimeOut);
        addSupplierCredential.GDS(EndpointURL);
        Thread.sleep(10000);
        addSupplierCredential.setSaveBtn();
        Thread.sleep(10000);



    }


    // Check the Reflection on Supplier Credential then activate it //
    @Test(priority = 2, dataProvider = "SearchSupplierCredential")
    public void setSearchSupplierCredential(Map<String, String> search) throws InterruptedException {
        searchCity = new SearchCity_Page(driver);
        searchSupplierCredential = new SearchSupplierCredential_Page(driver);
        searchSupplier = new SearchSupplier_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplierCredencial();
        String CredentialName = search.get("CredentialName");
        String Supplier = search.get("Supplier");
        searchSupplierCredential.setSearchCredential(CredentialName,Supplier);
        searchSupplierCredential.setInactive();
        searchSupplierCredential.setActiveCircle();


    }


    @Test(priority = 3,dataProvider = "ReflectionBranchSupplierCredential")
    public void SetReflectionOnBranch(Map<String, String> Reflection) throws InterruptedException {

        reflectionForSupplierCredential = new ReflectionForSupplierCredential_Page(driver);
        String OP = Reflection.get("OP");
        String Country = Reflection.get("Country");
        reflectionForSupplierCredential.setBranch(OP,Country);
        String Expected = Reflection.get("Expected");
//        String Actual = ElementActions.getInstance().getText(By.xpath("/html/body/div/section/div/div[2]/div/div/div[4]/form/div[6]/div[3]/div/div[1]/div/div[2]/div/div[1]/select/option[2]"));
//        Assert.assertEquals(Actual,Expected);

    }

    @Test(priority = 4,dataProvider = "ReflectionAgencySupplierCredential")
    public void setReflectionOnAgency(Map<String, String> Reflection){
        reflectionForSupplierCredential = new ReflectionForSupplierCredential_Page(driver);
        String Branch = Reflection.get("Branch");
        reflectionForSupplierCredential.SetAgency(Branch);
        String Expected = Reflection.get("Expected");
//        String Actual = ElementActions.getInstance().getText(By.xpath("/html/body/div[1]/section/div/div[2]/div/div/div[4]/form[1]/div[10]/div[2]/div[1]/div/div[2]/div/div[1]/select/option[2]"));
//        Assert.assertEquals(Actual,Expected);

    }

    @Test(priority = 5, dataProvider = "SearchSupplierCredential")
    public void setEditSupplierCredential(Map<String, String> search){
        searchCity = new SearchCity_Page(driver);
        searchSupplierCredential = new SearchSupplierCredential_Page(driver);
        searchSupplier = new SearchSupplier_Page(driver);
        editSupplierCredential = new EditSupplierCredential_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickSupplierMenue()
                .clickSupplierCredencial();
        String CredentialName = search.get("CredentialName");
        String Supplier = search.get("Supplier");
        searchSupplierCredential.setSearchCredential(CredentialName,Supplier);
        searchSupplierCredential.setActive();
        editSupplierCredential.setEditSuppliercredential("Amada");
    }

    @Test(dataProvider = "AddBranch")
    public void CreateBranch(Map<String, String> Branch) throws InterruptedException {
        addNewBranch = new AddNewBranch_Page(driver);
        String Brand = Branch.get("Brand");
        String Country =Branch.get("Country");
        String OperatingCountry = Branch.get("OperatingCountry");
        String BranchName = Branch.get("BranchName");
        String State = Branch.get("State");
        String City = Branch.get("City");
        String Address = Branch.get("Address");
        String PostOffice = Branch.get("PostOffice");
        String PhoneNo = Branch.get("PhoneNo");
        String EmailID = Branch.get("EmailID");
        String Password = Branch.get("Password");
        String Name = Branch.get("Name");
        String Email = Branch.get("Email");
        String Phone = Branch.get("Phone");
//        String Department = Branch.get("Department");
        String Creditlimit = Branch.get("Creditlimit");
        String Topup = Branch.get("Topup");
        String Creditterm = Branch.get("Creditterm");
        String Description = Branch.get("Description");
        String Managername = Branch.get("Managername");
        String Supplierdescription = Branch.get("Supplierdescription");

        Thread.sleep(1000000);


    }

    @Test(dataProvider = "Booking")
    public void setSearchBooking(Map<String,String> Book) throws InterruptedException {
        searchBooking = new SearchBookingBranch(driver);
        String SelectBranch = Book.get("SelectBranch");
        String StartingFrom = Book.get("StartingFrom");
        String GoingTo = Book.get("GoingTo");
        //searchBooking.setBooking(SelectBranch,StartingFrom,GoingTo);

    }
    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

