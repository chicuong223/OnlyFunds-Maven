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
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script type="text/javascript" src="scripts/manage_subscription_script.js" defer></script>
        </head>

        <body>  
            <main class="main-container">
                <!-- Vertical navbar -->
                <div>
                <c:import url="creator_vertical_navbar.jsp"></c:import>
            </div>
            <!-- Main content of the page -->
            <div class="main-content" id="main-content">
                <h1>Manage Subscriptions</h1>
                <table class="table table-bordered" id="subscriptionsList">

                </table>
            </div>
        </main>
        <script>
            console.log(document.querySelectorAll(".del-btn"));
        </script>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </body>
</html>
