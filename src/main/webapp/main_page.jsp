<%-- Document : main_page Created on : Jun 20, 2021, 7:02:19 PM Author : ASUS GAMING --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="navbar.jsp"></c:import>

    <!DOCTYPE html>
    <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="styles/main_page.css">
            <title>Only Funds</title>
        </head>

        <body>
            <!-- Main content -->
            <main class="mt-3 mb-5">
                <div class="container d-flex justify-content-end">
                    <a href="WritePostServlet">
                        <button class="btn rounded-pill button bg-secondary text-light me-2">
                            <!-- Không sửa id!!!! -->
                            <span id="Create post">
                                <i class="fas fa-plus-circle"></i>
                            </span>
                        </button>
                    </a>
                    <a href="YourPostsServlet">
                        <button class="btn rounded-pill button bg-secondary text-light">
                            <!-- Không sửa id!!!! -->
                            <span id="Your posts">
                                <i class="fas fa-portrait"></i>
                            </span>
                        </button>
                    </a>
                </div>
                <!-- Creators user has subscribed -->
                <div class="container mt-3">
                    <div class="row gx-5">
                        <div class="col-12 mb-3 h3" style="text-decoration: underline;">
                            Creators you have subscribed to
                        </div>
                        <div class="col-12 mb-3">
                            <a href="CreatorListServlet?action=sub">View All</a>
                        </div>
                    <c:forEach var="creator" items="${subCreators}">
                        <div class="justify-content-center col-4">
                            <div class="card shadow" style="width: 15rem">
                                <!-- Creator avatar -->
                                <img src="images/avatars/${creator.avatarURL}" alt="${creator.username}"/>
                                <div class="card-body">
                                    <!-- Creator username -->
                                    <div class="card-title fw-bold mb-4">${creator.username}</div>
                                    <!-- Creator's bio -->
                                    <div class="mb-4">${creator.bio}</div>
                                    <a href="CreatorInfoServlet?username=${creator.username}" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>       
                </div>
            </div>

            <!-- creator user is following -->
            <div class="container mt-3">
                <div class="row gx-5">
                    <div class="col-12 mb-3 h3" style="text-decoration: underline;">
                        Creators you're following
                    </div>
                    <div class="col-12 mb-3">
                        <a href="CreatorListServlet?action=follow">View All</a>
                    </div>
                    <c:forEach var="creator" items="${followCreators}">
                        <div class="justify-content-center col-4">
                            <div class="card shadow" style="width: 15rem">
                                <!-- Creator avatar -->
                                <img src="images/avatars/${creator.avatarURL}" alt="${creator.username}"/>
                                <div class="card-body">
                                    <!-- Creator username -->
                                    <div class="card-title fw-bold mb-4">${creator.username}</div>
                                    <!-- Creator's bio -->
                                    <div class="mb-4">${creator.bio}</div>
                                    <a href="CreatorInfoServlet?username=${creator.username}" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>       
                </div>
            </div>

            <!-- Creators you might like -->
            <div class="container mt-3" id="interest-creators">
                <div class="row gx-5">
                    <div class="col-12 mb-3 h3" style="text-decoration: underline;">Creators you might be
                        interested to
                    </div>
                    <c:forEach var="creator" items="${cateCreators}">
                        <div class="col-4 d-flex justify-content-center">
                            <div class="card shadow" style="width: 15rem;">
                                <!-- Creator's logo -->
                                <img src="images/avatars/${creator.avatarURL}" class="card-img-top" alt="logo" width="100px">
                                <div class="card-body">
                                    <!-- Creator's name -->
                                    <div class="card-title fw-bold mb-4">${creator.username}</div>
                                    <a href="CreatorInfoServlet?username=${creator.username}" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>


    </body>

</html>