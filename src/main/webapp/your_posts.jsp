<%-- 
    Document   : your_posts
    Created on : Jun 25, 2021, 5:12:49 PM
    Author     : chiuy
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${CreatorInfoServletFlag == null}">
    <c:import url="navbar.jsp"></c:import>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html;">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <!-- Logo -->
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <!-- Navbar and shared css -->
        <link type="text/css" rel="stylesheet" href="styles/navbar.css">
        <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
        <link rel="stylesheet" href="styles/shared.css">
        <!-- Main css -->
        <link type="text/css" rel="stylesheet" href="styles/main_page.css">
        <!-- Icon -->
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
        <!--Ajax-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>Your Posts</title>
        <style>
            .post{
                text-decoration: none;
                color: #000000;
            }
            .post:hover .container{
                background-color: gray;
                color: #000000;
            }
        </style>
    </head>

    <body>  
        <main class="main-container">
            <!-- Vertical navbar -->
            <div>
                <c:import url="vertical_navbar_post.jsp"></c:import>
                </div>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <div class="row">
                        <div class="col-2 w-75">
                            <h2>Your Posts</h2>
                        <c:if test="${CreatorInfoServletFlag == null}">
                            <a href="WritePostServlet" class="btn btn-warning ms-2">Write a new Post</a>
                        </c:if>
                    </div>
                    <div class="col-3">
                        <h2>Post count: ${count}</h2>
                    </div>
                </div>
                <div class="Posts">
                    <c:forEach items="${postList}" var="post">
                        <div class="my-2 w-75 mx-auto">
                            <a href="PostDetailServlet?id=${post.key.postId}" class="post">
                                <div class="container border my-5 overflow-hidden ">
                                    <div class="row">
                                        <div class="col" style="height: 150px">
                                            <p class="fw-bold">${post.key.title}</p>
                                            <p class="mh-100">${post.key.description}</p>
                                        </div>
                                        <div class="col text-center">
                                            <p>Posted on: <span><fmt:formatDate value="${post.key.uploadDate}" pattern="dd-MMM-yyyy"/></span></p>
                                            <div class="row">
                                                <div class="col">
                                                    <p class="fs-2">${post.key.viewCount}</p>
                                                    <p>Views</p>
                                                </div>
                                                <div class="col">
                                                    <p class="fs-2">${post.value[0]}</p>
                                                    <p>Likes</p>
                                                </div>
                                                <div class="col">
                                                    <p class="fs-2">${post.value[1]}</p>
                                                    <p>Comments</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <footer class="card-footer">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${end}" var="index">
                            <c:if test="${param.page == index}">
                                <c:set var="active" value="active"/>
                            </c:if>
                            <c:if test="${param.page != index}">
                                <c:set var="active" value=""/>
                            </c:if>
                            <li class="page-item ${active}"><a href="YourPostsServlet?page=${index}" class="page-link">${index}</a></li>
                            </c:forEach>
                    </ul>
                </footer>
            </div>
        </main>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <script src="scripts/account_info_script.js"></script>
    </body>
</html>