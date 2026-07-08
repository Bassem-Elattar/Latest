package AdminPages.Admin.Branch;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.FakerSingleton;

import java.util.List;

public class AddNewBranch_Page {

   public AddNewBranch_Page(SHAFT.GUI.WebDriver driver) {
      this.driver = driver;
   }
   SHAFT.GUI.WebDriver driver ;
    public static String branchName;
   /////////////Branch Details//////////////

   By Branch = By.xpath("//a[@href=\"/admin/branch\"]");
   By AddBranch = By.xpath("//button[@routerlink='add']");
   By SelectBrand = By.xpath("//select[@id=\"brand\"]");
   By SelectOperatingCountry = By.xpath("//input[@id=\"id-Operatingcountry\"]");
   By BranchName = By.xpath("(//input[@id=\"id-Name\"])[1]");
   By Country = By.xpath("//input[@id=\"id-Country\"]");
   By State = By.xpath("//p-dropdown[.//input[contains(@id, 'State')]]");
   By City = By.xpath("//p-dropdown[.//input[contains(@id, 'City')]]");
   By Address = By.xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div[1]/div/ndc-add-branch/div/div/ndc-fg-form-container/div/ndc-fg-form-generator/form/div[1]/div/ndc-fg-input[6]/ndc-fg-text-area-input/div/textarea");
   By PostOffice = By.xpath("//input[@id=\"id-PostOfficeBox\"]");
   By PhoneNO = By.xpath("(//input[contains(@id, 'Phone Number')])[1]");

   //////////Branch Email Config Info//////////

   By EmailID = By.xpath("(//input[@id=\"id-EmailID\"])[1]");
   By Password = By.xpath("//input[@id=\"id-EmailPassword\"]");

   ////////////Branch Contact Info//////////////

   By Name = By.xpath("(//input[@id=\"id-Name\"])[2]");
   By Email = By.xpath("(//input[@id=\"id-EmailID\"])[2]");
   By Phone = By.xpath("(//input[@id=\"Phone Number\"])[2]");
   By SelectDepartment = By.xpath("//a[@class=\"select-form-control branch-dept-arrow\"]");
   By branchManagerIcon = By.xpath("//input[@value=\"GDS\"]");
   By CreditLimit = By.xpath("//input[@id=\"id-CreditLimit\"]");
   By TopUpLimit = By.xpath("//input[@id=\"id-TopUpLimit\"]");
   By CreditTerms = By.xpath("//input[@id=\"id-CreditTerms(Days)\"]");
   By PaymentMode = By.xpath("//p-checkbox[.//input[@id=\"id-PaymentOption-Cash\"]]");
   By Supplier = By.xpath("//p-checkbox[.//input[@name=\"119\"]]");
   By Description = By.xpath("//input[@id=\"id-supplier-120\"]");
   By SupplierCredential = By.xpath("//p-multiselect[.//input[contains(@id, 'Genericsuppliercredentials')]]");
   // By ManagerName = By.xpath("//input[@id=\"managerNameGalileo0\"]");
   By SupplierDescription = By.xpath("//input[@id=\"id-Description\"]");
   By SendApprove = By.xpath("//button[@type=\"submit\"]");
   Alert alert;

   public String BranchDetails(String brand , String country ,String operatingcountry , String state , String city
           , String address, String postoffice, String phoneno, String emailid, String password, String name
           , String email, String phone, String creditlimit, String topup,String creditterm
           ,String description,String supplierdescription) throws InterruptedException {
      driver.element().click(Branch);
      driver.element().click(AddBranch);
      Thread.sleep(Long.parseLong("1000"));
      driver.element().select(SelectBrand,brand);
      driver.element().type(SelectOperatingCountry,operatingcountry);
      driver.element().select(SelectOperatingCountry,country);
      driver.element().type(BranchName, FakerSingleton.PassengerFactory.firstName());
      branchName = driver.element().getText(BranchName);
      Thread.sleep(Long.parseLong("1000"));
      driver.element().select(State,state);
      driver.element().select(City,city);
      driver.element().type(Address,address);
      driver.element().type(PostOffice,postoffice);
      driver.element().type(PhoneNO,phoneno);
      driver.element().type(EmailID,emailid);
      driver.element().type(Password,password);
      driver.element().type(Name,name);
      driver.element().type(Email,email);
      driver.element().type(Phone,phone);
      driver.element().click(SelectDepartment).scrollToElement(branchManagerIcon).click(branchManagerIcon);
      driver.element().type(CreditLimit,creditlimit);
      driver.element().type(TopUpLimit,topup);
      driver.element().type(CreditTerms,creditterm);
      driver.element().click(PaymentMode);
      driver.element().click(Supplier);
      driver.element().type(Description,description);
      driver.element().click(SupplierCredential);
      //driver.element().type(ManagerName,managername);
      driver.element().type(SupplierDescription,supplierdescription);
      driver.element().click(SendApprove);
        return branchName;

   }
   public void Alert() throws InterruptedException {
      Thread.sleep(1000);
      alert = driver.getDriver().switchTo().alert();
      // alertText = alert.getText();
      alert.accept();
      Thread.sleep(1000);
      alert.accept();
      Thread.sleep(1000);
      alert.accept();
      Thread.sleep(1000);


   }
   public void performAssertions () {
      String expectedHeaderStatus = "Approval Status";
      // String expectedHeaderApproval = "Payment Mode";
      String[] allowedStatusValues = {"Approved"};
      //     String[] allowedApprovalValues = {"Offline", "Online"};

      try {
         // Wait for the table to be present and the text to be loaded
         Thread.sleep(1000);

         // Find and verify the table headers
         String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[6]"));
         Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");


         // Get the number of rows in the table body
         List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
         int numberOfRows = rows.size();

         // Iterate through each row and verify the data in the relevant columns
         for (int i = 1; i <= numberOfRows; i++) {
            String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[6]"));

            Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                    "Row " + i + ": The 'Status' column data (" + actualDataStatus + ") does not match any of the allowed values.");



         }

      } catch (Exception e) {
         // Print the stack trace to help with debugging if an exception occurs
         e.printStackTrace();
         Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
      }
   }


   private boolean isValueInArray (String value, String[]array){
      for (String element : array) {
         if (element.equals(value)) {
            return true;
         }
      }
      return false;
   }
}