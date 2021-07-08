<%-- 
    Document   : cancel_payment
    Created on : Jul 4, 2021, 6:07:31 PM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="5; url=CreatorInfoServlet?username=${creator}"/>
        <title>Cancel Payment</title>
    </head>
    <body>
        <h1>Payment Cancelled</h1>
        <a href="CreatorInfoServlet?username=${creator}">Click here if your browser does not redirect automatically</a>
    </body>
</html>
