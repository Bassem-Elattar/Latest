package Create;

import Master.SearchAction;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utilities.FakerSingleton;

public class CreateDEalCode  {

    SHAFT.GUI.WebDriver driver ;
    SearchAction search ;


    public CreateDEalCode (SHAFT.GUI.WebDriver driver){
        this.driver=driver;
    }

    private final By Btn_AddDealCode = By.cssSelector("[style*=\"margin-inline-end\"]");
    private final By  Lst_Supplier = By.xpath("//p-dropdown[.//input[@id=\"id-Supplier\"]]");
    private final By  Lst_SupplierCredential = By.xpath("//p-dropdown[.//input[@id=\"id-Suppliercredential\"]]");
    private final By RBtn_SearchAirLine = By.xpath("(//span[contains(text(),'Select')])[1]");
    private final By RBtn_SearchAirLineText = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component']");
    private final By Txt_SearchAirLine = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component']");
    private final By RBtn_Sector = By.xpath("//p-radiobutton[.//input[@id=\"Select Level-Sector\"]]");
    private final By RBtn_Country = By.cssSelector("[class='p-radiobutton-box']");
    public final By Lst_SelectFrom = By.xpath("//label[contains(text(),'From')]/..//div[@class='p-multiselect p-component']");
    private final By Lst_SelectCountryFrom= By.xpath("//label[contains(text(),'Country From ')]/..//div[@class='p-multiselect p-component']");
    //TODO: write on search
    private final By Txt_writeofSearch_From = By.xpath("//input[@role='textbox']");

    private final By Txt_writeofSearch_TO = By.cssSelector("input[role='textbox']");
    //TODO : Click on CheckBox
    private final By Rtn_CheckBox = By.xpath("//span[normalize-space()='Cairo International Airport(CAI)']");
    private final By Rtn_CountryCheckBoxFrom = By.xpath("//li[@aria-label='United Arab Emirates(AE)']//div[@class='p-checkbox-box']");
    private final By Rtn_CountryCheckBoxTo = By.xpath("//li[@aria-label='Germany(DE)']//div[@class='p-checkbox-box']");
    // private final By Lst_SelectTO= By.xpath("(//*[@class=\"p-multiselect-label p-placeholder\"])[2]");
    public final By Lst_SelectTO= By.xpath("//label[contains(text(),'To')]/..//div[@class='p-multiselect p-component']");
    private final By Lst_SelectCountryTO= By.xpath("//label[contains(text(),'Country To')]/..//div[@class='p-multiselect p-component']");
    // private final By Lst_SelectTO = By.xpath("//p-multiselect[@class='p-element p-inputwrapper fg-input p-multiselect-clearable ng-pristine ng-invalid ng-star-inserted ng-touched']//div[@class='p-multiselect-label p-placeholder'][normalize-space()='Select']");
    private final By Txt_DEalCode = By.cssSelector("[placeholder=\"Deal Code\"]");
    private final By Txt_SalesValidityFrom = By.id("id-SalesValidityFrom");
    private final By Txt_SalesValidityTo = By.id("id-SalesValidityTo");
    private final By Txt_TravelValidityFrom = By.id("id-TravelValidityFrom");
    private final By Txt_TravelValidityTo = By.id("id-TravelValidityTo");
    private final By Btn_SendForApproval = By.cssSelector("[type=\"submit\"]");
    //
    private final By  Btn_ApprovalAction = By.xpath("(//div[@class='action exception-buttons ng-star-inserted'])[3]");
    private final By Txt_Remarks = By.xpath("//textarea[@placeholder='remarks...']");
    private final By RBtn_Submit = By.xpath("//span[normalize-space()='Submit']");
    //
    public final By APPROVALStatus = By.xpath("//tbody/tr[1]/td[5]");
    //
    //TODO : locator fro Edit

    public final By Btn_Editicone = By.xpath("(//i[@class='pi pi-pencil'])[1]");
    public final By Txt_Supplier = By.xpath("//span[@id='pr_id_15_label']");
    public final By Txt_supplierCredential = By.xpath("(//div[@class='p-dropdown p-component p-dropdown-clearable'])[2]");
    public final By Txt_AirLine = By.xpath("(//div[@class='p-dropdown p-component p-dropdown-clearable'])[3]");
    public final By Txt_DealCode = By.cssSelector("input[placeholder=\"Deal Code\"]");
    public final By Txt_SalesFrom = By.cssSelector("#id-SalesValidityFrom");
    public final By Txt_SalesTO = By.cssSelector("#id-SalesValidityTo");
    public final By Txt_TravelFrom = By.cssSelector("#id-TravelValidityFrom");
    public final By Txt_TravelTO = By.cssSelector("#id-TravelValidityTo");
    public final By Btn_Approve = By.xpath("//span[normalize-space()='Approve']");
    public final By Btn_Reject = By.xpath("//span[normalize-space()='Reject']");
    public final By Btn_sendForApproval = By.xpath("//span[normalize-space()='Send For Approval']");
    private final  By searchFields =By.cssSelector("[class=\"table-area\"]");



