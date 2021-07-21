<%-- 
    Document   : manage_categories
    Created on : Jul 3, 2021, 12:01:00 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--Navigation bar-->
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Manage Creator Page</title>
        </head>
        <body>
        <c:set var="user" value="${sessionScope.user}"/>
        <c:set var="ucList" value="${sessionScope.ucList}"/>
        <h1>Manage Creator Page</h1>
        <!-- Tiers -->
        <c:if test="${tierList.size() <= 0}">
            <div style="border: dashed 5px black;" class="card">
                <div class="card-body">
                    <p class="text-center">Click here to add tier</p>
                    <a href="AddTierServlet" class="stretched-link"></a>
                </div>
            </div>
        </c:if>
        <div class="container">
            <div class="row">
                <c:forEach items="${tierList}" var="tier">
                    <div class="col card">
                        <div class="card-header">${tier.tierTitle} - Price: $${tier.price}</div>
                        <div class="card-body">${tier.description}</div>
                        <div class="card-footer">
                            <a href="EditTierServlet?tierid=${tier.tierId}" class="btn btn-warning">Edit</a>
                            <a href="#" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modal-${tier.tierId}">Delete</a>
                            <div class="modal" id="modal-${tier.tierId}">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Delete Tier</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="DeleteTierServlet" method="post" id="form-${tier.tierId}">
                                                <input type="hidden" name="id" value="${tier.tierId}"/>
                                                <p>You and all other users will not be able to see this tier anymore</p>
                                                <p>Users who have subscribed to this tier are still able to view posts of this tier until their subscriptions are expired</p>
                                                <p class="text-danger">Are you sure?</p>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="submit" class="btn btn-danger" value="Delete" form="form-${tier.tierId}"/>
                                            <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <c:if test="${tierList.size() < 5 && tierList.size() > 0}">
            <button class="btn btn-success" onclick="location.href = 'AddTierServlet'">Add a new Tier</button>
        </c:if>
        <!--Change bio description-->
        <form action="ManageCreatorPage" method="POST">
            <p class="head">Bio:</p> 
            <textarea name="bio" rows="10">${user.bio}</textarea>
            <button type="submit" name="action" value="bio">Submit</button>
        </form>

        <!--Change interest-->
        <div>
            <p class="head">Current Interests:</p> 
            <div id="currentInterest">
                <c:set var="check" value="${false}"/>
                <c:forEach items="${ucList}" var="ucat">
                    <c:if test="${ucat.value}">
                        <c:set var="check" value="${true}"/>
                        <strong>${ucat.key.categoryName}</strong>
                    </c:if>
                </c:forEach>
                <c:if test="${!check}">
                    <strong>None</strong>
                </c:if>
            </div>
            <!--Interest form starts here-->
            <button id="change-interest-btn">Change interest:</button>
            <form id="change-interest-form" action="ManageCreatorPage" method="POST" hidden>
                <c:forEach items="${ucList}" var="cat">
                    <c:choose>
                        <c:when test="${cat.value}">
                            <input checked name="category" class="form-check-input" type="checkbox" id="${cat.key.categoryId}" value="${cat.key.categoryId}" />
                            <label class="form-check-label" for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                        </c:when>
                        <c:otherwise>
                            <input name="category" class="form-check-input" type="checkbox" id="${cat.key.categoryId}" value="${cat.key.categoryId}" />
                            <label class="form-check-label" for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <button type="submit" name="action" value="category">Submit</button>
            </form>
        </div>
        <script src="scripts/manage_creator_page_script.js"></script>
    </body>
</html>
