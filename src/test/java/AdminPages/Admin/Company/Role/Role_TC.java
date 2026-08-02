package AdminPages.Admin.Company.Role;

import AdminPages.Admin.AdminMenu;
import AdminPages.Admin.Staff.ActionStaff_TC;
import AdminPages.Admin.Staff_Page;
import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.*;
import utilities.DataUtils;

import java.util.HashMap;
import java.util.Map;

public class Role_TC {
    private SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;
    private Staff_Page addStaff;

    @BeforeMethod
    public void login(){
        testData = new SHAFT.TestData.JSON("Role.json");
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).superAdminLogin();
        new AdminMenu(driver).openSubAdmin().Company().Role();
    }


    @Test
    public void verifyThatUserCanCreateRoleWithViewOnly() throws Exception {
        new Role_Page(driver).ClickAddRole()
                .EnterAddRoleName()
                .ClickIsViewAtAllFields()
                .ClickSendForApprove();
    }
    @Test
    public void CreateRoleForCICD() throws Exception {
        new Role_Page(driver).ClickAddRole()
                .EnterAddRoleName()
                .createRoleForCICD()
                .ClickSendForApprove()
                .EnterRoleName(testData.getTestData("validData.roleName"))
                .ClickInactive()
                .ClickSearch()
                .VerifyTheRoleName()
                .ClickApprove()
                .EnterRemarkText(testData.getTestData("validData.Remark"))
                .ClickSubmit();
        ;
    }
    @Test
    public void verifyThatUserCanCreateRoleWithEditOnly() throws Exception {
        new Role_Page(driver).ClickAddRole()
                .EnterAddRoleName()
                .ClickIsEditAtAllFields()
                .ClickSendForApprove();
    }
    @Test
    public void verifyThatUserCanCreateRoleWithAprrovalOnly() throws Exception {
        new Role_Page(driver).ClickAddRole()
                .EnterAddRoleName()
                .ClickIsApproveAtAllFields()
                .ClickSendForApprove()
        ;
    }
