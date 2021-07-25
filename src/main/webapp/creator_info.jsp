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
            <link rel="stylesheet" href="styles/user_page.css">
            <link rel="stylesheet" href="styles/shared.css">
            <title>Creator's Info</title>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
                                <span  id="creator-username" class='h3'>
                                    ${creator.username}
                                </span>
                                <!-- if user is not signed in, click follow button will open login modal -->
                                <c:if test="${sessionScope.user == null}">
                                    <button style="background-color: #ce68a8; border-color: #ce68a8"
                                            class="btn btn-sm btn-primary ms-2" 
                                            data-bs-toggle="modal" data-bs-target="#modal-login">Follow</button>
                                </c:if>
                                <!-- if user has logged in -->
                                <c:if test="${sessionScope.user != null && sessionScope.user.username ne creator.username}">
                                    <c:if test="${followed == false}">
                                        <button style="background-color: #ce68a8; border-color: #ce68a8"
                                                class="btn btn-sm btn-primary ms-2" 
                                                id="follow">Follow</button>
                                    </c:if>
                                    <c:if test="${followed == true}">
                                        <button style="background-color: #cecece; border-color: #cecece"
                                                class="btn btn-sm btn-primary ms-2" 
                                                id="follow">Following</button>
                                    </c:if>
                                </c:if>
                            </h3>
                            <div class="cat-list text-muted mb-2">
                                <c:forEach items="${cateList}" var="cate">
                                    <span class="btn btn-sm cat">${cate.categoryName}</span>
                                </c:forEach>
                            </div>
                            <p class="bio">
                                ${creator.bio}
                            </p>
                        </div>
                        <div class="col-12 mt-2 d-flex justify-content-evenly" id="row-subheader">
                            <span class="text-center">
                                <h4>Posts</h4>
                                <h4>${count}</h4>
                            </span>
                            <span class="text-center">
                                <h4>Subscribers</h4>
                                <h4>${subCount}</h4>
                            </span>
                            <span class="text-center">
                                <h4>Followers</h4>
                                <h4 id='follow-count'>${followCount}</h4>
                            </span>
                        </div>
                    </div>
                    <!-- Subscription -->
                    <div class="row mb-2 mx-auto" style="width: 100%;">
                        <hr class="mt-2 mb-2">
                        <div class="col-12 mb-3 text-center">
                            <h2 class="fw-bold" style="text-transform: uppercase;">Subscribe to this Creator</h2>
                        </div>
                        <c:if test="${subscribed == true}">
                            <h3 class="fw-bold text-danger text-center">You have already subscribed to this creator</h3>
                            <h4 class="text-center">Tier: ${subscribedTier.tierTitle}</h4>
                        </c:if>
                        <c:if test="${tiers.size() <= 0}">
                            <h3 class="fw-bold text-danger text-center">This creator does not have any subscription tier</h3>
                        </c:if>
                        <c:if test="${subscribed == false}">
                            <c:forEach items="${tiers}" var="tier">
                                <!-- Mỗi tier tạo 1 column -->
                                <div class="col-lg-4 ps-0 pe-0 mx-auto">
                                    <div class="card tier mx-auto">
                                        <c:if test="${sessionScope.user == null}">
                                            <a href="#" class="stretched-link" data-bs-toggle="modal" data-bs-target="#pleaseLogin"></a>
                                        </c:if>
                                        <c:if test="${sessionScope.user.username ne creator.username}">
                                            <a href="#" class="stretched-link" data-bs-toggle="modal" data-bs-target="#modal-${tier.tierId}"></a>
                                        </c:if>
                                        <c:if test="${sessionScope.user.username eq creator.username}">
                                            <a href="#" class="stretched-link"></a>
                                        </c:if>
                                        <h4 class="card-header text-center text-truncate t-name">${tier.tierTitle}</h4>
                                        <div class="card-body p-2">
                                            <h4 class="card-title text-center price">${tier.price} USD</h4>
                                            <p class="description">
                                                ${tier.description}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <hr class="mt-4 mb-2">
                    </div>
                    <!-- Post -->
                    <div class="row mt-2 mb-2 mx-auto" style="width: 100%;">
                        <!-- Header -->
                        <div class="col-12 text-center">
                            <h2 class="fw-bold" style="text-transform: uppercase;;">Recent posts</h2>
                        </div>
                        <c:if test="${postList.size() <= 0}">
                            <h3 class="fw-bold text-danger text-center">This creator does not have any post</h3>
                        </c:if>
                        <c:forEach items="${postList}" var="post">
                            <div class="col-lg-4 mb-2">
                                <!-- Nếu là premium thì thêm class premium để blur text -->
                                <c:if test="${post.value[2] == 0}">
                                    <div class="card post premium mx-auto" id="post">
                                        <div class="ribbon-wrapper">
                                            <div class="ribbon">
                                                Premium
                                            </div>
                                        </div>
                                        <a href="PostDetailServlet?id=${post.key.postId}" class="stretched-link"></a>
                                        <div class="card-header p-2 pt-1">
                                            <h4 class="card-title fw-bold text-truncate">${post.key.title}</h4>
                                            <h6 class="card-subtitle text-muted"
                                                style="font-size: 16px; position: relative; z-index: 100;">${post.key.uploader.username}</h6>
                                        </div>
                                        <div class="card-body p-2 pt-1">
                                            <p class="card-text">
                                                Please login or signup to see this post
                                            </p>
                                        </div>
                                        <div class="card-footer p-2 pt-1 pb-1">
                                            <small><i class="fas fa-thumbs-up"></i>${post.value[0]}</small>
                                            <small><i class="fas fa-comment"></i>${post.value[1]}</small>
                                            <small><i class="far fa-eye"></i>${post.key.viewCount}</small>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${post.value[2] == 1}">
                                    <div class="card post mx-auto" id="post">
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
                                            <small><i class="fas fa-thumbs-up"></i>${post.value[0]}</small>
                                            <small><i class="fas fa-comment"></i>${post.value[1]}</small>
                                            <small><i class="far fa-eye"></i>${post.key.viewCount}</small>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <nav class="d-flex justify-content-center mb-4">
                    <ul class="pagination">
                        <li class="page-item">
                            <a class="page-link" href="#">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach var="index" begin="1" end="${end}">
                            <li class="page-item"><a class="page-link" href='CreatorInfoServlet?username=${creator.username}&page=${index}'>${index}</a></li>
                            </c:forEach>
                        <li class="page-item">
                            <a class="page-link" href="#">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </main>
        <c:forEach items="${tiers}" var="tier">
            <div class="modal fade" id="modal-${tier.tierId}">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Subscribe</h5>
                            <button class="btn btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form action="SubscribeServlet" method="POST" id="form-${tier.tierId}">
                                <input type="hidden" name="tierid" value="${tier.tierId}"/>
                                <p><span class="fw-bold">Tier: </span> ${tier.tierTitle}</p>
                                <p><span class="fw-bold">Description: </span> ${tier.description}</p>
                                <p><span class="fw-bold">Price: </span> ${tier.price} USD</p>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <input type="submit"class="btn btn-info" value="Subscribe" form="form-${tier.tierId}">
                            <button class="btn btn-dark" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="modal fade" id="pleaseLogin">
            <div class="modal-dialog modal-dialog-centered modal-dialog modal-dialog-centered-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Subscribe</h5>
                        <button class="btn btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <h3>Please sign in to continue</h3>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-login" data-bs-dismiss="modal">Sign in</button>
                        <button class="btn btn-dark" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script src='scripts/creator_info_script.js'></script>
    </body>
</html>
