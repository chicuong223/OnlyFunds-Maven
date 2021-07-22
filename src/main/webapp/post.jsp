<%-- Document : post Created on : Jun 26, 2021, 11:13:05 AM Author : chiuy --%>

    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <c:import url="navbar.jsp" />
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8" />
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
                    <title>Post Details</title>
                    <!-- Icon -->
                    <link rel="icon" href="images/logo_head.png" type="image/icon type">
                    <!-- CSS files -->
                    <link rel="stylesheet" href="styles/post_detail.css">
                    <link rel="stylesheet" href="styles/shared.css">
                    <!-- Ajax script -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                    <!-- Icon scripts -->
                    <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
                    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
                    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
                </head>

                <body>
                    <main class="main-container">
                        <c:if test="${sessionScope.user != null}">
                            <c:import url="vertical_navbar_post.jsp"></c:import>
                        </c:if>
                        <c:if test="${sessionScope.user == null}">
                            <c:import url="unauthorized_vertical_navbar.jsp"></c:import>
                        </c:if>
                        <div class="main-content" id="main-content">
                            <div class="container w-75 pt-3">
                                <h2 class="text-center mb-0 fw-bold">${post.title}</h2>
                                <div class="text-center col ps-0 mb-3" style="font-size: 18px;">Author:
                                    ${post.uploader.username}</div>
                                <div class="row mb-2">
                                    <!-- Date here -->
                                    <div class="col ps-0 fw-bold" style="font-size: 17px;">${post.uploadDate}</div>
                                    <!-- View count here -->
                                    <div class="col pe-0 fw-bold">
                                        <span class="float-end" style="font-size: 17px;">
                                            <i class="far fa-eye"></i>
                                            ${post.viewCount}
                                        </span>
                                    </div>
                                </div>
                                <div class="row border border-dark rounded p-3 mb-5">
                                    <!-- Post content -->
                                    <div class="col-12 pb-3" id="post-content">
                                        ${post.description}
                                    </div>
                                    <!-- Attachment -->
                                    <c:if test="${!post.attachmentURL.isEmpty()}">
                                        <div class="col-12 pb-3">
                                            <i class="fas fa-paperclip" style="transform: scale(0.8);"></i>
                                            <a href="post_file/${post.attachmentURL}" class="add-on"
                                                style="font-size: 17px; color: red; text-decoration: underline;">
                                                ${requestScope.post.attachmentURL}
                                            </a>
                                        </div>
                                    </c:if>
                                    <div class="col-12 pb-2 border-bottom" style="position: relative;" id="icon-bar">
                                        <!-- Like button -->
                                        <c:choose>
                                            <%-- Nếu user đã login, cho phép like, unlike --%>
                                            <c:when test="${sessionScope.user != null}">
                                                <c:choose>
                                                    <c:when test="${isPostLiked}">
                                                        <i id="postLike" class="fas fa-thumbs-up"
                                                            onclick="clickLikePost('${user.username}',${post.postId})">
                                                            <span
                                                                id="countPostLike">${requestScope.postLikeCount}</span>
                                                        </i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i id="postLike" class="far fa-thumbs-up"
                                                            onclick="clickLikePost('${user.username}',${post.postId})">
                                                            <span
                                                                id="countPostLike">${requestScope.postLikeCount}</span>
                                                        </i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <%-- Nếu user chưa login, bắt user phải login --%>
                                            <c:otherwise>
                                                <i id="postLike" class="far fa-thumbs-up" aria-hidden="true">
                                                    <span id="countPostLike" data-bs-toggle="modal"
                                                        data-bs-target="#modal-login">
                                                        ${requestScope.postLikeCount}
                                                    </span>
                                                </i>
                                            </c:otherwise>
                                        </c:choose>
                                        <!-- Comment number -->
                                        <a href="#CommentWrite" class="comment-number">
                                            <i class="fas fa-comment"></i> ${cmtList.size()}
                                        </a>
                                        <!-- Report button -->
                                        <!-- Ngày mai nhớ thêm modal -->
                                        <c:choose>
                                            <%-- Nếu user không phải là tác giả và chưa report thì cho phép report --%>
                                            <c:when
                                                test="${sessionScope.user != null && sessionScope.user.username != post.uploader.username}">
                                                <a href="#" class="float-end" data-bs-toggle="modal"
                                                    data-bs-target="#reportForm">
                                                    <i class="fa fa-exclamation-triangle text-dark"></i>
                                                </a>
                                            </c:when>
                                            <%-- Nếu user không phải là tác giả và đã report thì không cho phép report nữa --%>
                                            <%-- Cái này hiện tại đang sai --%>
                                            <c:when
                                                test="${sessionScope.user == null || sessionScope.user.username != post.uploader.username }">
                                                <span href="#" data-bs-toggle="tooltip" data-bs-placement="top"
                                                    title="You have already reported this post!" style="float: right;">
                                                    <i class="fas fa-exclamation-triangle"></i>
                                                </span>
                                            </c:when>
                                            <%-- NếU user là tác giả --%>
                                            <c:when
                                                test="${sessionScope.user != null && sessionScope.user.username == post.uploader.username}">
                                                <%-- Edit button --%>
                                                <a href="EditPostServlet?id=${requestScope.post.postId}"
                                                    class="float-end ms-2">
                                                    <i class="fa fa-pencil text-dark"></i>
                                                </a>
                                                <%-- Delete button --%>
                                                <a href="#" class="float-end" data-bs-toggle="modal"
                                                    data-bs-target="#deleteModal">
                                                    <i class="fa fa-trash text-dark"></i>
                                                </a>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <%-- Write comment --%>
                                    <div class="comment-input mt-3 mb-3">
                                        <%-- Nếu user chưa login --%>
                                        <c:if test="${sessionScope.user != null}">
                                            <div class="comment-ava">
                                                <img src="images/Avatar.png" alt="avatar">
                                            </div>
                                            <div class="comment-text">
                                                <form action="WriteCommentServlet" method="post">
                                                    <input class="rounded-pill ps-2" type="text" placeholder="Write a comment...">
                                                </form>
                                            </div>
                                        </c:if>
                                        <c:if test="${sessionScope.user == null}">
                                            <h4 class="text-center text-danger">You must login to comment on this post</h4>
                                        </c:if>
                                    </div>
                                </row>
                            </div>
                        </div>
                    </main>
                    <!-- Delete modal -->
                    <div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modelTitlel">
                                        Delete Post</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <form action="DeletePostServlet" method="Post">
                                    <input type="hidden" name="postID" value="${requestScope.post.postId}" />
                                    <div class="modal-body">
                                        You and all other users will not be able
                                        to see this post anymore <br>
                                        Are you sure you want to delete this
                                        post ?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Delete</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <script>
                        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                            return new bootstrap.Tooltip(tooltipTriggerEl);
                        });
                    </script>
                    <script defer type="text/javascript" src="scripts/post_detail.js"></script>
                </body>

                </html>



                