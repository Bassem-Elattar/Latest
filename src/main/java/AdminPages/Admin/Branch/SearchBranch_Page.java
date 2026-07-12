package AdminPages.Admin.Branch;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class SearchBranch_Page {
    public SearchBranch_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    public By Branch = By.xpath("//*[@id=\"accordion\"]/div[2]/a/div");
    By BranchCode = By.xpath("//input[@id=\"id-Branchcode\"]");
    By BranchName = By.xpath("//*[@id=\"groupName\"]");
    By SelectCountry = By.xpath("//p-multiselect[.//input[@id=\"id-Country\"]]");
    By SearchCountry = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    By ExitCountry = By.xpath("//span[@class=\"p-multiselect-close-icon pi pi-times\"]");
    By SelectState = By.xpath("//p-multiselect[.//input[@id=\"id-State\"]]");
    By SearchState = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    By ExitState = By.xpath("//span[@class=\"p-multiselect-close-icon pi pi-times\"]");
    By SelectCity = By.xpath("//p-multiselect[.//input[@id=\"id-City\"]]");
    By SearchCity = By.xpath("//input[@class=\"p-multiselect-filter p-inputtext p-component\"]");
    By ExitCity = By.xpath("//span[@class=\"p-multiselect-close-icon pi pi-times\"]");
    By Inactive = By.xpath("//*[@id=\"formId\"]/div/div[3]/div[2]/div/ul/li[1]/label/label");
    By Active = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Active\"]]");
    By Both = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Both\"]]");
    By search = By.xpath("//*[@id=\"searchButton\"]");
    By Btn_ThumpUp = By.xpath("/html/body/div/section/div/div[2]/div/div/div[4]/form/div/div[5]/div/table/tbody/tr[1]/td[7]/a[2]/img");
    By Btn_ThumpDown = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Txt_RemarksUp = By.xpath("//div[1]/section//div[4]/form//div[5]//tr/td[7]//div[2]/textarea");
    By Btn_Submitup =  By.xpath("//div[1]/section//div[2]//div[4]/form//div[5]//tr/td[7]//div[3]/button[1]");
    public void setsearchbranch(String branchcode,String branchname,String searchcountry,String searchstate,
                                String searchcity){
        driver.element().click(Branch);
        // driver.element().type(BranchCode,branchcode);
        driver.element().type(BranchName,branchname);

//        driver.element().click(SelectCountry);
//        driver.element().select(SearchCountry,searchcountry);
//        driver.element().click(ExitCountry);
//        driver.element().click(SelectState);
//        driver.element().select(SearchState,searchstate);
//        driver.element().click(ExitState);
//        driver.element().click(SelectCity);
//        driver.element().select(SearchCity,searchcity);
//        driver.element().click(ExitCity);

    }

    public void setActive(){
        driver.element().click(Active);
    }
    public void search(){
        driver.element().click(search);
    }
    public void setInactive(){
        driver.element().click(Inactive);
    }

    public void setBoth(){
        driver.element().click(Both);
    }

    public void setThumpUp(String remarksup){
        driver.element().click(Btn_ThumpUp);
        // driver.element().click(Txt_RemarksUp);
        driver.element().type(Txt_RemarksUp,remarksup);
        driver.element().click(Btn_Submitup);
    }

    public void RouteBranch(){
        driver.element().click(Branch);
    }

    public void Txt_BranchCode(String TXT){
        driver.element().type(BranchCode,TXT);
    }

    public void Txt_BranchName(String TXT){
        driver.element().type(BranchName,TXT);
    }

    public void Lst_Country(String Select){
        driver.element().select(SelectCountry,Select);
    }

    public void Lst_State(String Select){
        driver.element().select(SelectState,Select);
    }

    public void Lst_City(String Select){
        driver.element().select(SelectCity,Select);
    }

    public void Btn_Inactive(){
        driver.element().click(Inactive);
    }

    public void Btn_Active(){
        driver.element().click(Active);
    }

    public void Btn_Both(){
        driver.element().click(Both);
    }

    public void Btn_Search(){
        driver.element().click(search);
    }
}