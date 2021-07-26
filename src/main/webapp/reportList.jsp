<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report List</title>
    </head>
    <body>
        <main class="main-container">
            <!-- Vertical navbar -->
            <c:import url="staff_navbar.jsp"></c:import>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <h3>Type</h3>
                    <a href="ReportListServlet?type=all" style="${type=="all"?"color: red":""}">All</a>
                <a href="ReportListServlet?type=post" style="${type=="post"?"color: red":""}">Post</a>
                <a href="ReportListServlet?type=comment" style="${type=="comment"?"color: red":""}">Comment</a>
                <a href="ReportListServlet?type=user" style="${type=="user"?"color: red":""}">User</a>
                <h3>Status</h3>
                <a href="ReportListServlet?status=all" style="${status=="all"?"color: red":""}">All</a>
                <a href="ReportListServlet?status=pending" style="${status=="pending"?"color: red":""}">Pending</a>
                <a href="ReportListServlet?status=approved" style="${status=="approved"?"color: red":""}">Approved</a>
                <a href="ReportListServlet?status=declined" style="${status=="declined"?"color: red":""}">Declined</a>
                <table>
                    <thead>
                    <th>Id</th>
                    <th>Object Id</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Detail</th>
                    <th>Solved by</th>
                    <th>Solve Date</th>
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
                            <td>
                                <p>${report.solveStaff.username}</p>
                            </td>
                            <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${report.solveDate}"/></td>
                        </tr>
                    </c:forEach>
                </table>
                <%-- Navbar phan trang --%>
                <c:forEach begin="1" end="${numPage}" var="pageNum">
                    <a href="ReportListServlet?page=${pageNum}" style="${pageNum==page?"color: red":""}">Page ${pageNum}</a>
                </c:forEach>
            </div>
        </main>
    </body>
</html>
