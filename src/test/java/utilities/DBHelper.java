package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBHelper {

    private static final String URL = DataUtils.get("DB_URL");

    private static final String USERNAME = DataUtils.get("DB_USERNAME");

    private static final String PASSWORD = DataUtils.get("DB_PASSWORD");


    public static String getForgotPasswordLink(String agencyCode) {

        String link = "";
        System.out.println("URL: " + URL);
        System.out.println("USERNAME: " + USERNAME);
        System.out.println("PASSWORD: " + PASSWORD);

        String query =
                "SELECT LINK_URL " +
                        "FROM TT_TS_FORGOT_PASSWORD_LINK " +
                        "WHERE AGENCY_ID = ? " +
                        "ORDER BY ID DESC LIMIT 1";

        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement ps = connection.prepareStatement(query)
        ) {

            ps.setString(1, agencyCode);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                link = rs.getString("LINK_URL");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return link;
    }
}