<%-- 
    Document   : manage_subscriptions
    Created on : Jul 3, 2021, 12:00:35 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="navbar.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Subscriptions</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script type="text/javascript" src="scripts/manage_subscription_script.js" defer></script>
    </head>
    <body>
        <main>
            <table class="table table-bordered" id="subscriptionsList">

            </table>
        </main>
    </body>
    <script>
        console.log(document.querySelectorAll(".del-btn"));
    </script>
</html>
