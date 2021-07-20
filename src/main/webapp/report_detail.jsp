<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <main>
            <h2>Reported list</h2>
            <table>
                <thead>
                <th colspan="2">Report</th>
                </thead>
                <tr>
                    <th>Reporter</th>
                    <td>${report.reportUser.username}</td>
                </tr>
                <tr>
                    <th>Title</th>
                    <td>${report.title}</td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>${report.description}</td>
                </tr>
                <thead>
                <th colspan="2">Similar report</th>
                </thead>
                <c:forEach var="other" items="${otherReports}">
                    <tr>
                        <th>Reporter</th>
                        <td>${other.reportUser.username}</td>
                    </tr>
                    <tr>
                        <th>Title</th>
                        <td>${other.title}</td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>${other.description}</td>
                    </tr>
                </c:forEach>
            </table>
            <h1 class="text-center">${post.title}</h1>
            <div class="w-75 mx-auto">
                <!-- Post Details -->
                <p>${post.uploadDate}</p>
                <div class="border border-2 rounded" id="postDetails">
                    <div class="border-bottom border-2 text-break p-3">
                        <p>${post.description}</p>
                        <c:if test="${!requestScope.post.attachmentURL.isEmpty()}">
                            <a href="post_file/${requestScope.post.attachmentURL}" class="link-primary"><i class="fa fa-paperclip"></i>${requestScope.post.attachmentURL}</a> <br>
                            </c:if>
                    </div>
                    <!-- Comment Section -->
                    <c:if test="${!empty comment}">
                        <section class="text-break" id="commentSection" style="height: 300px; overflow-x: hidden; overflow-y: scroll">                                

                            <div class="row" id="comment-${comment.commentID}">
                                <div class="col-2">
                                    <img src="images/avatars/${comment.user.avatarURL}" alt="${comment.user.avatarURL}" class="img-thumbnail"/>
                                </div>
                                <div class="col">
                                    <p class="fw-bold">${comment.user.username}</p>
                                    <p id="${comment.commentID}-content">${comment.content}</p>
                                </div>
                            </div>
                        </section>
                    </c:if>

                </div>
            </div>
        </main>
    </body>
</html>
