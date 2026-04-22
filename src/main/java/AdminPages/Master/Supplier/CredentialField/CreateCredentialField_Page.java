package AdminPages.Master.Supplier.CredentialField;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class CreateCredentialField_Page {
    public CreateCredentialField_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_AddSupplierCredential = By.xpath("//button[@routerlink=\"add\"]");
    By Btn_SupplierCredential = By.xpath("//input[@id=\"id-SupplierCredentialFieldName\"]");
    By Lst_Supplier = By.xpath("//p-dropdown[.//input[@id=\"id-Supplier\"]]");
    By Btn_Submit = By.xpath("//button[@type=\"submit\"]");


    public void setSupplierCredintial(String supplierCredential ,  String supplier){
        driver.element().click(Btn_AddSupplierCredential);
        driver.element().type(Btn_SupplierCredential, supplierCredential);
        driver.element().click(Lst_Supplier);
        By option1 = By.xpath(String.format("//span[text()='%s']", supplier));
        driver.element().click(option1);
        driver.element().click(Btn_Submit);
    }

    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
        return S;
    }
    public String Actual2()
    {
        String  S =driver.element().getText(By.xpath("//span[@class='fg-error has-error']"));
        return S;
    }
}
