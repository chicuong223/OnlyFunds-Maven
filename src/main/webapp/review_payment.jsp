<%-- 
    Document   : review_payment
    Created on : Jul 4, 2021, 12:03:28 AM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Review</title>
        <link rel="stylesheet" href="styles/review.css"/>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
        crossorigin="anonymous">
    </head>
    <body>
        <div class="container w-50 shadow border p-3 mx-auto" id="form-review">
            <div class="alert alert-warning mt-3 mb-3 text-center fw-bold" role="alert">
                Please Review Before Continuining
            </div>
            <form action="ExecutePaymentServlet" method="post">
                <input type="hidden" name="paymentId" value="${param.paymentId}" />
                <input type="hidden" name="PayerID" value="${param.PayerID}" />
                <h4 class="text-center fw-bold">Payment Description</h4>
                <div class="row receipt">
                    <div class="col-lg-6 pb-3">
                        <h5 class="fw-bold">From: </h5>
                        <h6>${sessionScope.user.username}</h6>
                        <h6>${payer.email}</h6>
                    </div>
                    <div class="col-lg-6 pb-3">
                        <h5 class="fw-bold">To:</h5>
                        <h6>${sessionScope.tier.creator.username}</h6>
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

                                    <td>${sessionScope.tier.tierTitle}</td>
                                    <td class="tier-desc">
                                        ${transaction.description}
                                    </td>
                                    <td class="text-break">${transaction.amount.total} USD</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-12 d-flex justify-content-evenly">
                        <input class="btn btn-primary" type="submit" value="Pay Now"/>
                        <input class="btn btn-danger" type="submit" name="cancel" value="Cancel"/>
                    </div>
                </div>
            </form>
        </div>
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
    </body>
</html>
