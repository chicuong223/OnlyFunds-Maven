<%-- 
    Document   : reportList
    Created on : Jul 16, 2021, 9:23:10 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report List</title>
    </head>
    <body>
        <h1>Report List</h1>
        <h3>Type</h3>
        <a href="ReportListServlet?type=all">All</a>
        <a href="ReportListServlet?type=post">Post</a>
        <a href="ReportListServlet?type=commnet">Comment</a>
        <a href="ReportListServlet?type=user">User</a>
        <h3>Status</h3>
        <a href="ReportListServlet?status=pending">Pending</a>
        <a href="ReportListServlet?status=approved">Pending</a>
        <a href="ReportListServlet?status=declined">Pending</a>
        <c:forEach var="report" items="${reportList}">
            <div class="report type-${report.type} solved-${status}">
                <div>ID: ${report.id}</div>
                <div>Type: ${report.type}</div>
            </div>
        </c:forEach>
        <%-- Navbar phan trang --%>
        <c:forEach begin="1" end="${numPage}" var="pageNum">
            <a href="ReportListServlet/?pageNum=${pageNum}">Page ${pageNum}</a>
        </c:forEach>
    </body>
</html>
