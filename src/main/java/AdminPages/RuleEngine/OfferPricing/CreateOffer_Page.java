package AdminPages.RuleEngine.OfferPricing;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import utilities.FileUploadUtil;

public class CreateOffer_Page {
    private final SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;

    public CreateOffer_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        testData = new SHAFT.TestData.JSON("Createoffer.json");
    }
    By Btn_Rule = By.xpath("//a[@href=\"/rule-engine\"]");
    By Btn_Offer = By.xpath("(//div[@class=\"panel-heading accordion-toggle\"])[5]");
    By Btn_Add =By.xpath("//button[@routerlink=\"add\"]");
    By Txt_Id = By.xpath("//input[@id=\"id-DiscountID\"]");
    By Btn_Upload = By.xpath("//button[@label=\"Primary\"]");
    By Txt_Discreption = By.xpath("//textarea[@placeholder=\"Description...\"]");
    By Btn_SendForaproval = By.xpath("//button[@type=\"submit\"]");
    By Btn_Cancel = By.xpath("//button[@type=\"reset\"]");
    By ClickDiscount = By.xpath("//ul[@class=\"p-autocomplete-items ng-tns-c46-4 ng-star-inserted\"]");





    public void CreateOffer(String Discount,String Discreption,String value) throws InterruptedException {

        driver.element().click(Btn_Add);
        driver.element().type(Txt_Id,Discount);
        //Thread.sleep(1000);

        driver.element().click(By.xpath(String.format("//span[text()='%s']", value)));
        driver.element().type(Txt_Discreption,Discreption);


    }
    public void img(){
        driver.element().click(Btn_Upload);
    }
    public void uploadImage(){
        By fileInputLocator = By.xpath("(//input[@type='file'])[1]");
        String filePath = testData.getTestData("filePath");
        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath);
    }
    public void Sendapprove(){
        driver.element().click(Btn_SendForaproval);
    }
    public void Cancel(){
        driver.element().click(Btn_Cancel);
    }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }
    
}
