package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    private static final String url = "jdbc:mysql://localhost:3306/itse408";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url,user,password);
    }
}
