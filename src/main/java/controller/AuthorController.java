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

    private static final String NEXT_PAGE = "/AuthorQueryResults.jsp";

    private static final String ERR_MSG = "No parameter detected";
 private static final String ADMIN_TASK_PAGE = "/adminTask.jsp";
    private static final String ADMIN_RESULTS_PAGE = "/adminResults.jsp";
    private static final String HOME_PAGE = "/index.jsp";
    private static final String DELETE_ACTION = "delete";
    private static final String DELETESHOW_ACTION = "deleteShow";
    private static final String LIST_ACTION = "list";
    private static final String UPDATE_ACTION = "update";
    private static final String UPDATESHOW_ACTION = "updateShow";
    private static final String ADD_ACTION = "add";
    private static final String ADDSHOW_ACTION = "addShow";
    private static final String ACTION_PARAM = "action";

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
        String destination = HOME_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        try {

            AuthorService authorService = new AuthorService(new AuthorDAO(new MySqlDbAccessor(), "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/book",
                    "root", "admin"));

            switch (action) {
                case LIST_ACTION:
                    List<Author> authors = authorService.createList("author", 50);
                    request.setAttribute("authors", authors);
                    destination = ADMIN_RESULTS_PAGE;
                    break;
                case DELETE_ACTION:
                    Integer authorID = Integer.parseInt(request.getParameter("authorID"));
                    authorService.deleteAuthor("author", "author_id", authorID);
                    destination = HOME_PAGE;
                    break;
                case UPDATE_ACTION:
                    String authorName = request.getParameter("author_name");
                    Integer authorID2 = Integer.parseInt(request.getParameter("authorID"));

                     //authorService.updateAuthor(authorName, authorID2);
                    destination = HOME_PAGE;
                    break;
                case ADD_ACTION:
                    String name = request.getParameter("author_name");
                    //authorService.addAuthor(name);
                    destination = ADMIN_TASK_PAGE;
                    break;
                case ADDSHOW_ACTION:
                    destination =ADMIN_TASK_PAGE ;
                    break;
                case UPDATESHOW_ACTION:
                    List<Author> authorUpdate = authorService.createList("author", 50);
                    request.setAttribute("authorUpdate", authorUpdate);
                    destination = ADMIN_TASK_PAGE;
                    break;
                case DELETESHOW_ACTION:
                    String deleteForm = "deleteForm";
                    List<Author> authorDelete = authorService.createList("author", 50);
                    request.setAttribute("deleteForm", deleteForm);
                    request.setAttribute("authorDelete", authorDelete);
                    destination = ADMIN_TASK_PAGE;
                    break;

            }
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
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
