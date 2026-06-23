package AdminPages.BookingMidOffice.ManualBookingInvoice;

import AdminPages.BookingMidOffice.ManualBookingInvoice.PaxDetailsPage;
import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.Login.TestBase_TC;
import AdminPages.RuleEngine.Discount.Discount_Page;
import Drive_Factory.CommonMethod;
import AdminPages.Login.LogIn_Page;
import AdminPages.RuleEngine.RuleEngine_Common;

import org.json.JSONException;
import org.testng.annotations.*;
import utilities.DataUtils;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.json.JSONArray;
import org.json.JSONObject;

public class ManualBooking_TC {
    private SHAFT.TestData.JSON testData;
    private ManualBookingInvoice ManualBooking;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;

    @BeforeMethod
    public void setup() {

        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        logIn = new LogIn_Page(driver);
        logIn.AdminLogin();

        new Booking_Common(driver)
                .clickBookingMidOffice()
                .ShowMoreMenu()
                .click_Sub_BookingMidOffice()
                .clickMyManualBookingInvoice();


        testData = new SHAFT.TestData.JSON("ManualInvoice.json");

    }

    @Test(priority = 1
    )
    public void verifyThatAdminCanCreateManualBookingInvoiceSuccessfully() throws InterruptedException {
        ManualBookingInvoice manualBooking =
                new ManualBookingInvoice(driver);

        manualBooking
                .selectBranch()
                .enterFrom(testData.getTestData("from"))
                .enterTo(testData.getTestData("to"))
                .searchValidDate(
                        testData.getTestData("FromDate"),
                        testData.getTestData("FromYear"),
                        testData.getTestData("FromMonth"))
                .selectSupplier(testData.getTestData("supplier"))
                .selectAirline(testData.getTestData("airline"))
                .UploadPDF("src/test/resources/sendGridUsage.pdf")
                .enterBaseADT(testData.getTestData("passengers.adult.baseFare"))
                .enterTaxADT(testData.getTestData("passengers.adult.tax"))

                .enterPNR(testData.getTestData("gdsPnr"))
                .enterAirLine(testData.getTestData("airlinePnr"))
                .enterFlightNo(testData.getTestData("flightNo"))
                .addTraveler();
        Thread.sleep(4000);
        new PaxDetailsPage(driver).fillOnePassengerDetails(
                testData.getTestData("title"),
                testData.getTestData("firstName"),
                testData.getTestData("lastName"),
                testData.getTestData("dateOfBirth"),
                testData.getTestData("email"),
                testData.getTestData("phone"),
                testData.getTestData("documentNumber"),
                testData.getTestData("documentExpiry"),
                testData.getTestData("nationality"));
        manualBooking.enterADTTicketNumber(testData.getTestData("ticketNumber"))
                .selectADTDocumentType();
        Thread.sleep(4000);
        // ================= ASSERT =================

        String baseFare = testData.getTestData("passengers.adult.baseFare");
        String tax = testData.getTestData("passengers.adult.tax");

        String total = String.valueOf(
                Integer.parseInt(baseFare)
                        + Integer.parseInt(tax)
        );
        new ManualBookingInvoice(driver)
//           //     .OpenSegDetails()
//            //    .assertRouteVisible()
//             //   .assertPassengerCountVisible()
//              //  .assertTripTypeVisible()
                //.openSegmentsDetails()
                .assertBaseFare(baseFare)
                .assertTax(tax)
                .assertTotalADT(total);
        manualBooking.clickPay();
        Thread.sleep(4000);
    }





