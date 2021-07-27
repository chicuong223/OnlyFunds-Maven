<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff List</title>
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <link type="text/css" rel="stylesheet" href="styles/admin_page.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <main class="main-container">
            <c:import url="admin_vertical_navbar.jsp"></c:import>
            <div class="main-content" id="main-content">
                <form class="d-flex w-50 mx-auto mt-4" method="get" action="SearchStaffServlet">
                    <input type="hidden" name="a" value="searchstring">
                    <input class="form-control me-2" type="text" name="search" placeholder="Search" >
                    <button class="btn btn-outline-success" type="submit">Search...</button>
                </form>
                <div class="table-wrapper mt-5 w-75 mx-auto border shadow" id="wrapper">
                    <div class="table-header bg-primary p-3">
                        <div class="row">
                            <div class="col-sm-5">
                                <h4 class="fw-bold text-white mb-0">User management</h4>
                            </div>
                            <div class="col-sm-7 left-header">
                                <button data-bs-toggle="modal" data-bs-target="#modal-add" class="btn btn-sm btn-danger float-end" id="add-btn">
                                    <ion-icon class="me-3" name="add-circle-outline"></ion-icon>
                                    <span>Add staff</span>
                                </button>
                                <div class="dropdown ms-3">
                                    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-filter"></i>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                      <li><a class="dropdown-item" href="StaffListServlet?isBanned=all">All</a></li>
                                      <li><a class="dropdown-item" href="StaffListServlet?isBanned=unbanned">Active</a></li>
                                      <li><a class="dropdown-item" href="StaffListServlet?isBanned=banned">Inactive</a></li>
                                    </ul>
                                  </div>
                            </div>
                        </div>
                        <div class="table-contain p-3">
                            <table class="table table-striped table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th scope="col">Username</th>
                                        <th scope="col">First name</th>
                                        <th scope="col">Last name</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">No. Reports</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
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
                                        <td class="text-success">
                                            <c:if test="${staff.isActive == true}">
                                                <i class="fas fa-dot-circle"></i>
                                                <span class="text-success">
                                                    Active
                                                </span>
                                            </c:if>
                                            <c:if test="${staff.isActive == false}">
                                                <i class="fas fa-dot-circle text-muted"></i>
                                                <span class="text-muted">
                                                    Inactive
                                                </span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="ReportListByStaffServlet?staffUsername=${staff.username}">
                                                ${numSolvedReportList[staffLoop.index]} solved
                                            </a>
                                        </td>
                                        <td>
                                            <c:if test="${staff.isActive == false}">
                                                <a class="text-muted" href="#" style="cursor: default;">
                                                    <i class="fas fa-user-times text-muted me-2"></i>
                                                    <span>Inactive</span>
                                                </a>
                                            </c:if>
                                            <c:if test="${staff.isActive == true}">
                                                <a class="text-danger" href="#" data-bs-toggle="modal" data-bs-target="#modal-${staff.username}">
                                                    <i class="fas fa-user-minus me-2"></i>
                                                    <span>Deactive</span>
                                                </a>
                                            </c:if>
                                        </td>
                                    </c:forEach>
                            </tbody>
                        </table>
                        <nav class="navigation">
                            <ul class="pagination">
                                <c:forEach begin="1" end="${numPage}" var="pageNum">
                                    <li class="page-item <c:if test="${param.page == numpage}">active</c:if>">
                                        <c:choose>
                                            <c:when test="${empty search}">
                                                <a href="StaffListServlet?page=${pageNum}" class="page-link">${pageNum}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="StaffListServlet?search=${search}&page=${pageNum}" class="page-link">${pageNum}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
            <c:forEach items="${staffList}" var="staff">
                <div class="modal fade" id="modal-${staff.username}">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <div class="h4 mb-0">Deactive ${staff.username}'s account</div>
                                <button data-bs-dismiss="modal" class="btn btn-close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="DeactivateStaffServlet" method="POST" id="form-${staff.username}">
                                    <input type="hidden" value="${staff.username}" name="username"/>
                                    <div>
                                        <span class="fw-bold">Username: </span>
                                        <span>${staff.username}</span>
                                    </div>
                                    <div>
                                        <span class="fw-bold">Email: </span>
                                        <span>${staff.email}</span>
                                    </div>
                                    <div>
                                        <span class="fw-bold">Full Name: </span>
                                        <span>${staff.firstName} ${staff.lastName}</span>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-danger" form="form-${staff.username}">Deactivate</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <div class="modal fade" id="modal-add">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Add a staff</h5>
                            <button class="btn btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <div id="errors" class="text-danger">
                                <span id="usernameError"></span>
                                <span id="passwordError"></span>
                                <span id="emailError"></span>
                                <span id="confPassError"></span>
                                <span id="nameError"></span>
                            </div>
                            <form action="AddStaffServlet" method="POST" id="register-form">
                                <div class="row my-2">
                                    <div class="col">
                                        <label class="col-form-label" for="firstname">First Name: </label>
                                    </div>
                                    <div class="col">
                                        <input type="text" id="firstname" name="firstname" class="form-control"/>
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col">
                                        <label class="col-form-label" for="lastname">Last Name: </label>
                                    </div>
                                    <div class="col">
                                        <input type="text" id="lastname" name="lastname" class="form-control"/>
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col">
                                        <label class="col-form-label" for="username">Username: </label>
                                    </div>
                                    <div class="col">
                                        <input type="text" id="username" name="username" class="form-control"/>
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col">
                                        <label class="col-form-label" for="email">Email: </label>
                                    </div>
                                    <div class="col">
                                        <input type="email" id="email" name="email" class="form-control"/>
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col">
                                        <label class="col-form-label" for="password">Password: </label>
                                    </div>
                                    <div class="col">
                                        <input type="password" id="password" name="password" class="form-control"/>
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col">
                                        <label class="col-form-label" for="confPass">Confirm Password: </label>
                                    </div>
                                    <div class="col">
                                        <input type="password" id="confPass" name="confPassword" class="form-control"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success" form="register-form">Add</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
    </body>
</html>
