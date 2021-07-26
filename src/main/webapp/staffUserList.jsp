<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html;">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <!-- Logo -->
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <!-- Navbar and shared css -->
        <link type="text/css" rel="stylesheet" href="styles/navbar.css">
        <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
        <link rel="stylesheet" href="styles/shared.css">
        <!-- Main css -->
        <link type="text/css" rel="stylesheet" href="styles/bill_page.css">
        <!-- Icon -->
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
        <title>User Management</title>
    </head>
    <body>
        <main class="main-container">
            <!-- Vertical navbar -->
            <c:import url="staff_navbar.jsp"></c:import>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <form method="get" action="StaffSearchUserServlet">
                        <input type="hidden" value="searchstring" name="a"/>
                        <div class="input-group input-group-sm" id="search-input">
                            <input type="text" class="form-control" name="search" placeholder="Search..." value="${search}">
                        <button class="input-group-text" id="basic-addon2" type="submit"><i
                                class="fas fa-search"></i></button>
                    </div>
                </form>
                <h1>User List</h1>
                <c:choose>
                    <c:when test="${empty search}">
                        <h3>Status</h3>
                        <a href="StaffUserListServlet?isBanned=all" style="${isBanned=="all"?"color: red":""}">All</a>
                        <a href="StaffUserListServlet?isBanned=banned" style="${isBanned=="banned"?"color: red":""}">Banned</a>
                        <a href="StaffUserListServlet?isBanned=unbanned" style="${isBanned=="unbanned"?"color: red":""}">Not Banned</a>
                    </c:when>
                    <c:otherwise>
                        <a href="StaffUserListServlet">Back to list</a>
                    </c:otherwise>
                </c:choose>
                <table>
                    <thead>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Violations</th>
                    </thead>
                    <c:forEach var="user" items="${userList}" varStatus="userLoop">
                        <tr class="user status-${user.isBanned?"Banned":"Not banned"}">
                            <td>
                                <div>${user.username}</div>
                            </td>
                            <td>
                                <div>${user.firstName}</div>
                            </td>
                            <td>
                                <div>${user.lastName}</div>
                            </td>
                            <td>
                                <div>${user.email}</div>
                            </td>
                            <td>
                                <div>${user.isBanned?"Banned":"Active"}</div>
                            </td>
                            <td>
                                <div>${violationNumList[userLoop.index]}</div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <%-- Navbar phan trang --%>
                <nav class="d-flex justify-content-center mb-4">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${numPage}" var="pageNum">
                            <c:choose>
                                <c:when test="${empty search}">
                                    <li class="page-item ${param.page == index?"active":""}">
                                        <a class="page-link" href="StaffUserListServlet?page=${pageNum}" style="${pageNum==page?"color: red":""}">Page ${pageNum}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item ${param.page == index?"active":""}">
                                        <a class="page-link" href="StaffSearchUserServlet?search=${search}&page=${pageNum}" style="${pageNum==page?"color: red":""}">Page ${pageNum}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </main>
    </body>
</html>