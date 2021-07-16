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
                var end = 7;
                var reachedMax = false;
                getPostData();
                $(window).scroll(function () {
                    if ($(window).scrollTop() === $(document).height() - $(window).height()) {
                        getPostData();
                    }
                });
                function getPostData() {
                    $.ajax({
                        url: 'homepage',
                        method: 'POST',
                        dataType: 'text',
                        cache: false,
                        data: {start: start, end: end},
                        success: function (response) {
                            start += 7;
                            end += 7;
                            $("#postList").append(response);
                        }
                    });
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