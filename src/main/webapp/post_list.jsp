<%-- 
    Document   : post_list
    Created on : Jul 1, 2021, 8:16:54 PM
    Author     : chiuy
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${creator.username}</title>
        <link rel="stylesheet" href="/styles/Navbar.css">
        <link rel="stylesheet" href="/styles/vertical_nav.css">
        <link rel="stylesheet" href="/styles/shared.css">
        <link rel="stylesheet" href="/styles/user_page.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:set var="currUser" value="${sessionScope.user}"></c:set>
        <c:set var="cmp" value="${false}"></c:set>
            <header>
                <h1 class="text-center">Recent posts</h1>
            </header>
            <div class="main-content" id="main-content">
                <div class="content container-fluid">
                    <div id="row" class="row gx-4 p-3">
                        <div class="col-12 text-center">
                            <h2 class="fw-bold" style="text-transform: uppercase;;">Recent posts</h2>
                        </div>
                    <c:forEach items="${postList}" var="post">
                        <div class="col-lg-3 mb-2">
                            <c:choose>
                                <c:when test="${post.value == true}">
                                    <div class="card post mx-auto" id="post">
                                        <a href="PostDetailServlet?id=${post.key.postId}"class="stretched-link" />
                                        <div class="card-header p-2 pt-1">
                                            <h4 class="card-title fw-bold">${post.key.title}</h4>
                                            <h6 class="card-subtitle text-muted" style="font-size: 16px; position: relative; z-index: 100;">Author's name</h6>
                                        </div>
                                        <div class="card-body p-2 pt-1">
                                            <p class="card-text">
                                                ${post.key.description}
                                            </p>
                                        </div>
                                        <div class="card-footer p-2 pt-1 pb-1">
                                            <small><i class="fas fa-thumbs-up"></i> 1234</small>
                                            <small><i class="fas fa-comment"></i> 1234</small>
                                            <small><i class="far fa-eye"></i> 1234</small>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <!--  Them premium neu user khong duoc phep xem post de lam mo-->
                                    <div class="card post premium  mx-auto" id="post">
                                        <div class="ribbon-wrapper">
                                            <div class="ribbon">
                                                Premium
                                            </div>
                                        </div>
                                        <a href="#ToSubscription"class="stretched-link" />
                                        <div class="card-header p-2 pt-1">
                                            <h4 class="card-title fw-bold">${post.key.title}</h4>
                                            <h6 class="card-subtitle text-muted" style="font-size: 16px; position: relative; z-index: 100;">Author's name</h6>
                                        </div>
                                        <div class="card-body p-2 pt-1">
                                            <p class="card-text text-center">
                                                You must subscribe to this creator in order to see this post
                                            </p>
                                        </div>
                                        <div class="card-footer p-2 pt-1 pb-1">
                                            <small><i class="fas fa-thumbs-up"></i> 1234</small>
                                            <small><i class="fas fa-comment"></i> 1234</small>
                                            <small><i class="far fa-eye"></i> 1234</small>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
            </div>
            </div>
                <!--<footer>
                    <div class="paging card-footer text-center fs-5">
                        <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${end}" var="i">
                    <c:if test="${i == param.page}">
                        <c:set var="active" value="active"></c:set>
                    </c:if>
                    <c:if test="${i != param.page}">
                        <c:set var="active" value=""></c:set>
                    </c:if>
                    <li class='page-item ${active}'><a href="CreatorInfoServlet?username=${creator.username}&page=${i}" class="page-link">${i}</a></li>
                </c:forEach>
        </ul>
    </div>
</footer>-->
                </body>
                </html>
