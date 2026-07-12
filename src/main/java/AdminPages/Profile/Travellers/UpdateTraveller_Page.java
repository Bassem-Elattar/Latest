package AdminPages.Profile.Travellers;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class UpdateTraveller_Page {
    public UpdateTraveller_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By Btn_Update = By.xpath("//i[@class=\"pi pi-pencil\"]");
    By Lst_BranchName = By.xpath("//p-dropdown[.//input[@id=\"id-BranchName\"]]");
    By Lst_Title = By.xpath("//p-dropdown[.//input[@id=\"id-Title\"]]");
    By Txt_Firstname = By.xpath("//input[@id=\"id-FirstName\"]");
    By Txt_LastName = By.xpath("//input[@id=\"id-LastName\"]");
    By Dpick_Date = By.xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-add-traveller/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/div[1]/div[2]/ndc-fg-input[6]/ndc-fg-date-picker-input/span/p-calendar/span/button");
    By Txt_Email = By.xpath("//input[@id=\"id-EmailID\"]");
    By Lst_Nationality = By.xpath("//p-dropdown[.//input[@id=\"id-Nationality\"]]");
    By Lst_Gender = By.xpath("//p-dropdown[.//input[@id=\"id-Gender\"]]");
    By Txt_Phonenumber = By.xpath("//input[@id=\"phone number\"]");
    By Txt_Address = By.xpath("//input[@id=\"id-Address\"]");
    By Btn_Save = By.xpath("//button[@type=\"submit\"]");

    ////////// Passport Details/////////////

    By Txt_PassportNo = By.xpath("//input[@id=\"id-PassportNumber\"]");
    By Dpick_ExpDate = By.xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div/div/ndc-add-traveller/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/div[2]/div[2]/ndc-fg-input[2]/ndc-fg-date-picker-input/span/p-calendar/span/button");
    By Lst_CountryofIssue = By.xpath("(//p-dropdown[.//input[@id=\"id-CountryOfIssue\"]])[1]");


    public void setPersonalDetail(String branch, String title , String firstname , String lastname , String date , String
            email, String nationality ,String gender , String phonenumber , String address)
    {
        driver.element().click(Btn_Update);
        driver.element().click(Lst_BranchName);
        By option = By.xpath(String.format("//span[text()='%s']", branch));
        driver.element().click(option);

//        driver.element().click(Title);
//        By option1 = By.xpath(String.format("//span[text()='%s']", title));
//        driver.element().click(option1);

//        driver.element().type(Firstname,firstname);
//        driver.element().type(LastName,lastname);

//        driver.element().click(Date);
//        By option4 = By.xpath(String.format("//span[text()='%s']", date));
//        driver.element().click(option4);
//
//        driver.element().type(Email,email);
//
//        driver.element().click(Nationality);
//        By option2 = By.xpath(String.format("//span[text()='%s']", nationality));
//        driver.element().click(option2);

//        driver.element().click(Gender);
//        By option3 = By.xpath(String.format("//span[text()='%s']", gender));
//        driver.element().click(option3);

//        driver.element().type(Phonenumber,phonenumber);
        driver.element().type(Txt_Address,address);


    }
    public void setPassportdetails(String passportno , String expdate , String countryissue){
//        driver.element().type(PassportNo,passportno);
//        driver.element().click(ExpDate);
//        By option4 = By.xpath(String.format("//span[text()='%s']", expdate));
//        driver.element().click(option4);

        driver.element().click(Lst_CountryofIssue);
        By option3 = By.xpath(String.format("//span[text()='%s']", countryissue));
        driver.element().click(option3);
    }

    public void setSave(){
        driver.element().click(Btn_Save);
    }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Updated Successfully\"]"));
        return S;
    }
}
