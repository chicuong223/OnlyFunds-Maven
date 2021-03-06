<%-- 
    Document   : welcome_page
    Created on : Jun 12, 2021, 1:47:58 PM
    Author     : ASUS GAMING
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navigation bar -->
<c:import url="navbar.jsp"></c:import>  
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Only Funds</title>
            <link type="text/css" rel="stylesheet" href="styles/welcome_page.css">
            <link rel="stylesheet" href="styles/main_page.css">
            <link rel="stylesheet" href="styles/shared.css">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        </head>
        <body>
            <!-- Main content -->
            <main class="main-container" id="main-container">
            <c:import url="unauthorized_vertical_navbar.jsp"></c:import>
                <div class="main-content">
                    <!-- category bar -->
                <!-- <c:import url="category-bar.html"></c:import> -->
                    <div class="container mt-3">
                        <div class="row">
                            <div class="col-lg-12 p-3 pb-1 my-auto text-center">
                                <span style="font-size: 50px; color:#B82481; font-family: Righteous;">ONLY FUNDS</span>
                                <p style="font-size: 36px; color:#69336D;">Create your own work and earn money!</p>
                                <a href="RegisterServlet" class="register-link">
                                    <span>Get started!</span>
                                    <svg width="18px" height="15px" viewBox="0 0 13 10">
                                        <path d="M1,5 L11,5"></path>
                                        <polyline points="8 1 12 5 8 9"></polyline>
                                    </svg>
                                </a>
                            </div>
                        </div>
                        <div class="row gx-4 p-3 mb-2">
                            <div class="title mb-4">
                                <a href="explore">
                                    <span>Popular Creators</span>
                                    <i class="fas fa-angle-double-right"></i>
                                </a>
                            </div>
                        <c:forEach var="creator" items="${userList}">
                            <div class="col-lg-3 m-0 p-0">
                                <div class="card mx-auto text-center">
                                    <a href="CreatorInfoServlet?username=${creator.key.username}" class="stretched-link"></a>
                                    <img src="images/avatars/${creator.key.avatarURL}" class="avatar" alt="avatar">
                                    <div class="card-body p-0">
                                        <h5 class="card-title text-truncate">${creator.key.username}</h5>
                                        <hr style="margin: .2rem 0;">
                                        <div class="cat-tags">
                                            <c:forEach items="${creator.value}" var="cat">
                                                <span class="btn btn-sm subscribe">${cat.categoryName}</span>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Bottom -->
                    <div class="row gx-4 p-3" id="row">
                        <div class="title mb-4">
                            <a href="PostListServlet?action=recent">
                                <span>Recent posts</span>
                                <i class="fas fa-angle-double-right"></i>
                            </a>
                        </div>
                        <c:forEach var="post" items="${postList}">
                            <div class='col-lg-3 mb-3'>
                                <c:if test="${post.value[2] == 1}">
                                    <div class=card id=post>
                                        <a href="PostDetailServlet?id=${post.key.postId}" class=stretched-link></a>
                                        <div class='card-header p-2 pt-1'>
                                            <h4 class='card-title fw-bold text-truncate'>${post.key.title}</h4>
                                            <h6 class='card-subtitle text-muted' style='font-size: 16px;'>${post.key.uploader.username}    <fmt:formatDate pattern="dd-MM-yyyy" value="${post.key.uploadDate}"/></h6>
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
                                <c:if test="${post.value[2] == 0}">
                                    <div class='card post premium mx-auto' id=post>
                                        <div class="ribbon-wrapper">
                                            <div class="ribbon">
                                                Premium
                                            </div>
                                        </div>
                                        <a href="PostDetailServlet?id=${post.key.postId}" class=stretched-link></a>
                                        <div class='card-header p-2 pt-1'>
                                            <h4 class='card-title fw-bold text-truncate'>${post.key.title}  </h4>
                                            <h6 class='card-subtitle text-muted' style='font-size: 16px;'>${post.key.uploader.username}  <fmt:formatDate pattern="dd-MM-yyyy" value="${post.key.uploadDate}"/></h6>
                                        </div>
                                        <div class='card-body p-2 pt-1'>
                                            <a href='PostDetailServlet?id=${post.key.postId}' class='stretched-link'></a>
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
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </main>
        <!--        <footer style="position: relative; top:">
        
                </footer>-->
    </body>
</html>
