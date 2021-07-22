<%-- 
    Document   : post_form
    Created on : Jun 22, 2021, 9:04:29 PM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="navbar.jsp"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Post</title>
        <style>
            ul {
                -moz-column-count: 3;
                -moz-column-gap: 10px;
                -webkit-column-count: 3;
                -webkit-column-gap: 10px;
                column-count: 3;
                column-gap: 10px;
                list-style: none;
            }
            .list-group-item{
                border: none;
            }
        </style>
    </head>
    <body>
        <header>
            <h1 class="text-center">Edit Post</h1>
        </header>
        <main>
            <div class="w-75 mx-auto">
                <form action="EditPostServlet" method="POST" id="postForm" enctype="multipart/form-data">
                    <input type="hidden" name="postid" value="${post.postId}"/>
                    <div>
                        <label for="title" name="title" class="fw-bold">Title</label> <span class="fw-bold text-danger">*</span><span class="error text-danger ms-3" id="titleError">${titleError}</span>
                        <input type="text" class="form-control" name="title" value="${post.title}" placeholder="Max 30 characters" id="title"/>
                    </div>
                    <div>
                        <label for="desc" name="desc" class="fw-bold">Description</label> <span class="fw-bold text-danger">*</span><span class="error text-danger ms-3" id="descError">${descError}</span>
                        <textarea class="form-control" id="desc" name="desc" rows="10"  placeholder="Max 1000 characters" id="desc">${post.description}</textarea>
                    </div>
                    <div>
                        <p class="fw-bold" style="margin-bottom: 0">Old attachment</p>
                        <a href="post_file/${post.attachmentURL}">${post.attachmentURL}</a>
                    </div>
                    <div>
                        <label for="file" class="label-form fw-bold">Attachment</label>
                        <input type="file" id="file" name="attachment" class="form-control"/>
                    </div>
                    <div>
                        <label for="catList" class="label-form fw-bold">Categories</label>
                        <ul id="catList">
                            <c:forEach var="cat" items="${catList}">
                                <li>
                                    <label for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                                    <input type="checkbox" class="form-check-input" value="${cat.key.categoryId}" 
                                           id="${cat.key.categoryId}" name="cat" 
                                           <c:if test="${cat.value == true}">checked</c:if>/>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </form>
                <form action="EditPostServlet" method="POST" class="form-inline" id="cancelForm">
                    <input type="hidden" name="cancel" value="${post.postId}"/>
                </form>
                <div class="text-center my-3">
                    <input type="submit" class="btn btn-success rounded-pill mx-5 px-5" value="Update" form="postForm"/>
                    <input type="submit" class="btn btn-danger rounded-pill mx-5 px-5" value="Cancel" form="cancelForm"/>
                </div>
            </div>
        </main>
        <script defer src="scripts/post_form_script.js"></script>
    </body>
</html>
