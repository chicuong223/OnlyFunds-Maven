<%-- 
    Document   : transactions
    Created on : Jul 6, 2021, 10:25:56 AM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <title>Transactions History</title>
        </head>
        <body>
            <header>
                <h1>Transactions History</h1>
            </header>
            <main>
                <table class="table table-bordered">
                    <thead class="table-primary">
                        <tr>
                            <th>Content</th>
                            <th>Sender</th>
                            <th>Recipient</th>
                            <th>Amount</th>
                            <th>Transaction Date</th>
                        </tr>
                    </thead>
                    <tbody class="table-success">
                    <c:set var="user" value="${sessionScope.user}"></c:set>
                    <c:forEach items="${transactions}" var="bill">
                        <tr <c:if test="${bill.sender.username eq user.username}">class="table-danger"</c:if>>
                            <td>${bill.content}</td>
                            <td>${bill.sender.username}</td>
                            <td>${bill.recipient.username}</td>
                            <td>${bill.price}</td>
                            <td>${bill.transactionDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
            </table>
        </main>
    </body>
</html>
