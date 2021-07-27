<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="utf-8">
                <meta http-equiv="Content-Type" content="text/html;">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
                <!-- Bootstrap -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
                    integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
                    crossorigin="anonymous">
                <!-- Logo -->
                <link rel="icon" href="images/logo_head.png" type="image/icon type">
                <!-- Navbar and shared css -->
                <!-- Main css -->
                <link type="text/css" rel="stylesheet" href="styles/admin_page.css">
                <!-- Icon -->
                <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
                <title>Report Overview</title>
            </head>

            <body>
                <main class="main-container">
                    <!-- Vertical navbar -->
                    <c:if test="${sessionScope.staff != null}">
                        <c:import url="staff_navbar.jsp"></c:import>
                    </c:if>
                    <c:if test="${sessionScope.admin != null}">
                        <c:import url="admin_vertical_navbar.jsp"></c:import>
                    </c:if>
                    <!-- Main content of the page -->
                    <div class="main-content" id="main-content">
                        <form class="d-flex w-50 mx-auto mt-4">
                            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-success" type="submit">Search...</button>
                        </form>
                        <div class="table-wrapper mt-5 w-75 mx-auto border shadow" id="wrapper">
                            <div class="table-header bg-primary p-3">
                                <div class="row">
                                    <div class="col-sm-5">
                                        <h4 class="fw-bold text-white mb-0">Report Overview</h4>
                                    </div>
                                    <div class="col-sm-7 left-header">
                                        <div class="dropdown">
                                            <a class="btn btn-danger dropdown-toggle" href="#" role="button"
                                                id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                                Type
                                            </a>
                                            <ul class="dropdown-menu dropdown-menu-end">
                                                <li><a class="dropdown-item" href="ReportListServlet?type=all">All</a>
                                                </li>
                                                <li><a class="dropdown-item" href="ReportListServlet?type=post">Post</a>
                                                </li>
                                                <li><a class="dropdown-item"
                                                        href="ReportListServlet?type=comment">Comment</a></li>
                                                <li><a class="dropdown-item" href="ReportListServlet?type=user">User</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="dropdown ms-3">
                                            <a class="btn btn-danger dropdown-toggle" href="#" role="button"
                                                id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                                <i class="fas fa-filter"></i>
                                            </a>
                                            <ul class="dropdown-menu dropdown-menu-end">
                                                <li><a class="dropdown-item" href="ReportListServlet?status=all">All</a>
                                                </li>
                                                <li><a class="dropdown-item"
                                                        href="ReportListServlet?status=pending">Pending</a></li>
                                                <li><a class="dropdown-item"
                                                        href="ReportListServlet?status=approved">Approved</a></li>
                                                <li><a class="dropdown-item"
                                                        href="ReportListServlet?status=declined">Declined</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="table-contain p-3">
                                <table class="table table-striped table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Object Id</th>
                                            <th>Type</th>
                                            <th>Status</th>
                                            <th>Detail</th>
                                            <th>Solved by</th>
                                            <th>Solve Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
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
                                                <td><a
                                                        href="ReportDetailServlet?id=${report.id}">Report-${report.id}</a>
                                                </td>
                                                <td>
                                                    <p>${report.solveStaff.username}</p>
                                                </td>
                                                <td>
                                                    <fmt:formatDate pattern="dd-MMM-yyyy" value="${report.solveDate}" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <nav class="navigation">
                                    <ul class="pagination">
                                        <li class="page-item">
                                            <c:if test="${param.page != null && param.page > 1}">
                                                <a class="page-link" href="StaffListServlet?page=${param.page - 1}">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </c:if>
                                            <c:if test="${param.page == null || param.page == 1}">
                                                <a class="page-link text-muted" href="#">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </c:if>
                                        </li>
                                        <c:forEach var="index" begin="1" end="${end}">
                                            <li class="page-item">
                                                <c:choose>
                                                    <c:when test="${empty search}">
                                                        <a href="StaffListServlet?page=${pageNum}"
                                                            style="${pageNum==page?" color: red":""}">${pageNum}</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="StaffListServlet?search=${search}&page=${pageNum}"
                                                            style="${pageNum==page?" color: red":""}">${pageNum}</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <c:choose>
                                                <c:when test="${end <= 1}">
                                                    <a class="page-link text-muted" href="#">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </c:when>
                                                <c:when test="${param.page == null}">
                                                    <a class="page-link" href="homepaget?page=2">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </c:when>
                                                <c:when test="${param.page < end}">
                                                    <a class="page-link" href="homepage?page=${param.page + 1}">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="page-link text-muted" href='#'><span
                                                            aria-hidden="true">&raquo;</span></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </li>
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