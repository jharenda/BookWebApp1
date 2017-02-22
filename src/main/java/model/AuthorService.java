/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        AuthorService authorService
                = new AuthorService(
                        new AuthorDAO(new MySqlDbAccessor(),
                                "com.mysql.jdbc.Driver",
                                "jdbc:mysql://localhost:3306/book",
                                "root", "admin"));
      
     // int deleted =   authorService.deleteAuthor("author", "author_id", 3); 
     // System.out.println(deleted + " deleted");
        
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sdf.format(new Date()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();
     
  List<String> colNames = new ArrayList();
        colNames.add("author_id");
        colNames.add("author_name");
        colNames.add("date_added");
        
        
         List<Object> values = new ArrayList();
        values.add("13");
        values.add("Green");
        values.add(date);
      //int updated = authorService.updateAuthor("author", colNames, values, "author_id", 2); 
      //  System.out.println("updated: " + updated);
      
      //authorService.insertAuthor("author", colNames, values);
      
      
        List<Author> authors = authorService.createList("author", 22); 
        for(Author a : authors) {
            System.out.println(a);   
        }

    }
}
