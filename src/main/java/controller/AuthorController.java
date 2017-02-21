/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Author;
import model.AuthorDAO;
import model.AuthorService;
import model.MySqlDbAccessor;


/**
 *
 * @author Jennifer
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
private final String NEXT_PAGE= "/AuthorQueryResults.jsp"; 
  public String action;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
        try  {
            
            
             AuthorService authService = new AuthorService( new AuthorDAO(new MySqlDbAccessor(), "com.mysql.jdbc.Driver",
                                "jdbc:mysql://localhost:3306/book",
                                "root", "admin"));
            action = request.getParameter("action");
            if(action.equals("AuthorList")){
                    List<Author> authorList = authService.createList("author",5);
                    request.setAttribute("authorList", authorList);
                    
              
            }
            else if (action.equals("deleteAuthor")) {
                authService.deleteAuthor("author", "author_id", 5); 
                List<Author> authors = authService.createList("author", 10);
                request.setAttribute("authors", authors);
                //destination = VIEW_AUTHORS;
            } else  if (action.equals("insertAuthor")){
                authService.insertAuthor("author", Arrays.asList(new String[]{"author_name", "date_added"}), Arrays.asList(new String[]{"Hailey Scheidegger", "2004-1-19"}));
                List<Author> authors = authService.createList("author", 10);
                request.setAttribute("authors", authors);
               // destination = VIEW_AUTHORS;
            } else if (action.equals("updateAuthor")){
                authService.updateAuthor("author", Arrays.asList(new String[]{"author_name", "date_added"}), Arrays.asList(new String[]{"Mark Twain", "2017-03-04"}), "author_id", 6);
                List<Author> authors = authService.createList("author", 10);
                request.setAttribute("authors", authors);
              //  destination = VIEW_AUTHORS;
            } else {
                request.setAttribute("errMsg", "Nothing to do ...");
            }
        }
     
        catch(Exception e){
           e.printStackTrace();
        }
        RequestDispatcher view = request.getRequestDispatcher(NEXT_PAGE);
        view.forward(request, response);
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
