package com.pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:src/main/resources/pos.db";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void initDatabase() {
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to SQLite successfully!");
            } else {
                System.out.println("Failed to connect to SQLite.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------STAGE ONE---------
    // public static Connection connect() {
    // try {
    // return DriverManager.getConnection(URL);
    // } catch (SQLException e) {
    // throw new RuntimeException("Database connection failed", e);
    // }
    // }

    // public static void initDatabase() {
    // try(Connection conn = connect();
    // Statement stmt = conn.createStatement()){

    // String sql = "CREATE TABLE ID NOT EXISTS products (" +
    // "id TEXT PRIMARY KEY, " +
    // "name TEXT NOT NULL, " +
    // "price REAL NOT NULL, " +
    // "quantity INTEGER NOT NULL)";
    // stmt.execute(sql);
    // }
    // catch(SQLException e){
    // e.printStackTrace();
    // }
    // }
}
