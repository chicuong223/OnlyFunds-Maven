<%-- 
    Document   : transactions
    Created on : Jul 6, 2021, 10:25:56 AM
    Author     : chiuy
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
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
            <link type="text/css" rel="stylesheet" href="styles/main_page.css">
            <!-- Icon -->
            <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
            <!--Ajax-->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <!--            JS for transaction list load
                        <script src="scripts/transaction.js"></script>-->
            <title>Billing History</title>
        </head>

        <body>  
            <main class="main-container">
                <!-- Vertical navbar -->
            <c:import url="creator_vertical_navbar.jsp"></c:import>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <h1>Billing History</h1>
                <c:set var="user" value="${sessionScope.user}"></c:set>
                    <button class="btn btn-primary" onclick="location.href = 'ViewTransactionHistory?filter=all'">All</button>
                    <button class="btn btn-danger"onclick="location.href = 'ViewTransactionHistory?filter=sent'">Sent</button>
                    <button class="btn btn-success" onclick="location.href = 'ViewTransactionHistory?filter=received'">Received</button>
                    <table class="table table-bordered table-hover w-75 mx-auto">
                        <thead class="table-primary">
                            <tr>
                                <th>Content</th>
                                <th>Sender</th>
                                <th>Recipient</th>
                                <th>Amount</th>
                                <th>Transaction Date</th>
                            </tr>
                        </thead>
                        <tbody class="table-success" id="billList">
                            <!--The list of transactions is here--> 
                        <c:forEach items="${bills}" var="bill">
                            <tr <c:if test="${bill.sender.username eq user.username}">class='table-danger'</c:if>>
                                <td>${bill.content}</td>
                                <td>${bill.sender.username}</td>
                                <td>${bill.recipient.username}</td>
                                <td>$${bill.price}</td>
                                <td><fmt:formatDate pattern='dd-MMM-yyyy' value='${bill.transactionDate}'/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <nav class="d-flex justify-content-center mb-4">
                    <ul class="pagination">
                        <li class="page-item">
                            <c:if test="${param.page != null && param.page > 1}">
                                <a class="page-link" href="ViewTransactionHistory?page=${param.page - 1}&filter=${filter}">
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
                            <li class="page-item <c:if test="${param.page == index}">active</c:if>"><a class="page-link" href='ViewTransactionHistory?page=${index}&filter=${filter}'>${index}</a></li>
                            </c:forEach>
                        <li class="page-item">
                            <c:choose>
                                <c:when test="${end <= 1}">
                                    <a class="page-link text-muted" href="#">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </c:when>
                                <c:when test="${param.page == null}">
                                    <a class="page-link" href="ViewTransactionHistory?page=2&filter=${filter}">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </c:when>
                                <c:when test="${param.page < end}">
                                    <a class="page-link" href="ViewTransactionHistory?page=${param.page + 1}&filter=${filter}">
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
