<%-- 
    Document   : creator_list
    Created on : Jul 14, 2021, 4:22:57 PM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url='navbar.jsp'></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link type="text/css" rel="stylesheet" href="styles/welcome_page.css">
            <link rel="stylesheet" href="styles/main_page.css">
            <link rel="stylesheet" href="styles/shared.css">
            <title>
            <c:if test='${subList != null}'>
                Subscribes
            </c:if>
            <c:if test='${followList != null}'>
                Follows
            </c:if>
            <c:if test="${creatorList != null}">
                Explore
            </c:if>
        </title>
    </head>
    <body>
        <c:if test='${subList != null}'>
            <c:set var="lst" value="${subList}"></c:set>
        </c:if>
        <c:if test='${followList != null}'>
            <c:set var="lst" value="${followList}"></c:set>
        </c:if>
        <c:if test='${creatorList != null}'>
            <c:set var="lst" value="${creatorList}"></c:set>
        </c:if>
        <main class="main-container" id="main-container">
            <c:if test="${sessionScope.user == null}">
                <c:import url="unauthorized_vertical_navbar.jsp"></c:import>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <c:import url="vertical_navbar_post.jsp"></c:import>
            </c:if>
            <div class="main-content">
                <div class='container'>
                    <div class="row gx-4 p-3 mb-2">
                        <div class="header mb-4">
                            <span class="p-0 mb-5 mt-3"
                                  style="font-size: 40px; font-weight: bold; border-bottom: 2px solid #B82481;">
                                <c:if test='${subList != null}'>
                                    Creators that you have subscribed to
                                </c:if>
                                <c:if test='${followList != null}'>
                                    Creators that you are following
                                </c:if>
                                <c:if test="${creatorList != null}">
                                    Explore
                                </c:if>
                            </span>
                        </div>
                        <c:forEach var="creator" items="${lst}">
                            <div class="col-lg-3 m-0 p-0">
                                <div class="card mx-auto text-center">
                                    <a href="CreatorInfoServlet?username=${creator.key.username}" class="stretched-link"></a>
                                    <img src="images/avatars/${creator.key.avatarURL}" class="avatar" alt="avatar">
                                    <div class="card-body p-0">
                                        <h5 class="card-title text-truncate">${creator.key.username}</h5>
                                        <hr style="margin: .2rem 0;">
                                        <div class="cat-tags">
                                            <c:forEach items="${creator.value}" var="cat">
                                                <span class="btn btn-sm subscribe">${cat.categoryName}</span>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <nav class="d-flex justify-content-center mb-4">
                        <ul class="pagination">
                            <li class="page-item">
                                <a class="page-link" href="#">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="index" begin="1" end="${end}">
                                <c:if test='${subList != null}'>
                                    <li class="page-item"><a class="page-link" href='sub?page=${index}'>${index}</a></li>
                                    </c:if>
                                    <c:if test='${followList != null}'>
                                    <li class="page-item"><a class="page-link" href='follow?page=${index}'>${index}</a></li>
                                    </c:if>
                                    <c:if test="${creatorList != null}">
                                    <li class="page-item"><a class="page-link" href='explore?page=${index}'>${index}</a></li>
                                    </c:if>
                                </c:forEach>
                            <li class="page-item">
                                <a class="page-link" href="#">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>
    </body>
</html>
