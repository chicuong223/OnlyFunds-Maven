<%-- 
    Document   : creator_info
    Created on : Jul 3, 2021, 11:55:20 AM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Creator's Info</title>
        </head>
        <body>
            <main class="main-container">
            <c:if test="${sessionScope.user == null}">
                <c:import url="unauthorized_vertical_navbar.jsp"></c:import>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <c:import url="vertical_navbar_post.jsp"></c:import>
            </c:if>
            <div class="main-content" id="main-content">
                <div class="content container-fluid p-0 mt-4" style="width: 90%;">
                    <!-- User's info -->
                    <div class="row header mx-auto">
                        <div class="col-lg-4 text-center">
                            <img src="images/avatars/${creator.avatarURL}" alt="avatar" class="img-fluid rounded-circle">
                        </div>
                        <div class="col-lg-8 ps-0">
                            <h3 class="fw-bold">
                                ${creator.username}
                                <button style="background-color: #ce68a8; border-color: #ce68a8"
                                        class="btn btn-sm btn-primary ms-2" id="follow">Follow</button>
                            </h3>
                            <div class="cat-list text-muted mb-2">
                                <span class="btn btn-sm cat">Art</span>
                                <span class="btn btn-sm cat">Software & Game</span>
                                <span class="btn btn-sm cat">Journalism</span>
                                <span class="btn btn-sm cat">Photography</span>
                                <span class="btn btn-sm cat">Music</span>
                                <span class="btn btn-sm cat">Others</span>
                            </div>
                            <p class="bio">
                                ${creator.bio}
                            </p>
                        </div>
                        <div class="col-12 mt-2 d-flex justify-content-evenly" id="row-subheader">
                            <span class="text-center">
                                <h4>Posts</h4>
                                <h4>1234</h4>
                            </span>
                            <span class="text-center">
                                <h4>Following</h4>
                                <h4>1234</h4>
                            </span>
                            <span class="text-center">
                                <h4>Follower</h4>
                                <h4>1234</h4>
                            </span>
                        </div>
                    </div>
                    <!-- Subscription -->
                    <div class="row mb-2 mx-auto" style="width: 100%;">
                        <hr class="mt-2 mb-2">
                        <div class="col-12 mb-3 text-center">
                            <h2 class="fw-bold" style="text-transform: uppercase;">Subscribe to this Creator</h2>
                        </div>
                        <!-- Mỗi tier tạo 1 column -->
                        <c:forEach var="tier" items="${tiers}">
                            <div class="col-lg-4 ps-0 pe-0 mx-auto">
                                <div class="card tier mx-auto">
                                    <a href="#" class="stretched-link" data-bs-toggle="modal" data-bs-target="#modal-${tier.tierId}"></a>
                                    <h4 class="card-header text-center text-truncate t-name">Tier name 1</h4>
                                    <div class="card-body p-2">
                                        <h4 class="card-title text-center price">${tier.price} USD</h4>
                                        <p class="description">
                                            ${tier.description}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal" id="modal-${tier.tierId}">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Subscribe Confirmation</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <c:choose>
                                            <c:when test="${sessionScope.user == null}">
                                                <div class="modal-body">
                                                    <p class="text-danger">Please login to continue</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <a class="btn btn-success" href="login">Login</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="SubscribeServlet" method="post">
                                                    <input type="hidden" name="tierid" value="${tier.tierId}"/>
                                                    <div class="modal-body">
                                                        <p>Title: ${tier.tierTitle}</p>
                                                        <p>Description: ${tier.description}</p>
                                                        <p>Price: ${tier.price} USD</p>
                                                        <p class="text-primary">Do you want to subscribe to this tier?</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Confirm</button>
                                                    </div>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <hr class="mt-4 mb-2">
                    </div>
                    <!-- Post -->
                <div class="row mt-2 mb-2 mx-auto" style="width: 100%;">
                    <!-- Header -->
                    <div class="col-12 text-center">
                        <h2 class="fw-bold" style="text-transform: uppercase;;">Recent posts</h2>
                    </div>
                    <c:forEach items="${postList}" var="post">
                    <div class="col-lg-4 mb-2">
                        <!-- Nếu là premium thì thêm class premium để blur text -->
                        <div class="card post premium mx-auto" id="post">
                            <a href="PostDetailServlet?id=${post.key.postId}" class="stretched-link"></a>
                            <div class="card-header p-2 pt-1">
                                <h4 class="card-title fw-bold text-truncate">${post.key.title}</h4>
                                <h6 class="card-subtitle text-muted"
                                    style="font-size: 16px; position: relative; z-index: 100;">${post.key.uploader.username}</h6>
                            </div>
                            <div class="card-body p-2 pt-1">
                                <p class="card-text">
                                    ${post.key.description}
                                </p>
                            </div>
                            <div class="card-footer p-2 pt-1 pb-1">
                                <small><i class="fas fa-thumbs-up"></i> 1234</small>
                                <small><i class="fas fa-comment"></i> 1234</small>
                                <small><i class="far fa-eye"></i> 1234</small>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                </div>
            </div>
        </main>
    </body>
</html>
