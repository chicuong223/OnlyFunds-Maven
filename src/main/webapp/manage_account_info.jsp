<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
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
            <link type="text/css" rel="stylesheet" href="styles/manage_account.css">
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
                    <input type="hidden" name="oldPass" id="oldPass" value="${sessionScope.user.password}"/>
                    <div class="alert alert-success text-center alert-dismissible fade show mb-0" role="alert">
                        Hello, <strong>${user.username}</strong> .
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <div class="container-fluid mt-4">
                        <div class="row">
                            <h2 class="text-center fw-bold mb-5">${user.username}'s profile</h2>
                            <div class="col-6 text-center p-0" id="left-panel">
                                <form action="ManageAccount" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="action" value="avatar" />
                                    <img src="images/avatars/defaultAvatar.png" class="img-avatar" id="img-avatar" alt="avatar"/>
                                    <input accept="image/*" type="file" name="avatar" class="form-control new-ava"
                                        id="new-avatar" />
                                    <button class="btn action disabled" type="submit" id="update">Update</button>
                                </form>
                            </div>
                            <div class="col-6 p-0">
                                <div class="mb-3 row">
                                    <label for="staticUsername" class="col-sm-2 col-form-label "><strong>Username</strong>
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" readonly class="form-control disabled" id="staticUsername"
                                            value="${user.username}">
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label for="staticName" class="col-sm-2 col-form-label "><strong>Full
                                            name</strong></label>
                                    <div class="col-sm-8">
                                        <input type="text" readonly class="form-control disabled" id="staticName"
                                            value="${user.firstName} ${user.lastName}">
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label for="staticEmail"
                                        class="col-sm-2 col-form-label "><strong>Email</strong></label>
                                    <div class="col-sm-8">
                                        <input type="text" readonly class="form-control disabled" id="staticEmail"
                                            value="${user.email}">
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label for="staticPassword"
                                        class="col-sm-2 col-form-label "><strong>Password</strong></label>
                                    <div class="col-sm-8">
                                        <button id="staticPassword" type="button" class="btn action"
                                            data-bs-toggle="modal" data-bs-target="#passwordModal">
                                            Change Password
                                        </button>
                                    </div>
                                    <div class="modal fade" id="passwordModal" tabindex="-1">
                                        <div class="modal-dialog modal-dialog-centered">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Change Password</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                                </div>
                                                <form action="ManageAccount" method="POST" id="change-password-form">
                                                    <div class="modal-body">
                                                        <div class="form-group">
                                                            <label for="currPassword">Current password: </label>
                                                            <input class="form-control mt-2" type="password"
                                                                name="currentPassword" id="currPassword" />
                                                            <p class="text-danger" id="passwordError" class="error"></p>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="newPassword">New password: </label>
                                                            <input class="form-control mt-2" type="password"
                                                                name="newPassword" id="newPassword" />
                                                            <p class="text-danger" id="newPasswordError" class="error"></p>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="confPassword">Confirm new password: </label>
                                                            <input class="form-control mt-2" type="password"
                                                                name="confNewPassword" id="confPassword" />
                                                            <p class="text-danger mb-0" id="confPasswordError" class="error"></p>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer d-flex justify-content-center">
                                                        <button type="submit" name="action" value="password"
                                                            class="btn btn-primary w-100">Submit</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
            <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
            <script src="scripts/account_info_script.js"></script>
        </body>

        </html>