<%-- Document : post_form Created on : Jun 22, 2021, 9:04:29 PM Author : chiuy --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <c:import url="navbar.jsp" />
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
                <title>Write Post</title>
                <!-- Icon -->
                <link rel="icon" href="images/logo_head.png" type="image/icon type">
                <!-- CSS files -->
                <link rel="stylesheet" href="styles/shared.css">
                <link type="text/css" rel="stylesheet" href="styles/create_post.css">
                <!-- Icon scripts -->
                <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
                <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
                <!-- AJAX script -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            </head>

            <body>
                <main class="main-container">
                    <c:import url="vertical_navbar_post.jsp"></c:import>
                    <div class="main-content" id="main-content">
                        <h1 class="mt-3 fw-bold mx-auto w-50 text-center">Create a new post</h1>
                        <form action="WritePostServlet" method="POST" enctype="multipart/form-data" id="postForm">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-lg-8 pt-2 pb-2 pe-0">
                                        <div class="create">
                                            <div class="form-floating">
                                                <input id="title" name="title" type="text" class="form-control title"
                                                    placeholder="Title">
                                                <label for="title">Title of the post</label>
                                                <span class="error text-danger" id="titleError">${titleError}</span>
                                            </div>
                                            <textarea id="desc" name="desc" class="content" placeholder="What is your post about?"></textarea>
                                            <span class="error text-danger" id="descError">${descError}</span>
                                            <div class="attachment-grp">
                                                <label for="attachment">
                                                    <ion-icon name="attach-outline"></ion-icon> Attachment
                                                </label>
                                                <input id="attachment" name="attachment" type="file">
                                                <span class="text-danger" id="fileError"></span>
                                            </div>
                                            <div class="category">
                                                <span class="header">
                                                    <ion-icon name="pricetag-outline"></ion-icon> Category
                                                </span>
                                                <div class="cat-list">
                                                    <c:forEach var="cat" items="${catList}">
                                                        <span class="cat">
                                                            <label class="cat-label"
                                                                for="${cat.categoryName}">${cat.categoryName}</label>
                                                            <input type="checkbox" class="cat-name" name="cat"
                                                                value="${cat.categoryId}" id="${cat.categoryName}">
                                                        </span>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 pt-2 pb-2">
                                        <div class="accessibility">
                                            <span class="header">
                                                <ion-icon name="lock-closed-outline"></ion-icon> Who can see this post?
                                            </span>
                                            <hr>
                                            <div class="tier-list">
                                                <c:forEach var="tier" items="${tierList}">
                                                    <div class="tier">
                                                        <input class="form-check-input" type="checkbox"
                                                            value="${tier.tierId}" id="tier-id" name="tier">
                                                        <label class="form-check-label ms-3" for="flexCheckDefault">
                                                            ${tier.tierTitle} - $${tier.price}
                                                        </label>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <hr class="mt-2">
                                            <div class="reminder ps-2">
                                                <i class="far fa-minus-square me-1"></i>
                                                <span>If you did not select any tier, your post will be free by
                                                    default</span>
                                            </div>
                                            <div class="reminder ps-2">
                                                <i class="far fa-minus-square me-1"></i>
                                                <span>Tier cannot be changed afterward, so be careful!!</span>
                                            </div>
                                            <button class="btn w-100 create-btn mb-0 mt-3" type="submit">Create</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </main>
                <script defer src="scripts/post_form_script.js"></script>
            </body>

            </html>