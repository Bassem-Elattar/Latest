package AdminPages.Admin.Company.Department;
import AdminPages.Admin.AdminMenu;
import AdminPages.Admin.Company.Department_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.Browser;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.JsonDataUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;


public class ActionsDepartment_TC extends TestBase_TC {
  private Department_Page Department;
  private LogIn_Page logIn;
  //SHAFT.GUI.WebDriver driver;

  @DataProvider(name = "JsonProvider")
  public static Object[][] provideJsonData(Method method) throws IOException {
    String fileName = method.getName();
    String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
    return JsonDataUtil.readJsonData(filePath);
  }

  @BeforeTest
  public void sign() {
    logIn = new LogIn_Page(driver);
    logIn.ClickSuperAdmin();
    logIn.ClickOnLoginButton();
  }
    
  @Test(priority = 1, dataProvider = "JsonProvider")
  public void CreateDepartment(Map<String, String> department) throws InterruptedException {
    new AdminMenu(driver).openSubAdmin().Company().Department();
    Department = new Department_Page(driver);
    String DepartmentName = department.get("DepartmentName");
    String AssignedQueue = department.get("AssignedQueue");
//    addDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.AddDepartment(DepartmentName, AssignedQueue);
    Department.setSendApprroval();
    String Expected = "Added Successfully";
    String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
    Assert.assertEquals(Actual,Expected);
    }

  @Test(priority = 2)
    public void setActionsDepartment() throws InterruptedException {
    new AdminMenu(driver).openSubAdmin().Company().Department();
    Department = new Department_Page(driver);
//    Department = new SearchDepartment_Page(driver);
//    Department = new ActionsDepartment_Page(driver);
//      createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();
    Department.ActionDepartment("Approved","newww");
  }

  @Test(priority = 3)
  public void Search() throws InterruptedException {
    new AdminMenu(driver).openSubAdmin().Company().Department();
    Department = new Department_Page(driver);
//    Department = new SearchDepartment_Page(driver);
//    Department = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();

    ///////////////////////Check TableData///////////////////////
    String expectedHeader = "Department";
    String expectedData = "newww";
    String expectedHeader2 = "Approval Status";
    String expectedData2 = "Pending for approval";
    try {
      // Wait for the table to be present and the text to be loaded
      Thread.sleep(3000);

      // Find and verify the table header
      String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[1]")).getText();
      Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

      // Find and verify the data in the first row under the "Operating Country" header
      String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[1]")).getText();
      Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");

      String actualHeader2 = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[2]")).getText();
      Assert.assertEquals(actualHeader2, expectedHeader2, "The table header does not match the expected value.");

      String actualData2 = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
      Assert.assertEquals(actualData2, expectedData2, "The table data does not match the expected value.");

    } catch (Exception e) {
      // Print the stack trace to help with debugging if an exception occurs
      e.printStackTrace();
      Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
    }

  }


  @Test(priority = 4)
  public void Active() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
      Department.BothStatus();
    Department.Search();
    Department.ThumpUp("helelele");

