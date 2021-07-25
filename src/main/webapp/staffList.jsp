<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="ReportListServlet">Report List</a>
        <a href="StaffUserListServlet">User List</a>
        <a href="">Transaction List</a>
        <form method="get" action="SearchStaffServlet">
            <input type="hidden" value="searchstring" name="a"/>
            <div class="input-group input-group-sm" id="search-input">
                <input type="text" class="form-control" name="search" placeholder="Search..." value="${search}">
                <button class="input-group-text" id="basic-addon2" type="submit"><i
                        class="fas fa-search"></i></button>
            </div>
        </form>
        <h1>Staff List</h1>
        <c:choose>
            <c:when test="${empty search}">
                <h3>Status</h3>
                <a href="StaffListServlet?isBanned=all" style="${isBanned=="all"?"color: red":""}">All</a>
                <a href="StaffListServlet?isBanned=unbanned" style="${isBanned=="unbanned"?"color: red":""}">Active</a>
                <a href="StaffListServlet?isBanned=banned" style="${isBanned=="banned"?"color: red":""}">Banned</a>
            </c:when>
            <c:otherwise>
                <a href="StaffListServlet">Back to list</a>
            </c:otherwise>
        </c:choose>
        <table>
            <thead>
            <th>Username</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Status</th>
            <th>Solved Reports</th>
        </thead>
        <c:forEach var="staff" items="${staffList}" varStatus="staffLoop">
            <tr class="user status-${user.isBanned?"Banned":"Not banned"}">
                <td>
                    <div>${staff.username}</div>
                </td>
                <td>
                    <div>${staff.firstName}</div>
                </td>
                <td>
                    <div>${staff.lastName}</div>
                </td>
                <td>
                    <div>${staff.email}</div>
                </td>
                <td>
                    <div>${staff.isActive?"Active":"Banned"}</div>
                </td>
                <td>
                    <a href="ReportListByStaffServlet?username=${staff.username}">${numSolvedReportList[staffLoop.index]} reports</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <%-- Navbar phan trang --%>
    <c:forEach begin="1" end="${numPage}" var="pageNum">
        <c:choose>
            <c:when test="${empty search}">
                <a href="StaffListServlet?page=${pageNum}" style="${pageNum==page?"color: red":""}">Page ${pageNum}</a>
            </c:when>
            <c:otherwise>
                <a href="StaffListServlet?search=${search}&page=${pageNum}" style="${pageNum==page?"color: red":""}">Page ${pageNum}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</body>
</html>
