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
            <main class="main-container">
            <c:import url="vertical_navbar_post.jsp"></c:import>
                <div class="main-content">
                <c:import url="category-bar.html"></c:import>
                <div class="content container-fluid">
                    <div id="row" class="row gx-4 p-3">
                        <div class="title mb-4">
                            <a href="#View all">
                                <span>Recent posts</span>
                                <i class="fas fa-angle-double-right"></i>
                            </a>
                        </div>
                         <!-- Mỗi post tạo 1 column tương ứng -->
                    </div>
                </div>
            </div>
        </main>
    </body>

</html>