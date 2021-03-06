function clickBookmarkPost(username, postId) {
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
            }
        });
    }
}

function clickLikePost(username, postId) {
    var likePost = document.querySelector("#postLike");
    var countPostLike = document.querySelector("#countPostLike");
    var numOfLike = countPostLike.innerHTML;
    if (likePost.className === "fas fa-thumbs-up" || likePost.className === "fas fa-thumbs-up scaleUp") {
        //call action likePost
        numOfLike--;
        countPostLike.innerHTML = numOfLike;
        likePost.classList = ("far fa-thumbs-up");/*Replace with icon when liked, turn to unlike button*/
        scaleUp('postLike');
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
                setTimeout(function () {
                    likePost.classList.remove('scaleUp');
                }, 500);
            }
        });
    } else {
        numOfLike++;
        countPostLike.innerHTML = numOfLike;
        likePost.classList = ("fas fa-thumbs-up");/*Replace with icon when liked, turn to like icon*/
        scaleUp('postLike');
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
                setTimeout(function () {
                    likePost.classList.remove('scaleUp');
                }, 500);
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
    console.log(username, cmtId);
    event.preventDefault();
    var likeCmt = document.querySelector("#cmtLike-" + cmtId);
    var countCmtLike = document.querySelector("#countCommentLike-" + cmtId);
    var numOfLike = countCmtLike.innerHTML;
    if (likeCmt.className === "fas fa-thumbs-up") {
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
                numOfLike--;
                countCmtLike.innerHTML = numOfLike;
                likeCmt.className = ("far fa-thumbs-up");
                scaleUp('cmtLike-' + cmtId);
                setTimeout(function () {
                    likeCmt.classList.remove('scaleUp');
                }, 500);
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
                numOfLike++;
                countCmtLike.innerHTML = numOfLike;
                likeCmt.className = ("fas fa-thumbs-up");
                scaleUp('cmtLike-' + cmtId);
                setTimeout(function () {
                    likeCmt.classList.remove('scaleUp');
                }, 500);
            }
        });
    }
}

function openFormReport(objectId, type) {
    document.querySelector('#reportForm-objectId').value = objectId;
    document.querySelector('#reportForm-type').value = type;
}

function submitReport() {
    event.preventDefault();
    let objectId = document.querySelector('#reportForm-objectId').value;
    let type = document.querySelector('#reportForm-type').value;
    let title = document.querySelector('#reportForm-title').value;
    let description = document.querySelector('#reportForm-description').value;
    $.ajax({
        type: "POST",
        url: 'Report',
        data: {
            objectId: objectId,
            type: type,
            title: title,
            description: description
        },
        cache: false,
        success: function () {
            $('#reportForm').modal('hide');
            if (type === 'post') {
                $('#report-post-btn').replaceWith("\
            <span id='report-post-btn'\n\
                    data-bs-toggle='tooltip' \n\
                    data-bs-placement='top'\n\
                    title='You have already reported this post!'\n\
                    style='float:right' class='ms-4'>\n\
                <i class='fas fa-exclamation-triangle' style='cursor:default'></i>\n\
            </span>");

            }
            if (type === 'comment') {
                console.log('report-cmt-btn-' + objectId);
                $('#report-cmt-btn-' + objectId).replaceWith(`\
                <span id='report-cmt-btn-${$('#report-cmt-btn-' + objectId)}'\n\
                        data-bs-toggle='tooltip' \n\
                        data-bs-placement='top'\n\
                        title='You have already reported this comment!'\n\
                        class='ms-2'>\n\
                    <i class='fas fa-exclamation-triangle'></i>\n\
                </span>`)
            }
            tooltipFunct();
        }
    });
}

function scaleUp(element) {
    document.getElementById(element).classList.add('scaleUp');
}

function tooltipFunct() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

//check if report title is empty
const reportForm = document.getElementById("reportForm");
const reportTitle = document.getElementById('reportForm-title');
reportForm.addEventListener('submit', event => {
    if (!reportTitle.value.trim()) {
        event.preventDefault();
        document.getElementById('report-error').textContent = 'Title is required!';
    }
    else
        submitReport();
});
