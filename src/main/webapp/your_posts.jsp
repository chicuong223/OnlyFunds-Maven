<%-- Document : your_posts Created on : Jun 25, 2021, 5:12:49 PM Author : chiuy --%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<c:if test="${CreatorInfoServletFlag == null}">
    <c:import url="navbar.jsp"></c:import>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html;">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <!-- Logo -->
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <!-- Navbar and shared css -->
        <link type="text/css" rel="stylesheet" href="styles/navbar.css">
        <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
        <link rel="stylesheet" href="styles/shared.css">
        <!-- Main css -->
        <link type="text/css" rel="stylesheet" href="styles/your_post.css">
        <!-- Icon -->
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
        <!--Ajax-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>Your Posts</title>
    </head>

    <body>
        <main class="main-container">
            <!-- Vertical navbar -->
            <c:import url="vertical_navbar_post.jsp"></c:import>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
                    <div class="content container-fluid">
                        <div id="row" class="row gx-4 p-3">
                            <h1 class="mb-1 text-center fw-bold">${sessionScope.user.username}'s post(s) (${count})</h1>
                        <div class="mt-2">
                            <a href="WritePostServlet" class="btn create mb-4">
                                <span>Write a new post</span>
                                <i class="fas fa-plus-circle"></i>
                            </a>
                        </div>
                        <nav class="navbar">
                            <a class="nav-link" href="YourPostsServlet?action=all">All</a>
                            <a class="nav-link" href="YourPostsServlet?action=active">Active</a>
                            <a class="nav-link" href="YourPostsServlet?action=disabled">Inactive</a>
                        </nav>
                        <!-- Mỗi post tạo 1 column tương ứng -->
                        <c:forEach items="${postList}" var="post">
                            <div class="col-lg-3 mb-2" id="post-${post.key.postId}">
                                <c:if test="${post.key.isActive == true}">
                                    <a href="#" class="btn btn-thin state" onclick="enableOrDisable('${action}', ${post.key.postId})">
                                        <i class="fas fa-minus"></i>
                                        <span id="disable-or-enable-btn-${post.key.postId}">Disable</span>
                                    </a>
                                </c:if>
                                <c:if test="${post.key.isActive == false}">
                                    <a href="#" class="btn btn-thin state" onclick="enableOrDisable('${action}', ${post.key.postId})">
                                        <i class="fas fa-minus"></i>
                                        <span id="disable-or-enable-btn-${post.key.postId}">Enable</span>
                                    </a>
                                </c:if>

                                <div class="card" id="post">
                                    <a href="#post-detail" class="stretched-link"></a>
                                    <div class="card-header p-2 pt-1">
                                        <h4 class="card-title fw-bold">${post.key.title}</h4>
                                        <h6 class="card-subtitle text-muted"><fmt:formatDate value="${post.key.uploadDate}"
                                                        pattern="dd-MMM-yyyy" /></h6>
                                    </div>
                                    <div class="card-body p-2 pt-1">
                                        <p class="card-text">
                                            ${post.key.description}
                                        </p>
                                    </div>
                                    <div class="card-footer p-2 pt-1 pb-1">
                                        <small><i class="fas fa-thumbs-up"></i> ${post.value[0]}</small>
                                        <small><i class="fas fa-comment"></i> ${post.value[1]}</small>
                                        <small><i class="far fa-eye"></i> ${post.key.viewCount}</small>
                                    </div>
                                    <div class="back-drop">
                                        <a href="PostDetailServlet?id=${post.key.postId}" class="btn btn-action">View</a>
                                        <a href="EditPostServlet?id=${post.key.postId}" class="btn btn-action">Edit</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <footer class="card-footer">
                        <ul class="pagination">
                            <c:forEach begin="1" end="${end}" var="index">
                                <c:if test="${param.page == index}">
                                    <c:set var="active" value="active" />
                                </c:if>
                                <c:if test="${param.page != index}">
                                    <c:set var="active" value="" />
                                </c:if>
                                <li class="page-item ${active}"><a href="YourPostsServlet?page=${index}&action=${action}"
                                                                   class="page-link">${index}</a></li>
                                </c:forEach>
                        </ul>
                    </footer>
                </div>  
            </div>
        </main>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <script src="scripts/account_info_script.js"></script>
        <script>
            function enableOrDisable(action, postID){
                console.log(postID);
                var btn = $('#disable-or-enable-btn-' + postID).text();
                console.log(btn);
                if(btn === 'Disable'){
                    $.post('enable_or_disable', {action:"deactivate", postID:postID}, function(){
                        if(action === 'all')
                            $('#disable-or-enable-btn-' + postID).text('Enable');
                        else if(action === 'active' || action === 'disabled')
                            location.reload();
                    });
                }
                else if(btn === 'Enable'){
                    $.post('enable_or_disable', {action:"activate", postID:postID}, function(){
                        if(action === 'all')
                            $('#disable-or-enable-btn-' + postID).text('Disable');
                        else if(action === 'active' || action === 'disabled')
                            location.reload();
                    });
                }
            }
        </script>
    </body>

</html>