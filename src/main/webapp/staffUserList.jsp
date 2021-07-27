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
                <form class="d-flex w-50 mx-auto mt-4" method="get" action="StaffSearchUserServlet">
                    <input type="hidden" value="searchstring" name="a"/>
                    <input class="form-control me-2" name="search" type="search" placeholder="Search" value="${search}">
                    <button class="btn btn-outline-success" type="submit">Search...</button>
                </form>
                <div class="table-wrapper mt-5 w-75 mx-auto border shadow" id="wrapper" style="position: relative;">
                    <div class="table-header bg-primary p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <h4 class="fw-bold text-white mb-0">User management</h4>
                            </div>
                            <div class="col-sm-7 left-header d-flex justify-content-end">
                                <div class="dropdown">
                                    <a class="btn btn-danger dropdown-toggle" href="#" role="button"
                                        id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                        Type
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li><a class="dropdown-item" href="StaffUserListServlet?isBanned=all">All</a>
                                        </li>
                                        <li><a class="dropdown-item" href="StaffUserListServlet?isBanned=banned">Banned</a>
                                        </li>
                                        <li><a class="dropdown-item" href="StaffUserListServlet?isBanned=unbanned">Unbanned</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-contain p-3 text-center">
                        <table class="table table-striped table-hover align-middle">
                            <thead>
                              <tr>
                                <th scope="col">Username</th>
                                <th scope="col">First Name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Status</th>
                                <th scope="col">Violations</th>
                              </tr>
                            </thead>
                            <tbody>
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
                                            ${user.isBanned?"Banned":"Active"}
                                        </td>
                                        <td>
                                            ${violationNumList[userLoop.index]}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <nav class="d-flex justify-content-end navigation" style="position: absolute; bottom: 0;">
                            <ul class="pagination">
                                <c:forEach begin="1" end="${numPage}" var="pageNum">
                                    <c:choose>
                                        <c:when test="${empty search}">
                                            <li class="page-item ${param.page == index?"active":" "}">
                                                <a class="page-link" href="StaffUserListServlet?page=${pageNum}" style="${pageNum==page?"color: red":""}">${pageNum}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item ${param.page == index?"active":""}">
                                                <a class="page-link" href="StaffSearchUserServlet?search=${search}&page=${pageNum}" style="${pageNum==page?"color: red":""}">${pageNum}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </main>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
                    crossorigin="anonymous"></script>
    </body>
</html>

