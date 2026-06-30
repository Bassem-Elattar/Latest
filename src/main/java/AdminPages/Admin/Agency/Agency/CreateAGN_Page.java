package AdminPages.Admin.Agency.Agency;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.openqa.selenium.By.xpath;

public class CreateAGN_Page {
    public CreateAGN_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver;
//    public By AGNBtn = xpath("/html/body/ndc-root/ndc-layout/div/div[1]/tilde-theme-side-menu/div/ul/li[3]/tilde-theme-accordion/header/a");
    public By Agency = xpath("//a[@href=\"/admin/agency\"]");
    By ADDBtn = xpath("//button[@routerlink=\"add\"]");
    By SelectBranch = xpath("(//p-dropdown[.//input[@id=\"id-Branch\"]])[1]");
    By WRTAgencyName = xpath("//input[@id=\"id-Agency\"]");
    By SelectState = xpath("//p-dropdown[.//input[@id=\"id-State\"]]");
    By SelectCity = xpath("//p-dropdown[.//input[@id=\"id-City\"]]");
    By ADDpostBox = xpath("//input[@id=\"id-Postofficebox\"]");
    By Address = xpath("//textarea[@placeholder=\"Address 1\"]");
    By PHnum = xpath("//input[@id=\"Phone No\"]");
    By ContactPer = xpath("//input[@id=\"id-Contactperson\"]");
    By Email = xpath("(//input[@id=\"id-EmailID\"])[1]");
    By PhoneNum = xpath("(//input[@id=\"Phone Number\"])[1]");
    By UploadDOC = xpath("//ndc-fg-input//ndc-fg-file-input//button");
    By StartDate = xpath("//input[@id=\"id-ContractStartDate\"]");
    By EndDate = xpath("//input[@id=\"id-ContractEndDate\"]");
    By UploadLogo = xpath("//ndc-fg-input//ndc-fg-image-input//button");
    By Invoice = xpath("//p-dropdown[.//input[@id=\"id-Invoicegeneration\"]]");
    By Creditlimit = xpath("//input[@id=\"id-Creditlimit\"]");
    By TOPLimit = xpath("//input[@id=\"id-Topuplimit\"]");
    By CashPay = xpath("//p-checkbox[.//input[@id=\"id-Paymentoption-Cash\"]]");
    //By Amad = xpath("//a[@id=\"p-tabpanel-1-label\"]");
    By SendApprove = xpath("//button[@type=\"submit \"]");
    By After = xpath("//p-calendar//button[contains(@class,'p-datepicker-next')]//span");
    //  By path = By.xpath("//input[@type='file']");
//    By Galileo = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div[1]/div/ndc-add-agency/div/div/div[4]/p-tabview/div/div[1]/div/ul/li[1]/a/span");
//    By Sabre = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div[1]/div/ndc-add-agency/div/div/div[4]/p-tabview/div/div[1]/div/ul/li[3]/a/span");
//    By Select = xpath("/html/body/ndc-root/ndc-layout/div/div[2]/div[1]/div/ndc-add-agency/div/div/div[4]/p-tabview/div/div[2]/p-tabpanel[1]/div/div/ndc-fg-form-generator/form/div[1]/div/ndc-fg-input[1]/ndc-fg-dropdown-input/p-multiselect/div/div[3]");
//    By Rbtn_Inactive = By.xpath("//p-radiobutton[.//input[@id=\"id-Status-Inactive\"]]");
    public By Btn_Search = By.xpath("//span[@class=\"p-button-label\"]");
    By SearchAgencyN = By.xpath("//*[@id=\"id-Agencyname\"]");
    By Btn_ThumpUp = By.xpath("(//i[@class='pi pi-thumbs-up'])[1]");
    By Btn_ThumpDown = By.xpath("//i[@class=\"pi pi-thumbs-down\"]");
    By Txt_RemarksUp = By.xpath("//ndc-dialog-modal//ndc-fg-text-area-input//textarea");
    By Btn_Submitup =  By.xpath("//ndc-dialog-modal//button[@type='submit']");
    By Txt_AgencyName = xpath("//input[@id=\"id-Agencyname\"]");
    By Rbtn_StatusAGN = xpath("(//p-radiobutton[.//span[@class=\"p-radiobutton-icon\"]])[1]");
    By Submit = xpath("//button[@type=\"submit\"]");
    By Btn_Next = By.xpath("//ndc-agency//tilde-paginator//button//i[contains(@class,'pi-chevron-right')]");



