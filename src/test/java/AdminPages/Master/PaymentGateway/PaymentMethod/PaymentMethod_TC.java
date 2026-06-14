package AdminPages.Master.PaymentGateway.PaymentMethod;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass; // غيرنا دي
import org.testng.annotations.Test;

public class PaymentMethod_TC extends TestBase_TC {
    private PaymentMethod_Page paymentPage;
    private Faker faker = new Faker();

    @BeforeClass

    public void setUp() {
        new LogIn_Page(driver).ClickAdmin().ClickOnLoginButton();
        paymentPage = new PaymentMethod_Page(driver);
    }

    @Test(priority = 1)
    public void testAddNewPayment() {
        new Master_Common(driver).clickMaster().clickpaymentGateWay().clickPaymentMethod();
        String name = faker.name().firstName();
        paymentPage.addNewPayment(name, "Initial Description");
        paymentPage.validateSuccessMessage("Payment method created successfully");
        paymentPage.validatePaymentExists(name);
    }

    @Test(priority = 2, dependsOnMethods = "testAddNewPayment")
    public void testEditPayment() {
        String name = faker.name().firstName();
        paymentPage.addNewPayment(name, "To Be Edited");
        paymentPage.editPayment(name, "Updated Description");
        paymentPage.validateSuccessMessage("Payment method updated successfully");
    }

    @Test(priority = 3, dependsOnMethods = "testEditPayment")
    public void testToggleStatus() {
        String name = faker.name().firstName();
        paymentPage.addNewPayment(name, "Status Toggle Test");
        paymentPage.togglePaymentStatus(name);
        paymentPage.validateSuccessMessage(name + " is Disabled Successfully");
        paymentPage.validateActivationCircleChanged(name);
    }

}