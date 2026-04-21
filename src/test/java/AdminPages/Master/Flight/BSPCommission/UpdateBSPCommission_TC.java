package AdminPages.Master.Flight.BSPCommission;

import AdminPages.Master.Flight.BSPCommission_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class UpdateBSPCommission_TC extends TestBase_TC {
    private BSPCommission_Page createBSPCommission;
    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;
    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        testData = new SHAFT.TestData.JSON("CreateBSPCommission.json");
    }

    @Test()
    public void UpdateBSPCommission() throws InterruptedException {
        createBSPCommission = new BSPCommission_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickBSP();
        createBSPCommission.setCommissionName(testData.getTestData("SearchCommissionName"));
        createBSPCommission.setBoth();
        createBSPCommission.setSearchButton();
        createBSPCommission.setUpdateButton();
        Thread.sleep(3000);
        createBSPCommission.setRemarks("Approved");
        createBSPCommission.setApprove();
        String Expected = "Updated Successfully";
        Assert.assertEquals(createBSPCommission.Actualupdate(),Expected);
    }
}
