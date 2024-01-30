package me.slime.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteUtils {

    private Connection con = null;

    public void connect() {
        try {
            String url = "jdbc:sqlite:sqlite/accounts.db";
            con = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public boolean isConnected() {
        if(con != null) {
            return true;
        }else {
            return false;
        }
    }

    public Connection getConnection() {
        return this.con;
    }

    public void createTable(String name, String table) {
        try {
            this.con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + name + "(" + table + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
