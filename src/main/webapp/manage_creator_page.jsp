<%-- Document : manage_categories Created on : Jul 3, 2021, 12:01:00 PM Author : ASUS GAMING --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!--Navigation bar-->
            <c:import url="navbar.jsp"></c:import>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="utf-8">
                <meta http-equiv="Content-Type" content="text/html;">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
                <!-- Bootstrap -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
                    integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
                    crossorigin="anonymous">
                <!-- Logo -->
                <link rel="icon" href="images/logo_head.png" type="image/icon type">
                <!-- Navbar and shared css -->
                <link type="text/css" rel="stylesheet" href="styles/navbar.css">
                <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
                <link rel="stylesheet" href="styles/shared.css">
                <!-- Main css -->
                <link type="text/css" rel="stylesheet" href="styles/manage_creator_page.css">
                <!-- Icon -->
                <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
                <!--Ajax-->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

            </head>

            <body>
                <main class="main-container">
                    <!-- Vertical navbar -->
                    <div>
                        <c:import url="creator_vertical_navbar.jsp"></c:import>
                    </div>
                    <!-- Main content of the page -->
                    <div class="main-content" id="main-content">
                        <c:set var="user" value="${sessionScope.user}" />
                        <c:set var="ucList" value="${sessionScope.ucList}" />
                        <h1 class="fw-bold text-center mt-4">Manage Creator Page</h1>
                        <div class="container-fluid p-0">
                            <div class="row mt-3 d-flex justify-content-center">
                                <button type="button" class="btn m-2 fill" data-bs-toggle="modal"
                                    data-bs-target="#bioModal">Change
                                    your bio</button>
                                <button type="button" class="btn m-2 fill" data-bs-toggle="modal"
                                    data-bs-target="#catModal">Change
                                    your interests</button>
                            </div>
                            <hr>
                            <div class="row mt-2">
                                <h3 class="header fw-bold text-center">Your subscriptions</h3>
                                <c:if test="${tierList.size() < 3}">
                                    <a href="AddTierServlet" class="btn slide">
                                        <span>Add new tier</span>
                                        <i class="fas fa-plus-circle"></i>
                                    </a>
                                </c:if>
                                <c:if test="${tierList.size() >=3}">
                                    <h3 class="text-danger text-center fw-bold">Deactive some tier(s) to add more</h3>
                                </c:if>
                                <c:forEach items="${tierList}" var="tier">
                                    <div class="col-lg-4 ps-0 pe-0 mx-auto mt-4">
                                        <div class="card tier mx-auto">
                                            <h4 class="card-header text-center text-truncate t-name">${tier.tierTitle}
                                            </h4>
                                            <div class="card-body p-2">
                                                <h4 class="card-title text-center price">$${tier.price} VND</h4>
                                                <p class="description">
                                                    ${tier.description}
                                                </p>
                                            </div>
                                            <div class="card-footer d-flex justify-content-center">
                                                <a href="EditTierServlet?tierid=${tier.tierId}"
                                                    class="btn action me-2">Edit</a>
                                                <a href="#" class="btn action ms-2" data-bs-toggle="modal"
                                                    data-bs-target="#modal-${tier.tierId}">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="#modal-${tier.tierId}">
                                        <div class="modal-dialog modal-dialog-centered">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Delete Tier</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="DeleteTierServlet" method="post"
                                                        id="form-${tier.tierId}">
                                                        <input type="hidden" name="id" value="${tier.tierId}" />
                                                        <p>You and all other users will not be able to see this tier
                                                            anymore</p>
                                                        <p>Users who have subscribed to this tier are still able to view
                                                            posts of this tier until their subscriptions are expired</p>
                                                        <p class="text-danger">Are you sure?</p>
                                                    </form>
                                                </div>
                                                <div class="modal-footer">
                                                    <input type="submit" class="btn btn-danger" value="Delete"
                                                        form="form-${tier.tierId}" />
                                                    <button class="btn btn-secondary"
                                                        data-bs-dismiss="modal">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <!-- Biography form -->
                        <div class="modal fade" id="bioModal" tabindex="-1">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="bioModalLabel">Change your biography</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="bio-form" action="ManageCreatorPage" method="POST">
                                            <textarea id="bio" name="bio" class="form-control">${user.bio}</textarea>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" form="bio-form" name="action" value="bio"
                                            class="btn btn-primary w-100">Save changes</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Category form -->
                        <div class="modal fade" id="catModal" tabindex="-1">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="catModalLabel">Change your interests</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container-fluid p-0">
                                            <div class="row">
                                                <div class="col-lg-6 border-end">
                                                    <h6 class="text-center">Current Interests:</h6>
                                                    <c:set var="check" value="${false}" />
                                                    <div class="cat-list">
                                                        <c:forEach items="${ucList}" var="ucat">
                                                            <span class="cat selected disabled">
                                                                <label class="cat-label-old"
                                                                    for="${ucat.key.categoryName}">${ucat.key.categoryName}</label>
                                                                <input disabled type="checkbox" class="cat-name"
                                                                    name="${ucat.key.categoryName}"
                                                                    value="${ucat.key.categoryName}"
                                                                    id="${ucat.key.categoryName}">
                                                            </span>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="col-lg-6">
                                                    <form id="change-interest-form" action="ManageCreatorPage"
                                                        method="POST">
                                                        <h6 class="text-center">Change Interests:</h6>
                                                        <div class="cat-list">
                                                            <c:forEach items="${ucList}" var="cat">
                                                                <c:choose>
                                                                    <c:when test="${cat.value}">
                                                                        <span class="cat selected">
                                                                            <label class="cat-label"
                                                                                for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                                                                            <input checked type="checkbox"
                                                                                class="cat-name"
                                                                                name="${cat.key.categoryName}"
                                                                                value="${cat.key.categoryId}"
                                                                                id="${cat.key.categoryId}">
                                                                        </span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="cat">
                                                                            <label class="cat-label"
                                                                                for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                                                                            <input type="checkbox" class="cat-name"
                                                                                name="${cat.key.categoryName}"
                                                                                value="${cat.key.categoryId}"
                                                                                id="${cat.key.categoryId}">
                                                                        </span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" form="change-interest-form" name="action" value="bio"
                                            class="btn btn-primary w-100">Save changes</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
                <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
                <script src="scripts/manage_creator_page_script.js"></script>
            </body>

            </html>