<%-- Document : main_page Created on : Jun 20, 2021, 7:02:19 PM Author : ASUS GAMING --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="navbar.jsp"></c:import>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/main_page.css">
        <title>Only Funds</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                var start = 1;
                getPostData(start, start + 5);
                start += 6;
                $(window).scroll(function () {
                    if ($(window).scrollTop() === $(document).height() - $(window).height()) {
                        getPostData(start, start + 2);
                        start += 3;
                    }
                });
                function getPostData(startNo, endNo) {
                    $.post('homepage', {start: startNo, end: endNo}, function (response) {
                        $("#postList").append(response);
                    }, 'text');
                }
            });
        </script>
        <!-- Main content -->
        <main class="mt-3 mb-5">
            <div class="container">
                <div class="row" id="postList">

                </div>
            </div>
        </main>
    </body>

</html>