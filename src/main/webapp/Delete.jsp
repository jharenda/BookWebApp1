<%-- 
    Document   : Delete
    Created on : Feb 20, 2017, 9:01:19 PM
    Author     : Jennifer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        
        <h1>Delete Authosssssssssr</h1>
        <form id="delete_author" name="delete_author" method="POST" action="AuthorController?action=delete">
            <select id="authorID" name="authorID"> 
                <c:forEach var="a" items="${authorDelete}">
                    <option value="${a.authorId}">${a.authorName}</option>                
                </c:forEach>
            </select>
            <input type="submit" name="delete" value="Delete">
    </body>
</html>
