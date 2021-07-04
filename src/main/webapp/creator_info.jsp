<%-- 
    Document   : creator_info
    Created on : Jul 3, 2021, 11:55:20 AM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Creator's Info</title>
        </head>
        <body>
            <div class='w-25 mx-auto'>
                <img src='${pageContext.request.contextPath}/images/avatars/${creator.avatarURL}' width="100%"/>
        </div>
        <c:if test="${sessionScope.user.username != creator.username}">
            <c:if test="${subscribed != null}">
                <h3 class="text-center text-danger">${subscribed}</h3>
            </c:if>
            <c:if test="${tiers != null}">
                <h1 class="text-center">Subscribe to this user</h1>
                <div class="container container-fluid">
                    <div class="row">
                        <c:forEach var="tier" items="${tiers}">
                            <div class="border rounded col-3 mx-2 my-3">
                                <a href="#" class="tier text-dark" data-bs-toggle="modal" data-bs-target="#confirmModal">
                                    <p class="fw-bold border-bottom">${tier.tierTitle}</p>
                                    <div class="text-center">
                                        <p>${tier.price} USD</p>
                                        <p>${tier.description}</p>
                                    </div>
                                </a>
                            </div>
                            <div class="modal fade" id="confirmModal" tab-index="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Subscribe Confirmation</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <c:choose>
                                            <c:when test="${sessionScope.user == null}">
                                                <div class="modal-body">
                                                    <p class="text-danger">Please login to continue</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <a class="btn btn-success" href="login">Login</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="SubscribeServlet" method="post">
                                                    <input type="hidden" name="tierid" value="${tier.tierId}"/>
                                                    <div class="modal-body">
                                                        <p>Title: ${tier.tierTitle}</p>
                                                        <p>Description: ${tier.description}</p>
                                                        <p>Price: ${tier.price} USD</p>
                                                        <p class="text-primary">Do you want to subscribe to this tier?</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Confirm</button>
                                                    </div>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <c:import url='post_list.jsp'></c:import>
        </c:if>
        <c:if test="${sessionScope.user.username == creator.username}">
            <c:import url='your_posts.jsp'></c:import>
        </c:if>
    </body>
</html>
