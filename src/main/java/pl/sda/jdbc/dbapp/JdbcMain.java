package pl.sda.jdbc.dbapp;

import java.sql.*;

public class JdbcMain {
    public static void main(String[] args) throws Exception {
//        firstInsert();
        firstSelect();
        return;
    }

    private static void firstSelect() {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from test");
            while (resultSet.next()){
                int id = resultSet.getInt("id"); //resultSet.getInt(1);
                String name = resultSet.getString("name");
                System.out.println("Record: " + id + ", name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void firstInsert() {
        Connection connection = null;
        try {
            connection = getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static Connection getConnection() throws SQLException {
        String connectionString = "jdbc:mysql://127.0.0.1:3306/jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "jdbc-app-user";
        String password = "jdbc";

        return DriverManager.getConnection(connectionString, userName, password);
    }
}