    private By rowAgency(String agencyname) {
        return By.xpath("//td[normalize-space()='" + agencyname + "']");

    }

    public void CreateValidAGN(String selectbranch, String AgencyName, String state, String city, String PostBox, String address, String phone, String contact, String email, String ph, String START, String END, String invoice, String Credit, String Top, String pcc1) {
//        driver.element().click(AGNBtn);
//        driver.element().click(Agency)
                driver.element().click(ADDBtn);
        driver.element().click(SelectBranch);
        By option11 = xpath(String.format("(//span[contains(text(), '%s')])[1]", selectbranch));
        driver.element().click(option11)
                .type(WRTAgencyName, AgencyName).type(WRTAgencyName, AgencyName)
                .select(SelectState, state);
        driver.element().click(SelectCity);
        By option12 = xpath(String.format("//span[contains(text(), '%s')]", city));
        driver.element().click(option12)
                .type(ADDpostBox, PostBox)
                .type(Address, address)
                .type(PHnum, phone)
                .type(ContactPer, contact)
                .type(Email, email)
                .type(PhoneNum, ph)
        ;
        driver.element().click(StartDate);
        By option4 = xpath(String.format("//span[text()='%s']", START));
        driver.element().click(option4);
        driver.element().click(EndDate);
        driver.element().click(After);
        By option500 = xpath(String.format("//span[text()='%s']", END));
        driver.element().click(option500);
        driver.element().click(Invoice);
        By option = xpath(String.format("//span[text()='%s']", invoice));
        driver.element().click(option);
        driver.element().type(Creditlimit, Credit)
                .type(TOPLimit, Top)
                .click(CashPay);
//        driver.element().click(Select);
//        By option30 = xpath(String.format("//span[text()='%s']", pcc1));
//        driver.element().click(option30);


        driver.element().click(SendApprove);
    }

    public void Sendapprove() {
        driver.element().click(SendApprove);
    }

    public String Actual() {
        String S = driver.element().getText(xpath(""));
        return S;
    }

    public void DOC() {
        driver.element().click(UploadDOC);
    }

    public void IMG() {
        driver.element().click(UploadLogo);

    }

    //    public void search(String agencyname) throws InterruptedException {
//
//        driver.element().click(Rbtn_Inactive);
//        driver.element().type(SearchAgencyN,"");
//        Thread.sleep(Long.parseLong("2000"));
//        driver.element().click(Btn_Search);
//
//    }
    public void setThumpUp(String remarksup) throws InterruptedException {
        driver.element().click(Btn_ThumpUp);
        Thread.sleep(2000);
        driver.element().type(Txt_RemarksUp,remarksup);
        driver.element().click(Btn_Submitup);
    }
    public void searchBtc(){
        driver.element().click(Btn_Search);

    }

//    public void serachAgency(String Agencyname) {
//        driver.element().click(AGNBtn);
//        driver.element().click(Agency)
//                .type(Txt_AgencyName,Agencyname)
//                .click(Rbtn_StatusAGN)
//                .click(Submit);
//    }
    public boolean findAgencyInPages(String agencyName) {
        int maxPages = 20; // safety limit to avoid infinite loops

        for (int i = 0; i < maxPages; i++) {

            // Check if agency exists in current page
            if (driver.element().isElementDisplayed(rowAgency(agencyName))) {
                return true;
            }

            // Check if Next button is disabled or not visible
            if (!driver.element().isElementDisplayed(Btn_Next)) {
                break;
            }

            // Scroll and go to next page
            driver.element().scrollToElement(Btn_Next);
            driver.element().click(Btn_Next);
        }

        return false; // agency not found in any page
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

