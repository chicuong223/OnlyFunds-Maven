<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report List</title>
    </head>
    <body>
        <h1>Report List of ${ofStaff.username}</h1>
        <h3>Type</h3>
        <table>
            <thead>
            <th>Id</th>
            <th>Object Id</th>
            <th>Type</th>
            <th>Status</th>
            <th>Detail</th>
        </thead>
        <c:forEach var="report" items="${reportList}">
            <tr class="report type-${report.type} solved-${status}">
                <td>
                    <div>${report.id}</div>
                </td>
                <td>
                    <div>${report.reportedObjectId}</div>
                </td>
                <td>
                    <div>${report.type}</div>
                </td>
                <td>
                    <div>${report.status}</div>
                </td>
                <td><a href="ReportDetailServlet?id=${report.id}">Report-${report.id}</a></td>
            </tr>
        </c:forEach>
    </table>
    <%-- Navbar phan trang --%>
    <c:forEach begin="1" end="${numPage}" var="pageNum">
        <a href="ReportListServlet?staffUsername=${ofStaff.username}&page=${pageNum}" style="${pageNum==page?"color: red":""}">Page ${pageNum}</a>
    </c:forEach>
</body>
</html>
