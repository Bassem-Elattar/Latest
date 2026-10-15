package AdminPages.BookingMidOffice.Booking;

import AdminPages.Login.LogIn_Page;
import Drive_Factory.CommonMethod;
import com.fasterxml.jackson.databind.JsonNode;
import com.shaft.driver.SHAFT;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.DataUtils;

import java.io.IOException;
import java.lang.reflect.Method;

// Ancillaries (bundle / meals / baggage) E2E: one test case per scenario in Ancillaries.json.
// Each @Test's description is the scenario's exact JSON "Name"; the data, rules and checks stay in
// the JSON and Ancillaries_Page. A new JSON scenario needs a matching @Test here.
// Disabled scenarios and ones filtered out by -Dscenario / -Dtags are skipped before the browser opens.
// Scenarios, rules and command-line options are described in the JSON ("_HowToUse") and in
// docs/Ancillaries-QC-Coverage.md.
public class Ancillaries_TC implements ITest {

    SHAFT.GUI.WebDriver driver;
    private final ThreadLocal<String> scenarioName = new ThreadLocal<>();
    private final ThreadLocal<JsonNode> scenario = new ThreadLocal<>();

    // Shows each scenario under its JSON "Name" in the TestNG / Allure report.
    @Override
    public String getTestName() {
        return scenarioName.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void sign(Method method) throws IOException {
        String name = method.getAnnotation(Test.class).description();
        scenarioName.set(name);
        scenario.set(Ancillaries_Page.findScenario(name));
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).AdminLogin();
    }

    private void run() throws Exception {
        new Ancillaries_Page(driver).runScenario(scenarioName.get(), scenario.get());
    }

    // ---------------------------------------------------------------- one way

    @Test(description = "OW dry run - per-leg rules, no booking")
    public void OW_DryRun_PerLegRules_NoBooking() throws Exception {
        run();
    }

    @Test(description = "OW 1A+1C - bundle only")
    public void OW_1A1C_BundleOnly() throws Exception {
        run();
    }

    @Test(description = "OW 2A - meal on every leg for every passenger")
    public void OW_2A_MealOnEveryLegForEveryPassenger() throws Exception {
        run();
    }

    @Test(description = "OW 1A+1C - different baggage per passenger")
    public void OW_1A1C_DifferentBaggagePerPassenger() throws Exception {
        run();
    }

    @Test(description = "OW 1A+1C - free meals pax 1, paid meals pax 2, baggage pax 1")
    public void OW_1A1C_FreeMealsPax1_PaidMealsPax2_BaggagePax1() throws Exception {
        run();
    }

    @Test(description = "OW 1A+1C - per-leg control: leg 1 FIRST for all, leg 2 SECOND for pax 1 only")
    public void OW_1A1C_PerLegControl() throws Exception {
        run();
    }

    @Test(description = "OW 1A - change meal and baggage before booking, only the final choice is charged")
    public void OW_1A_ChangeMealAndBaggageBeforeBooking() throws Exception {
        run();
    }

    @Test(description = "OW 1A+1C+1I - everything, infant gets nothing")
    public void OW_1A1C1I_Everything_InfantGetsNothing() throws Exception {
        run();
    }

    @Test(description = "OW 1A+1C - hold then pay, everything")
    public void OW_1A1C_HoldThenPay_Everything() throws Exception {
        run();
    }

    // ---------------------------------------------------------------- round trip

    @Test(description = "RT 1A+1C - everything on both journeys")
    public void RT_1A1C_EverythingOnBothJourneys() throws Exception {
        run();
    }

    @Test(description = "RT 1A+1C - different bundle and baggage per journey")
    public void RT_1A1C_DifferentBundleAndBaggagePerJourney() throws Exception {
        run();
    }

    @Test(description = "RT 2A - meals outbound only, baggage return only")
    public void RT_2A_MealsOutboundOnly_BaggageReturnOnly() throws Exception {
        run();
    }

    @Test(description = "RT 1A+1C - hold then pay, everything")
    public void RT_1A1C_HoldThenPay_Everything() throws Exception {
        run();
    }

    // ---------------------------------------------------------------- multi city

    @Test(description = "MC 1A+1C - everything on every leg")
    public void MC_1A1C_EverythingOnEveryLeg() throws Exception {
        run();
    }

    @Test(description = "MC 2A - pax 1 meal on first leg only, pax 2 baggage on last journey only")
    public void MC_2A_Pax1MealFirstLeg_Pax2BaggageLastJourney() throws Exception {
        run();
    }

    @Test(description = "MC 1A+1C - hold then pay, everything")
    public void MC_1A1C_HoldThenPay_Everything() throws Exception {
        run();
    }

    // ---------------------------------------------------------------- negative

    @Test(description = "Negative - bundle is required to continue")
    public void Negative_BundleIsRequired() throws Exception {
        run();
    }

    @Test(description = "Negative - infant has no ancillaries tab")
    public void Negative_InfantHasNoAncillariesTab() throws Exception {
        run();
    }

    @Test(description = "Negative - clearing meals and baggage removes their price")
    public void Negative_ClearingMealsAndBaggageRemovesPrice() throws Exception {
        run();
    }

    @AfterMethod(alwaysRun = true)
    public void Reload() {
        try {
            new LogIn_Page(driver).ClickOnLogOuTButton();
        } catch (Throwable ignored) {
            // Session may already be gone after a hard failure, or never opened for a skipped scenario.
        }
    }
}
