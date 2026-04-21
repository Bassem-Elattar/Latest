package AdminPages.Master.Flight.BSPCommission;

import AdminPages.Master.Flight.BSPCommission_Page;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Master.Master_Common;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class CreateBSPCommission_TC extends TestBase_TC {
    private BSPCommission_Page createBSPCommission;

    private LogIn_Page logIn;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }


    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }

    @Test(dataProvider = "JsonProvider")
    public void CreateBSPCommission(Map<String,String> create) throws InterruptedException {
        createBSPCommission = new BSPCommission_Page(driver);
        new Master_Common(driver).clickMaster()
                .clickFlight()
                .clickBSP();
        createBSPCommission.AddCommission();
        String AirlineName = create.get("AirlineName");
        String CommissionName = create.get("CommissionName");
        String GDSSupplier = create.get("GDSSupplier");
        String SupplierCredential = create.get("SupplierCredential");
        String JourneyType = create.get("JourneyType");
        String TicketDesignator = create.get("TicketDesignator");
        String TourCode = create.get("TourCode");
        String OriginAirport = create.get("OriginAirport");
        String DestinationAirport = create.get("DestinationAirport");
        String OriginCity = create.get("OriginCity");
        String DestinationCity = create.get("DestinationCity");
        String OriginCountry = create.get("OriginCountry");
        String DestinationCountry = create.get("DestinationCountry");
        String FareBasisCode = create.get("FareBasisCode");
        String ValidityPeriodFromDay = create.get("ValidityPeriodFromDay");
        String ValidityPeriodFromMonth = create.get("ValidityPeriodFromMonth");
        String ValidityPeriodFromYear = create.get("ValidityPeriodFromYear");
        String ValidityPeriodToDay = create.get("ValidityPeriodToDay");
        String ValidityPeriodToMonth = create.get("ValidityPeriodToMonth");
        String ValidityPeriodToYear = create.get("ValidityPeriodToYear");
        String OutboundPeriodFrom = create.get("OutboundPeriodFrom");
        String OutboundPeriodTo = create.get("OutboundPeriodTo");
        String InboundPeriodFrom = create.get("InboundPeriodFrom");
        String InboundPeriodTo = create.get("InboundPeriodTo");
        String BlackoutInboundPeriodFrom = create.get("BlackoutInboundPeriodFrom");
        String BlackoutInboundPeriodTo = create.get("BlackoutInboundPeriodTo");
        String BlackoutOutboundPeriodFrom = create.get("BlackoutOutboundPeriodFrom");
        String BlackoutOutboundPeriodTo = create.get("BlackoutOutboundPeriodTo");
        String EnterValue = create.get("EnterValue");
        String FareType = create.get("FareType");
        String Amount = create.get("Amount");
        String A = create.get("A");
        String B = create.get("B");
        String C = create.get("C");
        String D = create.get("D");
        String E = create.get("E");
        String F = create.get("F");
        String G = create.get("G");
        String H = create.get("H");
        String I = create.get("I");
        String J = create.get("J");
        String K = create.get("K");
        String L = create.get("L");
        String M = create.get("M");
        String N = create.get("N");
        String O = create.get("O");
        String P = create.get("P");
        String Q = create.get("Q");
        String R = create.get("R");
        String S = create.get("S");
        String U = create.get("U");
        String V = create.get("V");
        String W = create.get("W");
        String X = create.get("X");
        String Y = create.get("Y");
        String Z = create.get("Z");
        createBSPCommission.AirlineName(AirlineName);
        createBSPCommission.CommissionName(CommissionName);
        createBSPCommission.GDSSupplier(GDSSupplier);
        createBSPCommission.SupplierCredential(SupplierCredential);
        createBSPCommission.JourneyType(JourneyType);
        createBSPCommission.AirPort();
        createBSPCommission.OriginAirPort(OriginAirport);
        createBSPCommission.DestinationAirPort(DestinationAirport);
        createBSPCommission.Adult();
        createBSPCommission.searchValidFromDate(ValidityPeriodFromDay, ValidityPeriodFromYear, ValidityPeriodFromMonth);
        createBSPCommission.searchValidToDate(ValidityPeriodToDay, ValidityPeriodToYear, ValidityPeriodToMonth);
        createBSPCommission.Economy();
        createBSPCommission.EnterValue(EnterValue);
        createBSPCommission.FareType(FareType);
        createBSPCommission.Amount(Amount);
        createBSPCommission.setSubmit();
        String Expected = "Added Successfully";
        Assert.assertEquals(createBSPCommission.Actual(),Expected);
    }
}
