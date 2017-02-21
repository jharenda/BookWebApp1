/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jennifer
 */
public interface DbAccessor {

    void closeConnection() throws SQLException;
    //public List<Map<String, Object>> getAllRecords(String table, int maxRecords, List<String> colNames) {
    // }

    List<Map<String, Object>> getAllRecords(String table, int maxRecords) throws SQLException;

    int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws SQLException, ClassNotFoundException;

    int deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException, ClassNotFoundException;

    void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws Exception;

    // Method parameters need validation
    void openConnection(String driverClass, String url, String userName, String pwd) throws ClassNotFoundException, SQLException;

}
