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
    </head>
    <body>
        <h1>Please Review Before Continuing</h1>
        <form action="ExecutePaymentServlet" method="post">
            <input type="hidden" name="paymentId" value="${param.paymentId}" />
            <input type="hidden" name="PayerID" value="${param.PayerID}" />
            <div>
                <h4>Payment Description</h4>
                <p>Creator: ${sessionScope.tier.creator.username} </p>
                <p>Tier Title: ${transaction.description}</p>
                <p>Price: ${transaction.amount.total} USD</p>
            </div>
            <div>
                <h4>Subscriber Description</h4>
                <p>Username: ${sessionScope.user.username}}</p>
                <p>Email: ${payer.email}</p>
            </div>
            <div>
                <input type="submit" value="Pay Now"/>
                <input type="submit" name="cancel" value="Cancel"/>
            </div>
        </form>
    </body>
</html>
