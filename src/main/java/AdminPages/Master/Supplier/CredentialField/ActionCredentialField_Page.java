package AdminPages.Master.Supplier.CredentialField;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class ActionCredentialField_Page {
    public ActionCredentialField_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_Action = By.xpath("//i[@class=\"pi pi-pencil\"]");
    By Btn_SupplierCredintial = By.xpath("//input[@id=\"id-SupplierCredentialFieldName\"]");
    By Lst_Supplier = By.xpath("//p-dropdown[.//input[@id=\"id-Supplier\"]]");
    By Btn_Submit = By.xpath("//button[@type=\"submit\"]");


    public void setSupplierCredential(String supplierCredential ,  String supplier){

        driver.element().click(Btn_Action);
        driver.element().type(Btn_SupplierCredintial,supplierCredential);
        driver.element().click(Lst_Supplier);
        By option1 = By.xpath(String.format("//span[text()='%s']", supplier));
        driver.element().click(option1);
        driver.element().click(Btn_Submit);
    }
}
