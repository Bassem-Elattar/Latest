package AdminPages.RuleEngine.OfferPricing;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class UpdateOffer_Page {
    public UpdateOffer_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver;
    By Btn_Rule = By.xpath("//a[@href=\"/rule-engine\"]");
    By Btn_Offer = By.xpath("(//div[@class=\"panel-heading accordion-toggle\"])[5]");
    By Btn_Edit =By.xpath("(//div[@class=\"action exception-buttons ng-star-inserted\"])[1]");
    By Txt_Id = By.xpath("//input[@id=\"id-DiscountID\"]");
    By Btn_Upload = By.xpath("//button[@label=\"Primary\"]");
    By Txt_Discreption = By.xpath("//textarea[@placeholder=\"Description...\"]");
    By Btn_SendForaproval = By.xpath("(//button[@type=\"submit\"])[3]");
    By Btn_Cancel = By.xpath("//button[@type=\"reset\"]");
    By ClickDiscount = By.xpath("//ul[@class=\"p-autocomplete-items ng-tns-c46-4 ng-star-inserted\"]");
    By Btn_Approve = By.xpath("(//button[@type=\"submit\"])[1]");
    By Btn_Reject = By.xpath("(//button[@type=\"submit\"])[2]");
    By Txt_Remarks = By.xpath("//textarea[@placeholder=\"remarks...\"]");


    public void navigateToOfferSection() {
        driver.element().click(Btn_Rule)
                .click(Btn_Offer);
    }

    public void UpdateOffer(String Discreption ,String Remarks) throws InterruptedException {

        driver.element().click(Btn_Edit);
//        driver.element().type(Txt_Id,Discount);
        Thread.sleep(1000);
//        driver.element().click(ClickDiscount);
        driver.element().clear(Txt_Discreption);
        driver.element().type(Txt_Discreption,Discreption);
        driver.element().type(Txt_Remarks,Remarks);



    }
    public void img(){
        driver.element().click(Btn_Upload);
    }
    public void Sendapprove(){
        driver.element().click(Btn_SendForaproval);
    }
    public void Approve(){
        driver.element().click(Btn_Approve);
    }
    public void Reject(){
        driver.element().click(Btn_Reject);
    }
    public void Cancel(){
        driver.element().click(Btn_Cancel);
    }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Updated Successfully\"]"));
        return S;
    }


}