    public CreateDEalCode clickONAddDealCodeButton(){
        driver.element().click(Btn_AddDealCode);
        return this;

    }
    public CreateDEalCode choseSupplier (String supplier){
        driver.element().select(Lst_Supplier,supplier);
        return this;
    }

    public CreateDEalCode choseAireLine(String AireLine) {
        driver.element().click(RBtn_SearchAirLine);
        driver.element().type(RBtn_SearchAirLineText,AireLine);
        driver.element().click(By.xpath("//li[@aria-label='"+AireLine+"']"));
        return this;
    }

    public CreateDEalCode choseSupplierCredential (String credential)  {
//       Thread.sleep(1000);
        driver.element().select(Lst_SupplierCredential,credential);
//        driver.element().click(Lst_SupplierCredential);
//        By option11 = By.xpath(String.format("//span[contains(text(), '%s')]", credential));
//        driver.element().click(option11);
        return this;
    }
    public CreateDEalCode clickAireLine (){
        driver.element().click(RBtn_SearchAirLine);


        return this ;
    }

    public CreateDEalCode scrollToElement() {
        try {
            // Get the WebElement using the locator
            WebElement element = driver.getDriver().findElement(Rtn_CheckBox);

            // Use JavascriptExecutor to scroll the element into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            System.out.println("Scrolled to element: " + element);
        } catch (Exception e) {
            System.out.println("Error occurred while scrolling to the element: " + e.getMessage());
        }
        return this;
    }


    //TODO : Chose Sector option from select level radio button
    public CreateDEalCode ChoseSector (){
        driver.element().click(RBtn_Sector);
        return this;
    }
    public CreateDEalCode ChoseCountry ()
    {
        driver.element().click(RBtn_Country);
        return this;
    }
    //TODO : Click only on field
    public CreateDEalCode  SelectFrom (){
        driver.element().click(Lst_SelectFrom);
        return this ;
    }
    public CreateDEalCode  SelectCountryFrom (){
        driver.element().click(Lst_SelectCountryFrom);
        return this ;
    }

    //TODO : Write on search "cai"
    public CreateDEalCode writeFirstSectorFrom (String firstSectorFrom) {
        driver.element().waitToBeReady(Txt_writeofSearch_From).type(Txt_writeofSearch_From,firstSectorFrom);
        return this;
    }



    public CreateDEalCode clickONCheckBox1 () throws InterruptedException {
        //Thread.sleep(250);
        //  driver.element().waitUntilPresenceOfAllElementsLocatedBy(Rtn_CheckBox);
        driver.element().waitToBeReady(Rtn_CheckBox);
        driver.element().click(Rtn_CheckBox);
        return this;
    }
    public CreateDEalCode clickONCountryCheckBox1 () throws InterruptedException {
        //Thread.sleep(250);
        //  driver.element().waitUntilPresenceOfAllElementsLocatedBy(Rtn_CheckBox);
        driver.element().waitToBeReady(Rtn_CountryCheckBoxFrom);
        driver.element().click(Rtn_CountryCheckBoxFrom);
        return this;
    }
    public CreateDEalCode clickONCountryCheckBox2 () throws InterruptedException {
        //Thread.sleep(250);
        //  driver.element().waitUntilPresenceOfAllElementsLocatedBy(Rtn_CheckBox);
        driver.element().waitToBeReady(Rtn_CountryCheckBoxTo);
        driver.element().click(Rtn_CountryCheckBoxTo);
        return this;
    }




    //TODO : Write on search "AUH"



    public CreateDEalCode writeSecondSectorFrom (String secondSectorFrom) {
        driver.element().type(Txt_writeofSearch_From,secondSectorFrom);

        return this;
    }


    public CreateDEalCode clickONCheckBox2 () throws InterruptedException {

//        driver.element().waitUntilPresenceOfAllElementsLocatedBy(Rtn_CheckBox);

//Thread.sleep(500);
        driver.element().click(Rtn_CheckBox);
        return this;
    }

