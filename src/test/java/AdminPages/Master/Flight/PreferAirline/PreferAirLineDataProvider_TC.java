package AdminPages.Master.Flight.PreferAirline;
import com.shaft.tools.io.JSONFileManager;
import org.testng.annotations.DataProvider;

public class PreferAirLineDataProvider_TC {
    // Adjust the file path here
    private static JSONFileManager testData = new JSONFileManager("src/test/java/AdminPages/Master/Flight/PreferAirline/PreferAirLineData.json");
    @DataProvider(name = "SearchWith ValidDataPreferAirLine with Name and Supplier name")
    public static Object[][] SearchPreferAirLineDataProvider1() {
        return new Object[][] {
                // { testData.getTestData("validSearch.supplierName")},
                {testData.getTestData("validSearch.AirLineName"), testData.getTestData("validSearch.supplierName")},
                // {testData.getTestData("EmptyFieldsSearch.AirLineName"), testData.getTestData("EmptyFieldsSearch.supplierName")},
        };
    }
    @DataProvider(name = "SearchWith ValidDataPreferAirLine with Name and Supplier name DataNotFounded")
    public static Object[][] SearchPreferAirLineDataProvider7() {
        return new Object[][] {

                {testData.getTestData("validSearch2.AirLineName"), testData.getTestData("validSearch2.supplierName")},
        };
    }
    @DataProvider(name = "SearchWithValidDataPreferAirLine Name only and active status")
    public static Object[][] SearchPreferAirLineDataProvider2() {
        return new Object[][] {

                {testData.getTestData("validSearch.AirLineName")}
        };
    }
    @DataProvider(name = "SearchWithValidDataPreferAirLine Supplier only and inActive Status")
    public static Object[][] SearchPreferAirLineDataProvider3() {
        return new Object[][] {

                {testData.getTestData("validSearch.supplierName")}
        };
    }

    @DataProvider(name = "SearchWith ValidDataPreferAirLine with Name,Supplier name and Both Status")
    public static Object[][] SearchPreferAirLineDataProvider4() {
        return new Object[][] {
                {testData.getTestData("validSearch2.AirLineName"), testData.getTestData("validSearch2.supplierName")}
        };
    }

    @DataProvider(name = "SearchWith InValidDataPreferAirLine with InValidName")
    public static Object[][] SearchPreferAirLineDataProvider5() {
        return new Object[][]{
                {testData.getTestData("invalidSearch.AirLineName")}
        };
    }
    @DataProvider(name = "SearchWithInvalid Arabic NameForSupplierName")
    public static Object[][]SearchPreferAirLineDataProvider6()
    {
        return new Object[][]{
                {testData.getTestData("invalidArabicNameSearch.AirLineName")}

        };


    }

    @DataProvider(name = "CreatePreferAirLineWithValidData")
    public static Object[][]CreatePreferAirLineDataProvider1()
    {
        return new Object[][]{
                {testData.getTestData("validCreate.AirLineName"),testData.getTestData("validCreate.supplierName"),testData.getTestData("validCreate.SupplierPcc.")}

        };


    }
    @DataProvider(name = "CreatePreferAirLineWithValidDataAlreadyExist")
    public static Object[][]CreatePreferAirLineDataProvider2()
    {
        return new Object[][]{
                {testData.getTestData("validCreate1.AirLineName"),testData.getTestData("validCreate1.supplierName"),testData.getTestData("validCreate1.SupplierPcc")}

        };


    }
    @DataProvider(name = "CreatePreferAirLineWithValidDataWithSupplierPCC")
    public static Object[][]CreatePreferAirLineDataProvider3()
    {
        return new Object[][]{
                {testData.getTestData("validCreate2.AirLineName"),testData.getTestData("validCreate2.supplierName"),testData.getTestData("validCreate2.supplierPCC")}

        };


    }
    @DataProvider(name = "CreatePreferAirLineWithValidDataWithSupplierPCCAlreadyExist")
    public static Object[][]CreatePreferAirLineDataProvider4()
    {
        return new Object[][]{
                {testData.getTestData("validCreate2.AirLineName"),testData.getTestData("validCreate2.supplierName"),testData.getTestData("validCreate2.supplierPCC")}

        };


    }
    @DataProvider(name = "CreatePreferAirLineWithINValidName")
    public static Object[][]CreatePreferAirLineDataProvider5()
    {
        return new Object[][]{
                {testData.getTestData("InvalidCreate4.AirLineName"),testData.getTestData("InvalidCreate4.supplierName"),testData.getTestData("InvalidCreate4.supplierPCC")}

        };
    }
    @DataProvider(name = "CreatePreferAirLineWithoutName")
    public static Object[][]CreatePreferAirLineDataProvider6()
    {
        return new Object[][]{
                {testData.getTestData("InvalidCreate5.AirLineName"),testData.getTestData("InvalidCreate5.supplierName")}
        };
    }
    @DataProvider(name = "UpdatePreferAirLineWithCValidData")
    public static Object[][] UpdatePreferAirLineDataProvider7()
    {
        return new Object[][]{
                {testData.getTestData("ValidUpdateData.AirLineName"),testData.getTestData("ValidUpdateData.supplierName"),testData.getTestData("ValidUpdateData.supplierPCC"),testData.getTestData("ValidUpdateData.Remark")}
        };
    }
    @DataProvider(name = "UpdatePreferAirLineWithCValidDataWithPcc")
    public static Object[][]UpdatePreferAirLineDataProvider8()
    {
        return new Object[][]{
                {testData.getTestData("ValidUpdateDataWithPcc.AirLineName"),testData.getTestData("ValidUpdateDataWithPcc.supplierName"),testData.getTestData("ValidUpdateDataWithPcc.Remark")}
        };
    }
    @DataProvider(name = "UpdatePreferAirLineWithCValidDataAlreadyExist")
    public static Object[][]UpdatePreferAirLineDataProvider9()
    {
        return new Object[][]{
                {testData.getTestData("ValidUpdateDataWithPcc.AirLineName"),testData.getTestData("ValidUpdateDataWithPcc.supplierName"),testData.getTestData("ValidUpdateDataWithPcc.Remark")}
        };
    }
    @DataProvider(name = "UpdatePreferAirLineWithCValidDataWithOUtRemark")
    public static Object[][]UpdatePreferAirLineDataProvider10()
    {
        return new Object[][]{
                {testData.getTestData("ValidUpdateDataWithPcc.AirLineName"),testData.getTestData("ValidUpdateDataWithPcc.supplierName")}
        };
    }
    @DataProvider(name = "UpdatePreferAirLineWithCValidDataWithOUtName")
    public static Object[][]UpdatePreferAirLineDataProvider11()
    {
        return new Object[][]{
                {testData.getTestData("ValidUpdateData.supplierName")}
        };
    }


}



