/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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

        int colCount = rsmd.getColumnCount();

        while (rs.next()) {

            Map<String, Object> record = new LinkedHashMap<>();

            for (int col = 1; col < colCount + 1; col++) {

                record.put(rsmd.getColumnName(col), rs.getObject(col));

            }
            results.add(record);

        }

        return results;

    }

    @Override
    public final void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws Exception {
        PreparedStatement stmt = buildInsertStatement(tableName, colNames, colValues);
        stmt.executeUpdate();
    }

    public PreparedStatement buildInsertStatement(String tableName, List<String> colNames, List<Object> colValues) throws Exception {
        PreparedStatement ps = null;
        String sql = "INSERT INTO " + tableName;
        StringJoiner sj = new StringJoiner(", ", " (", ")");
        for (String columnName : colNames) {
            sj.add(columnName);
        }
        sql += sj.toString();
        sql += " VALUES ";
        sj = new StringJoiner(", ", " (", ")");

        for (Object colValue : colValues) {
            sj.add("?");
        }
        sql += sj.toString();
        ps = conn.prepareStatement(sql);
        for (int d = 0; d < colValues.size(); d++) {
            ps.setObject(d + 1, colValues.get(d));
        }
        return ps;
    }

    public int deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException {
        if (tableName.isEmpty() || columnName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // can only use question mark for column value- nothing else

        PreparedStatement deleteRecord = null;
        String deleteQryString = null;
        deleteQryString = "DELETE FROM " + tableName + " WHERE " + columnName + "=?";

        deleteRecord = conn.prepareStatement(deleteQryString);

// posistion is one based, not 0 based
        deleteRecord.setObject(1, primaryKey);
        int result = deleteRecord.executeUpdate();
        return result;

    }

    /**
     * Have to open and close connection for this method. 
     */
    @Override
    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws SQLException, ClassNotFoundException  {
        //use  list of column names to find the current values for multiple
        // column update
        PreparedStatement pstmt = null;
        int recsUpdated = 0;
        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj;

            // set parameters for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // set parameters for where value
            pstmt.setObject(index, pkValue);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            }
        }

        return recsUpdated;
    }

    /**
     * @param conn - a JDBC Connection object
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
            List colDescriptors, String whereField)
            throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sdf.format(new Date()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();
        System.out.println(date);
        System.out.println("_________ ");

        DbAccessor db = new MySqlDbAccessor();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

        List<String> cols = new ArrayList();
        cols.add("author_id");
        cols.add("author_name");
        cols.add("date_added");

        // how to add a List of dates?
        List<Object> colValues = new ArrayList();
        colValues.add(7);
        colValues.add("Smith");
        colValues.add(date);

       // db.insertRecord("author", cols, colValues);
        
          // how to add a List of dates?
        List<Object> colValues2 = new ArrayList();
        colValues.add(8);
        colValues.add("Miller");
        colValues.add(date);
        db.updateRecordById("author", cols, colValues2,"author_id" , 8 );

        // System.out.println(db.deleteRecordById("author", "author_id", 4));
        List<Map<String, Object>> records = db.getAllRecords("author", 10);

        db.closeConnection();

        for (Map<String, Object> rec : records) {
            System.out.println(rec);
        }
    }

}
