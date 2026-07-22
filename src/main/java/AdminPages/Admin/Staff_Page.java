package AdminPages.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import utilities.FakerSingleton;

import java.io.File;

public class Staff_Page {

    public static final int MILLIS = 1000;
    public static String Branch;
    public static String Departement;

    public Staff_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    public static String username;
    public static String employeeName;
    SHAFT.GUI.WebDriver driver ;

    By Btn_AddStuff = By.xpath("//span[@style=\"margin-inline-end: 10px;\"]");
    By Lst_Usertype = By.xpath("//p-dropdown[.//input[@id=\"id-Usertype\"]]");
    By Lst_OperatingCountry = By.xpath("//p-multiselect[.//input[@name='Operating country']]");
    By Lst_AddBranch = By.xpath("//p-multiselect[.//input[@id=\"id-Branch\"]]");
    By Lst_SearchBranch = By.xpath("//p-dropdown[.//input[@id=\"id-Branch\"]]");
    By Lst_Department = By.xpath("//p-multiselect[.//input[@id=\"id-Department\"]]");
    By Lst_DepartmentFirst = By.xpath("(//p-multiselectitem/li)[1]");
    By Lst_Role = By.xpath("(//p-dropdown)[2]");
    By Icn_ExitRole = By.xpath("//span[@id=\"pr_id_6_label\"]");
    By Txt_EmployeeName = By.xpath("//input[@id=\"id-Employeename\"]");
    By Txt_EmployeeEmail = By.xpath("//input[@id=\"id-Employeeemail\"]");
    By Txt_EmployeePhoneNo = By.xpath("//input[@id=\"Employee phone number\"]");
    By Txt_EmployeeSecondaryNo = By.xpath("//input[@id=\"Secondary phone number\"]");
    By Txt_AddUserName = By.xpath("//input[@id=\"id-Username\"]");
    By Lst_ApproverList = By.xpath("//p-dropdown[.//input[@id=\"id-ApproverList\"]]");
    By Lst_ImportPNROption = By.xpath("//div[@class=\"p-checkbox-box\"]");
    By Rbtn_YesUndercut = By.xpath("//p-radiobutton[.//input[@id=\"id-Optiontoundercut-Yes\"]]");
    By Rbtn_NoUndercut = By.xpath("//p-radiobutton[.//input[@id=\"id-Optiontoundercut-No\"]]");
    By Btn_SendforApproval = By.xpath("//button[@type=\"submit\"]");
    ///
    By Btn_Admin = By.xpath("//a[@href=\"/admin\"]");
    By Btn_Staff = By.xpath("//a[@href=\"/admin/staff\"]");
    By Txt_StaffName = By.xpath("//input[@id=\"id-StaffName\"]");
    By Btn_Search = By.xpath("//span[@class=\"p-button-label\"]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Txt_Remarks1 = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Txt_SearchUserName = By.xpath("//input[@id=\"id-UserName\"]");
    ///

    By Btn_Edit = By.xpath("//i[@class=\"pi pi-pencil\"]");
    By Btn_ThumpUp = By.xpath("//i[@class=\"pi pi-thumbs-up\"]");
    By Btn_ThumpDown = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Txt_RemarksUp = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_Submitup =  By.xpath("//span[contains(text(),'Submit')]");
    By Txt_RemarkDown = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_SubmitDown =  By.xpath("//span[contains(text(),'Submit')]");
    By Txt_Remarks = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_SendForApproval = By.xpath("//button[@type=\"submit\"]");

    public void addstuff() throws InterruptedException {
        driver.element().click(Btn_AddStuff);
        Thread.sleep(MILLIS);
    }
    public void addEmployeeName(String employeeName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File("src/test/resources/testDataFiles/ActionStaff.json");

        ObjectNode json = (ObjectNode) mapper.readTree(file);

        json.put("EmployeeName", employeeName);

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

    public void addUserName(String username) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File("src/test/resources/testDataFiles/ActionStaff.json");

        ObjectNode json = (ObjectNode) mapper.readTree(file);

        json.put("UserName", username);

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

    public void AddStuff(String usertype ,String searchoperatingcountry,String searchbranch , String searchdepartment
             ,String searchrole,String employeeemail,String employeephoneno,String
                                    employeesecno,String approverlist ) throws Exception {
        System.out.println("Start Method : Adding a new staff");
        driver.element().click(Lst_Usertype);
        By option11 = By.xpath(String.format("//span[contains(text(),'%s')]", usertype));
        driver.element().click(option11);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_OperatingCountry);
        By option = By.xpath(String.format("//span[contains(text(), '%s')]", searchoperatingcountry));
        driver.element().click(option);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_AddBranch);
        Branch =  driver.element().getText(Lst_DepartmentFirst);
        By option1 = By.xpath(String.format("//span[contains(text(), '%s')]", searchbranch));
        driver.element().click(Lst_DepartmentFirst);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_Department);
        Departement = driver.element().getText(Lst_DepartmentFirst);
        By option2 = By.xpath(String.format("//span[contains(text(), '%s')]", searchdepartment));
        driver.element().click(Lst_DepartmentFirst);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_Role);
        By option3 = By.xpath(String.format("//span[contains(text(), '%s')]", searchrole));
        driver.element().click(option3);
        //driver.element().click(Icn_ExitRole);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeeName, FakerSingleton.PassengerFactory.firstName());
        Thread.sleep(MILLIS);
        employeeName = driver.element().getText(Txt_EmployeeName);
        addEmployeeName(employeeName);
        driver.element().type(Txt_EmployeeEmail,employeeemail);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeePhoneNo,employeephoneno);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeeSecondaryNo,employeesecno);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_AddUserName, FakerSingleton.PassengerFactory.firstName());
        Thread.sleep(MILLIS);
        username = driver.element().getText(Txt_AddUserName);
        addUserName(username);
        driver.element().click(Lst_ApproverList);
        By option4 = By.xpath(String.format("//span[contains(text(), '%s')]", approverlist));
        driver.element().click(option4);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_ImportPNROption);
    }


    public String ActualCreate()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }

    ////SearchStaff///////////////////////////////////



    public void Clickonadmin() throws InterruptedException {

        driver.element().click(Btn_Admin);
        Thread.sleep(3000);

    }

    public void ClickonStuff() throws InterruptedException {
        driver.element().click(Btn_Staff);
        Thread.sleep(3000);
    }


    public void fill( String s , String u  , String s1  , String s2  ) throws InterruptedException {

        driver.element().type(Txt_StaffName,s);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_SearchUserName,u);
        driver.element().click(Lst_SearchBranch);
        By option1 = By.xpath(String.format("//span[contains(text(), '%s')]",s1 ));
        driver.element().click(option1);
        driver.element().click(Lst_Department);
        By option2 = By.xpath(String.format("//span[contains(text(), '%s')]",s2 ));
        driver.element().click(option2);
