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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
        </footer>
    </body>
</html>
