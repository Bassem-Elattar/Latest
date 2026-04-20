package AdminPages.RuleEngine.TermsAndConditions;

import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class TermsAndConditions_Page {
    //constant
    private final SoftAssert softAssert;

    public TermsAndConditions_Page(SHAFT.GUI.WebDriver driver) {

        this.softAssert = new SoftAssert();

        this.driver = driver;
    }
    static SHAFT.GUI.WebDriver driver;
    By Btn_RuleEngine = By.xpath("//a[@href=\"/rule-engine\"]");
    By Btn_TermsANDCondition = By.xpath("//img[@src=\"../static/img/term_cond_icon.png\"]");
    By Btn_Search = By.xpath("//button[@type=\"submit\"]");
    By Btn_ADD = By.xpath("//span[@style=\"margin-inline-end: 10px;\"]");
    By Rbtn_ActiveAndInactive = By.xpath("(//div[@class=\"p-radiobutton-box\"])[1]");
    By Rbtn_Both = By.xpath("(//div[@class=\"p-radiobutton-box\"])[2]");
    By Lst_SupplierName = By.xpath("//span[@class=\"p-dropdown-label p-inputtext p-placeholder ng-star-inserted\"]");
    By Lst_CountryPOS= By.xpath("(//div[@class=\"p-multiselect-label p-placeholder\"])[1]");
    By Lst_Branch= By.xpath("(//div[@class=\"p-multiselect-label p-placeholder\"])[1]");
    By Cbox_AllSelect = By.xpath("//div[@role=\"checkbox\"]");
    By Cbox_ETicket = By.xpath("(//div[@class=\"p-checkbox-box\"])[1]");
    By Cbox_ConfirmationPage = By.xpath("(//div[@class=\"p-checkbox-box\"])[2]");
    By Cbox_ReviewPage = By.xpath("(//div[@class=\"p-checkbox-box\"])[3]");
    By Cbox_Email = By.xpath("(//div[@class=\"p-checkbox-box\"])[4]");
    By Btn_Edit = By.xpath("(//i[@class=\"pi pi-pencil\"])[1]");
    By Btn_Trach = By.xpath("(//i[@class=\"pi pi-trash ng-star-inserted\"])[1]");
    By Btn_ActiveCircle = By.xpath("(//i[@class=\"pi pi-circle-fill\"])[1]");
    By Btn_InactiveCircle = By.xpath("(//i[@class=\"pi pi-circle-fill\"])[1]");
    By Btn_ThumbsDown = By.xpath("(//i[@class=\"pi pi-thumbs-down\"])[1]");
    By Btn_ThumbsUp = By.xpath("(//i[@class=\"pi pi-thumbs-up\"])[1]");
    By Txt_Remarks = By.xpath("//textarea[@class=\"p-inputtextarea p-inputtext p-component p-element " +
            "ng-untouched ng-pristine ng-invalid\"]");
    By Btn_CancelRemarks = By.xpath("//button[@type=\"reset\"]");
    By Btn_Submit = By.xpath("(//button[@type=\"submit\"])[2]");
    By Btn_Delete = By.xpath("(//button[@type=\"submit\"])[2]");
    By Txt_Description = By.xpath("//div[@class=\"ql-editor ql-blank\"]");
    By Btn_SendForApprovalADD = By.xpath("//button[@type=\"submit\"]");
    By Btn_Cancel = By.xpath("//button[@class=\"col-sm-12 col-md-6 col-lg-2 p-element " +
            "p-ripple p-button-outlined p-button p-component justify-content-center\"]");
    By Btn_Reject = By.xpath("(//button[@class=\"col-sm-12 col-md-6 col-lg-2 p-element " +
            "p-ripple p-button p-component justify-content-center ng-star-inserted\"])[1]");
    By Btn_Approve = By.xpath("(//button[@class=\"col-sm-12 col-md-6 col-lg-2 p-element " +
            "p-ripple p-button p-component justify-content-center ng-star-inserted\"])[2]");
    By Btn_SendForApprovalEdit = By.xpath("(//button[@class=\"col-sm-12 col-md-6 col-lg-2 " +
            "p-element p-ripple p-button p-component justify-content-center ng-star-inserted\"])[3]");
    By Msg_Required1 = By.xpath("(//span[@class=\"fg-error has-error\"])[1]");
    By Msg_Required2 = By.xpath("(//span[@class=\"fg-error has-error\"])[2]");
    By Msg_Required3 = By.xpath("(//span[@class=\"fg-error has-error\"])[3]");
    By Msg_Required4 = By.xpath("(//span[@class=\"fg-error has-error\"])[4]");
    By Msg_Required5 = By.xpath("(//span[@class=\"fg-error has-error\"])[5]");

    By Txt_RemarksEdit = By.xpath("//textarea[@placeholder=\"remarks...\"]");

    By Lst_CountryEdit = By.xpath("(//div[@class=\"p-element p-multiselect-label-container\"])[1]");
    By Lst_BranchEdit = By.xpath("(//div[@class=\"p-element p-multiselect-label-container\"])[2]");

    By Cbox_SelectedAll = By.xpath("(//span[@class=\"p-checkbox-icon pi pi-check\"])[1]");
    By Cbox_CheckedETicket = By.xpath("(//span[@class=\"p-checkbox-icon pi pi-check\"])[1]");
    By Cbox_CheckedReviewPage = By.xpath("(//span[@class=\"p-checkbox-icon pi pi-check\"])[2]");
    public TermsAndConditions_Page SelectSupplierOrCountryOrBranch (String S){
        By Selection = By.xpath("//li[@aria-label=\""+S+"\"]");
        return this;
    }

    public TermsAndConditions_Page NavigateToTermsAndCondition(){

        driver.element().click(Btn_RuleEngine);
        driver.element().click(Btn_TermsANDCondition);

        return this;
    }

    public TermsAndConditions_Page ClickOnSearch()
    {
        driver.element().click(Btn_Search);
        return this;
    }

    public TermsAndConditions_Page ClickOnAdd()
    {
        driver.element().click(Btn_ADD);
        return this;
    }

    public TermsAndConditions_Page ClickOnActiveAndInactive()
    {
        driver.element().click(Rbtn_ActiveAndInactive);
        return this;
    }
    public TermsAndConditions_Page ClickOnBoth()
    {
        driver.element().click(Rbtn_Both);
        return this;
    }

    public TermsAndConditions_Page ChooseSupplier(String S){
        driver.element().click(Lst_SupplierName);
        By Selection = By.xpath("//li[@aria-label=\""+S+"\"]");
        driver.element().click(Selection);
        return this;
    }
    public TermsAndConditions_Page ChooseCountry(String S){
        driver.element().click(Lst_CountryPOS);
        By Selection = By.xpath("//li[@aria-label=\""+S+"\"]");
        driver.element().click(Selection);
        return this;
    }
    public TermsAndConditions_Page ChooseAllCountry(){
        driver.element().click(Lst_CountryPOS);
        driver.element().click(Cbox_AllSelect);
        return this;
    }
    public TermsAndConditions_Page ChooseBranch(String S){
        driver.element().click(Lst_Branch);
        By Selection = By.xpath("//li[@aria-label=\""+S+"\"]");
        driver.element().click(Selection);
        return this;
    }
    public TermsAndConditions_Page FillDescription(String S){
        driver.element().type(Txt_Description,S);
        return this;
    }
    public TermsAndConditions_Page ChooseAllBranch(){
        driver.element().click(Lst_Branch);
        driver.element().click(Cbox_AllSelect);
        return this;
    }
    public TermsAndConditions_Page ClickOnETicket()
    {
        driver.element().click(Cbox_ETicket);
        return this;
    }
    public TermsAndConditions_Page ClickOnConfirmationPage()
    {
        driver.element().click(Cbox_ConfirmationPage);
        return this;
    }
    public TermsAndConditions_Page ClickOnReviewPage()
    {
        driver.element().click(Cbox_ReviewPage);
        return this;
    }
    public TermsAndConditions_Page ClickOnEmail()
    {
        driver.element().click(Cbox_Email);
        return this;
    }
    public TermsAndConditions_Page ClickOnSendForApprovalAdd()
    {
        driver.element().click(Btn_SendForApprovalADD);
        return this;
    }
    public TermsAndConditions_Page ClickOnSendForApprovalEdit()
    {
        driver.element().click(Btn_SendForApprovalEdit);
        return this;
    }
    public TermsAndConditions_Page ClickOnCancel()
    {
        driver.element().click(Btn_Cancel);
        return this;
    }
    public TermsAndConditions_Page ClickOnReject()
    {
        driver.element().click(Btn_Reject);
        return this;
    }
    public TermsAndConditions_Page ClickOnApprove()
    {
        driver.element().click(Btn_Approve);
        return this;
    }
    public TermsAndConditions_Page ClickOnEdit()
    {
        driver.element().click(Btn_Edit);
        return this;
    }
    public TermsAndConditions_Page DeleteTerm()
    {
        driver.element().click(Btn_Trach);
        driver.element().click(Btn_Delete);
        return this;
    }
    public TermsAndConditions_Page ClickOnActiveCircle()
    {
        driver.element().click(Btn_ActiveCircle);
        return this;
    }
    public TermsAndConditions_Page ClickOnInactiveCircle()
    {
        driver.element().click(Btn_InactiveCircle);
        return this;
    }
    public TermsAndConditions_Page ActiveTerm(String Remarks)
    {
        driver.element().click(Btn_ThumbsUp);
        driver.element().type(Txt_Remarks,Remarks);
        driver.element().click(Btn_Submit);

        return this;
    }
    public TermsAndConditions_Page RejectTerm(String Remarks)
    {
        driver.element().click(Btn_ThumbsDown);
        driver.element().type(Txt_Remarks,Remarks);
        driver.element().click(Btn_Submit);

        return this;
    }
    String URL = "http://192.168.1.216/rule-engine/terms";
    public TermsAndConditions_Page NavigationURLTerms(){
        driver.browser().navigateToURL(URL);
        return this;
    }
    public String ActualResult(){
        String S = ElementActions.getInstance().getText(Msg_Required1);
        return S;
    }
    public String ActualResult2(){
        String S = ElementActions.getInstance().getText(Msg_Required2);
        return S;
    }
    public String ActualResult3(){
        String S = ElementActions.getInstance().getText(Msg_Required3);
        return S;
    }
    public String ActualResult4(){
        String S = ElementActions.getInstance().getText(Msg_Required4);
        return S;
    }
    public TermsAndConditions_Page DeselectAll(){
        driver.element().click(Cbox_SelectedAll);
        return this;
    }
    public TermsAndConditions_Page ClickOnCheckedETicket()
    {
        driver.element().click(Cbox_CheckedETicket);
        return this;
    }

    public TermsAndConditions_Page ClickOnCheckedReviewPage()
    {
        driver.element().click(Cbox_CheckedReviewPage);
        return this;
    }
    public TermsAndConditions_Page ChooseAllCountryEdit(){
        driver.element().click(Lst_CountryEdit);
        driver.element().click(Cbox_AllSelect);
        return this;
    }
    public TermsAndConditions_Page RemarksSendForApprovalEdit(String Remarks)
    {

        driver.element().type(Txt_RemarksEdit,Remarks);


        return this;
    }
    public String GetSupplier (){
        By SupplierInTable = By.xpath("(//td[@class=\"ng-star-inserted\"])[2]");
        String S = ElementActions.getInstance().getText(SupplierInTable);
        return S;
    }
    public String GetStatus (){
        By ApprovalStatus = By.xpath("(//td[@class=\"ng-star-inserted\"])[3]");
        String S = ElementActions.getInstance().getText(ApprovalStatus);
        return S;
    }

}