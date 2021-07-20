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
            <link type="text/css" rel="stylesheet" href="styles/main_page.css">
            <link type="text/css" rel="stylesheet" href="styles/welcome_page.css">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        </head>
        <body>
            <script type="text/javascript" src="scripts/welcome_page_script.js">
            </script>
            <!-- Main content -->
            <main class="main-container" id="main-container">
            <c:import url="vertical_navbar_post.jsp"></c:import>
                <div class="main-content" id="main-content">
                    <!-- category bar -->
                <c:import url="category-bar.html"></c:import>
                    <div class="container" style="margin: 1rem">
                        <div class="row gx-4 p-3 mb-2">
                            <div class="header mb-4">
                                <span class="p-0 mb-5 mt-3"
                                      style="font-size: 40px; font-weight: bold; border-bottom: 2px solid #B82481;">Popular
                                    Creators</span>
                            </div>
                        <c:forEach var="creator" items="${userList}">
                            <div class="col-lg-3 m-0 p-0">
                                <div class="card mx-auto text-center">
                                    <a href="CreatorInfoServlet?username=${creator.key.username}" class="stretched-link"></a>
                                    <img src="images/avatars/${creator.key.avatarURL}" class="avatar" alt="avatar">
                                    <div class="card-body p-0">
                                        <h5 class="card-title">${creator.key.username}</h5>
                                        <h6 class="card-subtitle text-muted">1234 subscribers</h6>
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
                    <!-- Bottom -->
                    <div class="row gx-4 p-3 mb-4" id="row">
                        <div class="header mb-4">
                            <span class="p-0 mb-5 mt-3"
                                  style="font-size: 40px; font-weight: bold; border-bottom: 2px solid #B82481;">Newly uploaded posts</span>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
