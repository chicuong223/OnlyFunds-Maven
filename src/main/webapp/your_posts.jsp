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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <header>
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
        </header>
        <main>
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
        </main>
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
    </body>
</html>
