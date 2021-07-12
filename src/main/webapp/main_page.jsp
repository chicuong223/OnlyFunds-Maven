<%-- Document : main_page Created on : Jun 20, 2021, 7:02:19 PM Author : ASUS GAMING --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="navbar.jsp"></c:import>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/main_page.css">
        <title>Only Funds</title>
    </head>

    <body>
        <!-- Main content -->
        <main class="mt-3 mb-5">
            <div class="container d-flex justify-content-end">
                <a href="WritePostServlet">
                    <button class="btn rounded-pill button bg-secondary text-light me-2">
                        <!-- Không sửa id!!!! -->
                        <span id="Create post">
                            <i class="fas fa-plus-circle"></i>
                        </span>
                    </button>
                </a>
                <a href="YourPostsServlet">
                    <button class="btn rounded-pill button bg-secondary text-light">
                        <!-- Không sửa id!!!! -->
                        <span id="Your posts">
                            <i class="fas fa-portrait"></i>
                        </span>
                    </button>
                </a>
            </div>
            <!-- Favorite creators -->
            <div class="container mt-3" id="fav-creators">
                <div class="row gx-5">
                    <div class="col-12 mb-3 h3" style="text-decoration: underline;">New posts from creators you
                        like
                    </div>
                    <!-- Create 1 column for each creator -->
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Recent creators -->
            <div class="container mt-3" id="recent-creators">
                <div class="col-12 mb-3 h3" style="text-decoration: underline;">Recent creators you have viewed
                </div>
                <div class="row gx-5">
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Creators you might like -->
            <div class="container mt-3" id="interest-creators">
                <div class="row gx-5">
                    <div class="col-12 mb-3 h3" style="text-decoration: underline;">Creators you might be
                        interested to
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-4 d-flex justify-content-center">
                        <div class="card shadow" style="width: 15rem;">
                            <!-- Creator's logo -->
                            <img src="images/Cat.jpg" class="card-img-top" alt="logo" width="100px">
                            <div class="card-body">
                                <!-- Creator's name -->
                                <div class="card-title fw-bold mb-4">Mike Johnson</div>
                                <!-- Upload date & Basic calculation if possible -->
                                <div class="card-body m-0 p-0">
                                    <span class="new-post m-0 p-0" style="color: red;">2 new posts</span>
                                    <span class="date float-end" style="color: #B5AFAF">Yesterday</span>
                                    <!-- Link to creator's page -->
                                    <a href="#Creator's info" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>


    </body>

</html>