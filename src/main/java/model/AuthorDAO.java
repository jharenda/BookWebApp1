/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  // need to add validation to getters and setters
 * // DAO should do conversion of the passed values from the controller/web page (like id for delete method (string to int) 
 * 
 * 
 * @author Jennifer DOA should use the language of the customer/ problem domain
 * use DAO to translate raw data we will have different DAOs for unit testing
 * (no access to external test- can test faster) - run after every edit and
 * production, and integration test (does talk to db)
 */
public class AuthorDAO implements iAuthorDAO {

    private DbAccessor db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    private static final String TABLE_NAME = "author";
    private static final String PRIMARY_KEY_COLUMN_NAME = "author_id";
    private static final String AUTHOR_NAME_COL_NAME = "author_name";
    private static final String DATE_ADDED_COL_NAME = "date_added";

    public AuthorDAO(DbAccessor db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException {
        List<Author> records = new ArrayList<>();

        db.openConnection(driverClass, url, userName, password);
        List<Map<String, Object>> rawData = db.getAllRecords(tableName, maxRecords);

        for (Map<String, Object> rawRec : rawData) {
            //requires default empty constructor in Author object
            Author author = new Author();

            Object objId = rawRec.get("author_id");
            Integer authorId = (Integer) objId;
            author.setAuthorId(authorId);

            Object objName = rawRec.get("author_name");
            String name = (objName != null) ? objName.toString() : "";
            author.setAuthorName(name);

            Object objDate = rawRec.get("date_added");
            Date date = (objDate != null) ? (Date) objDate : null;
            author.setDateAdded(date);

            records.add(author);

        }

        db.closeConnection();

        return records;
    }

    @Override
    public DbAccessor getDb() {
        return db;
    }

    @Override
    public void setDb(DbAccessor db) {
        this.db = db;
    }

    @Override
    public String getDriverClass() {
        return driverClass;
    }

    @Override
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.driverClass);
        hash = 19 * hash + Objects.hashCode(this.url);
        hash = 19 * hash + Objects.hashCode(this.userName);
        hash = 19 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthorDAO other = (AuthorDAO) obj;
        if (!Objects.equals(this.driverClass, other.driverClass)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        iAuthorDAO dao = new AuthorDAO(new MySqlDbAccessor(),
                "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book",
                "root", "admin");

        int result = dao.deleteRecordById("author", "author_id", 2);
        System.out.println("deleted " + result + " record");

//        List<Author> authors = dao.getAuthorList("author", 5); 
//        for(Author a : authors){
//            System.out.println(a);
//           
//        }
    }

    @Override
    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);

        int result = db.updateRecordById(tableName, colNames, colValues, pkColName, pkValue);
        db.closeConnection();
        return result;
    }

    @Override
    public int deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);
        int execResult = db.deleteRecordById(tableName, columnName, primaryKey);

        db.closeConnection();
        return execResult;

    }

    @Override
    public void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        db.insertRecord(tableName, colNames, colValues);
        db.closeConnection();

    }
}
