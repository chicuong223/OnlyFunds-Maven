<%-- 
    Document   : search_page
    Created on : Jul 2, 2021, 3:08:06 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:import url="navbar.jsp">
        </c:import>  
        <h1>Hello world</h1>
        <main>
            <div class="creator-list">
                <c:forEach items="${userList}" var="user" varStatus="userLoop">
                    <div>
                        <a href="CreatorInfoServlet?username=${user.username}">
                            <div>
                                <h3>${user.username}</h3>
                                <img src="images/avatars/${user.avatarURL}" alt="${user.avatarURL}" width="100">
                            </div>
                            <div>Num sub: ${numSubscriberList[userLoop.index]}</div>
                            <div>Cate list:</div>
                            <c:forEach var="cate" items="${cateListList[userLoop.index]}">
                                <div>${cate.categoryName}</div>
                            </c:forEach>
                        </a>
                    </div>                    
                </c:forEach>
            </div>
            <div class="Posts">
                <c:forEach items="${postList}" var="post" varStatus="postLoop">
                    <div>
                        <a href="PostDetailServlet?id=${post.postId}" class="post">
                            <div class="container border my-5 overflow-hidden ">
                                <div class="row">
                                    <div class="col" style="height: 150px">
                                        <p class="fw-bold">${post.title}</p>
                                        <p class="mh-100">${post.description}</p>
                                    </div>
                                    <%--<div class="col text-center">
                                        <p>Posted on: <span>${post.uploadDate}</span></p>
                                        <div class="row">
                                            <div class="col">
                                                <p class="fs-2">${post.viewCount}</p>
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
                                    </div--%>
                                    <>
                                </div>
                            </div>
                            <div>num view: ${post.viewCount}</div>
                            <div>num like: ${numLikeList[postLoop.index]}</div>
                            <div>num comment: ${numCommentList[postLoop.index]}</div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </main>
    </body>
</html>
