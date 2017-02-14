/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jennifer
 */
public class MySqlDbAccessor implements DbAccessor {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    // Method parameters need validation 
    @Override
    public void openConnection(String driverClass, String url, String userName, String pwd) throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, pwd);

    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
    
    //public List<Map<String, Object>> getAllRecords(String table, int maxRecords, List<String> colNames) {
        
        
   // }
    
  
    
    
    
    
    @Override
    public List<Map<String, Object>> getAllRecords(String table, int maxRecords) throws SQLException {

        String sql = "";

        if (maxRecords > 0) {
            sql = "SELECT * FROM " + table + " LIMIT " + maxRecords;
        } else {

            sql = "SELECT * FROM " + table;
        }

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        List<Map<String, Object>> results = new ArrayList<>();
        
        
        ResultSetMetaData rsmd = rs.getMetaData(); 
        
       int colCount =  rsmd.getColumnCount(); 
       
       while(rs.next()) {
           
           Map<String, Object> record = new LinkedHashMap<>(); 
           
           for(int col = 1; col < colCount +1; col++) {
              
             record.put(rsmd.getColumnName(col), rs.getObject(col)); 
               
           }
           results.add(record); 
    
       }

        return results;

    }
    
    
    
    public static void main(String[] args) throws Exception  {
        
        
        DbAccessor db = new MySqlDbAccessor();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        
        
        List<Map<String,Object>> records = db.getAllRecords("author", 3); 
        
        db.closeConnection();
        
        
        for(Map<String,Object> rec : records){
            System.out.println(rec);
        }
    }

}
