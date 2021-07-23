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
                        </c:when>
                        <c:otherwise>
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
                                    <c:choose>
                                        <%-- Nếu user không phải là tác giả và chưa report thì cho phép report --%>
                                        <c:when
                                            test="${sessionScope.user != null && sessionScope.user.username != post.uploader.username}">
                                            <a id="report_post_btn" href="#" class="float-end"
                                               data-bs-toggle="modal" data-bs-target="#reportForm"
                                               onclick="openFormReport(${post.postId}, 'post')">
                                                <i class="fa fa-exclamation-triangle text-dark"></i>
                                            </a>
                                        </c:when>
                                        <%-- Nếu user không phải là tác giả và đã report thì không cho phép
                                            report nữa --%>
                                        <%-- Cái này hiện tại đang sai --%>
                                        <c:when
                                            test="${sessionScope.user == null || sessionScope.user.username != post.uploader.username }">
                                            <a id="report-post-btn" href="#" data-bs-toggle="tooltip"
                                               data-bs-placement="top"
                                               title="You have already reported this post!"
                                               style="float: right;">
                                                <i class="fas fa-exclamation-triangle"></i>
                                            </a>
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
                                            <a href="#" class="float-end"
                                               data-bs-toggle="modal"
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
                                            <img src="images/avatars/${sessionScope.user.avatarURL}"
                                                 alt="avatar">
                                        </div>
                                        <div class="comment-text">
                                            <form action="WriteCommentServlet" method="post">
                                                <input class="rounded-pill ps-2" type="text"
                                                       placeholder="Write a comment...">
                                            </form>
                                        </div>
                                    </c:if>
                                    <c:if test="${sessionScope.user == null}">
                                        <h4 class="text-center text-danger">You must login to comment on
                                            this post</h4>
                                        </c:if>
                                </div>
                                <%-- Comment list --%>
                                <c:forEach items="${cmtList}" var="cmt" varStatus="cmtLoop">
                                    <div class="row" id="cmt-${cmt.commentID}">
                                        <div class="col-2">
                                            <img src="images/avatars/${cmt.user.avatarURL}"
                                                 alt="${cmt.user.avatarURL}" class="img-thumbnail" />
                                        </div>
                                        <div class="col">
                                            <p class="fw-bold">${cmt.user.username}</p>
                                            <p id="${cmt.commentID}-content">${cmt.content}</p>
                                            <c:if test="${cmt.user.username eq sessionScope.user.username}">
                                                <button class="btn btn-warning" data-bs-toggle="modal"
                                                        data-bs-target="#edit-modal-${cmt.commentID}">Edit</button>
                                                <div class="editform modal"
                                                     id="edit-modal-${cmt.commentID}">
                                                    <div class="modal-dialog modal-dialog-centered">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">Edit comment</h5>
                                                                <button type="button" class="btn-close"
                                                                        data-bs-dismiss="modal"
                                                                        aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="EditCommentServlet"
                                                                      method="post"
                                                                      id="edit-form-${cmt.commentID}">
                                                                    <input type="hidden" name="cmtID"
                                                                           value="${cmt.commentID}" />
                                                                    <p>New Content: </p>
                                                                    <textarea class="form-control"
                                                                              style="resize:none"
                                                                              id="newContent-${cmt.commentID}">${cmt.content}</textarea>
                                                                </form>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button"
                                                                        class="btn btn-secondary"
                                                                        data-bs-dismiss="modal">Close</button>
                                                                <button class="btn btn-warning"
                                                                        type="button"
                                                                        onclick="editCmt(${cmt.commentID})"
                                                                        data-bs-dismiss="modal">Edit</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <button class="btn btn-danger" data-bs-toggle="modal"
                                                        data-bs-target="#delete-modal-${cmt.commentID}">Delete</button>
                                                <div class="modal" id="delete-modal-${cmt.commentID}">
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
                                                                      id="delete-form-${cmt.commentID}">
                                                                    <input type="hidden" name="cmtID"
                                                                           value="${cmt.commentID}" />
                                                                    <p>You and other users will not be able
                                                                        to see this comment anymore</p>
                                                                    <p class="text-danger">Are you sure ?
                                                                    </p>
                                                                </form>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button"
                                                                        class="btn btn-secondary"
                                                                        data-bs-dismiss="modal">Close</button>
                                                                <button class="btn btn-danger"
                                                                        onclick="deleteCmt(${cmt.commentID})"
                                                                        data-bs-dismiss="modal" />Delete</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <c:choose>
                                            <c:when test="${sessionScope.user != null}">
                                                <c:choose>
                                                    <c:when test="${isCommnetLikedList[cmtLoop.index]}">
                                                        <%-- when user already liked post --%>
                                                        <i id="cmtLike-${cmt.commentID}"
                                                           class="fa fa-heart" aria-hidden="true"
                                                           onclick="clickLikeComment('${user.username}',${cmt.commentID})">
                                                            <span
                                                                id="countCommentLike-${cmt.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                        </i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i id="cmtLike-${cmt.commentID}"
                                                           class="fa fa-heart-o" aria-hidden="true"
                                                           onclick="clickLikeComment('${user.username}',${cmt.commentID})">
                                                            <span
                                                                id="countCommentLike-${cmt.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                        </i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <i id="cmtLike-${cmt.commentID}" class="fa fa-heart-o"
                                                   aria-hidden="true">
                                                    <span
                                                        id="countCommentLike-${cmt.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                </i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
        </main>
        <!-- Report modal -->
        <div class="modal fade" id="reportForm" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <form action="Report" id="reportForm" onsubmit="submitReport()">
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
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
        </script>
        <script defer type="text/javascript" src="scripts/post_detail.js"></script>
    </body>

</html>