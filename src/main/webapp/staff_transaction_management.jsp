<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>Transaction Management</title>
    </head>

    <body>  
        <main class="main-container">
            <!-- Vertical navbar -->
            <c:import url="staff_navbar.jsp"></c:import>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <form method="get" action="SearchBillServlet">
                        <input type="hidden" value="searchstring" name="a"/>
                        <div class="input-group input-group-sm" id="search-input">
                            <input type="text" class="form-control" name="search" placeholder="Search..." value="${search}">
                        <button class="input-group-text" id="basic-addon2" type="submit"><i
                                class="fas fa-search"></i></button>
                    </div>
                </form>
                <input type="hidden" value="${active_tab}" id="active-tab">
                <h1 class="text-center fw-bold mt-3 mb-5">Billing History</h1>
                <form action="StaffBillListServlet" method="GET">
                    <input type="hidden" name="action" value="date"/>
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <label class="label-form" for="start-date">From: </label>
                                <input type="date" name="start" id="start-date" value="${param.start}"/>
                            </div>
                            <div class="col">
                                <label class="label-form" for="end-date">To: </label>
                                <input type="date" name="end" id="end-date" value="${param.end}"/>
                            </div>
                            <div class="col">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </div>
                    </div>
                </form>
                <c:set var="user" value="${sessionScope.user}"></c:set>
                    <div class="button-group border-bottom mb-5">
                    </div>
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
                        <c:forEach var="index" begin="1" end="${end}">
                            <c:choose>
                                <c:when test="${empty search && action == null}">
                                    <li class="page-item ${param.page == index?"active":""}">
                                        <a class="page-link" href='StaffBillListServlet?page=${index}'>${index}</a>
                                    </li>
                                </c:when>
                                <c:when test="${action != null}">
                                    <li class="page-item ${param.page == index?"active":""}">
                                        <a class="page-link" href='StaffBillListServlet?page=${index}&start=${start}&end=${end}'>${index}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item ${param.page == index?"active":""}">
                                        <a class="page-link" href='SearchBillServlet?search=${search}&page=${index}'>${index}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </main>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <script type="text/javascript" defer>
            let activeTab = document.getElementById("active-tab");
            let arr = document.querySelectorAll(".sort");
            arr.forEach(element => {
                if (element.id === activeTab.value) {
                    element.classList.add('active');
                }
            });
        </script>
    </body>
</html>
