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
    if (likePost.className == "fas fa-thumbs-up"/*icon when liked*/) {
        //call action likePost
        numOfLike--;
        countPostLike.innerHTML = numOfLike;
        likePost.className = ("far fa-thumbs-up");/*Replace with icon when liked, turn to unlike button*/
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
        likePost.className = ("fas fa-thumbs-up");/*Replace with icon when liked, turn to like icon*/
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

function editCmt(cmt) {
    let cmtContentEl = document.getElementById(cmt + "-content");
    let cmtContent = cmtContentEl.textContent;
    let newContent = document.getElementById("newContent-" + cmt);
    if (!newContent.value.trim()) {
        return;
    }
    $.ajax({
        type: "POST",
        url: "DeleteOrEditComment",
        data: {
            commentId: cmt,
            newContent: newContent.value,
            action: "edit"
        },
        cache: false,
        success: function () {
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