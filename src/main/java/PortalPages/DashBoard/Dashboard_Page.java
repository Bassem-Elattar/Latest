package PortalPages.DashBoard;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class Dashboard_Page {

    private final SHAFT.GUI.WebDriver driver;

    public Dashboard_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    private final By LBL_TOTAL_FLIGHTS =
            By.cssSelector(
                    ".flights-summary__stat-value" +
                            ":not(.flights-summary__stat-value--pending)" +
                            ":not(.flights-summary__stat-value--ticketed)"
            );
    private final By LBL_PENDING_FLIGHTS =
            By.cssSelector(".flights-summary__stat-value--pending");
    private final By LBL_TICKETED_FLIGHTS =
            By.cssSelector(".flights-summary__stat-value--ticketed");
    private final By BTN_BOOK_FLIGHT =
            By.cssSelector("a.flights-summary__book-btn");
    private final By BTN_FLIGHT_PERIOD_TRIGGER =
            By.cssSelector(
                    ".flights-summary__actions .p-dropdown-trigger"
            );
    private final By LBL_SELECTED_FLIGHT_PERIOD =
            By.cssSelector(
                    ".flights-summary__actions .p-dropdown-label"
            );

    private final By BTN_ADD_TASK =
            By.cssSelector("button[aria-label='Add task']");

    private final By TXT_TASK =
            By.xpath("//textarea[@placeholder='Add Task...']");

    private final By TXT_TASK_DATE =
            By.id("id-Date");

    private final By BTN_ADD_TASK_SUBMIT =
            By.xpath(
                    "//button[@type='submit']" +
                            "[.//span[normalize-space()='Add Task']]"
            );
    private final By LBL_TASK_ADDED_SUCCESS =
            By.cssSelector("div[role='alert'][aria-label='Task added successfully']");
    private final By BTN_UPDATE_TASK =
            By.xpath(
                    "//button[@type='submit']" +
                            "[.//span[normalize-space()='Update Task']]"
            );
    private final By LBL_TASK_UPDATED_SUCCESS =
            By.cssSelector(
                    "div[role='alert'][aria-label='Task updated successfully']"
            );
    private final By LBL_TASK_DELETED_SUCCESS =
            By.cssSelector(
                    "div[role='alert'][aria-label='Task deleted successfully']"
            );
    private final By LBL_PROMOTIONS =
            By.xpath("//span[normalize-space()='Promotions']");
    private final By BTN_PREVIOUS_PROMOTION =
            By.cssSelector(
                    "button[aria-label='Previous Promotion']"
            );

    private final By BTN_NEXT_PROMOTION =
            By.cssSelector(
                    "button[aria-label='Next Promotion']"
            );

    private final By PROMOTION_CAROUSEL =
         //   By.id("promotionsCarousel");
            By.cssSelector(".carousel");
    private final By ACTIVE_PROMOTION =
            By.cssSelector(
                    "#promotionsCarousel .carousel-item.active"
            );
    private By getTodoItem(String taskName) {

        return By.xpath(
                "(//div[contains(@class,'todo-item')]" +
                        "[.//div[contains(@class,'todo-title') " +
                        "and normalize-space()='" +
                        taskName +
                        "']])[1]"
        );
    }


    private By getTodoEditButton(String taskName) {

        return By.xpath(
                "(//div[contains(@class,'todo-item')]" +
                        "[.//div[contains(@class,'todo-title') " +
                        "and normalize-space()='" +
                        taskName +
                        "']]" +
                        "//button[contains(@class,'todo-edit')])[1]"
        );
    }


    private By getTodoDeleteButton(String taskName) {

        return By.xpath(
                "(//div[contains(@class,'todo-item')]" +
                        "[.//div[contains(@class,'todo-title') " +
                        "and normalize-space()='" +
                        taskName +
                        "']]" +
                        "//button[contains(@class,'todo-delete')])[1]"
        );
    }

    public By getLBL_TOTAL_FLIGHTS() {
        return LBL_TOTAL_FLIGHTS;
    }
    public By getLBL_PENDING_FLIGHTS() {
        return LBL_PENDING_FLIGHTS;
    }

    public By getLBL_TICKETED_FLIGHTS() {
        return LBL_TICKETED_FLIGHTS;
    }

    public By getBTN_BOOK_FLIGHT() {
        return BTN_BOOK_FLIGHT;
    }

    public By getLBL_SELECTED_FLIGHT_PERIOD() {
        return LBL_SELECTED_FLIGHT_PERIOD;
    }

    public By getaddedMessageLocator() {
        return LBL_TASK_ADDED_SUCCESS;
    }

    public By getupdatedMessageLocator() {
        return LBL_TASK_UPDATED_SUCCESS;
    }

    public By getdeletedMessageLocator() {
        return LBL_TASK_DELETED_SUCCESS;
    }

    public By getLBL_PROMOTIONS() {
        return LBL_PROMOTIONS;
    }


    public By getPROMOTION_CAROUSEL (){
        return PROMOTION_CAROUSEL;
    }

    public By getACTIVE_PROMOTION() {
        return ACTIVE_PROMOTION;
    }

    public String getTotalFlights() {
        return driver.element().getText(LBL_TOTAL_FLIGHTS);
    }

    public String getPendingFlights() {
        return driver.element().getText(LBL_PENDING_FLIGHTS);
    }

    public String getTicketedFlights() {
        return driver.element().getText(LBL_TICKETED_FLIGHTS);
    }

    public Dashboard_Page verifyFlightStatisticsCalculation() {

        int total = Integer.parseInt(getTotalFlights());
        int pending = Integer.parseInt(getPendingFlights());
        int ticketed = Integer.parseInt(getTicketedFlights());

        SHAFT.Validations.assertThat()
                .object(total)
                .isEqualTo(pending + ticketed);

        return this;
    }

    public Dashboard_Page clickBookFlight() {

        driver.element()
                .click(BTN_BOOK_FLIGHT);

        return this;
    }

    public Dashboard_Page clickFlightPeriodDropdown() {

        driver.element()
                .click(BTN_FLIGHT_PERIOD_TRIGGER);

        return this;
    }


    public Dashboard_Page selectFlightPeriod(String period) {

        clickFlightPeriodDropdown();

        By option = By.xpath(
                "//li[@role='option']" +
                        "//span[normalize-space()='" +
                        period +
                        "']"
        );

        driver.element()
                .click(option);

        return this;
    }

    public Dashboard_Page clickAddTask() {

        driver.element()
                .click(BTN_ADD_TASK);

        return this;
    }
    public Dashboard_Page enterTask(String task) {

        driver.element()
                .type(TXT_TASK, task);

        return this;
    }
    public Dashboard_Page enterTaskDate(String date) {

        driver.element()
                .type(TXT_TASK_DATE, date);

        return this;
    }
    public Dashboard_Page clickAddTaskSubmit() {

        driver.element()
                .click(BTN_ADD_TASK_SUBMIT);

        return this;
    }

    public Dashboard_Page clickUpdateTask() {

        driver.element()
                .click(BTN_UPDATE_TASK);

        return this;
    }

    public Dashboard_Page verifyTodoDisplayed(String taskName) {

        driver.assertThat()
                .element(getTodoItem(taskName))
                .isVisible()
                .perform();

        return this;
    }

    public Dashboard_Page clickEditTodo(String taskName) {

        driver.element()
                .click(getTodoEditButton(taskName));

        return this;
    }

    public Dashboard_Page clickDeleteTodo(String taskName) {

        driver.element()
                .click(getTodoDeleteButton(taskName));

        return this;
    }

    public Dashboard_Page clickNextPromotion() {

        driver.element()
                .click(BTN_NEXT_PROMOTION);

        return this;
    }
    public Dashboard_Page clickPreviousPromotion() {

        driver.element()
                .click(BTN_PREVIOUS_PROMOTION);

        return this;
    }
}
