package AdminPages.Helper;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class PaginationHelper {
    private final SHAFT.GUI.WebDriver driver;
    private List<WebElement> pagesLink;
    private By nextBtn;

    public PaginationHelper(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    public int getTotalPages() {
        int totalPages;
        System.out.println("Entering getPages() method");

        // Attempt to find pagination elements with the class .page-no selected
        pagesLink = driver.getDriver().findElements(By.xpath("//button[contains(@class,'page-no')]"));
        System.out.println("Found " + pagesLink.size() + " elements with .page-no");

        if (pagesLink.size() == 0) {
            // If no elements were found, try another selector
            pagesLink = driver.getDriver().findElements(By.xpath("//button[contains(text(),'2')]"));
            System.out.println("Found " + pagesLink.size() + " elements with alternative selector");
        }

        // Calculate total pages
        if (pagesLink.size() > 0) {
            // Assuming the last page number is the total number of pages
            String lastPageNumber = pagesLink.get(pagesLink.size() - 1).getText();
            totalPages = Integer.parseInt(lastPageNumber);
            System.out.println("Total pages: " + totalPages);

            nextBtn = By.xpath("//button[@class='next']");
        } else {
            totalPages = 1; // Assume 1 page if no pagination found
            System.out.println("No pagination found, assuming single page");
            nextBtn = null;
        }
        return totalPages;
    }

    public void navigateToNextPage() throws InterruptedException {
        if (nextBtn != null && driver.element().getElementsCount(nextBtn) > 0) {
            System.out.println("Clicking on the next button to move to the next page");
            driver.element().click(nextBtn);
            // Wait for the page to load before continuing
            Thread.sleep(1000);
        } else {
            System.out.println("Reached the last page or no more pages available.");
        }
    }

    By Pagination = By.xpath("//span[@class=\"p-dropdown-trigger-icon pi pi-chevron-down\"]");

    public void setPagination(String pagination){
        driver.element().click(Pagination);
        By option = By.xpath(String.format("//span[text()='%s']", pagination));
        driver.element().click(option);

    }
}
