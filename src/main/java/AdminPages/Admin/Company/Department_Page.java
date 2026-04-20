package AdminPages.Admin.Company;
import com.shaft.driver.SHAFT;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;

public class Department_Page {
    public Department_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver ;
    By Btn_Company = By.xpath("//span[contains(text(),'Company')]");
    By Btn_Department = By.xpath("//a[@href=\"/admin/department\"]");
    By Btn_AddDepartment = By.xpath("//button[@routerlink=\"add\"]");
    By Txt_DepartmentName = By.xpath("//input[@id=\"id-Department\"]");
    By Lst_AssignedQueue = By.xpath("//p-multiselect[.//input[@id=\"id-Assignedqueue\"]]");
    By Btn_Exit = By.xpath("//button[@class=\"p-ripple p-element p-multiselect-close p-link ng-star-inserted\"]");
    By Btn_SendApprroval = By.xpath("//button[@type=\"submit\"]");
    By Btn_Cancel = By.xpath("//button[@type=\"reset\"]");
    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    By Rbtn_Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Rbtn_Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By Btn_Search = By.xpath("//button[@type=\"submit\"]");
    By Txt_departmentName = By.xpath("//input[@class=\"p-inputtext p-component p-element fg-input ng-untouched ng-pristine ng-valid\"]");
    By Btn_Edit = By.xpath("//i[@class=\"pi pi-pencil\"]");
    By Btn_ThumpUp = By.xpath("//i[@class=\"pi pi-thumbs-up\"]");
    By Btn_ThumpDown = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Btn_Trash = By.xpath("//i[@class=\"pi pi-trash\"]");
    By Txt_RemarksUp = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_Submitup =  By.xpath("//span[contains(text(),'Submit')]");
    By Txt_RemarkDown = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Btn_SubmitDown =  By.xpath("//span[contains(text(),'Submit')]");
    By Txt_EditDepartmentName = By.xpath("//input[@id=\"id-Department\"]");
    By Btn_AssignedQueue = By.xpath("//p-multiselect[//input[@id=\"id-Assignedqueue\"]]");
    By Lst_Search = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    By Btn_ExitSearch = By.xpath("//span[@class=\"p-multiselect-close-icon pi pi-times\"]");
    By Btn_SendAprroval = By.xpath("(//button[@type=\"submit\"])[3]");
    By Btn_InActiveCircle = By.xpath("//i[@class=\"pi pi-circle-fill\"]");
    By Btn_ActiveCircle = By.xpath("//i[@class=\"pi pi-circle\"]");
    By ActualResult = By.xpath("//div[@aria-label=\"Added Successfully\"]");

    public String getActualResult(){
        return driver.element().getText(ActualResult);
    }

    public void ClickSideMDepartment(){
        driver.element().click(Btn_Department);
    }
    public void AddDepartment(String departname , String searchqueue) throws InterruptedException {
        driver.element().click(Btn_AddDepartment);
        Thread.sleep(1000);
        driver.element().type(Txt_DepartmentName,departname);
        Thread.sleep(1000);
        driver.element().select(Lst_AssignedQueue,searchqueue);
       // Thread.sleep(1000);
       // driver.element().select(SearchQueue,searchqueue);
        Thread.sleep(1000);
        driver.element().click(Btn_Exit);
    }
    public void setSendApprroval(){
        driver.element().click(Btn_SendApprroval);

    }
    public void setCancel(){
        driver.element().click(Btn_Cancel);
    }

    public void clickOnCompany() {
        driver.element().click(Btn_Company);
    }

    public void OpenDepartment() {
        driver.element().click(Btn_Department);
    }
    public void Search() throws InterruptedException {
        Thread.sleep(3000);
        driver.element().click(Btn_Search);
    }
    public void searchDepartment(String DepName) {
        driver.element().type(Txt_departmentName, DepName);
    }
    public void ActiveStatus() {
        driver.element().click(Rbtn_Active);
    }
    public void InactiveStatus() {
        driver.element().click(Rbtn_Inactive);
    }
    public void BothStatus() {
        driver.element().click(Rbtn_Both);
    }

    ///ActionDepartment///////////////////////
    public void ActionDepartment(String remarks,String editdepartmentname )
    {
        System.out.println("Start Method: ActionDepartment (Edit Dep. Name and Add Remarks)");

        driver.element().click(Btn_Edit);
        driver.element().type(Txt_EditDepartmentName,editdepartmentname);
        driver.element().click(Btn_AssignedQueue);
        driver.element().click(Btn_ExitSearch);
        driver.element().type(Txt_RemarksUp,remarks);
        driver.element().click(Btn_SendAprroval);
    }
    public void ThumpUp(String remarksup){
        System.out.println("Start Method: Approve the Department");
        driver.element().click(Btn_ThumpUp);
        driver.element().type(Txt_RemarksUp,remarksup);
        driver.element().click(Btn_Submitup);
    }
    public void ThumpDown(String remarksdown){
        System.out.println("Start Method: Reject the Department");

        driver.element().click(Btn_ThumpDown);
        driver.element().type(Txt_RemarkDown,remarksdown);
        driver.element().click(Btn_SubmitDown);
    }
    public void InActiveCircle()
    {
        System.out.println("Start Method: InActive the department");
        driver.element().click(Btn_InActiveCircle);
    }

    public void ActiveCircle()
    {
        System.out.println("Start Method: Active the department ");
        driver.element().click(Btn_ActiveCircle);
    }
    public void Trash(){

        System.out.println("Start Method: Delete the department");
        driver.element().click(Btn_Trash);
    }
}