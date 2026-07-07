package AdminPages.Admin.Staff;

import AdminPages.Admin.AdminMenu;
import AdminPages.Admin.Staff_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
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

public class ActionStaff_TC {

    private Staff_Page staff;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    String UserName = "";
    String Branch = "";
    String Department = "";
    String StaffName = "";

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
        new AdminMenu(driver).openSubAdmin().openStaff();
    }

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void CreateStaff(Map<String,String> st) throws InterruptedException {
        staff = new Staff_Page(driver);
       // staff.Clickonadmin();
//        staff.ClickonStuff();
        staff.addstuff();
        String Usertype =st.get("Usertype");
        String SearchOperatingCountry=st.get("SearchOperatingCountry");
        Branch = st.get("SearchBranch");
        Department =st.get("SearchDepartment");
        String SearchRole = st.get("SearchRole");
        StaffName = st.get("EmployeeName");
        String EmployeeEmail = st.get("EmployeeEmail");
        String EmployeePhoneNo = st.get("EmployeePhoneNo");
        String EmployeeSecondaryNo = st.get("EmployeeSecondaryNo");
        UserName = st.get("UserName");
        String ApprovalList = st.get("ApprovalList");
        staff.AddStuff(Usertype,SearchOperatingCountry,Branch,Department,SearchRole
                ,EmployeeEmail,EmployeePhoneNo,EmployeeSecondaryNo,ApprovalList);
        staff.YesUndercut();
        Thread.sleep(3000);
        staff.setInactive();
        staff.fill(StaffName,UserName,Branch,Department);
        staff.ThumpUp("Approved");
        Thread.sleep(3000);
    }
//        String Expected = "Added Successfully";
//        String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
//        Assert.assertEquals(Actual,Expected);




//    @Test(priority = 2, dataProvider = "SearchStaffData")
//
//    public void Searchstaff(Map<String,String> st) throws InterruptedException {
//        staff = new SearchStaff(driver);
//        staff.Clickonadmin();
//        staff.ClickonStuff();
//        String StaffName = st.get("StaffName");
//        String UserName = st.get("UserName");
//        String Branch = st.get("Branch");
//        String Department = st.get("Department");
//        staff.setBoth();
//        staff.fill(StaffName,UserName,Branch,Department);
//
//    }

//    @Test(priority = 2)
//    public void Actionstaff() throws InterruptedException {
//        staff = new SearchStaff_Page(driver);
//        actionStaff = new ActionStaff_Page(driver);
//        staff.Clickonadmin();
//        staff.ClickonStuff();
//
//        staff.setInactive();
//        staff.fill(StaffName,UserName,Branch,Department);
//        actionStaff.setThumpUp("Approved");
//        Thread.sleep(3000);
//    }
//    @Test(priority = 3)
//    public void Actionstaffassert() throws InterruptedException {
//        staff = new SearchStaff_Page(driver);
//        actionStaff = new ActionStaff_Page(driver);
//        staff.Clickonadmin();
//        staff.ClickonStuff();
//        staff.setActive();
//        staff.fill(StaffName,UserName,Branch,Department);
//
//        actionStaff.performAssertions();
//
//
//    }
public void AddStaff(Map<String,String> st) throws InterruptedException {
    staff = new Staff_Page(driver);
    // staff.Clickonadmin();
//        staff.ClickonStuff();
    new AdminMenu(driver).openSubAdmin().openStaff();
    staff.addstuff();
    String Usertype =st.get("Usertype");
    String SearchOperatingCountry=st.get("SearchOperatingCountry");
    Branch = st.get("SearchBranch");
    Department =st.get("SearchDepartment");
    String SearchRole = st.get("SearchRole");
    StaffName = st.get("EmployeeName");
    String EmployeeEmail = st.get("EmployeeEmail");
    String EmployeePhoneNo = st.get("EmployeePhoneNo");
    String EmployeeSecondaryNo = st.get("EmployeeSecondaryNo");
    UserName = st.get("UserName");
    String ApprovalList = st.get("ApprovalList");
    staff.AddStuff(Usertype,SearchOperatingCountry,Branch,Department,SearchRole
            ,EmployeeEmail,EmployeePhoneNo,EmployeeSecondaryNo,ApprovalList);
    staff.YesUndercut();
    Thread.sleep(3000);
    staff.setInactive();
    staff.fill(StaffName,UserName,Branch,Department);
    staff.ThumpUp("Approved");
    Thread.sleep(3000);
}

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}