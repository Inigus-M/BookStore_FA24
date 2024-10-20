/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class DBContext {

    protected Connection conn;

    /**
     * get an conn
     *
     * @return conn or null
     * @throws ClassNotFoundException
     */
    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=4USER_PRJ301";
            String user = "sa";
            String password = "sa";
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error " + e.getMessage() + " at DBContext");
            return null;
        }
    }
/*
    public static void main(String[] args) {
        DBContext test = new DBContext();
        test.conn = test.getConnection();
        System.out.println(test.conn);
    }*/
}