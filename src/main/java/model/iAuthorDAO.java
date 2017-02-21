/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jennifer
 */
public interface iAuthorDAO {

    boolean equals(Object obj);

    List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException;
    
    
    
    int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws SQLException, ClassNotFoundException;

    int deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException,ClassNotFoundException ;

    void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws Exception, ClassNotFoundException ;
    

    DbAccessor getDb();

    String getDriverClass();

    String getPassword();

    String getUrl();

    String getUserName();

    int hashCode();

    void setDb(DbAccessor db);

    void setDriverClass(String driverClass);

    void setPassword(String password);

    void setUrl(String url);

    void setUserName(String userName);
    
}
