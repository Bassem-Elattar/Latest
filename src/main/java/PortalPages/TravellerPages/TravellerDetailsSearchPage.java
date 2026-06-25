package PortalPages.TravellerPages;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TravellerDetailsSearchPage {
    SHAFT.GUI.WebDriver driver ;
    private final By btn_AddTraveller = By.xpath("//button[.//span[normalize-space()='Add Traveller']]");
    private final By txt_ClientID = By.id("id-ClientID");
    private final By txt_ClientName = By.id("id-Clientname");
    private final By txt_Email = By.id("id-Email");
    private final By txt_PhoneNumber = By.id("Phone Number");
    private final By ddl_Country = By.xpath("//input[@id='id-Country']/ancestor::div[contains(@class,'p-dropdown')]//div[@role='button']");
    private final By rdo_StatusActive = By.id("id-Status-Active");
    private final By rdo_StatusInactive = By.xpath("//div/div/div/p-radiobutton[1]/div/div[2]");
    private final By rdo_StatusBoth = By.id("id-Status-Both");
    private final By btn_Reset = By.xpath("//button[.//span[text()='Reset']]");
    private final By btn_Search = By.xpath("//button[.//span[text()='Search']]");
    private final By toastMessage = By.xpath("//div[@role='alert']");
    public TravellerDetailsSearchPage(SHAFT.GUI.WebDriver driver) {

        this.driver = driver;
    }
    public void ElementClick(By by){

        driver.element().click(by);
    }

    public void openTravellerDetailsCreationForm()
    {
        ElementClick(btn_AddTraveller);
    }
    public void ElementType(By by,String Value)
    {
        driver.element().type(by,Value);
    }
    public void selectCoutry(String Country) {
        ElementClick(ddl_Country);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + Country + "']"));
    }
    public void AssertThatToastMessageDisplayed(String expectedText)
    {
        driver.assertThat()
                .element(toastMessage)
                .text()
                .contains(expectedText)
                .perform();
    }
    public TravellerDetailsSearchPage SearchByClientID(String ClientID){
        ResetFields();
        ElementType(txt_ClientID,ClientID);
        ElementClick(btn_Search);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage SearchByClientName(String ClientName){
        ResetFields();
        ElementType(txt_ClientName,ClientName);
        ElementClick(btn_Search);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage SearchByClientEmail(String Email){
        ResetFields();
        ElementType(txt_Email,Email);
        ElementClick(btn_Search);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage SearchByClientPhoneNumber(String PhoneNumber){
        ResetFields();
        ElementType(txt_PhoneNumber,PhoneNumber);
        ElementClick(btn_Search);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage SearchByClientCoutry(String Country){
        ResetFields();
        selectCoutry(Country);
        ElementClick(btn_Search);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage SearchByAllFields(String ClientID,String ClientName,String Email,String PhoneNumber,String Country){
        ResetFields();
        ElementType(txt_ClientID,ClientID);
        ElementType(txt_ClientName,ClientName);
        ElementType(txt_Email,Email);
        ElementType(txt_PhoneNumber,PhoneNumber);
        selectCoutry(Country);
        ElementClick(btn_Search);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage DeactivateTraveller(String ClientID) {
        SearchByClientID(ClientID);
        By deactivateIcon = By.xpath(
                "//tbody//tr[td[contains(normalize-space(),'" + ClientID + "')]]//*[@ptooltip='Deactivate']"
        );
        driver.element().waitToBeReady(deactivateIcon);
        ElementClick(deactivateIcon);
        return new TravellerDetailsSearchPage(driver);
    }
    public TravellerDetailsSearchPage ActivateTraveller(String ClientID) {
        ResetFields();
        ElementType(txt_ClientID,ClientID);
        ElementClick(rdo_StatusInactive);
        ElementClick(btn_Search);
        By activateIcon = By.xpath(
                "//tbody//tr[td[contains(normalize-space(),'" + ClientID + "')]]//*[@ptooltip='Activate']"
        );
        driver.element().waitToBeReady(activateIcon);
        ElementClick(activateIcon);
        return new TravellerDetailsSearchPage(driver);
    }
    public int getColumnIndex(String columnName) {

        int headersCount = driver.element().getElementsCount(By.xpath("//table//thead//th"));

        for (int i = 1; i <= headersCount; i++) {

            String headerText = driver.element().getText(
                    By.xpath("(//table//thead//th)[" + i + "]")
            ).trim();

            if (headerText.equalsIgnoreCase(columnName)) {
                return i;
            }
        }

        throw new RuntimeException("Column not found: " + columnName);
    }
    public boolean AssertThatCorrectDataAreDisplayed(String columnName,String expectedValue) {

        int columnIndex = getColumnIndex(columnName);

        driver.element().waitToBeReady(
                By.xpath("//table//tbody")
        );

        List<WebElement> visibleRows = driver.getDriver().findElements(
                By.xpath("//table//tbody//tr[td]")
        );

        if (visibleRows.isEmpty()) {
            return false;
        }

        int visibleRowIndex = 0;

        for (WebElement row : visibleRows) {

            try {

                if (!row.isDisplayed()) {
                    continue;
                }

                visibleRowIndex++;

                By cellLocator = By.xpath(
                        "(//table//tbody//tr[td])[" +
                                visibleRowIndex +
                                "]/td[" + columnIndex + "]"
                );

                String actualValue = driver.element()
                        .getText(cellLocator)
                        .trim();

                if (!actualValue.toLowerCase()
                        .contains(expectedValue.trim().toLowerCase())) {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }
    public void ResetFields(){
        ElementClick(btn_Reset);
    }
}