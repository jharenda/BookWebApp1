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
import java.util.logging.Logger;

/**
 *
 * @author Jennifer
 */
public class AuthorService {

    private iAuthorDAO aDAO;

    private DbAccessor db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    

    public AuthorService(iAuthorDAO aDAO) {
        this.aDAO = aDAO;
        
    }

    public List<Author> createList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException {

        return aDAO.getAuthorList(tableName, maxRecords);

    }
    public int updateAuthor(String tableName, List<String> colNames, List<Object> values, String keyColName, Object keyColValue) throws ClassNotFoundException, SQLException{
       return  aDAO.updateRecordById(tableName, colNames, values, keyColName, keyColValue);
    }
    
    public void insertAuthor(String tableName, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException, Exception{
        aDAO.insertRecord(tableName, colNames, colValues);
    }
    
    public int deleteAuthor(String tableName, String colName, int id) throws ClassNotFoundException, SQLException{
       return aDAO.deleteRecordById(tableName, colName, id);
        
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService authorService
                = new AuthorService(
                        new AuthorDAO(new MySqlDbAccessor(),
                                "com.mysql.jdbc.Driver",
                                "jdbc:mysql://localhost:3306/book",
                                "root", "admin"));
        
        List<Author> authors = authorService.createList("author", 5); 
        for(Author a : authors) {
            System.out.println(a);
            
        }
        
        
        int inserted = authorService.insertAuthor("author",  colValues)
        
      //int deleted =   authorService.deleteAuthor("author", "author_id", 5); 
      //  System.out.println(deleted + " deleted");
        
   
    }
}
