package AdminPages.Profile.ChangePassword;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;


public class ChangePassword_Page {
    public   boolean foundError ;

    public ChangePassword_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver ;
//    }

    By Btn_changePassword = By.linkText("Change Password");
    By Txt_yourOldPassword = By.xpath("/html/body/ndc-root/ndc-layout/div/div[3]/div[1]/div/ndc-change-password-page/div/div/form/div[1]/div[1]/p-password/div/input");
    By Txt_enterNewPasword = By.xpath("/html/body/ndc-root/ndc-layout/div/div[3]/div[1]/div/ndc-change-password-page/div/div/form/div[1]/div[2]/p-password/div/input");
    By Txt_enterConfirmPassword = By.xpath("/html/body/ndc-root/ndc-layout/div/div[3]/div[1]/div/ndc-change-password-page/div/div/form/div[1]/div[3]/p-password/div/input");
    By Btn_confirm = By.xpath("//button[@type='submit']");
    By Btn_eyeForOldPassword =By.xpath("(//i[@class='ng-tns-c220-6 pi pi-eye ng-star-inserted'])[1]");
    By Btn_eyeFornewPassword = By.xpath("(//i[@class='ng-tns-c220-7 pi pi-eye ng-star-inserted'])[1]");
    By Btn_eyeForConfirmNewPassword = By.xpath("(//i[@class='ng-tns-c220-8 pi pi-eye ng-star-inserted'])[1]");
    By Lst_Sign = By.xpath("//i[@class='pi pi-angle-down profile-name__icon']");
    // By ValidEmptyField=By.xpath("//span[@class=\"fg-error\"]");
    By ValidEmptyField=By.xpath("//small[@class='p-error']");
    By ValidMisMatchField=By.xpath("//small[@class='p-error']");
    By ValidMaxField=By.xpath("//div[2]//ul[1]//li[2]//small[1]");
    By ValidMinField=By.xpath("//div[2]//ul[1]//li[2]//small[1]");
    public  By InValidOldPassWord=By.xpath("//div[@aria-label=\"Password change was not successful: Old password is wrong, Please enter correct password\"]");
    public  By  ValidationInOldPass=By.xpath("//small[normalize-space()='New password cannot be same as old Password.']");

    public void ChangePasswordButton()
    {
        driver.element().click(Btn_changePassword);
    }
    public Void SelectOldPassword(String User)
    {
        driver.element().type(Txt_yourOldPassword,User);
        return null;

    }
    public String GetOldPassword ()
    {
        driver.element().click(Btn_eyeForOldPassword);
        String oldPassword = driver.element().getText(Txt_yourOldPassword);
        return oldPassword;
    }
    public String changeOldPassword(String oldPassword) {

        driver.element().clear(Txt_yourOldPassword);
        driver.element().type(Txt_yourOldPassword, oldPassword);
        return oldPassword;
    }

    public void SetNewPassword(String x)
    {
        driver.element().type(Txt_enterNewPasword,x);
    }

    public String GetNewPassword()
    {
        driver.element().click(Btn_eyeFornewPassword);
        String Newpassword = driver.element().getText(Txt_enterNewPasword);
        return Newpassword;
    }

    public void SetConfirmPassword (String x)
    {
        driver.element().type(Txt_enterConfirmPassword,x);
    }
    public String GetConfirmPassword ()
    {
        driver.element().click(Btn_eyeForConfirmNewPassword);
        String ConfirmPassword = driver.element().getText(Txt_enterConfirmPassword);
        return ConfirmPassword;
    }
    public void ClickConfirm ()
    {
        driver.element().click(Btn_confirm);
    }
    public void ClickDropdwnSign()
    {
        driver.element().click(Lst_Sign);
    }

    public boolean ChangePasswordValidation(String Message) {
        LogEntries logEntries = driver.getDriver().manage().logs().get(LogType.BROWSER);

        // Iterate through logs to find validation error
        boolean foundError = false;
        for (LogEntry entry : logEntries) {
            if (entry.getMessage().contains(Message)) {
                foundError = true;
                break;
            }
        }

        return  foundError;
// Assert validation error message is found in logs
        //  Assert.assertTrue("Validation error message not found in logs", foundError);
    }
}