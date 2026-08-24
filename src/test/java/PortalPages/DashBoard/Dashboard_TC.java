package PortalPages.DashBoard;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class Dashboard_TC {

    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Dashboard_Page dashboard;


    // =========================================================
    // Setup
    // =========================================================

    @BeforeMethod
    public void setup() {

        CommonMethod.setupDriver(
                DataUtils.get("browser")
        );

        driver = CommonMethod.getDriver();

        driver.browser()
                .navigateToURL(
                        DataUtils.get("Portal_Url")
                );

        new Login_Page(driver)
                .PortalLogin();

        testData =
                new SHAFT.TestData.JSON(
                        "DashBoardPortal.json"
                );

        dashboard =
                new Dashboard_Page(driver);
    }


    // =========================================================
    // Verify Dashboard Loaded
    // =========================================================

    @Test(priority = 2)
    public void verifyDashboardLoadedSuccessfully() {

        driver.assertThat()
                .element(dashboard.getBTN_BOOK_FLIGHT())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getLBL_TOTAL_FLIGHTS())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getLBL_PENDING_FLIGHTS())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getLBL_TICKETED_FLIGHTS())
                .isVisible()
                .perform();    }


    // =========================================================
    // Verify Flight Statistics
    // =========================================================

    @Test(priority = 3)
    public void verifyFlightStatisticsCalculation() {

        dashboard
                .selectFlightPeriod(
                        testData.getTestData("flightPeriod")
                )
                .verifyFlightStatisticsCalculation();
    }


    // =========================================================
    // Verify Book Flight Navigation
    // =========================================================

    @Test(priority = 4)
    public void verifyBookFlightNavigation() {

        driver.assertThat()
                .element(dashboard.getBTN_BOOK_FLIGHT())
                .attribute("href")
                .isEqualTo(testData.getTestData("link"))
                .perform();
        dashboard.clickBookFlight();

    }



    // =========================================================
    // Verify Flight Period Change
    // =========================================================

    @Test(priority = 5)
    public void verifyFlightPeriodChange() {

        dashboard
                .selectFlightPeriod(
                        testData.getTestData("flightPeriod")
                );
        driver.assertThat()
                .element(dashboard.getLBL_SELECTED_FLIGHT_PERIOD())
                .text()
                .isEqualTo(testData.getTestData("flightPeriod"))
                .perform();
    }




    // =========================================================
    // Verify Add Task
    // =========================================================

    @Test(priority = 6)
    public void verifyAddTask() {

        String taskName =
                testData.getTestData("todoName");

        dashboard
                .clickAddTask()
                .enterTask(taskName)
                .enterTaskDate(
                        testData.getTestData("todoDate")
                )
                .clickAddTaskSubmit();

        driver.assertThat()
                .element(dashboard.getaddedMessageLocator())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getaddedMessageLocator())
                .text()
                .isEqualTo("Task updated successfully")
                .perform();
        dashboard.verifyTodoDisplayed(taskName);
    }


    // =========================================================
    // Verify Edit Task
    // =========================================================

    @Test(priority = 7)
    public void verifyEditTask() {

        String oldTask =
                testData.getTestData("todoName");

        String newTask =
                testData.getTestData("newtask");

        dashboard
                .clickEditTodo(oldTask)
                .enterTask(newTask)
                .clickUpdateTask();
        driver.assertThat()
                .element(dashboard.getupdatedMessageLocator())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getupdatedMessageLocator())
                .text()
                .isEqualTo("Task updated successfully")
                .perform();

        dashboard.verifyTodoDisplayed(newTask);
    }


    // =========================================================
    // Verify Delete Task
    // =========================================================

    @Test(priority = 8)
    public void verifyDeleteTask() {

        String taskName =
                testData.getTestData("newtask");

        dashboard
                .clickDeleteTodo(taskName);
        driver.assertThat()
                .element(dashboard.getdeletedMessageLocator())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getdeletedMessageLocator())
                .text()
                .isEqualTo("Task deleted successfully")
                .perform();
    }


    // =========================================================
    // Verify Promotions
    // =========================================================

    @Test(priority = 9)
    public void verifyPromotions() {

        driver.assertThat()
                .element(dashboard.getLBL_PROMOTIONS())
                .isVisible()
                .perform();

        driver.assertThat()
                .element(dashboard.getPROMOTION_CAROUSEL())
                .isVisible()
                .perform();
        dashboard.clickNextPromotion();
        driver.assertThat()
                .element(dashboard.getACTIVE_PROMOTION())
                .isVisible()
                .perform();
        dashboard.clickPreviousPromotion();
    }


    // =========================================================
    // DB-011
    // End To End Dashboard Scenario
    // =========================================================

    @Test(priority = 1)
    public void endToEndDashboard() {

        String todoName =
                testData.getTestData("todoName");

        String newTask =
                testData.getTestData("newtask");

        dashboard
                // Dashboard
//                .verifyDashboardLoaded()

                // Statistics
                .selectFlightPeriod(
                        testData.getTestData("flightPeriod")
                )
                .verifyFlightStatisticsCalculation()

                // Add
                .clickAddTask()
                .enterTask(todoName)
                .enterTaskDate(
                        testData.getTestData("todoDate")
                )
                .clickAddTaskSubmit()
//                .verifyTaskAddedSuccessfully()
                .verifyTodoDisplayed(todoName)

                // Edit
                .clickEditTodo(todoName)
                .enterTask(newTask)
                .clickUpdateTask()
//                .verifyTaskUpdatedSuccessfully()
                .verifyTodoDisplayed(newTask)

                // Delete
                .clickDeleteTodo(newTask)
//                .verifyTaskDeletedSuccessfully()

                // Promotions
//                .verifyPromotionsDisplayed()
//                .verifyActivePromotionDisplayed()
                .clickNextPromotion()
//                .verifyActivePromotionDisplayed()
                .clickPreviousPromotion()

                // Book Flight
                .clickBookFlight();
    }


    // =========================================================
    // Tear Down
    // =========================================================

    @AfterMethod
    public void tearDown() {

        CommonMethod.quitDriver();
    }
}
