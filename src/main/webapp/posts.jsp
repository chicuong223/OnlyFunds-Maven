<%-- 
    Document   : posts
    Created on : Jul 21, 2021, 12:24:46 AM
    Author     : chiuy
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="styles/main_page.css">
            <link rel="stylesheet" href="styles/shared.css">
            <title>${actionTitle}</title>
    </head>
    <body>
        <main class="main-container">
            <c:if test="${sessionScope.user == null}">
                <c:import url="unauthorized_vertical_navbar.jsp"></c:import>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <c:import url="vertical_navbar_post.jsp"></c:import>
            </c:if>
            <div class="main-content">
                <c:import url="category-bar.html"></c:import>
                    <div class="content container-fluid">
                        <div id="row" class="row gx-4 p-3">
                            <div class="title mb-4">
                                <a href="#View all">
                                    <span>${actionTitle}</span>
                                <i class="fas fa-angle-double-right"></i>
                            </a>
                        </div>
                        <!-- Mỗi post tạo 1 column tương ứng -->
                        <c:forEach var="post" items="${postList}">
                            <div class='col-lg-3 mb-3'>
                                <c:if test="${post.value[2] == 1}">
                                    <div class=card id=post>
                                        <a href="PostDetailServlet?id=${post.key.postId}" class=stretched-link></a>
                                        <div class='card-header p-2 pt-1'>
                                            <h4 class='card-title fw-bold'>${post.key.title}</h4>
                                            <h6 class='card-subtitle text-muted' style='font-size: 16px;'>${post.key.uploader.username}  <fmt:formatDate value="${post.key.uploadDate}" pattern="dd-MM-yyyy"/></h6>

                                        </div>
                                        <div class='card-body p-2 pt-1'>
                                            <a href='PostDetailServlet?id=${post.key.postId}' class='stretched-link'></a>
                                            <p class='card-text'>
                                                ${post.key.description}
                                            </p>
                                        </div>
                                        <div class='card-footer p-2 pt-1 pb-1'>
                                            <small><i class='fas fa-thumbs-up'></i>${post.value[0]}</small>
                                            <small><i class='fas fa-comment'></i>${post.value[1]}</small>
                                            <small><i class='far fa-eye'></i>${post.key.viewCount}</small>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${post.value[2] == 0}">
                                    <div class='card post premium' id=post>
                                        <div class="ribbon-wrapper">
                                            <div class="ribbon">
                                                Premium
                                            </div>
                                        </div>
                                        <a href="PostDetailServlet?id=${post.key.postId}" class=stretched-link></a>
                                        <div class='card-header p-2 pt-1'>
                                            <h4 class='card-title fw-bold'>${post.key.title}</h4>
                                            <h6 class='card-subtitle text-muted' style='font-size: 16px;'>${post.key.uploader.username}  <fmt:formatDate value="${post.key.uploadDate}" pattern="dd-MM-yyyy"/></h6>
                                        </div>
                                        <div class='card-body p-2 pt-1'>
                                            <p class='card-text'>
                                                You must subscribe to the author of this post to view
                                            </p>
                                        </div>
                                        <div class='card-footer p-2 pt-1 pb-1'>
                                            <small><i class='fas fa-thumbs-up'></i>${post.value[0]}</small>
                                            <small><i class='fas fa-comment'></i>${post.value[1]}</small>
                                            <small><i class='far fa-eye'></i>${post.key.viewCount}</small>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                    <nav class="d-flex justify-content-center mb-4">
                        <ul class="pagination">
                            <li class="page-item">
                                <c:if test="${param.page != null && param.page > 1}">
                                    <a class="page-link" href="PostListServlet?page=${param.page - 1}&action=${action}">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </c:if>
                                <c:if test="${param.page == null || param.page == 1}">
                                    <a class="page-link text-muted" href="#">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </c:if>
                            </li>
                            <c:forEach var="index" begin="1" end="${end}">
                                <li class="page-item <c:if test="${param.page == index}">active</c:if>"><a class="page-link" href='PostListServlet?page=${index}&action=${action}'>${index}</a></li>
                                </c:forEach>
                            <li class="page-item">
                                <c:choose>
                                    <c:when test="${end <= 1}">
                                        <a class="page-link text-muted" href="#">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:when>
                                    <c:when test="${param.page == null}">
                                        <a class="page-link" href="PostListServlet?page=2&action=${action}">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:when>
                                    <c:when test="${param.page < end}">
                                        <a class="page-link" href="PostListServlet?page=${param.page + 1}&action=${action}">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="page-link text-muted" href='#'><span aria-hidden="true">&raquo;</span></a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>
    </body>
</html>
