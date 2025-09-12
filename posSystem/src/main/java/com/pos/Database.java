package com.pos;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:pos.db";

    public static Conncection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static void initDatabase() {
        try(Connection conn = connect();
        Statement stmt = conn.createStatement()){

            String sql = "CREATE TABLE ID NOT EXISTS products (" +
            "id TEXT PRIMARY KEY, " +
            "name TEXT NOT NULL, " +
            "price REAL NOT NULL, " +
            "quantity INTEGER NOT NULL");
            stmt.execute(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
