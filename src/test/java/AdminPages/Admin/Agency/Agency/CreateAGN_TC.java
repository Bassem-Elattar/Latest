package AdminPages.Admin.Agency.Agency;

import AdminPages.Admin.AdminMenu;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.FileUploadUtil;
import utilities.JsonDataUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CreateAGN_TC{
    CreateAGN_Page CRAGN;
    private LogIn_Page logIn;
    String agencyname = "";
    String SearchAgencyN = "";
    SearchAgency_Page SRAGN;
    SHAFT.GUI.WebDriver driver;
    public static String agencyName1;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }
    @BeforeTest
    public void sign() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        // Admin login
        new LogIn_Page(driver).AdminLogin();;
        new AdminMenu(driver).openSubAdmin().Agency().SubAgency();
    }

    @Test(priority = 1, dataProvider = "JsonProvider")
    public void CreateAGN(Map<String, String> Create) throws InterruptedException {
        CRAGN = new CreateAGN_Page(driver);
        SRAGN = new SearchAgency_Page(driver);
        String selectbranch = Create.get("selectbranch");

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
        agencyName1 = CRAGN.CreateValidAGN(selectbranch, selectstate, selectcity, PostBox, ADDRESS, PH, ContactPer, email, Phone, START, END, invoice, Top, Credit, pcc1);

        String imagePath = new File("src/test/resources/image_200x200.png").getAbsolutePath();
        WebElement imageInput = driver.getDriver()
                .findElement(By.xpath("(//input[@type='file'])[2]"));

        imageInput.sendKeys(imagePath);


// Upload PDF

        String filePath = new File("src/test/resources/sendGridUsage.pdf").getAbsolutePath();
        WebElement pdfInput = driver.getDriver()
                .findElement(By.xpath("(//input[@type='file'])[1]"));

        pdfInput.sendKeys(filePath);



        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file


        // Replace with your file path
        // Call the static uploadFile method from utilities.FileUploadUtil to upload the file

        CRAGN.Sendapprove();
        Thread.sleep(3000);
//            String Expected = "Added Successfully";
//            String Actual = driver.element().getText(By.xpath("//div[@aria-label=\"Added Successfully\"]"));
//            Assert.assertEquals(Actual, Expected);
        new AdminMenu(driver).openSubAdmin().Agency().SubAgency();
        agencyName1 = SRAGN.setSearchAgency(agencyname);
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

