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
            <div class="w-75 shadow mx-auto">
                <h2>Receipt Details: </h2>
                <div>
                    <p>Payer: ${payer.firstName} ${payer.lastName}</p>
                    <p>Description: ${transaction.description}</p>
                    <p>Price: ${transaction.amount.total}</p>
                </div>
            </div>
        </main>
    </body>
</html>
