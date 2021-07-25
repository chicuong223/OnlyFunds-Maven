<%-- Document : main_page Created on : Jun 20, 2021, 7:02:19 PM Author : ASUS GAMING --%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="styles/main_page.css">
            <link rel="stylesheet" href="styles/shared.css">
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
                                <c:if test="${post.value[2] == 0}">
                                    <div class='card post premium mx-auto' id=post>
                                        <div class="ribbon-wrapper">
                                            <div class="ribbon">
                                                Premium
                                            </div>
                                        </div>
                                        <a href="CreatorInfoServlet?username=${post.key.uploader.username}" class=stretched-link></a>
                                        <div class='card-header p-2 pt-1'>
                                            <h4 class='card-title fw-bold'>${post.key.title}</h4>
                                            <div class='card-subtitle text-muted' style='font-size: 16px;'>
                                                <span>${post.key.uploader.username}</span>
                                                <span class="float-end" style="font-size: 14px; padding-top: .2rem"><fmt:formatDate pattern="dd-MM-yyyy" value="${post.key.uploadDate}"/></span>
                                            </div>
                                        </div>
                                        <div class='card-body p-2 pt-1'>
                                            <p class='card-text'>
                                                You must subscribe to the author of this post to view
                                            </p>
                                        </div>
                                        <div class='card-footer p-2 pt-1 pb-1'>
                                            <small><i class='fas fa-thumbs-up'></i>&nbsp;${post.value[0]}</small>
                                            <small><i class='fas fa-comment'></i>&nbsp;${post.value[1]}</small>
                                            <small><i class='far fa-eye'></i>&nbsp;${post.key.viewCount}</small>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${post.value[2] == 1}">
                                    <div class='card post' id=post>
                                        <a href="PostDetailServlet?id=${post.key.postId}" class=stretched-link></a>
                                        <div class='card-header p-2 pt-1'>
                                            <h4 class='card-title fw-bold'>${post.key.title}</h4>
                                            <div class='card-subtitle text-muted' style='font-size: 16px;'>
                                                <span>${post.key.uploader.username}</span>
                                                <span class="float-end" style="font-size: 14px; padding-top: .2rem"><fmt:formatDate pattern="dd-MM-yyyy" value="${post.key.uploadDate}"/></span>
                                            </div>
                                        </div>
                                        <div class='card-body p-2 pt-1'>
                                            <a href='PostDetailServlet?id=${post.key.postId}' class='stretched-link'></a>
                                            <p class='card-text'>
                                                ${post.key.description}
                                            </p>
                                        </div>
                                        <div class='card-footer p-2 pt-1 pb-1'>
                                            <small><i class='fas fa-thumbs-up'></i>&nbsp;${post.value[0]}</small>
                                            <small><i class='fas fa-comment'></i>&nbsp;${post.value[1]}</small>
                                            <small><i class='far fa-eye'></i>&nbsp;${post.key.viewCount}</small>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                    <nav class="d-flex justify-content-center">
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