    //TODO : Click on select to  only
    public CreateDEalCode selectTo () {
        driver.element().click(Lst_SelectTO);

//driver.element().type(Txt_writeofSearch_TO,FirstSectorTO);
        return this ;
    }
    public CreateDEalCode  SelectCountryTo (){
        driver.element().click(Lst_SelectCountryTO);
        return this ;
    }
    // ToDO : write on search  of sector to  " cai "
    public CreateDEalCode writeFirstSectorTo (String FirstSectorTO) throws InterruptedException {
        Thread.sleep(500);
        driver.element().type(Txt_writeofSearch_TO,FirstSectorTO);

        //driver.element().waitToBeReady(Txt_writeofSearch_TO).type(Txt_writeofSearch_TO,FirstSectorTO);
        return this;

    }

//TODO : write deal code

    public CreateDEalCode writeDealCode (){
        driver.element().type(Txt_DEalCode, FakerSingleton.PassengerFactory.firstName());
        return this;
    }


    //TODO : write date of sales validity from
    public CreateDEalCode salesValidityFrom (String SalesFrom){
        driver.element().type(Txt_SalesValidityFrom,SalesFrom);
        return this;
    }

    //TODO : write date of sales validity TO
    public CreateDEalCode salesValidityTO (String SalesTO){
        driver.element().type(Txt_SalesValidityTo,SalesTO);
        return this;
    }

    public CreateDEalCode travelValidityFrom (String travelFrom){
        driver.element().type(Txt_TravelValidityFrom,travelFrom);
        return this;
    }


    public CreateDEalCode travelValidityTO (String travelTO){
        driver.element().type(Txt_TravelValidityTo,travelTO);
        return this;
    }


    public CreateDEalCode clickOnSendForAPPROVEL (){
        driver.element().click(Btn_SendForApproval);
        return this;
    }

    public CreateDEalCode clickONapprovelAction () throws InterruptedException {
        // driver.element().click(Btn_ApprovalAction);

        //TODO : To verify or to delay that not click until appear " Pending for approval" and highlight for this locator

        //driver.element().verifyThat(APPROVALStatus).text().equals("Pending for approval");
        driver.element().waitToBeReady(Btn_ApprovalAction).click(Btn_ApprovalAction);
        return this;
    }
    public CreateDEalCode writeRemark (String Remark){
        driver.element().type(Txt_Remarks,Remark);
        return this;
    }
    public CreateDEalCode clickONSubmit (){
        driver.element().click(RBtn_Submit);
        return this;
    }


    public  String chosenSector (){
        String dataOFSector = driver.element().getText(By.xpath("//div[@class='group d-flex flex-column']//div[1]//ndc-fg-input[1]//ndc-chips-dropdown-input[1]//div[1]//div[1]//div[1]//div[1]"));
        return dataOFSector;
    }

    public String returnActualMsg (){
        String addedDealCode = driver.element().getText(By.xpath("//div[@aria-label='Deal Code Added Successfully']"));
        driver.browser().captureScreenshot();
        return addedDealCode;
//        System.out.println(approveMessage);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertEquals(approveMessage,"Deal Code hAdded Successfully");
//        softAssert.assertAll();
        //return this;
    }

    public String returnapprovedMsg (){
        String approvedDealCode = driver.element().getText(By.xpath("//div[@aria-label='Deal Code Approved']"));
        driver.browser().captureScreenshot();
        return approvedDealCode;
    }

//    //
//     public CreateDEalCode approvalStatus (){
//        driver.element().getText(APPROVALStatus);
//         System.out.println("LABLABLAB   "+APPROVALStatus);
//        return this;
//     }
//    //

    //TODO : for Edit function
    public CreateDEalCode Edit  (){
       // search.waitForSearchFieldsToBeReady();
        driver.element().waitToBeReady(searchFields);
        driver.element().click(Btn_Editicone);
        return this;
    }
//    public String EditePage (){
//        String Supplier = driver.element().getText(By.xpath("//span[@id='pr_id_15_label']"));
//        driver.browser().captureScreenshot();
//
//        return Supplier;
//    }




    public CreateDEalCode approveButton (){
        driver.element().click(Btn_Approve);
        return this;
    }
    public CreateDEalCode rejectButton (){
        driver.element().click(Btn_Reject);
        return this;
    }
    public CreateDEalCode sendForApprovelButton (){
        driver.element().click(Btn_sendForApproval);
        return this;
    }




    public CreateDEalCode EditeDealCode (String editDEalCoe){
        driver.element().type(Txt_DealCode,editDEalCoe);
        return this ;
    }
}

