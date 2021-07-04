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
        <title>Write Post</title>
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
        <h1>Create a new post</h1>
        <c:set var="post" value="${sessionScope.post}"></c:set>
            <div class="w-75 mx-auto my-5">
                <form action="WritePostServlet" method="POST" enctype="multipart/form-data" id="postForm">
                    <div class="form-group">
                        <label for="title" class="label-form fw-bold">Title</label><span class="text-danger">*</span><span class="error text-danger ms-3" id="titleError">${titleError}</span>
                        <input type="text" name="title" id="title"  placeholder="Max 30 characters" class="form-control"/><span class="error text-danger" id="titleError">${titleError}</span>
                </div>
                <div class="form-group">
                    <label for="desc" class="label-form fw-bold">Description</label><span class="text-danger">*</span><span class="error text-danger ms-3" id="descError">${descError}</span>
                    <textarea name="desc" id="desc"  rows="10" cols="100" placeholder="Max 1000 characters" class="form-control"></textarea><span class="error text-danger" id="descError">${descError}</span>
                </div>
                <div class="form-group">
                    <label for="file" class="label-form fw-bold">Attachment</label>
                    <input type="file" id="file" name="attachment" class="form-control"/>
                </div>
                <div class="form-group">
                    <label for="catList" class="fw-bold">Categories</label>
                    <ul id="catList">
                        <c:forEach var="cat" items="${catList}">
                            <li>
                                <label for="${cat.categoryId}">${cat.categoryName}</label>
                                <input type="checkbox" class="form-check-input" value="${cat.categoryId}" id="${cat.categoryId}" name="cat"/>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="form-group">
                    <label for="tierList" class="fw-bold">Gain access to (Default: everyone)</label>
                    <ul  id="tierList">
                        <c:forEach var="tier" items="${tierList}">
                            <li class="list-group-item">
                                <label for="${tier.tierId}" class="label-form">${tier.tierTitle} - ${tier.price}$</label>
                                <input type="checkbox" name="tier" value="${tier.tierId}" class="form-check-input"/>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </form>
            <form action="WritePostServlet" method="POST" id="cancelForm">
                <input type="hidden" name="cancel"/>
            </form>
            <div class="text-center">
                <input class="btn btn-success mx-5 rounded-pill" type="submit" value="Post" form="postForm"/>
                <input class="btn btn-danger mx-5 rounded-pill" type="submit" value="Cancel" form="cancelForm"/>
            </div>
        </div>
        <script defer src="scripts/post_form_script.js"></script>
    </body>
</html>
