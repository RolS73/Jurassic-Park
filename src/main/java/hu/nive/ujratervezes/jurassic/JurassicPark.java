package hu.nive.ujratervezes.jurassic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JurassicPark {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public JurassicPark(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    List<String> checkOverpopulation() {
        List<String> resultList = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String SQL = "SELECT breed FROM dinosaur WHERE expected < actual ORDER BY breed";

            PreparedStatement st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                resultList.add(rs.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultList;
    }
}
