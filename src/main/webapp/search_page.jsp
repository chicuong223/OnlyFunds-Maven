<%-- Document : search_page Created on : Jul 2, 2021, 3:08:06 PM Author : DELL --%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
        <title>Search result</title>
        <!-- Icon -->
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <!-- Shared css -->
        <link rel="stylesheet" href="styles/welcome_page.css">
        <link rel="stylesheet" href="styles/shared.css">
        <link type="text/css" rel="stylesheet" href="styles/main_page.css">
        <!-- Icon script -->
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </head>

    <body>
        <c:import url="navbar.jsp">
        </c:import>
        <main class="main-container">
            <c:if test="${sessionScope.user!=null}">
                <c:import url="vertical_navbar_post.jsp"></c:import>
            </c:if>
            <c:if test="${sessionScope.user==null}">
                <c:import url="unauthorized_vertical_navbar.jsp"></c:import>
            </c:if>
            <div class="main-content" id="main-content">
                <c:if test='${action eq "searchstring"}'>
                    <a href="SearchServlet?a=${requestScope.action}&type=post&search=${search}">Posts</a>
                    <a href="SearchServlet?a=${requestScope.action}&type=creator&search=${search}">Creators</a>
                </c:if>
                <c:if test='${action eq "searchtag"}'>
                    <a href="SearchServlet?a=${requestScope.action}&type=post&id=${id}">Posts</a>
                    <a href="SearchServlet?a=${requestScope.action}&type=creator&id=${id}">Creators</a>
                </c:if>
                <div class="content container-fluid">
                    <!-- Mỗi 1 field lớn tạo 1 row tương ứng -->
                    <div class="row gx-4 p-3 mb-2">
                        <div class="header mb-4">
                            <span class="p-0 mb-5 mt-3"
                                  style="font-size: 32px; font-weight: bold; border-bottom: 2px solid #B82481;">${title} </span>
                        </div>
                        <c:if test='${type.equals("creator")}'>
                            <c:forEach items="${userList}" var="user">
                                <div class="col-lg-3 m-0 p-0">
                                    <div class="card mx-auto text-center">
                                        <a href="CreatorInfoServlet?username=${user.username}" class="stretched-link"></a>
                                        <img src="images/avatars/${user.avatarURL}" class="avatar" alt="avatar">
                                        <div class="card-body p-0">
                                            <h5 class="card-title">${user.username}</h5>
                                            <h6 class="card-subtitle text-muted">1234 subscribers</h6>
                                            <hr style="margin: .2rem 0;">
                                            <div class="cat-tags">
                                                <c:forEach var="cate" items="${cateListList[userLoop.index]}">
                                                    <span class="btn btn-sm subscribe">${cate.categoryName}</span>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test='${type.equals("post")}'>
                            <c:forEach  items="${postList}" var="post" varStatus="postLoop">
                                <div class="col-lg-3 mb-2">
                                    <c:if test="${post.value[2] == 1}">
                                        <div class="card" id="post">
                                            <a href="PostDetailServlet?id=${post.key.postId}" class="stretched-link"></a>
                                            <div class="card-header p-2 pt-1">
                                                <h4 class="card-title fw-bold">${post.key.title}</h4>
                                                <h6 class="card-subtitle text-muted" style="font-size: 16px; position: relative; z-index: 100;">${post.key.uploader.username}   <fmt:formatDate pattern="dd-MM-yyyy" value="${post.key.uploadDate}"/></h6>
                                            </div>
                                            <div class="card-body p-2 pt-1">
                                                <p class="card-text">
                                                    ${post.key.description}
                                                </p>
                                            </div>
                                            <div class="card-footer p-2 pt-1 pb-1">
                                                <small><i class="fas fa-thumbs-up"></i> ${post.value[0]}</small>
                                                <small><i class="fas fa-comment"></i> ${post.value[1]}</small>
                                                <small><i class="far fa-eye"></i> ${post.key.viewCount}</small>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${post.value[2] == 0}">
                                        <div class="card premium post" id="post">
                                            <div class="ribbon-wrapper">
                                                <div class="ribbon">
                                                    Premium
                                                </div>
                                            </div>
                                            <a href="PostDetailServlet?id=${post.key.postId}" class="stretched-link"></a>
                                            <div class="card-header p-2 pt-1">
                                                <h4 class="card-title fw-bold">${post.key.title}</h4>
                                                <h6 class="card-subtitle text-muted" style="font-size: 16px; position: relative; z-index: 100;">${post.key.uploader.username}   <fmt:formatDate pattern="dd-MM-yyyy" value="${post.key.uploadDate}"/></h6>
                                            </div>
                                            <div class="card-body p-2 pt-1">
                                                <p class="card-text">
                                                    You must subscribe to the author of this post to view
                                                </p>
                                            </div>
                                            <div class="card-footer p-2 pt-1 pb-1">
                                                <small><i class="fas fa-thumbs-up"></i> ${post.value[0]}</small>
                                                <small><i class="fas fa-comment"></i> ${post.value[1]}</small>
                                                <small><i class="far fa-eye"></i> ${post.key.viewCount}</small>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>

                </div>
            </div>
        </main>
    </body>

</html>