//        driver.element().click(ExitDepartment);
        Thread.sleep(3000);
        driver.element().click(Btn_Search);


    }

    public void Remarks(String remarks){
        driver.element().type(Txt_Remarks1,remarks);
    }
    public void setActive(){
        driver.element().click(Rbtn_Active);
    }

    public void setInactive(){
        driver.element().click(Rbtn_Inactive);
    }

    public void setBoth(){
        driver.element().click(Rbtn_Both);
    }

    public String Actual_()
    {
        String  S =driver.element().getText(By.xpath("//td[@class=\"message\"]"));
        return S;
    }

    ////ActionStaff//////////////////////



    public void ActionStuff(String usertype ,String searchoperatingcountry,String searchbranch , String searchdepartment
            ,String searchrole,String employeename,String employeeemail,String employeephoneno,String
                                       employeesecno,String username,String approverlist,String remark ) throws InterruptedException {

        System.out.println("Start Method : Editing a stuff ");
        // driver.element().click(BTN_Edit);
        driver.element().click(Lst_Usertype);
        By option11 = By.xpath(String.format("//span[contains(text(),'%s')]", usertype));
        driver.element().click(option11);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_OperatingCountry);
        By option = By.xpath(String.format("//span[contains(text(), '%s')]", searchoperatingcountry));
        driver.element().click(option);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_AddBranch);
        By option1 = By.xpath(String.format("//span[contains(text(), '%s')]", searchbranch));
        driver.element().click(option1);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_Department);
        By option2 = By.xpath(String.format("//span[contains(text(), '%s')]", searchdepartment));
        driver.element().click(option2);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_Role);
        By option3 = By.xpath(String.format("//span[contains(text(), '%s')]", searchrole));
        driver.element().click(option3);
        driver.element().click(Icn_ExitRole);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeeName,employeename);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeeEmail,employeeemail);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeePhoneNo,employeephoneno);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_EmployeeSecondaryNo,employeesecno);
        Thread.sleep(MILLIS);
        driver.element().type(Txt_AddUserName,username);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_ApproverList);
        By option4 = By.xpath(String.format("//span[contains(text(), '%s')]", approverlist));
        driver.element().click(option4);
        Thread.sleep(MILLIS);
        driver.element().click(Lst_ImportPNROption);


        driver.element().click(Btn_SendforApproval);
    }
    public void setRemarks(String remarks){
        driver.element().type(Txt_Remarks,remarks);
    }
    public void setEdit(){
        driver.element().click(Btn_Edit);
    }
    public void ThumpUp(String remarksup){
        driver.element().click(Btn_ThumpUp);
        driver.element().type(Txt_RemarksUp,remarksup);
        driver.element().click(Btn_Submitup);
    }

    public void ThumpDown(String remarksdown){
        driver.element().click(Btn_ThumpDown);
        driver.element().type(Txt_RemarkDown,remarksdown);
        driver.element().click(Btn_SubmitDown);
    }


    public void YesUndercut(){
        System.out.println("Start Method : click on UnderCut");
        driver.element().click(Rbtn_YesUndercut);
        driver.element().click(Btn_SendForApproval);
    }

    public void NoUndercut(){
        System.out.println("Start Method : click on NoUnderCut");
        driver.element().click(Rbtn_NoUndercut);
        driver.element().click(Btn_SendForApproval);
    }
    public String ActualUpdate()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Updated Successfully\"]"));
        return S;
    }


}
