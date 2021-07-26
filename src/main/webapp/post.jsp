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
                    <c:choose>
                        <c:when test="${tiererror != null}">
                            <h3 class="text-danger">
                                ${tiererror}
                            </h3>
                            <h4>Please subscribe to the tiers below to be able to view this post</h4>
                                <c:forEach items="${tierList}" var="tier">
                                    <p><a href="CreatorInfoServlet?username=${tier.creator.username}">${tier.tierTitle}</a></p>
                                </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <h2 class="text-center mb-0 fw-bold">${post.title}</h2>
                            <div class="text-center col ps-0 mb-3">
                                <a href="CreatorInfoServlet?username=${post.uploader.username}"
                                   style="font-size: 18px;">Author:
                                    ${post.uploader.username}</a>
                            </div>
                            <div class="row mb-2">
                                <!-- Date here -->
                                <div class="col ps-0 fw-bold" style="font-size: 17px;">
                                    <fmt:formatDate value="${post.uploadDate}" pattern="dd-MM-yyyy"></fmt:formatDate>
                                    </div>
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
                                <!-- Icon bar -->
                                <div class="col-12 pb-2 border-bottom" style="position: relative;" id="icon-bar">
                                    <!-- Like button -->
                                    <c:choose>
                                        <%-- Nếu user đã login, cho phép like, unlike --%>
                                        <c:when test="${sessionScope.user != null}">
                                            <c:choose>
                                                <%-- Đã like --%>
                                                <c:when test="${isPostLiked}">
                                                    <a id="like-btn" class="me-4" href="#" onclick="clickLikePost('${user.username}',${post.postId})">
                                                        <i class="fas fa-thumbs-up" id="postLike"></i>
                                                        <span id="countPostLike">${requestScope.postLikeCount}</span>
                                                    </a>
                                                </c:when>
                                                <%-- Chưa like --%>
                                                <c:otherwise>
                                                    <a id="like-btn" class="me-4" href="#" onclick="clickLikePost('${user.username}',${post.postId})">
                                                        <i class="far fa-thumbs-up" id="postLike"></i>
                                                        <span id="countPostLike">${requestScope.postLikeCount}</span>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <%-- Nếu user chưa login, bắt user phải login --%>
                                        <c:otherwise>
                                            <a class="me-4" id="like-post-btn" href="#" data-bs-toggle="modal" data-bs-target="#modal-login">
                                                <i class="far fa-thumbs-up"></i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                    <%-- Comment number --%>
                                    <a href="#CommentWrite" class="comment-number">
                                        <i class="fas fa-comment"></i>
                                        <span id="cmtCount">
                                            ${cmtList.size()}
                                        </span>
                                    </a>
                                    <c:choose>
                                        <%-- Nếu là user hoặc chưa login, hiện nút bookmark và nút report--%>
                                        <c:when test="${sessionScope.user == null || sessionScope.user.username != post.uploader.username}">
                                            <!-- Report button -->
                                            <c:choose>
                                                <%-- Nếu user chưa login, hiện form login--%>
                                                <c:when test="${sessionScope.user == null}">
                                                    <a id="report-post-btn" href="#" data-bs-toggle="modal"
                                                       data-bs-target="#modal-login" style="float: right;" class="ms-4">
                                                        <i class="fas fa-exclamation-triangle"></i>
                                                    </a>
                                                </c:when>
                                                <%-- Nếu user không phải là tác giả và chưa report thì cho phép report --%>
                                                <c:when test="${sessionScope.user.username != post.uploader.username && reported == false}">
                                                    <a id="report-post-btn" href="#" class="float-end ms-4"
                                                       data-bs-toggle="modal" data-bs-target="#reportForm"
                                                       onclick="openFormReport(${post.postId}, 'post')" class="me-4">
                                                        <i class="fa fa-exclamation-triangle text-dark"></i>
                                                    </a>
                                                </c:when>
                                                <%-- Nếu user không phải là tác giả và đã report thì không cho phép report nữa --%>
                                                <c:when test="${sessionScope.user.username != post.uploader.username && reported == true}">
                                                    <span id="report-post-btn" href="#"
                                                          data-bs-toggle="tooltip"
                                                          data-bs-placement="top"
                                                          title="You have already reported this post!"
                                                          style="float: right;" class="ms-4">
                                                        <i class="fas fa-exclamation-triangle"
                                                           style="cursor: default;"></i>
                                                    </span>
                                                </c:when>
                                            </c:choose>
                                            <!-- Bookmark button -->
                                            <c:choose>
                                                <%-- Nếu user chưa login, hiện form login--%>
                                                <c:when test="${sessionScope.user == null}">
                                                    <a id="bookmark-post-btn" href="#" data-bs-toggle="modal"
                                                       data-bs-target="#modal-login" style="float: right;">
                                                        <i class="far fa-bookmark"></i>
                                                    </a>
                                                </c:when>
                                                <%-- Nếu user đã bookmark --%>
                                                <c:when test="${isBookmarked}">
                                                    <span
                                                        style="cursor: pointer; float: right;">
                                                        <i id="btnBookmark"
                                                           onclick="clickBookmarkPost('${user.username}',${post.postId})"
                                                           class="fas fa-bookmark"></i>
                                                    </span>
                                                </c:when>
                                                <%-- Nếu user chưa bookmark --%>
                                                <c:otherwise>
                                                    <span
                                                        style="cursor: pointer; float: right;">
                                                        <i id="btnBookmark"
                                                           onclick="clickBookmarkPost('${user.username}',${post.postId})"
                                                           class="far fa-bookmark"></i>
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <%-- NếU user là tác giả --%>
                                        <c:when test="${sessionScope.user.username == post.uploader.username}">
                                            <%-- Edit button --%>
                                            <a href="EditPostServlet?id=${requestScope.post.postId}" class="float-end ms-4">
                                                <i class="fa fa-pencil"></i>
                                            </a>
                                            <%-- Delete button --%>
                                            <a href="#" class="float-end" data-bs-toggle="modal" data-bs-target="#deleteModal">
                                                <i class="fa fa-trash"></i>
                                            </a>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <%-- Write comment --%>
                                <div class="comment-input mt-3 mb-3">
                                    <%-- Nếu user chưa login --%>
                                    <c:if test="${sessionScope.user == null}">
                                        <h4 class="text-center text-danger">You must login to
                                            comment on
                                            this post</h4>
                                        </c:if>
                                        <c:if test="${sessionScope.user != null}">
                                        <div class="comment-ava">
                                            <img src="images/avatars/${sessionScope.user.avatarURL}" alt="avatar">
                                        </div>
                                        <div class="comment-text">
                                            <form action="WriteCommentServlet" method="post">
                                                <input type="hidden" name="postId" value="${post.postId}">
                                                <input class="rounded-pill ps-2" type="text"
                                                       name="content" placeholder="Write a comment...">
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <%-- Comment list --%>
                                <div class="comment-list">
                                    <c:forEach items="${cmtList}" var="cmt" varStatus="cmtLoop">
                                        <div class="comment mb-3" id="cmt-${cmt.key.commentID}">
                                            <div class="comment-ava">
                                                <img src="images/avatars/${cmt.key.user.avatarURL}"
                                                     alt="avatar" class="avatar" />
                                            </div>
                                            <div class="comment-body">
                                                <div class="comment-content border rounded p-2 pt-1">
                                                    <div class="comment-name">
                                                        <a href="CreatorInfoServlet?username=${cmt.key.user.username}" class="info fw-bold" id="author-link">${cmt.key.user.username}</a>
                                                        <small>commented on</small> <strong><fmt:formatDate pattern="dd-MM-yyyy" value="${cmt.key.commentDate}"></fmt:formatDate></strong>
                                                    </div>
                                                    <p class="mb-0" id="${cmt.key.commentID}-content">
                                                        ${cmt.key.content}
                                                    </p>
                                                </div>
                                                <ul class="comment icon p-0 ps-2 pt-1">
                                                    <li>
                                                        <c:choose>
                                                            <%-- Nếu user đã login--%>
                                                            <c:when test="${sessionScope.user != null}">
                                                                <%-- Nút like comment --%>
                                                                <c:choose>
                                                                    <c:when test="${isCommnetLikedList[cmtLoop.index]}">
                                                                        <%-- when user already liked post --%>
                                                                        <a class="like" href="#Like" onclick="clickLikeComment('${user.username}',${cmt.key.commentID})">
                                                                            <i id="cmtLike-${cmt.key.commentID}" class="fas fa-thumbs-up"></i>
                                                                            <span id="countCommentLike-${cmt.key.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                                        </a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a class="like" href="#Like" onclick="clickLikeComment('${user.username}',${cmt.key.commentID})">
                                                                            <i id="cmtLike-${cmt.key.commentID}" class="far fa-thumbs-up"></i>
                                                                            <span id="countCommentLike-${cmt.key.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                                        </a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <%-- Nếu user không phải là tác giả của comment --%>
                                                                <c:if test="${cmt.key.user.username ne sessionScope.user.username}">
                                                                    <%-- Nếu user chưa report, cho phép report comment --%>
                                                                    <c:if test="${cmt.value == false}">
                                                                        <a class="ms-2" id="report-cmt-btn-${cmt.key.commentID}" href="#" data-bs-toggle="modal" data-bs-target="#reportForm"
                                                                            onclick="openFormReport('${cmt.key.commentID}', 'comment')">
                                                                            <i class="fas fa-exclamation-triangle"></i>
                                                                        </a>
                                                                    </c:if>
                                                                    <%-- Nếu user đã report, không cho phép report comment --%>
                                                                    <c:if test="${cmt.value == true}">
                                                                        <span id="report-cmt-btn" href="#"
                                                                                data-bs-toggle="tooltip"
                                                                                data-bs-placement="top"
                                                                                title="You have already reported this comment!" class="ms-2">
                                                                            <i class="fas fa-exclamation-triangle"></i>
                                                                        </span>
                                                                    </c:if>
                                                                </c:if>
                                                                <%-- Nếu user là tác giả của comment --%>
                                                                <c:if test="${cmt.key.user.username eq sessionScope.user.username}">
                                                                    <%-- Form and button for edit --%>
                                                                    <a class="ms-2 me-2" id="edit-cmt-btn" href="#" data-bs-toggle="modal" data-bs-target="#edit-modal-${cmt.key.commentID}">
                                                                        <i class="far fa-edit"></i>
                                                                    </a>
                                                                    <div class="editform modal" id="edit-modal-${cmt.key.commentID}" style="z-index: 1050;">
                                                                        <div class="modal-dialog modal-dialog-centered">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title">Edit comment</h5>
                                                                                    <button type="button" class="btn-close"
                                                                                            data-bs-dismiss="modal"
                                                                                            aria-label="Close"></button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <form action="EditCommentServlet" method="post"
                                                                                            id="edit-form-${cmt.key.commentID}">
                                                                                        <input type="hidden" name="cmtID"
                                                                                                value="${cmt.key.commentID}" />
                                                                                        <p>New Content: </p>
                                                                                        <textarea class="form-control"
                                                                                                    style="resize:none"
                                                                                                    id="newContent-${cmt.key.commentID}">${cmt.key.content}</textarea>
                                                                                    </form>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary"
                                                                                            data-bs-dismiss="modal">Close</button>
                                                                                    <button class="btn btn-warning" type="button"
                                                                                            onclick="editCmt(${cmt.key.commentID})"
                                                                                            data-bs-dismiss="modal">Edit</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <%-- Form and delete for delete --%>
                                                                    <a id="delete-cmt-btn" href="#" 
                                                                        data-bs-toggle="modal" 
                                                                        data-bs-target="#delete-modal-${cmt.key.commentID}">
                                                                        <i class="far fa-trash-alt"></i>
                                                                    </a>
                                                                    <div class="modal" id="delete-modal-${cmt.key.commentID}">
                                                                        <div class="modal-dialog modal-dialog-centered">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title">Delete comment</h5>
                                                                                    <button type="button" class="btn-close"
                                                                                            data-bs-dismiss="modal"
                                                                                            aria-label="Close"></button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <form action="DeleteCommentServlet"
                                                                                            method="post"
                                                                                            id="delete-form-${cmt.key.commentID}">
                                                                                        <input type="hidden" name="cmtID"
                                                                                                value="${cmt.key.commentID}" />
                                                                                        <p>You and other users will not be able to
                                                                                            see this comment anymore</p>
                                                                                        <p class="text-danger">Are you sure ?</p>
                                                                                    </form>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary"
                                                                                            data-bs-dismiss="modal">Close</button>
                                                                                    <button class="btn btn-danger"
                                                                                            onclick="deleteCmt(${cmt.key.commentID})"
                                                                                            data-bs-dismiss="modal">Delete</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:if>
                                                                <%-- Nếu user là tác giả của post, cho phép ẩn comment --%>
                                                                <c:if test="${sessionScope.user.username == post.uploader.username}">    
                                                                    <a id="delete-cmt-btn" href="#" 
                                                                        data-bs-toggle="modal" 
                                                                        data-bs-target="#delete-modal-${cmt.key.commentID}">
                                                                        Hide
                                                                    </a>
                                                                    <div class="modal" id="delete-modal-${cmt.key.commentID}">
                                                                        <div class="modal-dialog modal-dialog-centered">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title">Hide comment</h5>
                                                                                    <button type="button" class="btn-close"
                                                                                            data-bs-dismiss="modal"
                                                                                            aria-label="Close"></button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <form action="DeleteCommentServlet"
                                                                                            method="post"
                                                                                            id="delete-form-${cmt.key.commentID}">
                                                                                        <input type="hidden" name="cmtID"
                                                                                                value="${cmt.key.commentID}" />
                                                                                        <p>You and other users will not be able to
                                                                                            see this comment anymore</p>
                                                                                        <p class="text-danger">Are you sure ?</p>
                                                                                    </form>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary"
                                                                                            data-bs-dismiss="modal">Close</button>
                                                                                    <button class="btn btn-danger"
                                                                                            onclick="deleteCmt(${cmt.key.commentID})"
                                                                                            data-bs-dismiss="modal">Hide</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:if>
                                                            </c:when>
                                                            <%-- Nếu user chưa login hiện form login --%>
                                                            <c:otherwise>
                                                                <%-- Nút like --%>
                                                                <a class="me-2" id="like-cmt-btn" href="#" data-bs-toggle="modal" data-bs-target="#modal-login">
                                                                    <i class="far fa-thumbs-up"></i>
                                                                </a>
                                                                <%-- Nút report --%>
                                                                <a id="report-cmt-btn" href="#" data-bs-toggle="modal" data-bs-target="#modal-login">
                                                                    <i class="fas fa-exclamation-triangle"></i>
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="line"></div>
                                        </div>
                                    </c:forEach>
                                </div>   
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
        <!-- Report modal -->
        <div class="modal fade" id="reportForm" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <form action="Report" id="reportForm">
                    <input type="hidden" name="objectId" id="reportForm-objectId" />
                    <input type="hidden" name="type" id="reportForm-type" />
                    <div class="modal-content p-3">
                        <div class="modal-header d-flex justify-content-center">
                            <h6 class="m-0 p-0 fw-bold">Please provide information about your problem</h6>
                            <button style="position: absolute; top:10px; right: 10px;" type="button"
                                    class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body p-2">
                            <div class="container-fluid ps-4 pt-2 m-0">
                                <div class="row mb-3">
                                    <div class="col-12 my-auto">
                                        <span>Title: </span>
                                        <i class="fas fa-asterisk fa-xs" style="color: red;"></i>
                                        <span id='report-error' class="text-danger"></span>
                                    </div>
                                    <div class="col-12">
                                        <input type="text" class="form-control form-control-sm"
                                               id="reportForm-title" name="title">
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <span>Description: </span>
                                    </div>
                                    <div class="col-12">
                                        <textarea style="min-height: 10rem;" class="p-2 w-100 form-control"
                                                  name="description" id="reportForm-description"
                                                  placeholder="Additional information if needed..."></textarea>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <button type="submit" class="btn btn-success w-100">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
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
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
        </script>
        <script defer type="text/javascript" src="scripts/post_detail.js"></script>
    </body>
</html>