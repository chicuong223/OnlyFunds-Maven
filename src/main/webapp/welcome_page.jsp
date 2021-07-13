<%-- 
    Document   : welcome_page
    Created on : Jun 12, 2021, 1:47:58 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navigation bar -->
<c:import url="navbar.jsp"></c:import>  
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Only Funds</title>
        </head>
        <body>
            <!-- Main content -->
            <main class="mt-3">
                <div class="container" style="margin-top: 10vh;">
                    <!-- Top -->
                    <div class="row">
                        <div class="col-lg-6 p-3 my-auto text-center">
                            <span style="font-size: 50px; color:#B82481; font-family: Righteous;">ONLY FUNDS</span>
                            <p style="font-size: 40px; color:#69336D;">Create your own work and earn money!</p>
                        </div>
                        <div class="col-lg-6 p-0">
                            <img src="images/Wallpaper-2.jfif" alt="Insert image here" class="img-fluid">
                        </div>
                    </div>
                    <!-- Bottom -->
                    <div class="row">
                        <span class="p-0 mb-5 mt-3"
                              style="font-size: 40px; font-weight: bold; border-bottom: 1px solid #B82481;">Popular
                            Creators</span>
                        <div id="carouselExampleDark" class="carousel carousel-dark slide p-0 m-0" data-bs-ride="carousel">
                            <div class="carousel-inner">
                            <c:set var="i" value="0"></c:set>
                            <c:forEach var="user" items="${userList}">
                                <c:set var="i" value="${i + 1}"></c:set>
                                <c:choose>
                                    <c:when test="${i == 1}">
                                        <c:set var="active" value="active"></c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="active" value=""></c:set>
                                    </c:otherwise>
                                </c:choose>
                                <div class="container carousel-item ${active}" data-bs-interval="10000">
                                    <div class="mx-auto w-50">
                                        <div class="card mx-auto">
                                            <img src="${pageContext.request.contextPath}/images/avatars/${user.avatarURL}" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">${user.firstName} ${user.lastName}</h5>
                                                <p class="card-text">
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    ${user.bio}
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="CreatorInfoServlet?username=${user.username}" class="stretched-link"></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <br><br>
                        <div class="carousel-indicators carousel-indicators-round">
                            <c:set var="i" value="0"></c:set>
                            <c:forEach var="index" begin="0" end="${userList.size() - 1}">
                                <c:set var="i" value="${i + 1}"></c:set>
                                <c:choose>
                                    <c:when test="${i == 1}">
                                        <c:set var="active" value="active"></c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="active" value=""></c:set>
                                    </c:otherwise>
                                </c:choose>
                                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="${index}" class="${active}"></button>
                            </c:forEach>
                            <!--                            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0"
                                                                class="active"></button>
                                                        <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1"></button>
                                                        <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2"></button>-->
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
