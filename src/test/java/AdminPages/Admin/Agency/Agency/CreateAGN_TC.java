package AdminPages.Admin.Agency.Agency;

import AdminPages.Admin.AdminMenu;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.FileUploadUtil;
import utilities.JsonDataUtil;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CreateAGN_TC extends TestBase_TC {
    CreateAGN_Page CRAGN;
    private LogIn_Page logIn;
    String agencyname = "";
    String SearchAgencyN = "";
    SearchAgency_Page SRAGN;

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
        new AdminMenu(driver).openSubAdmin().Agency();

    }

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void CreateAGN(Map<String, String> Create) throws InterruptedException {
        CRAGN = new CreateAGN_Page(driver);
        SRAGN = new SearchAgency_Page(driver);
        String selectbranch = Create.get("selectbranch");
        agencyname = Create.get("agencyname");
        String selectstate = Create.get("selectstate");
        String selectcity = Create.get("selectcity");
        String PostBox = Create.get("PostBox");
        String ADDRESS = Create.get("ADDRESS");
        String PH = Create.get("PH");
        String ContactPer = Create.get("ContactPer");
        String email = Create.get("email");
        String Phone = Create.get("Phone");

        String START = Create.get("START");
        String END = Create.get("End");

        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file
        String invoice = Create.get("invoice");
        String Top = Create.get("Top");
        String Credit = Create.get("Credit");
        String pcc1 = Create.get("pcc1");


        // Specify the file input locator and file path
        CRAGN.CreateValidAGN(selectbranch, agencyname, selectstate, selectcity, PostBox, ADDRESS, PH, ContactPer, email, Phone, START, END, invoice, Top, Credit, pcc1);
        CRAGN.IMG();
        By fileInputLocator = By.xpath("(//input[@type='file'])[1]");
        By fileInputLocator2 = By.xpath("(//input[@type='file'])[2]");
        String filePath = "C:\\Users\\Mahmoud\\Desktop\\white-empty-canvas_1194-7555.jpg";
        driver.element().type(fileInputLocator,"C:\\Users\\Ahmed Refat\\NDC-Uplift-Automation\\src\\test\\resources\\sample.png");
      //  FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator2, filePath);
        CRAGN.DOC();
        String filePath2 = "C:\\Users\\Mahmoud\\Desktop\\New Microsoft Word Document.docx"; // Replace with your file path
        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath2);

        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file


        // Replace with your file path
        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file

        CRAGN.Sendapprove();
        Thread.sleep(3000);
//            String Expected = "Added Successfully";
//            String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
//            Assert.assertEquals(Actual, Expected);

        SRAGN.setSearchAgency(agencyname);
        SRAGN.setInactive();
        CRAGN.searchBtc();
        CRAGN.setThumpUp("Approved");
    }


    //search agency TC ------------------------------------------





    //            @Test(priority = 2)
//            public void SearchAgency() throws InterruptedException {
//                SRAGN = new SearchAgency_Page(driver);
//                CRAGN = new CreateAGN_Page(driver);
//                SRAGN.setSearchAgency(agencyname);
//                SRAGN.setInactive();
//                CRAGN.searchBtc();
//                CRAGN.setThumpUp("Approved");
//
//
//
//
//
//            }
//
//        @Test(priority = 3)
//        public void SearchActiveAgency() throws InterruptedException {
//            SRAGN = new SearchAgency_Page(driver);
//            CRAGN = new CreateAGN_Page(driver);
//            SRAGN.setActive();
//            CRAGN.searchBtc();
//            CRAGN.performAssertions();
//
//
//
//
//        }
    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

