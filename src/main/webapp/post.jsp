<%-- 
    Document   : post
    Created on : Jun 26, 2021, 11:13:05 AM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Details</title>
        <style>
            .fa-paperclip{
                color: #000000;
                font-size: 120%;
            }
            a{
                text-decoration: none;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script defer>
            function clickBookmarkPost(username, postId) {
                window.alert("clickBookmarkPost");
                event.preventDefault();
                var btnBookmark = document.querySelector("#btnBookmark");
                if (btnBookmark.className === "fas fa-bookmark") {

                    //Call action Bookmark/AddBookmark
                    $.ajax({
                        type: "POST",
                        url: 'AddOrDeleteBookmarkServlet',
                        data: {
                            username: username,
                            postId: postId,
                            action: 'delete'
                        },
                        cache: false,
                        success: function () {
                            btnBookmark.className = "far fa-bookmark";
                            alert('delete bm');
                        }
                    });
                } else {
                    //Call action Bookmark/RemoveBookmark
                    $.ajax({
                        type: "POST",
                        url: 'AddOrDeleteBookmarkServlet',
                        data: {
                            username: username,
                            postId: postId,
                            action: 'add'
                        },
                        cache: false,
                        success: function () {
                            btnBookmark.className = "fas fa-bookmark";
                            alert('add bm');
                        }
                    });
                }
            }
            function clickLikePost(username, postId) {
                alert("here");
                alert(postId + typeof (postId));
                var likePost = document.querySelector("#postLike");
                var countPostLike = document.querySelector("#countPostLike");
                var numOfLike = countPostLike.innerHTML;
                if (likePost.className == "fa fa-heart"/*icon when liked*/) {
                    //call action likePost
                    numOfLike--;
                    countPostLike.innerHTML = numOfLike;
                    likePost.className = ("fa fa-heart-o");/*Replace with icon when liked, turn to unlike button*/
                    $.ajax({
                        type: "POST",
                        url: 'LikeOrUnlikePostServlet',
                        data: {
                            username: username,
                            postId: postId,
                            action: "unlike"
                        },
                        cache: false,
                        success: function () {
                            alert("unliked post");
                        }
                    });
                } else {
                    numOfLike++;
                    countPostLike.innerHTML = numOfLike;
                    likePost.className = ("fa fa-heart");/*Replace with icon when liked, turn to like icon*/
                    $.ajax({
                        type: "POST",
                        url: 'LikeOrUnlikePostServlet',
                        data: {
                            username: username,
                            postId: postId,
                            action: "like"
                        },
                        cache: false,
                        success: function () {
                            alert("liked post");
                        }
                    });
                }
            }

            function deleteCmt(cmt) {
                let cmtDiv = document.getElementById('cmt-' + cmt);
                let cmtCountEl = document.getElementById('cmtCount');
                let cmtCount = cmtCountEl.innerHTML;
                $.ajax({
                    type: "POST",
                    url: "DeleteOrEditComment",
                    data: {
                        commentId: cmt,
                        action: "delete"
                    },
                    cache: false,
                    success: function () {
                        cmtCount--;
                        cmtCountEl.innerHTML = cmtCount;
                        cmtDiv.remove();
                    }
                });
            }
            
            function editCmt(cmt){
                let cmtContentEl = document.getElementById(cmt + "-content");
                let cmtContent = cmtContentEl.textContent;
                let newContent = document.getElementById("newContent-" + cmt);
                if(!newContent.value.trim()){
                    return;
                }
                $.ajax({
                   type: "POST",
                   url: "DeleteOrEditComment",
                   data:{
                       commentId: cmt,
                       newContent: newContent.value,
                       action: "edit"
                   },
                   cache: false,
                   success: function(){
                       cmtContent = newContent.value;
                       cmtContentEl.textContent = cmtContent;
                   }
                });
            }
            function clickLikeComment(username, cmtId) {
                alert("clickLikeComment");
                console.log(username, cmtId);
                event.preventDefault();
                var likeCmt = document.querySelector("#cmtLike-" + cmtId);
                var countCmtLike = document.querySelector("#countCommentLike-" + cmtId);
                var numOfLike = countCmtLike.innerHTML;
                if (likeCmt.className === "fa fa-heart") {
                    //call action likeCmt
                    $.ajax({
                        type: "POST",
                        url: 'LikeOrUnlikeCommentServlet',
                        data: {
                            username: username,
                            commentId: cmtId,
                            action: 'unlike'
                        },
                        cache: false,
                        success: function () {
                            alert("unliked comment");
                            numOfLike--;
                            countCmtLike.innerHTML = numOfLike;
                            likeCmt.className = ("fa fa-heart-o");
                        }
                    });
                } else {
                    $.ajax({
                        type: "POST",
                        url: 'LikeOrUnlikeCommentServlet',
                        data: {
                            username: username,
                            commentId: cmtId,
                            action: 'like'
                        },
                        cache: false,
                        success: function () {
                            alert("liked comment");
                            numOfLike++;
                            countCmtLike.innerHTML = numOfLike;
                            likeCmt.className = ("fa fa-heart");
                        }
                    });
                }
            }
        </script>
    </head>
    <body>
        <c:choose>
            <c:when test="${tiererror != null}">
                <h1>${tiererror}</h1>
            </c:when>
            <c:otherwise>
                <header>
                    <h1 class="text-center">${requestScope.post.title}</h1>
                </header>
                <main>
                    <div class="w-75 mx-auto">
                        <!-- Post Details -->
                        <p>${requestScope.post.uploadDate}</p>
                        <div class="border border-2 rounded" id="postDetails">
                            <div class="border-bottom border-2 text-break p-3">
                                <p>${requestScope.post.description}</p>
                                <c:if test="${!requestScope.post.attachmentURL.isEmpty()}">
                                    <a href="post_file/${requestScope.post.attachmentURL}" class="link-primary"><i class="fa fa-paperclip"></i>${requestScope.post.attachmentURL}</a> <br>
                                    </c:if>
                                    <%--
                                <i class="fa fa-heart-o" aria-hidden="true">${requestScope.postLikeCount}</i>
                                    --%>
                                    <%--Like post button--%>
                                    <c:choose>
                                        <c:when test="${sessionScope.user != null}">
                                            <c:choose>
                                                <c:when test="${isPostLiked}">
                                                    <%-- when user already liked post --%>
                                                <i id="postLike" class="fa fa-heart" aria-hidden="true" onclick="clickLikePost('${user.username}',${post.postId})">
                                                    <span id="countPostLike">${requestScope.postLikeCount}</span>
                                                </i>
                                            </c:when>
                                            <c:otherwise>
                                                <i id="postLike" class="fa fa-heart-o" aria-hidden="true" onclick="clickLikePost('${user.username}',${post.postId})">
                                                    <span id="countPostLike">${requestScope.postLikeCount}</span>
                                                </i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <i id="postLike" class="fa fa-heart-o" aria-hidden="true">
                                            <span id="countPostLike">${requestScope.postLikeCount}</span>
                                        </i>
                                    </c:otherwise>
                                </c:choose>
                                <%-- Bookmark button --%>
                                <c:choose>
                                    <c:when test="${sessionScope.user != null}">
                                        <c:choose>
                                            <c:when test="${isBookmarked}">
                                                <%-- when user already bookmarked post --%>
                                                <span class="me-2" style="cursor: pointer; float: right;">
                                                    <i id="btnBookmark" onclick="clickBookmarkPost('${user.username}',${post.postId})" class="fas fa-bookmark"></i>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="me-2" style="cursor: pointer; float: right;">
                                                    <i id="btnBookmark" onclick="clickBookmarkPost('${user.username}',${post.postId})" class="far fa-bookmark"></i>
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>

                                <i class="fa fa-comments" aria-hidden="true" id="cmtCount">${cmtList.size()}</i>
                                <c:choose>
                                    <c:when test="${sessionScope.user != null && sessionScope.user.username != post.uploader.username}">
                                        <a href="ReportServlet" class="float-end link-primary"><i class="fa fa-exclamation-triangle text-dark" aria-hidden="true"></i>Report</a>
                                    </c:when>
                                    <c:when test="${sessionScope.user != null && sessionScope.user.username == post.uploader.username}">
                                        <a href="EditPostServlet?id=${requestScope.post.postId}" class="float-end link-primary mx-2"><i class="fa fa-pencil text-dark" aria-hidden="true"></i>Edit</a>
                                        <a href="#" class="float-end link-danger" data-bs-toggle="modal" data-bs-target="#deleteModal"><i class="fa fa-trash text-dark" aria-hidden="true"></i>Delete</a>
                                        <div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="modelTitlel">Delete Post</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <form action="DeletePostServlet" method="Post">
                                                        <input type="hidden" name="postID" value="${requestScope.post.postId}"/>
                                                        <div class="modal-body">
                                                            You and all other users will not be able to see this post anymore <br>
                                                            Are you sure you want to delete this post ?
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                            <button type="submit" class="btn btn-primary">Delete</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                            <!-- Comment Section -->
                            <section class="text-break" id="commentSection" style="height: 300px; overflow-x: hidden; overflow-y: scroll">
                                <c:if test="${sessionScope.user != null}">
                                    <form action="WriteCommentServlet" method="POST">
                                        <div class="row">
                                            <div class="col-2">
                                                <img src="images/avatars/${sessionScope.user.avatarURL}" alt="avatar" class="img-thumbnail" width="100px"/>
                                            </div>
                                            <div class="col">
                                                <input type="text" name="content"  placeholder="Write your thought here" class="w-75 p-3 rounded border-1"/>
                                                <input type="hidden" name="postId" value="${requestScope.post.postId}"/>
                                                <button type="submit" class="btn btn-warning py-2">Post</button>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                                <c:forEach items="${cmtList}" var="cmt" varStatus="cmtLoop">
                                    <div class="row" id="cmt-${cmt.commentID}">
                                        <div class="col-2">
                                            <img src="images/avatars/${cmt.user.avatarURL}" alt="${cmt.user.avatarURL}" class="img-thumbnail"/>
                                        </div>
                                        <div class="col">
                                            <p class="fw-bold">${cmt.user.username}</p>
                                            <p id="${cmt.commentID}-content">${cmt.content}</p>
                                            <c:if test="${cmt.user.username eq sessionScope.user.username}">
                                                <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#edit-modal-${cmt.commentID}">Edit</button>
                                                <div class="editform modal" id="edit-modal-${cmt.commentID}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">Edit comment</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="EditCommentServlet" method="post" id="edit-form-${cmt.commentID}">
                                                                    <input type="hidden" name="cmtID" value="${cmt.commentID}"/>
                                                                    <p>New Content: </p>
                                                                    <textarea class="form-control" style="resize:none" id="newContent-${cmt.commentID}">${cmt.content}</textarea>
                                                                </form>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                <button class="btn btn-warning" type="button" onclick="editCmt(${cmt.commentID})" data-bs-dismiss="modal">Edit</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#delete-modal-${cmt.commentID}">Delete</button>
                                                <div class="modal" id="delete-modal-${cmt.commentID}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">Delete comment</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="DeleteCommentServlet" method="post" id="delete-form-${cmt.commentID}">
                                                                    <input type="hidden" name="cmtID" value="${cmt.commentID}"/>
                                                                    <p>You and other users will not be able to see this comment anymore</p>
                                                                    <p class="text-danger">Are you sure ?</p>
                                                                </form>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                <button class="btn btn-danger" onclick="deleteCmt(${cmt.commentID})" data-bs-dismiss="modal"/>Delete</button>
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
                                                        <i id="cmtLike-${cmt.commentID}" class="fa fa-heart" aria-hidden="true" onclick="clickLikeComment('${user.username}',${cmt.commentID})">
                                                            <span id="countCommentLike-${cmt.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                        </i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i id="cmtLike-${cmt.commentID}" class="fa fa-heart-o" aria-hidden="true" onclick="clickLikeComment('${user.username}',${cmt.commentID})">
                                                            <span id="countCommentLike-${cmt.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                        </i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <i id="cmtLike-${cmt.commentID}" class="fa fa-heart-o" aria-hidden="true">
                                                    <span id="countCommentLike-${cmt.commentID}">${countCommentLikeList[cmtLoop.index]}</span>
                                                </i>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:forEach>
                            </section>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </body>
</html>
