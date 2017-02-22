<%-- 
    Document   : adminResults
    Created on : Feb 21, 2017, 4:50:32 AM
    Author     : Jennifer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
     
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Book Web App</title>
    </head>
    
        <style>
            /* Remove the navbar's default rounded borders and increase the bottom margin */ 
            .navbar {
                margin-bottom: 50px;
                border-radius: 0;
            }

            /* Remove the jumbotron's default bottom margin */ 
            .jumbotron {
                margin-bottom: 0;
            }

            /* Add a gray background color and some padding to the footer */
            footer {
                background-color: #f2f2f2;
                padding: 25px;
            }
        </style>
    </head>
<body>

    <div class="jumbotron">
        <div class="container text-center">
            <h1></h1>      
            <p>Book Web App</p>
        </div>
    </div>

    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>                        
                </button>
                <a class="navbar-brand" href="#">Logo</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#">Products</a></li>
                    <li><a href="#">Deals</a></li>
                    <li><a href="#">Locations</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="results">
        
        <div id ="${deleteForm}">
       <h1>Delete Author</h1>
        <form id="delete_author" name="delete_author" method="POST" action="AuthorController?action=delete">
            <select id="authorID" name="authorID"> 
                <c:forEach var="a" items="${authorDelete}">
                    <option value="${a.authorId}">${a.authorName}</option>                
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete">
          
</form>
  
        <h1>Update Author</h1>
        <form id="update_author" name="update_author" method="POST" action="AuthorController?action=update">
            <table>
                <tr>
                    <td>
                        <select id="authorUpdate" name="authorID"> 
                            <c:forEach var="a" items="${authorUpdate}">
                                <option value="${a.authorId}">${a.authorName}</option>                
                            </c:forEach>                                
                        </select>                       
                    </td>
                    <td>
                        <input type="text" id="author_name" name="author_name">
                    </td>
                    <td>
                        <input type="submit" name="update" value="Update">
                    </td>
                </tr>
                
<!--                <tr>
                    <td>ID</td>
                    <td>
                        <input type="text" id="author_id" name="author_id" value="<c:out value="${a.authorId}"/>">
                    </td>                
                </tr>
-->
            </table>
                    </div>
        </form>   
        
        
    </div>
    </body>
    
</html>