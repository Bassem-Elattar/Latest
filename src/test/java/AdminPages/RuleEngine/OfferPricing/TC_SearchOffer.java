package AdminPages.RuleEngine.OfferPricing;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.RuleEngine.RuleEngine_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class TC_SearchOffer {
    private SearchOffer_Page SearchOffer;
    private LogIn_Page logIn;
    private AdminPages.Helper.PaginationHelper paginationHelper;
    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;



    @BeforeTest
    public void sign() throws InterruptedException {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("Searchoffer.json");
        SearchOffer = new SearchOffer_Page(driver);
    }

    @Test
    public void Searchoffer() throws InterruptedException {
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        SearchOffer = new SearchOffer_Page(driver);
        String Discount = testData.getTestData("Discount");
        new RuleEngine_Common(driver).clickRuleEngine().clickOfferPricing();
        SearchOffer.SetSearchOffer(Discount);
        SearchOffer.setBoth();
        SearchOffer.search();
        String remarksup = testData.getTestData("remarksup");
        SearchOffer.setThumpUp(remarksup);
        SearchOffer.performAssertions();

        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Perform assertions
            SearchOffer.performAssertions();

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }


       }


    }

}

