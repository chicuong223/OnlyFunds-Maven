<%-- Document : search_page Created on : Jul 2, 2021, 3:08:06 PM Author : DELL --%>

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
                    <c:import url="vertical_navbar_post.jsp"></c:import>
                    <div class="main-content" id="main-content">
                        <div class="content container-fluid">
                            <div class="row gx-4 p-3 mb-2">
                                <div class="header mb-4">
                                    <span class="p-0 mb-5 mt-3"
                                        style="font-size: 32px; font-weight: bold; border-bottom: 2px solid #B82481;">Creator(s) with keyword(s): </span>
                                </div>
                                <c:forEach items="${userList}" var="user">
                                    <div class="col-lg-3 m-0 p-0">
                                        <div class="card mx-auto text-center">
                                            <a href="CreatorInfoServlet?username=${user.username}" class="stretched-link"></a>
                                            <img src="${user.avatarURL}" class="avatar" alt="avatar">
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
                            </div>
                            <!-- Mỗi 1 field lớn tạo 1 row tương ứng -->
                            <div id="row" class="row gx-4 p-3">
                                <div class="header mb-4">
                                    <span class="p-0 mb-5 mt-3"
                                        style="font-size: 32px; font-weight: bold; border-bottom: 2px solid #B82481;">Post(s) with keyword(s): </span>
                                </div>
                                <!-- Mỗi post tạo 1 column tương ứng -->
                                <c:forEach  items="${postList}" var="post" varStatus="postLoop">
                                    <div class="col-lg-3 mb-2">
                                        <div class="card" id="post">
                                            <!-- Nếu là hot post thì thêm cái này -->
                                            <div class="ribbon-wrapper">
                                                <div class="ribbon hot">
                                                    Hot
                                                </div>
                                            </div>
                                            <a href="PostDetailServlet?id=${post.postId}" class="stretched-link"></a>
                                            <div class="card-header p-2 pt-1">
                                                <h4 class="card-title fw-bold">${post.title}</h4>
                                                <h6 class="card-subtitle text-muted" style="font-size: 16px; position: relative; z-index: 100;">${post.uploader.username}</h6>
                                            </div>
                                            <div class="card-body p-2 pt-1">
                                                <p class="card-text">
                                                    ${post.description}
                                                </p>
                                            </div>
                                            <div class="card-footer p-2 pt-1 pb-1">
                                                <small><i class="fas fa-thumbs-up"></i> ${numLikeList[postLoop.index]}</small>
                                                <small><i class="fas fa-comment"></i> ${numCommentList[postLoop.index]}</small>
                                                <small><i class="far fa-eye"></i> ${post.viewCount}</small>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </main>
            </body>

            </html>