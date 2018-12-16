package pl.sda.jdbc.dbapp;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

public class JdbcMain {

    private static DataSource datasource;

    public static void main(String[] args) throws Exception {
//        firstInsert();
//        firstSelect();
//        selectEmployees(new BigDecimal(1500), new BigDecimal(2500));
//        selectEmployeesWithPreparedStatement(new BigDecimal("1600"), new BigDecimal("2500"));
//        selectEmployee("CLARK?' OR 1 = 1 -- "); //SQL injection
//        selectEmployeeWithPreparedStatement("CLARK?' OR 1 = 1 -- "); //SQL injection
//        updateEmployeeSalary("MANAGER", new BigDecimal("100"));
        updateTotalPayouts( new BigDecimal("100"),7369);

        return;
    }

    private static void updateTotalPayouts(BigDecimal amount, int id) throws Exception{
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false); //transakcja, wykonaja sie wszystkie operacje albo zadna + reczny commit lub rollback!
            String sql = "update employee set totalPayouts = coalesce (totalPayouts, 0) + ? where empno = ?"; //ifnull(totalPayouts, 0)
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(2, id);
            ps.setBigDecimal(1, amount);
            ps.executeUpdate();
            String sql2 = "insert into payout (empid, amount, added) values ( ?, ?, NOW())";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, id);
            ps2.setBigDecimal(2, amount);
            ps2.executeUpdate();
            connection.commit();
        } catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateEmployeeSalary(String job, BigDecimal raise) {
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "update employee set sal = (sal + ?) where job = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, raise);
            ps.setString(2, job);
            ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void selectEmployeeWithPreparedStatement(String name){
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "select empno from employee where ename = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int empno = rs.getInt("empno");
                System.out.println("Employee no: " + empno);
            } else {
                System.out.println("Employee not found");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void selectEmployee(String name) throws Exception{
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from employee where ename = '" + name + "'";
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                System.out.println("Record: " + empno + ", name: " + ename);
            } else {
                System.out.println("No employee found");
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

    private static void selectEmployeesWithPreparedStatement(BigDecimal min, BigDecimal max) {
        Connection connection = null;
        String query = "select e.empno, e.ename, e.sal, d.dname from department d " +
                "join employee e on e.deptno = d.deptno " +
                "where sal between ? and ? order by e.sal desc";
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, min);
            statement.setBigDecimal(2, max);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int empno = resultSet.getInt("empno");
                String ename = resultSet.getString("ename");
                BigDecimal sal = resultSet.getBigDecimal("sal");
                String dname = resultSet.getString("dname");
                System.out.println("Record: " + empno + ", name: " + ename +  ", salary: " +  sal + ", dept: " + dname);
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

    private static void selectEmployees(BigDecimal min, BigDecimal max) {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select e.empno, e.ename, e.sal, d.dname from department d " +
                    "join employee e on e.deptno = d.deptno " +
                    "where sal between " + min + " and " + max + " order by e.sal desc");
            while (resultSet.next()){
                int empno = resultSet.getInt("empno");
                String ename = resultSet.getString("ename");
                BigDecimal sal = resultSet.getBigDecimal("sal");
                String dname = resultSet.getString("dname");
                System.out.println("Record: " + empno + ", name: " + ename +  ", salary: " +  sal + ", dept: " + dname);
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

    private static Connection getConnection() throws SQLException { //pula połączeń, aby zoptymalizować tworzenie bazy danych. Tworzenie połączeń kosztuje

        if(datasource == null) {

            String connectionString = "jdbc:mysql://127.0.0.1:3306/jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true";
            String userName = "jdbc-app-user";
            String password = "jdbc";

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(connectionString);
            basicDataSource.setUsername(userName);
            basicDataSource.setPassword(password);
            basicDataSource.setMaxTotal(5);
            basicDataSource.setInitialSize(3);
            basicDataSource.setMaxWaitMillis(5000);

            datasource = basicDataSource;
        }
        return datasource.getConnection(); //nie ten sam getConnection() metoda.
    }

//    private static Connection getConnection() throws SQLException {
//        String connectionString = "jdbc:mysql://127.0.0.1:3306/jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true";
//        String userName = "jdbc-app-user";
//        String password = "jdbc";
//
//        return DriverManager.getConnection(connectionString, userName, password);
//    }
}
