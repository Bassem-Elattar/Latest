package AdminPages.Profile.Travellers;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class CreateTravellers_Page {

    public CreateTravellers_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By Btn_Add = By.xpath("//button[@routerlink=\"add\"]");
    By Lst_BranchName = By.xpath("//p-dropdown[.//input[@id=\"id-BranchName\"]]");
    By Lst_Title = By.xpath("//p-dropdown[.//input[@id=\"id-Title\"]]");
    By Txt_Firstname = By.xpath("//input[@id=\"id-FirstName\"]");
    By Txt_LastName = By.xpath("//input[@id=\"id-LastName\"]");
    By Btn_BackDate = By.xpath("(//p-calendar//div[contains(@class,'p-datepicker-header')]//button[1])[1]");
    By Dpick_Date = By.xpath("(//ndc-add-traveller//p-calendar//button)[1]");
    By Txt_Email = By.xpath("//input[@id=\"id-EmailID\"]");
    By Lst_Nationality = By.xpath("//p-dropdown[.//input[@id=\"id-Nationality\"]]");
    By Lst_Gender = By.xpath("//p-dropdown[.//input[@id=\"id-Gender\"]]");
    By Txt_Phonenumber = By.xpath("//input[@id=\"phone number\"]");
    By Txt_Address = By.xpath("//input[@id=\"id-Address\"]");
    By Btn_Save = By.xpath("//button[@type=\"submit\"]");
    By Year = By.xpath("//button[normalize-space()='2026']");
    ////////// Passport Details/////////////
    By Txt_PassportNo = By.xpath("//input[@id=\"id-PassportNumber\"]");
    By Dpick_ExpDate = By.xpath("(//ndc-add-traveller//p-calendar//button)[2]");
    By Lst_CountryofIssue = By.xpath("(//p-dropdown[.//input[@id=\"id-CountryOfIssue\"]])[1]");


    public void setPersonalDetail(String branch, String title , String firstname , String lastname , String year,String month , String From , String
                                  email, String nationality ,String gender , String phonenumber , String address) throws InterruptedException {
        driver.element().click(Btn_Add);
        driver.element().click(Lst_BranchName);
        By option = By.xpath(String.format("//span[text()='%s']", branch));
        driver.element().click(option);

        driver.element().click(Lst_Title);
        By option1 = By.xpath(String.format("//span[text()='%s']", title));
        driver.element().click(option1);

        driver.element().type(Txt_Firstname,firstname);
        driver.element().type(Txt_LastName,lastname);

//        driver.element().click(Date);
//        driver.element().click(BackDate);
//        Thread.sleep(3000);

        driver.element().click(Dpick_Date);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);


        driver.element().type(Txt_Email,email);

        driver.element().click(Lst_Nationality);
        By option2 = By.xpath(String.format("//span[text()='%s']", nationality));
        driver.element().click(option2);

        driver.element().click(Lst_Gender);
        By option3 = By.xpath(String.format("//span[text()='%s']", gender));
        driver.element().click(option3);

        driver.element().type(Txt_Phonenumber,phonenumber);
        driver.element().type(Txt_Address,address);


    }


    public void setPassportdetails(String passportno , String expdate , String countryissue){
        driver.element().type(Txt_PassportNo,passportno);
        driver.element().click(Dpick_ExpDate);
        By option4 = By.xpath(String.format("//span[text()='%s']", expdate));
        driver.element().click(option4);

        driver.element().click(Lst_CountryofIssue);
        By option3 = By.xpath(String.format("//span[text()='%s']", countryissue));
        driver.element().click(option3);
    }

    public void setSave(){
        driver.element().click(Btn_Save);
    }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }
}
