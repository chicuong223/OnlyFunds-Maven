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
    </head>
    <body>
        <c:set var="currUser" value="${sessionScope.user}"></c:set>
        <c:set var="cmp" value="${false}"></c:set>
            <header>
                <h1 class="text-center">Recent posts</h1>
            </header>
            <main>
            <c:forEach items="${postList}" var="post">
                <div class="w-50 mx-auto border my-3 rounded">
                    <c:choose>
                        <c:when test="${post.value == true}">
                            <a href="PostDetailServlet?id=${post.key.postId}"class="post text-dark" style="text-decoration: none">
                                <div>
                                    <p class="border-bottom p-3">${post.key.title}</p>
                                    <div class="overflow-auto" style="height: 150px">
                                        <p class="p-3 text-break">${post.key.description}</p>
                                    </div>
                                </div>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <p class="border-bottom p-3">${post.key.title}</p>
                                <div class="overflow-auto text-center" style="height: 150px">
                                    <p class="p-3 text-break">You must subscribe a tier of this post to view</p>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </main>
        <footer>
            <div class="paging card-footer text-center fs-5">
                <c:forEach begin="1" end="${end}" var="i">
                    <a href="CreatorInfoServlet?username=${creator.username}&page=${i}" class="pageIndex">${i}</a>
                </c:forEach>
            </div>
        </footer>
    </body>
</html>
