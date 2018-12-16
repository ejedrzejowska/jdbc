package pl.sda.jdbc.dbapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcMain {
    public static void main(String[] args) throws Exception {
//        firstInsert();
        return;
    }

    private static void firstInsert() throws SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into test (name) values('first name')");
        connection.close();
    }

    private static Connection getConnection() throws SQLException {
        String connectionString = "jdbc:mysql://127.0.0.1:3306/jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "jdbc-app-user";
        String password = "jdbc";

        return DriverManager.getConnection(connectionString, userName, password);
    }
}
