<%-- 
    Document   : receipt
    Created on : Jul 4, 2021, 12:13:33 AM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Receipt</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <link rel="stylesheet" href="styles/receipt.css"/>
    </head>
    <body>
        <main class="main-container">
            <c:set var="user" value="${sessionScope.user}"></c:set>
            <div class="main-content" id="main-content">
                <div class="container-fluid">
                    <div class="alert alert-success mt-3 mb-3 text-center" role="alert">
                        Payment successful!
                    </div>
                    <div class="row receipt">
                        <h4 class="header text-center p-3">Receipt</h4>
                        <div class="col-lg-6 pb-3">
                            <h5 class="fw-bold">From: </h5>
                            <h6>${user.firstName} ${user.lastName}</h6>
                            <h6>${user.email}</h6>
                        </div>
                        <div class="col-lg-6 pb-3">
                            <h5 class="fw-bold">To:</h5>
                            <h6>${tier.creator.firstName} ${tier.creator.lastName}</h6>
                            <h6>${tier.creator.email} </h6>
                        </div>
                        <div class="col-12">
                            <table class="table text-center align-middle">
                                <thead>
                                    <tr>
                                        <th scope="col" style="width: 5%;">#</th>
                                        <th scope="col" style="width: 30%;">Tilte</th>
                                        <th scope="col" style="width: 50%;">Description</th>
                                        <th scope="col" style="width: 15%;">Price</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td>${tier.tierTitle}</td>
                                        <td class="tier-desc">
                                            ${tier.description}
                                        </td>
                                        <td class="text-break">${transaction.amount.total}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="navigation">
                        <a href="homepage" class="btn btn-sm left">
                            <i class="fas fa-arrow-left me-2"></i>
                            <span>Homepage</span>
                        </a>
                        <a href="CreatorInfoServlet?username=${tier.creator.username}" 
                        class="btn btn-sm float-end right">
                            <span>Creator's page</span>
                            <i class="fas fa-arrow-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
        </main>
    </body>
</html>
