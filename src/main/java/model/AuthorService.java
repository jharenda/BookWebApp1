/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jennifer
 */
public class AuthorService {
   
   
    public final List<Author> createList(){
        List<Author> newList = new ArrayList<>();
        newList.add(new Author(12, "Hilary Mantel", new Date()));
        newList.add(new Author(23, "J.K. Rowling", new Date()));
        newList.add(new Author(34, "Ernest Hemmingway", new Date()));
        
        return newList;
    }
}
