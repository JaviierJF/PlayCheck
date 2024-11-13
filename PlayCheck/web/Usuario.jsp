<%-- 
    Document   : Usuario
    Created on : 1 nov 2024, 19:32:53
    Author     : Javier
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <c:forEach items="${usuarios}" var="u">
            <c:out value="${u.username}"/> |
            <c:out value="${u.password}"/> |
            <c:out value="${u.fechaRegistro}"/> |
            <c:out value="${u.email}"/> |
            </br>
        </c:forEach>
    </body>
</html>
</html>
