package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC"); // IMPORTANT
            return DriverManager.getConnection("jdbc:sqlite:bank.db");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}