//    @Test
//    public void verifyThatUserCanSearchWithRoleName(){
//        new Role_Page(driver).EnterRoleName(testData.getTestData("validData.roleName"))
//                .ClickInactive()
//                .ClickSearch()
//                .VerifyTheRoleName();
//    }
    @Test
    public void verifyThatUserCanSearchWithRoleNameAndApproveTheRole(){
        new Role_Page(driver).EnterRoleName(testData.getTestData("validData.roleName"))
                .ClickInactive()
                .ClickSearch()
                .VerifyTheRoleName()
                .ClickApprove()
                .EnterRemarkText(testData.getTestData("validData.Remark"))
                .ClickSubmit();
    }

    @Test
    public void CreateRoleWithViewPermessionAndCreateStaffAndLoginWithIt() throws Exception {
        addStaff = new Staff_Page(driver);
        Map<String,String> st = new HashMap<>();
        st.put("Usertype", testData.getTestData("validData.Usertype"));
        st.put("SearchOperatingCountry" , testData.getTestData("validData.SearchOperatingCountry"));
        st.put("SearchBranch", testData.getTestData("validData.SearchBranch"));
        st.put("SearchDepartment", testData.getTestData("validData.SearchDepartment"));
        st.put("SearchRole", testData.getTestData("validData.SearchRole"));
        st.put("EmployeeName", testData.getTestData("validData.EmployeeName"));
        st.put("EmployeeEmail", testData.getTestData("validData.EmployeeEmail"));
        st.put("EmployeePhoneNo", testData.getTestData("validData.EmployeePhoneNo"));
        st.put("EmployeeSecondaryNo", testData.getTestData("validData.EmployeeSecondaryNo"));
        st.put("UserName", testData.getTestData("validData.UserName"));
        st.put("ApprovalList", testData.getTestData("validData.ApprovalList"));

        verifyThatUserCanCreateRoleWithViewOnly();
        verifyThatUserCanSearchWithRoleNameAndApproveTheRole();
        ActionStaff_TC actionStaff = new ActionStaff_TC();
//        actionStaff.driver = this.driver;
        actionStaff.AddStaff(st);
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
    @Test(dependsOnMethods = "CreateRoleWithViewPermessionAndCreateStaffAndLoginWithIt")
    public void VerifyThatUserHasHisPermissionOnly() throws InterruptedException {
        Thread.sleep(30000);
        new LogIn_Page(driver).ClickUser(testData.getTestData("validData.Userame"),(testData.getTestData("validData.Password")));
        new LogIn_Page(driver).ClickOnLoginButton();
        Thread.sleep(1000);
        driver.browser().refreshCurrentPage();
        Thread.sleep(1000);
        new AdminMenu(driver).openSubAdmin().OperatingCountry();
        new Role_Page(driver).verifyUserHasPermissionsViewOnly();
    }
    @Test
    public void CreateRoleWithEditAndVeiwPermessionAndCreateStaffAndLoginWithIt() throws Exception {
        addStaff = new Staff_Page(driver);
        Map<String,String> st = new HashMap<>();
        st.put("Usertype", testData.getTestData("validData.Usertype"));
        st.put("SearchOperatingCountry" , testData.getTestData("validData.SearchOperatingCountry"));
        st.put("SearchBranch", testData.getTestData("validData.SearchBranch"));
        st.put("SearchDepartment", testData.getTestData("validData.SearchDepartment"));
        st.put("SearchRole", testData.getTestData("validData.SearchRole"));
        st.put("EmployeeName", testData.getTestData("validData.EmployeeName"));
        st.put("EmployeeEmail", testData.getTestData("validData.EmployeeEmail"));
        st.put("EmployeePhoneNo", testData.getTestData("validData.EmployeePhoneNo"));
        st.put("EmployeeSecondaryNo", testData.getTestData("validData.EmployeeSecondaryNo"));
        st.put("UserName", testData.getTestData("validData.UserName"));
        st.put("ApprovalList", testData.getTestData("validData.ApprovalList"));

        verifyThatUserCanCreateRoleWithEditOnly();
        verifyThatUserCanSearchWithRoleNameAndApproveTheRole();
        ActionStaff_TC actionStaff = new ActionStaff_TC();
//        actionStaff.driver = this.driver;
        actionStaff.AddStaff(st);
        new LogIn_Page(driver).ClickOnLogOuTButton();
        Thread.sleep(30000);
        new LogIn_Page(driver).ClickUser(testData.getTestData("validData.Userame"),(testData.getTestData("validData.Password")));
        new LogIn_Page(driver).ClickOnLoginButton();
        Thread.sleep(1000);
        driver.browser().refreshCurrentPage();
        Thread.sleep(1000);
        new AdminMenu(driver).openSubAdmin().Company().OperatingCountry();
        new Role_Page(driver).verifyUserHasPermissionsViewAndEdit();
    }
    @Test
    public void CreateRoleWithApproveAndVeiwPermessionAndCreateStaffAndLoginWithIt() throws Exception {
        addStaff = new Staff_Page(driver);
        Map<String,String> st = new HashMap<>();
        st.put("Usertype", testData.getTestData("validData.Usertype"));
        st.put("SearchOperatingCountry" , testData.getTestData("validData.SearchOperatingCountry"));
        st.put("SearchBranch", testData.getTestData("validData.SearchBranch"));
        st.put("SearchDepartment", testData.getTestData("validData.SearchDepartment"));
        st.put("SearchRole", testData.getTestData("validData.SearchRole"));
        st.put("EmployeeName", testData.getTestData("validData.EmployeeName"));
        st.put("EmployeeEmail", testData.getTestData("validData.EmployeeEmail"));
        st.put("EmployeePhoneNo", testData.getTestData("validData.EmployeePhoneNo"));
        st.put("EmployeeSecondaryNo", testData.getTestData("validData.EmployeeSecondaryNo"));
        st.put("UserName", testData.getTestData("validData.UserName"));
        st.put("ApprovalList", testData.getTestData("validData.ApprovalList"));
        verifyThatUserCanCreateRoleWithAprrovalOnly();
        verifyThatUserCanSearchWithRoleNameAndApproveTheRole();
        ActionStaff_TC actionStaff = new ActionStaff_TC();
//        actionStaff.driver = this.driver;
        actionStaff.AddStaff(st);
        new LogIn_Page(driver).ClickOnLogOuTButton();
        Thread.sleep(30000);

        new LogIn_Page(driver).ClickUser(testData.getTestData("validData.Userame"),(testData.getTestData("validData.Password")));
        //new LogIn_Page(driver).ClickUser(mUser.toString(),mPass.toString());
        new LogIn_Page(driver).ClickOnLoginButton();
        Thread.sleep(1000);
        driver.browser().refreshCurrentPage();
        Thread.sleep(1000);
        new AdminMenu(driver).openSubAdmin().OperatingCountry();
        new Role_Page(driver).verifyUserHasPermissionsViewAndApprove();
    }
//    @Test
//    public void send(){
//        String s = GmailReaderUtil.getLatestEmail("ahmedref124@gmail.com","gljl enks vept uiwv");
//        System.out.println(s);
//    }
@AfterMethod
public void navigateBackToURL() {
    new LogIn_Page(driver).ClickOnLogOuTButton();
//    driver.quit();

}
}
