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
 *
 * @author Jennifer DOA should use the language of the customer/ problem domain
 * use DAO to translate raw data we will have different DAOs for unit testing
 * (no access to external test- can test faster) - run after every edit and
 * production, and integration test (does talk to db)
 */
public class AuthorDAO {

    private DbAccessor db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public AuthorDAO(DbAccessor db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException {
        List<Author> records = new ArrayList<>();
        
         db.openConnection(driverClass, url, userName, password);
        List<Map<String, Object>> rawData = db.getAllRecords(tableName, maxRecords);
        
        for(Map<String, Object> rawRec: rawData){
            //requires default empty constructor in Author object
            Author author = new Author(); 
            
            Object objId = rawRec.get("author_id"); 
            Integer authorId = (Integer) objId; 
            author.setAuthorId(authorId);
            
            Object objName = rawRec.get("author_name");
            String name = (objName !=null) ? objName.toString() : ""; 
            author.setAuthorName(name);
            
            
            Object objDate = rawRec.get("date_added");
            Date date = (objDate != null) ? (Date)objDate : null;
            author.setDateAdded(date);
            
            records.add(author); 
            
        }

       db.closeConnection(); 

        return records;
    }

    public DbAccessor getDb() {
        return db;
    }

    public void setDb(DbAccessor db) {
        this.db = db;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

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
        AuthorDAO  dao = new AuthorDAO (new MySqlDbAccessor(), 
                "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", 
                "root", "admin");
        
        List<Author> authors = dao.getAuthorList("author", 5); 
        for(Author a : authors){
            System.out.println(a);
            System.out.println("pizza!");
        }
    }
}