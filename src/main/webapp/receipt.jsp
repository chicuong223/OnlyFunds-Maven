<%-- 
    Document   : receipt
    Created on : Jul 4, 2021, 12:13:33 AM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Receipt</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
    </head>
    <body>
        <header>
            <h1 class="text-center">Payment Done</h1>
        </header>
        <main>
            <c:set var="user" value="${sessionScope.user}"></c:set>
            <div class="w-75 shadow mx-auto">
                <h2>Receipt Details: </h2>
                <div>
                    <h3>Subscriber</h3>
                    <p>Name: ${user.firstName} ${user.lastName}</p>
                    <p>Email: ${user.email}</p>
                </div>
                <div>
                    <h3>Content</h3>
                    <p>Creator: ${tier.creator.firstName} ${tier.creator.lastName}</p>
                    <p>Tier title: ${tier.tierTitle}</p>
                    <p>Price: ${transaction.amount.total}</p>
                </div>
                <div>
                    <a href="CreatorInfoServlet?username=${tier.creator.username}" class="link-primary">Click here to go back</a>
                </div>
            </div>
        </main>
    </body>
</html>
