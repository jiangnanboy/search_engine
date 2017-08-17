package com.sy.searchengine.query;

import java.io.IOException;
import java.sql.*;

/**
 * Created by yanshi on 2017/6/26.
 */
public class SQLiteJDBC {
    public static void main(String[] args){
        Connection conn=null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:src/com/sy/searchengine/database/test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully!");
    }
}
