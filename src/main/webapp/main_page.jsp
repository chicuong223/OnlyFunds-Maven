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
                        <c:forEach var="post" items="${postList}">
                            <div class='col-lg-3 mb-3'>
                                <div class=card id=post>
                                    <a href="PostDetailServlet?id=${post.postId}" class=stretched-link></a>
                                    <div class='card-header p-2 pt-1'>
                                        <h4 class='card-title fw-bold'>${post.title}</h4>
                                        <h6 class='card-subtitle text-muted' style='font-size: 16px;'>${post.uploader.username}</h6>
                                    </div>
                                    <div class='card-body p-2 pt-1'>
                                        <a href='PostDetailServlet?id=${post.postId}' class='stretched-link'></a>
                                        <p class='card-text'>
                                            ${post.description}
                                        </p>
                                    </div>
                                    <div class='card-footer p-2 pt-1 pb-1'>
                                        <!--<small><i class='fas fa-thumbs-up'></i></small>-->
                                        <!--                                        <small><i class='fas fa-comment'></i></small>
                                                                                <small><i class='far fa-eye'></i></small>-->
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <nav class="d-flex justify-content-center mb-4">
                        <ul class="pagination">
                            <li class="page-item">
                                <a class="page-link" href="#">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="index" begin="1" end="${end}">
                                <li class="page-item"><a class="page-link" href='homepage?page=${index}'>${index}</a></li>
                                </c:forEach>
                            <li class="page-item">
                                <a class="page-link" href="#">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>
    </body>

</html>