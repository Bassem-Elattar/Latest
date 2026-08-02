package AdminPages.Admin.Staff;

import AdminPages.Admin.AdminMenu;
import AdminPages.Admin.Staff_Page;
import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
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

public class CreateStaff_TC{

   // private Staff_Page addStaff;
    private Staff_Page staff;
    private LogIn_Page logIn;
    public SHAFT.GUI.WebDriver driver;
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

        new LogIn_Page(driver).superAdminLogin();
        new AdminMenu(driver).openSubAdmin().openStaff();

    }

    @Test(dataProvider = "JsonProvider")
    public void CreateStaff(Map<String, String> st) throws Exception {
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
        String Expected = "Added Successfully";
        Assert.assertEquals(staff.ActualCreate(),Expected);

//        addStaff = new Staff_Page(driver);
//       // addStaff.Clickonadmin();
////        addStaff.ClickonStuff();
//        addStaff.addstuff();
//        String Usertype =st.get("Usertype");
//        String SearchOperatingCountry=st.get("SearchOperatingCountry");
//        String SearchBranch = st.get("SearchBranch");
//        String SearchDepartment =st.get("SearchDepartment");
//        String SearchRole = st.get("SearchRole");
//        String EmployeeName = st.get("EmployeeName");
//        String EmployeeEmail = st.get("EmployeeEmail");
//        String EmployeePhoneNo = st.get("EmployeePhoneNo");
//        String EmployeeSecondaryNo = st.get("EmployeeSecondaryNo");
//        String UserName = st.get("UserName");
//        String ApprovalList = st.get("ApprovalList");
//        addStaff.AddStuff(Usertype,SearchOperatingCountry,SearchBranch,SearchDepartment,SearchRole
//                ,EmployeeName,EmployeeEmail,EmployeePhoneNo,EmployeeSecondaryNo,UserName,ApprovalList);
//        addStaff.YesUndercut();
//        String Expected = "Added Successfully";
//        Assert.assertEquals(addStaff.ActualCreate(),Expected);
    }


    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }



}