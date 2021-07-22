<%-- 
    Document   : tier_management
    Created on : Jul 3, 2021, 11:00:17 AM
    Author     : chiuy
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>

    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Tiers Management</title>
        </head>
        <body>
            <header class="text-center my-3">
                <h1>Subscription Tiers</h1>
            </header>
            <main>
                <div class='row mx-auto'>
                    <p class="text-danger">${tiererror}</p>
                <c:forEach items="${tiers}" var="tier">
                    <div class="border rounded col-3 mx-2 my-3">
                        <div class="border-bottom text-center">
                            <p class="fw-bold">${tier.tierTitle}</p>
                        </div>
                        <div class="text-center">
                            <p>${tier.price} USD</p>
                            <p>${tier.description}</p>
                        </div>
                        <div class="border-top">
                            <a href="EditTierServlet?tierid=${tier.tierId}">Edit</a>
                            <a href="#" class="link-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</a>
                            <div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="modelTitlel">Delete Tier</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <form action="DeleteTierServlet" method="Post">
                                            <input type="hidden" name="id" value="${tier.tierId}"/>
                                            <div class="modal-body">
                                                You and all other users will not be able to see this tier anymore <br>
                                                Are you sure you want to delete this tier?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="text-center">
                <a href="AddTierServlet" class="btn btn-success">Add a new Tier</a>
            </div>
        </main>
    </body>
</html>
