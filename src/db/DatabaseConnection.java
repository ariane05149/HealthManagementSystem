package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/HospitalManagement";
    private static final String user = "postgres";
    private static final String password = "Ariane@123";
    private static Connection connect;

  public static Connection getConnect() throws SQLException {
      connect=DriverManager.getConnection(url, user, password);
      System.out.println("DB Connected sucessfuly");
      return connect;
    }
}


