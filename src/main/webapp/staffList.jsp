<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
    </head>
    <body>
        <form method="get" action="SearchStaffServlet">
            <input type="hidden" value="searchstring" name="a"/>
            <div class="input-group input-group-sm" id="search-input">
                <input type="text" class="form-control" name="search" placeholder="Search..." value="${search}">
                <button class="input-group-text" id="basic-addon2" type="submit"><i
                        class="fas fa-search"></i></button>
            </div>
        </form>
        <h1>Staff List</h1>
        <a href="#" data-bs-toggle="modal" data-bs-target="#modal-add">Add Staff</a>
        <c:choose>
            <c:when test="${empty search}">
                <h3>Status</h3>
                <a href="StaffListServlet?isBanned=all" style="${isBanned=="all"?"color: red":""}">All</a>
                <a href="StaffListServlet?isBanned=unbanned" style="${isBanned=="unbanned"?"color: red":""}">Active</a>
                <a href="StaffListServlet?isBanned=banned" style="${isBanned=="banned"?"color: red":""}">Inactive</a>
            </c:when>
            <c:otherwise>
                <a href="StaffListServlet">Back to list</a>
            </c:otherwise>
        </c:choose>
        <table class="table table-bordered table-hover table-fluid">
            <thead class="table-dark">
            <th>Username</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Status</th>
            <th>No. of Reports</th>
            <th>Action</th>
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
                    <a href="ReportListByStaffServlet?staffUsername=${staff.username}">
                        ${numSolvedReportList[staffLoop.index]} reports
                    </a>
                </td>
                <td>
                    <c:if test="${staff.isActive == false}">
                        <p>Inactive</p>
                    </c:if>
                    <c:if test="${staff.isActive == true}">
                        <a href="#" data-bs-toggle="modal" data-bs-target="#modal-${staff.username}">Deactivate</a>
                    </c:if>
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
    crossorigin="anonymous"></script>
    <c:forEach items="${staffList}" var="staff">
        <div class="modal fade" id="modal-${staff.username}">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Deactivate Staff</h4>
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
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-danger" form="form-${staff.username}">Deactivate</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </form>
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
                    <form action="AddStaffServlet" method="POST" id="form-add">
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
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success" form="form-add">Add</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>