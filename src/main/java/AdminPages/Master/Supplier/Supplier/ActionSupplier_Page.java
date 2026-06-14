package AdminPages.Master.Supplier.Supplier;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class ActionSupplier_Page {

    public ActionSupplier_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;

    By Btn_Edit = By.xpath("//i[@class=\"pi pi-pencil\"]");
    By Btn_Approve = By.xpath("//i[@class=\"pi pi-thumbs-up\"]");
    By Txt_RemarksApprove = By.xpath("//textarea[@placeholder=\"Type your remarks description...\"]");
    By Btn_Submit = By.xpath("//span[contains(text(),'Submit')]");
    By Btn_Reject = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Txt_RemarksReject = By.xpath("//textarea[@placeholder=\"remarks...\"]");
    By Lst_ProductType = By.xpath("//p-dropdown[.//input[@id=\"id-ProductsType\"]]");
    By Lst_Country = By.xpath("//input[@id=\"id-Country\"]");
    By Txt_Email = By.xpath("//input[@id=\"id-1-Email\"]");
    By Lst_City = By.xpath("//input[@id=\"id-City\"]");
    By Txt_PinCode = By.xpath("//input[@id=\"id-PINcode\"]");
    By Btn_ClearCountry = By.xpath("//INPUT[@id='id-Country']");
    By Lst_WhitelistBoard = By.xpath("//input[@id=\"id-WhitelistAirports\"]");
    By Btn_Approved = By.xpath("(//i[@class=\"pi pi-thumbs-up\"])[1]");

   public void setEditBtn(String producttype , String country , String email , String city , String pincode , String whitelistboard,String whitelistboard2,String remarks) throws InterruptedException {
     driver.element().click(Btn_Edit);
     driver.element().click(Lst_ProductType);
     By option = By.xpath(String.format("//span[text()='%s']", producttype));
     driver.element().click(option);
     driver.element().click(Btn_ClearCountry);
       Thread.sleep(5000);
     driver.element().type(Lst_Country,country);
//     Thread.sleep(5000);
     driver.element().select(Lst_Country,"Egypt");
//     By option1 = By.xpath(String.format("//span[text()='%s']", country));
//     driver.element().click(option1);
     driver.element().type(Txt_Email,email);
     driver.element().clear(Lst_City);
       Thread.sleep(5000);
     driver.element().type(Lst_City,city);
//       Thread.sleep(5000);
       driver.element().select(Lst_City,"Cairo");
//     By option2 = By.xpath(String.format("//span[text()='%s']", city));
//     driver.element().click(option2);
     driver.element().type(Txt_PinCode,pincode);
     driver.element().type(Lst_WhitelistBoard,whitelistboard);
     Thread.sleep(10000);
     driver.element().select(Lst_WhitelistBoard,"Ada Municipal").click(Lst_WhitelistBoard);
     Thread.sleep(10000);
     driver.element().type(Lst_WhitelistBoard,whitelistboard2);
     Thread.sleep(10000);
     driver.element().select(Lst_WhitelistBoard,"Cairo").click(Lst_WhitelistBoard);
     driver.element().type(Txt_RemarksApprove,remarks);
     driver.element().click(Btn_Approved);
     Thread.sleep(6000);
   }

   public void setApprove(String remarks){
       driver.element().click(Btn_Approved);
       driver.element().type(Txt_RemarksReject,remarks);
       driver.element().click(Btn_Submit);

   }

   public void setReject(String remarks){

       driver.element().click(Btn_Reject);
       driver.element().type(Txt_RemarksReject,remarks);
       driver.element().click(Btn_Submit);

   }
}
