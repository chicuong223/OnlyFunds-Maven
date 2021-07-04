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
                <c:forEach items="${userList}" var="user">
                    <div>
                        <a href="UserPageServlet?username=${user.username}">
                            <div>
                                <h3>${user.username}</h3>
                                <img src="${user.avatarURL}">
                            </div>
                        </a>
                    </div>                    
                </c:forEach>
            </div>
            <div class="Posts">
                <c:forEach items="${postList}" var="post">
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
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </main>
    </body>
</html>
