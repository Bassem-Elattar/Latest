package AdminPages.Master.PaymentGateway.PaymentMethod;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class PaymentMethod_Page {
    private SHAFT.GUI.WebDriver driver;

    private final By addPaymentMethod_btn = By.xpath("//span[contains(text(),'Add Payment Method')]/parent::button");
    private final By paymentName_input = By.id("id-Payment");
    private final By description_input = By.id("id-Description");
    private final By save_btn = By.xpath("//span[text()='Save']/parent::button");
    private final By update_btn = By.xpath("//button[@type='submit']");
    private final By btn_Next = By.xpath("(//button[contains(@class,'next')])[1]");
    private final By success_toast = By.xpath("(//div[@role='alert' and contains(@class,'toast-message')])[last()]");
    private By dynamicToast(String expectedText) {
        return By.xpath("(//div[@role='alert' and contains(@class,'toast-message') and contains(.,'" + expectedText + "')])[last()]");
    }
    private By nameInTable(String paymentName) {
        return By.xpath("//td[text()='" + paymentName + "']");
    }

    private By editBtn(String paymentName) {
        return By.xpath("(//tr[td[text()='" + paymentName + "']]//i[contains(@class,'pi-pencil')])[last()]");
    }

    private By actionBtn(String paymentName) {
        return By.xpath("(//tr[td[text()='" + paymentName + "']]//div[contains(@class,'action')])[last()]");
    }

    private By inactiveCircle(String paymentName) {
        return By.xpath("(//tr[td[text()='" + paymentName + "']]//i[contains(@class,'pi-circle')])[last()]");
    }

    public PaymentMethod_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    private void navigateToPayment(String paymentName) {
        boolean isFound = driver.getDriver().findElements(nameInTable(paymentName)).size() > 0;

        while (!isFound) {
            if (driver.getDriver().findElements(btn_Next).size() > 0 &&
                    driver.getDriver().findElement(btn_Next).isEnabled()) {
                driver.element().click(btn_Next);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isFound = driver.getDriver().findElements(nameInTable(paymentName)).size() > 0;
            } else {
                break;
            }
        }
    }

    public void addNewPayment(String name, String desc) {
        driver.element().click(addPaymentMethod_btn)
                .type(paymentName_input, name)
                .type(description_input, desc)
                .click(save_btn);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public void editPayment(String paymentName, String newDesc) {
        navigateToPayment(paymentName);
        driver.element().click(editBtn(paymentName))
                .waitToBeReady(description_input)
                .clear(description_input)
                .type(description_input, newDesc)
                .click(update_btn);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public void togglePaymentStatus(String paymentName) {
        navigateToPayment(paymentName);
        driver.element().click(actionBtn(paymentName));
    }

    public void validateSuccessMessage(String expectedText) {
        driver.assertThat()
                .element(dynamicToast(expectedText))
                .exists()
                .perform();
    }
    public void validatePaymentExists(String paymentName) {
        navigateToPayment(paymentName);
        driver.assertThat()
                .element(nameInTable(paymentName))
                .exists()
                .perform();
    }

    public void validateActivationCircleChanged(String paymentName) {
        driver.assertThat()
                .element(inactiveCircle(paymentName))
                .attribute("class")
                .doesNotContain("fill")
                .perform();
    }
}