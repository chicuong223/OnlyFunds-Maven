<%-- 
    Document   : error
    Created on : Jul 1, 2021, 2:32:30 PM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${posterror}</h1>
        <h1>Post</h1>
        <c:forEach var="tier" items="${postTiers}">
            <p>${tier.tierId}</p>
        </c:forEach>
        <h1>User</h1>
        <c:forEach var="tier" items="${userTiers}">
            <p>${tier.tierId}</p>
        </c:forEach>
        <h1>${cmp}</h1>
    </body>
</html>