    @Test (priority = 3)
    public void verifyThatAgencyCanCreateManualBookingInvoiceSuccessfully() throws InterruptedException, JSONException {


        ManualBookingInvoice manualBooking =
                new ManualBookingInvoice(driver);

        manualBooking
                .selectBranch()
                .selectAgency(testData.getTestData("agency"))
                .selectAgent(testData.getTestData("agent"))
                .enterFrom(testData.getTestData("from"))
                .enterTo(testData.getTestData("to"))
                .searchValidDate(
                        testData.getTestData("FromDate"),
                        testData.getTestData("FromYear"),
                        testData.getTestData("FromMonth"))
                .clickAddPassenger()
                .selectSupplier(testData.getTestData("supplier"))
                .selectAirline(testData.getTestData("airline"))
                .UploadPDF("src/test/resources/sendGridUsage.pdf")
                .enterBaseADT(testData.getTestData("passengers.adult.baseFare"))
                .enterTaxADT(testData.getTestData("passengers.adult.tax"))
                .enterBaseCHD(testData.getTestData("passengers.child.baseFare"))
                .enterTaxCHD(testData.getTestData("passengers.child.tax"))
                .enterBaseINF(testData.getTestData("passengers.infant.baseFare"))
                .enterTaxINF(testData.getTestData("passengers.infant.tax"))
                .enterPNR(testData.getTestData("gdsPnr"))
                .enterAirLine(testData.getTestData("airlinePnr"))
                .enterFlightNo(testData.getTestData("flightNo"))
                .addTraveler();
        Thread.sleep(4000);


        new PaxDetailsPage(driver).fillOnePassengerDetails(
                testData.getTestData("title"),
                testData.getTestData("firstName"),
                testData.getTestData("lastName"),
                testData.getTestData("dateOfBirth"),
                testData.getTestData("email"),
                testData.getTestData("phone"),
                testData.getTestData("documentNumber"),
                testData.getTestData("documentExpiry"),
                testData.getTestData("nationality"));
       manualBooking.enterADTTicketNumber(testData.getTestData("ticketNumber"))
              .selectADTDocumentType();
        new PaxDetailsPage(driver).selectCHDTraveller()
        .fillCHDPassengerDetails1(
                testData.getTestData("title1"),
                testData.getTestData("firstName1"),
                testData.getTestData("lastName1"),
                testData.getTestData("dateOfBirth1"),
                testData.getTestData("email1"),
                testData.getTestData("phone1"),
                testData.getTestData("documentNumber1"),
                testData.getTestData("documentExpiry1"),
                testData.getTestData("nationality1")


        );
        manualBooking.enterCHDTicketNumber(testData.getTestData("ticketNumber1"))
                .selectCHDDocumentType();
        new PaxDetailsPage(driver).selectINFTraveller()
                .fillINFPassengerDetails1(
                        testData.getTestData("title2"),
                        testData.getTestData("firstName2"),
                        testData.getTestData("lastName2"),
                        testData.getTestData("dateOfBirth2"),
                        testData.getTestData("email2"),
                        testData.getTestData("phone2"),
                        testData.getTestData("documentNumber2"),
                        testData.getTestData("documentExpiry2"),
                        testData.getTestData("nationality2")


                );
        manualBooking.enterINFTicketNumber(testData.getTestData("ticketNumber2"))
                .selectINFDocumentType().selectAssignedUser();
        Thread.sleep(4000);



        // ================= ASSERT =================

        String adultBase = testData.getTestData("passengers.adult.baseFare");
        String adultTax = testData.getTestData("passengers.adult.tax");

        String childBase = testData.getTestData("passengers.child.baseFare");
        String childTax = testData.getTestData("passengers.child.tax");

        String infantBase = testData.getTestData("passengers.infant.baseFare");
        String infantTax = testData.getTestData("passengers.infant.tax");

        int baseTotal =
                Integer.parseInt(adultBase) +
                        Integer.parseInt(childBase) +
                        Integer.parseInt(infantBase);

        int taxTotal =
                Integer.parseInt(adultTax) +
                        Integer.parseInt(childTax) +
                        Integer.parseInt(infantTax);

        int total = baseTotal + taxTotal;
        new ManualBookingInvoice(driver)
                .assertBaseFare_ADT(adultBase)
                .assertTax_ADT(adultTax)
                .assertBaseFarecCHD(childBase)
                .assertTaxCHD(childTax)
                .assertBaseFare_INF(infantBase)
                .assertTax_INF(infantTax)
                .assertTotal(String.valueOf(total));
          manualBooking.clickPay();

    }
    @Test (priority = 2)
    public void verifyThatAdminCanCreateManualBookingInvoiceSuccessfullyWithRuleEngine() throws InterruptedException {
        ManualBookingInvoice manualBooking =
                new ManualBookingInvoice(driver);

        manualBooking
                .selectBranch()
                .enterFrom(testData.getTestData("from"))
                .enterTo(testData.getTestData("to"))
                .searchValidDate(
                        testData.getTestData("FromDate"),
                        testData.getTestData("FromYear"),
                        testData.getTestData("FromMonth"))
                .selectSupplier(testData.getTestData("supplier"))
                .selectAirline(testData.getTestData("airline"))
                .UploadPDF("src/test/resources/sendGridUsage.pdf")
                .enterBaseADT(testData.getTestData("passengers.adult.baseFare"))
                .enterTaxADT(testData.getTestData("passengers.adult.tax"))
                .addRuleEngine(testData.getTestData("markup"), testData.getTestData("servicecharge"), testData.getTestData("discount") )

                .enterPNR(testData.getTestData("gdsPnr"))
                .enterAirLine(testData.getTestData("airlinePnr"))
                .enterFlightNo(testData.getTestData("flightNo"))
                .addTraveler();
        Thread.sleep(4000);
        new PaxDetailsPage(driver).fillOnePassengerDetails(
                testData.getTestData("title"),
                testData.getTestData("firstName"),
                testData.getTestData("lastName"),
                testData.getTestData("dateOfBirth"),
                testData.getTestData("email"),
                testData.getTestData("phone"),
                testData.getTestData("documentNumber"),
                testData.getTestData("documentExpiry"),
                testData.getTestData("nationality"));
        manualBooking.enterADTTicketNumber(testData.getTestData("ticketNumber"))
                .selectADTDocumentType();
        Thread.sleep(4000);
        // ================= ASSERT =================

        String baseFare = testData.getTestData("passengers.adult.baseFare");
        String markUp = testData.getTestData("markup");
        String tax = testData.getTestData("passengers.adult.tax");
        String discount = testData.getTestData("discount");
        String serviceCharge = testData.getTestData("servicecharge");

        String Base = String.valueOf(
                Integer.parseInt(baseFare)
                        + Integer.parseInt(markUp)
        );

        String total = String.valueOf(
                Integer.parseInt(Base)
                        + Integer.parseInt(tax) + Integer.parseInt(serviceCharge) - Integer.parseInt(discount)
        );
        new ManualBookingInvoice(driver)
//           //     .OpenSegDetails()
//            //    .assertRouteVisible()
//             //   .assertPassengerCountVisible()
//              //  .assertTripTypeVisible()
                //.openSegmentsDetails()
                .assertBaseFare(Base)
                .assertTax(tax)
                .assertDiscount(discount)
                .assertServiceCharge(serviceCharge)
                .assertTotalwithRule(total);
          manualBooking.clickPay();
    }
}

