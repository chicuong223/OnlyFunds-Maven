<%-- 
    Document   : manage_subscriptions
    Created on : Jul 3, 2021, 12:00:35 PM
    Author     : ASUS GAMING
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="navbar.jsp"></c:import>

    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <title>Subscriptions</title>
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
            <link type="text/css" rel="stylesheet" href="styles/manage_subscription_page.css">
            <!-- Icon -->
            <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
        </head>

        <body>  
            <main class="main-container">
                <!-- Vertical navbar -->
                <div>
                <c:import url="creator_vertical_navbar.jsp"></c:import>
                </div>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <h1 class="fw-bold text-center mb-3 mt-3">Manage Subscriptions</h1>
                    <table class="table align-middle table-striped table-hover" id="subscriptionsList">
                        <tr>
                            <th scope="col">Creator</th>
                            <th scope="col">Tier</th>
                            <th scope="col">Price</th>
                            <th scope="col">Start date</th>
                            <th scope="col">End date</th>
                            <th scope="col">Status</th>
                            <th scope="col">Action</th>
                        </tr>
                            <c:forEach items="${subList}" var="sub">
                                <tr>
                                    <td class="creator">
                                        <a href="CreatorInfoServlet?username=${sub.tier.creator.username}">
                                            <img src="images/avatars/${sub.tier.creator.avatarURL}" width=50 height=50/>
                                            <span>${sub.tier.creator.username}</span>
                                        </a>
                                    </td>
                                    <td>
                                        ${sub.tier.tierTitle}
                                    </td>
                                    <td>
                                        $${sub.tier.price} 
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="dd-MM-yyyy" value="${sub.startDate}"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="dd-MM-yyyy" value="${sub.endDate}"/>
                                    </td>
                                    <c:if test="${sub.isActive == true}">
                                        <td class="text-success">
                                            <i class="fas fa-dot-circle"></i> <span>Active</span>
                                        </td>
                                    </c:if>
                                    <c:if test="${sub.isActive == false}">
                                        <td class="text-danger">
                                            <i class="fas fa-dot-circle"></i> <span>Inactive</span>
                                        </td>
                                    </c:if>
                                    <td>
                                        <c:if test="${sub.isActive == true}">
                                            <form action="ManageSubscriptions" method="POST">
                                                <input type="hidden" value="${sub.subscriptionId}" name="id"/>
                                                <button class="btn btn-sm btn-danger" type="submit" onclick="return confirm('Are you sure?')">Cancel</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${sub.isActive == false}">
                                            <button class="btn btn-sm btn-warning" onclick="location.href = 'CreatorInfoServlet?username=${sub.tier.creator.username}'">Subscribe</button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                </table>
                <nav class="d-flex justify-content-center mt-3">
                    <ul class="pagination">
                        <li class="page-item">
                            <c:if test="${param.page != null && param.page > 1}">
                                <a class="page-link" href="ManageSubscriptions?page=${param.page - 1}">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </c:if>
                            <c:if test="${param.page == null || param.page <= 1}">
                                <a class="page-link text-muted" href="#">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </c:if>
                        </li>
                        <c:forEach var="index" begin="1" end="${end}">
                            <li class="page-item <c:if test="${param.page == index}">active</c:if>"><a class="page-link" href='ManageSubscriptions?page=${index}'>${index}</a></li>
                            </c:forEach>
                        <li class="page-item">
                            <c:choose>
                                <c:when test="${end <= 1}">
                                    <a class="page-link text-muted" href="#">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </c:when>
                                <c:when test="${param.page == null}">
                                    <a class="page-link" href="ManageSubscriptions?page=2">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </c:when>
                                <c:when test="${param.page < end}">
                                    <a class="page-link" href="ManageSubscriptions?page=${param.page + 1}">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="page-link text-muted" href='#'><span aria-hidden="true">&raquo;</span></a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </nav>
            </div>
        </main>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </body>
</html>
