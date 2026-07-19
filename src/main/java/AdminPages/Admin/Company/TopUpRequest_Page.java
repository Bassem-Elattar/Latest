package AdminPages.Admin.Company;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class TopUpRequest_Page {
    public TopUpRequest_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    By Btn_Advance_Credit = By.xpath("//a[@href=\"/admin/topUpRequestDetails\"]");
    By Lst_AgencyName = By.xpath("//p-multiselect[.//input[@id=\"id-Agencyname\"]]");
    By Btn_Close = By.xpath("//button[@class=\"p-ripple p-element p-multiselect-close p-link ng-star-inserted\"]");
    By Dpick_FromDate = By.xpath("//input[@id=\"id-FromDate\"]");
    By Dpick_ToDate = By.xpath("//input[@id=\"id-ToDate\"]");
    By Lst_Status = By.xpath("//p-dropdown[.//input[@id=\"id-Status\"]]");
    By Lst_PaymentType = By.xpath("//p-dropdown[.//input[@id=\"id-PaymentType\"]]");
    By Txt_TopUpReferenceNumber = By.xpath("//input[@id=\"id-TopUpReferenceNumber\"]");
    By Txt_ReceiptNumber = By.xpath("//input[@id=\"id-ReceiptNumber\"]");
 //   By Clickoncleander = By.xpath("class=\"p-button-icon pi pi-calendar\"");
    By Lst_PaymentMethod = By.xpath("//p-dropdown[.//input[@id=\"id-Paymentmode\"]]");
    By Btn_Reset = By.xpath("//button[@type=\"reset\"]");
    By Btn_SearchGrid = By.xpath("//button[@type=\"submit\"]");
    By Btn_AddRequest = By.xpath("//button[@routerlink=\"add\"]");
    By Lst_BranchName = By.xpath("//p-dropdown[.//input[@id=\"id-Branchname\"]]");
    By Txt_AgencyName = By.xpath("//input[@id=\"id-Agencyname\"]");
    By Txt_TopUpAmount = By.xpath("//input[@id=\"id-AddTopUpAmount\"]");
    By Txt_Remarks = By.xpath("//textarea[@placeholder=\"Type your product description...\"]");
    By Btn_SendApproval = By.xpath("//button[@type=\"submit\"]");
    By Btn_Upload = By.xpath("//i[@class=\"pi pi-cloud-upload\"]");


    public void setTopUpRequest(String agencyname,String fromdate,String todate,String status,String paymenttype
            ,String topupref,String recepitnum, String paymentmethod)
    {
        driver.element().click(Lst_AgencyName);
        By option3 = By.xpath(String.format("//span[text()='%s']", agencyname));
        driver.element().click(option3);
        driver.element().click(Btn_Close);
        driver.element().type(Dpick_FromDate,fromdate);
        driver.element().type(Dpick_ToDate,todate);
        driver.element().click(Lst_Status);
        By option = By.xpath(String.format("//span[text()='%s']", status));
        driver.element().click(option);
        driver.element().click(Lst_PaymentType);
        By option2 = By.xpath(String.format("//span[text()='%s']", paymenttype));
        driver.element().click(option2);
        driver.element().type(Txt_TopUpReferenceNumber,topupref);
        driver.element().type(Txt_ReceiptNumber,recepitnum);
        driver.element().click(Lst_PaymentMethod);
        By option1 = By.xpath(String.format("//span[text()='%s']", paymentmethod));
        driver.element().click(option1);

    }
    public void Advance_Credit(){
        System.out.println("Start Method : Click on TopUpRequest");
        driver.element().click(Btn_Advance_Credit);
    }
    public void setReset(){
        driver.element().click(Btn_Reset);
    }

    public void setSearchGrid(){
        driver.element().click(Btn_SearchGrid);
    }

    public void performAssertions () {
        String expectedHeaderStatus = "Status";
        String expectedHeaderApproval = "Payment Mode";
        String[] allowedStatusValues = {"Approved"};
        String[] allowedApprovalValues = {"Offline", "Online"};

        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[10]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");

            String actualHeaderApproval = driver.element().getText(By.xpath("//table/thead/tr/th[4]"));
            Assert.assertEquals(actualHeaderApproval, expectedHeaderApproval, "The 'Approval Status' table header does not match the expected value.");

            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[10]"));
                String actualDataApproval = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[4]"));

                Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                        "Row " + i + ": The 'Status' column data (" + actualDataStatus + ") does not match any of the allowed values.");

                Assert.assertTrue(isValueInArray(actualDataApproval, allowedApprovalValues),
                        "Row " + i + ": The 'Approval Status' column data (" + actualDataApproval + ") does not match any of the allowed values.");

                // Additional validation: If Approval Status is "Rejected", Status cannot be "Active"
                if (actualDataApproval.equals("Rejected")) {
                    Assert.assertNotEquals(actualDataStatus, "Active",
                            "Row " + i + ": The 'Status' cannot be 'Active' if the 'Approval Status' is 'Rejected'.");
                }
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
////Create_TopUp/////////////////////


    public void AddRequest(String branchname , String agencyname , String topupamount , String paymenttype, String remarks) throws InterruptedException {
        System.out.println("Start Method : Adding TopUpRequest");
        driver.element().click(Btn_AddRequest);
        driver.element().click(Lst_BranchName);
        By option = By.xpath(String.format("//span[text()='%s']", branchname));
        driver.element().click(option);
        driver.element().type(Txt_AgencyName,agencyname).click(Txt_AgencyName);
        driver.element().type(Txt_TopUpAmount,topupamount);
        driver.element().click(Lst_PaymentType);
        By option2 = By.xpath(String.format("//span[text()='%s']", paymenttype));
        driver.element().click(option2);
        driver.element().type(Txt_Remarks,remarks);
//        driver.element().click(Btn_Upload);


    }
    public String Actual()
    {
        String  S =driver.element().getText(By.xpath("//div[@aria-label=\"Your Top up has been processed successfully and sent for approval!!\"]"));
        return S;
    }
    public void setSendApproval() throws InterruptedException {
        driver.element().click(Btn_SendApproval);

    }
}