    ///////////////////////Check TableData///////////////////////
    String expectedHeader = "Status";
    String expectedData = "Active";
    String expectedHeader2 = "Approval Status";
    String expectedData2 = "Approved";
    try {
      // Wait for the table to be present and the text to be loaded
      Thread.sleep(3000);

      // Find and verify the table header
      String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
      Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

      // Find and verify the data in the first row under the "Operating Country" header
      String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
      Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");

      String actualHeader2 = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[2]")).getText();
      Assert.assertEquals(actualHeader2, expectedHeader2, "The table header does not match the expected value.");

      String actualData2 = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
      Assert.assertEquals(actualData2, expectedData2, "The table data does not match the expected value.");
    } catch (Exception e) {
      // Print the stack trace to help with debugging if an exception occurs
      e.printStackTrace();
      Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
    }

  }


  @Test(priority = 5 )
  public void inActive() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();
    Department.ThumpDown("helelele");


    ///////////////////////Check TableData///////////////////////
    String expectedHeader = "Status";
    String expectedData = "Inactive";
    String expectedHeader2 = "Approval Status";
    String expectedData2 = "Rejected";
    try {
      // Wait for the table to be present and the text to be loaded
      Thread.sleep(3000);

      // Find and verify the table header
      String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
      Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

      // Find and verify the data in the first row under the "Operating Country" header
      String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
      Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");

      String actualHeader2 = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[2]")).getText();
      Assert.assertEquals(actualHeader2, expectedHeader2, "The table header does not match the expected value.");

      String actualData2 = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
      Assert.assertEquals(actualData2, expectedData2, "The table data does not match the expected value.");

    } catch (Exception e) {
      // Print the stack trace to help with debugging if an exception occurs
      e.printStackTrace();
      Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
    }
  }

  @Test(priority = 6)
  public void Active2() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();
    Department.ThumpUp("helelele");

    ///////////////////////Check TableData///////////////////////
    String expectedHeader = "Status";
    String expectedData = "Active";
    String expectedHeader2 = "Approval Status";
    String expectedData2 = "Approved";
    try {
      // Wait for the table to be present and the text to be loaded
      Thread.sleep(3000);

      // Find and verify the table header
      String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
      Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

      // Find and verify the data in the first row under the "Operating Country" header
      String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
      Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");

      String actualHeader2 = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[2]")).getText();
      Assert.assertEquals(actualHeader2, expectedHeader2, "The table header does not match the expected value.");

      String actualData2 = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
      Assert.assertEquals(actualData2, expectedData2, "The table data does not match the expected value.");
    } catch (Exception e) {
      // Print the stack trace to help with debugging if an exception occurs
      e.printStackTrace();
      Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
    }

  }

  @Test(priority = 7)
  public void inActiveCircle() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();
    Department.InActiveCircle();

    ///////////////////////Check TableData///////////////////////
    String expectedHeader1 = "Status";
    String expectedData1 = "Inactive";

    try {
      // Wait for the table to be present and the text to be loaded
      Thread.sleep(3000);

      // Find and verify the table header
      String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
      Assert.assertEquals(actualHeader, expectedHeader1, "The table header does not match the expected value.");

      // Find and verify the data in the first row under the "Operating Country" header
      String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
      Assert.assertEquals(actualData, expectedData1, "The table data does not match the expected value.");


    } catch (Exception e) {
      // Print the stack trace to help with debugging if an exception occurs
      e.printStackTrace();
      Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
    }
  }

  @Test(priority = 8)
  public void ActiveCircle() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();
    Department.ActiveCircle();

    ///////////////////////Check TableData///////////////////////
    String expectedHeader = "Status";
    String expectedData = "Active";
    try {
      // Wait for the table to be present and the text to be loaded
      Thread.sleep(3000);

      // Find and verify the table header
      String actualHeader = driver.getDriver().findElement(By.xpath("//table/thead/tr/th[3]")).getText();
      Assert.assertEquals(actualHeader, expectedHeader, "The table header does not match the expected value.");

      // Find and verify the data in the first row under the "Operating Country" header
      String actualData = driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
      Assert.assertEquals(actualData, expectedData, "The table data does not match the expected value.");
    } catch (Exception e) {
      // Print the stack trace to help with debugging if an exception occurs
      e.printStackTrace();
      Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
    }
  }

  @Test(priority = 9)
  public void Delete() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("neww");
    Department.BothStatus();
    Department.Search();
    Department.Trash();
    String expectedError = "No data has been found!";

    ///////////////////////Check TableData///////////////////////
    try {
        String actualerror = driver.getDriver().findElement(By.xpath("//td[@class=\"message\"]")).getText();
        Assert.assertEquals(actualerror, expectedError, "The table header does not match the expected value.");
      } catch (Exception ex) {
        // Print the stack trace to help with debugging if an exception occurs
        ex.printStackTrace();
        Assert.fail("An exception occurred while trying to verify the table header or data: " + ex.getMessage());
      }

    }
  @Test(priority = 10)
  public void MaptoStaff() throws InterruptedException {
    Department = new Department_Page(driver);
    new AdminMenu(driver).openSubAdmin().Company().Department();
//    searchDepartment = new SearchDepartment_Page(driver);
//    actionsDepartment = new ActionsDepartment_Page(driver);
//    createDepartment.setCompany();
//    Department.ClickSideMDepartment();
    Department.searchDepartment("OFFICE ADMIN");
    Department.BothStatus();
    Department.Search();
    Department.Trash();
    String Expected = "Department Mapped To Staff You Need To Delete The Staff First";
    String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Department Mapped To Staff You Need To Delete The Staff First\"]"));
    Assert.assertEquals(Actual,Expected);
//    String Expected2 = "Department has mapped staff and cannot be deleted";
//    String Actual2 = driver.element().getText(By.xpath("//div[@aria-label=\"Department has mapped staff and cannot be deleted\"]"));
//    Assert.assertEquals(Actual2,Expected2);
  }

  @AfterMethod
  public void navigateBackToURL() {
    driver.browser().navigateToURL("http://192.168.1.70");
  }

}




