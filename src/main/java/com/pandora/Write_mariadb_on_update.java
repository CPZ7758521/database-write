package com.pandora;

import java.sql.*;
import java.util.Arrays;

public class Write_mariadb_on_update {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        //定义链接
        Connection conn = null;
        //定义预编译sql
        PreparedStatement ps = null;
        //定义结果集
        ResultSet rs = null;

        //sql语句
        String dropSql = "drop table test.a";
        String createSql = "create table if not exists test.a (\n" +
                "tage int,\n" +
                "name varchar(20),\n" +
                "tsex varchar(20),\n" +
                "`create_time` timestamp not null default current_timestamp(),\n" +
                "`update_time` timestamp not null default current_timestamp() on update current_timestamp(),\n" +
                "unique key (name)\n" +
                ") ENGIN=InnoDB CHARSET=utf-8";

        String insertSql = "insert into test.a (age,name,sex) values (?,?,?) on duplicate key update age = ?, name = ?, sex = ?";
        String querySql =
                "SELECT * FROM employees; " +
                        "UPDATE departments SET name = 'New Department Name' WHERE id = 1; " +
                        "SELECT * FROM employees;";


//        String url = "jdbc:mariadb://10.89.79.63:12121";
        String url = "jdbc:mysql://10.89.79.63:12121";
        String username = "root";
        String password = "root";

        conn = getConn(url, username, password);
        Statement statement = conn.createStatement();

        statement.executeUpdate(dropSql);
        statement.executeUpdate(createSql);

        //statement or preparedStatement 执行execute，返回多个结果集的时候。
        boolean execute = statement.execute(querySql);
        boolean moreResults = statement.getMoreResults();

        PreparedStatement ps1 = conn.prepareStatement(querySql);
        boolean execute1 = ps1.execute();
        boolean moreResults1 = ps1.getMoreResults();

        String[][] arrs = {{"7","zs","man"},{"8","ls","man"},{"2","ww","man"},{"3","zl","man"}};
        ps = conn.prepareStatement(insertSql);

        for (String[] arr : arrs) {
            ps.setInt(1, Integer.parseInt(arr[0]));
            ps.setString(2, arr[1]);
            ps.setString(3, arr[2]);
            ps.setInt(4, Integer.parseInt(arr[0]));
            ps.setString(5, arr[1]);
            ps.setString(6, arr[2]);

            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println(Arrays.asList(arr) + "--》数据插入成功《--");
                System.out.println("等待10秒......");
                Thread.sleep(10000L);
            }
        }

        statement.close();
        ps.close();
        conn.close();
    }

    public static Connection getConn(String url, String username, String password) throws ClassNotFoundException, SQLException {
        // 要想用jdbc，需要先加载 jdbc 的类，把要用的类加载进来，com.mysql.cj.jdbc.Driver
        //        Class.forName("org.mariadb.jdbc.Driver");
        Class.forName("org.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static boolean checkConnValid(Connection conn, int timeout) throws SQLException {
        return conn.isValid(timeout);
    }
}
