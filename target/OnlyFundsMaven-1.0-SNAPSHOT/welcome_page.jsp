<%-- 
    Document   : welcome_page
    Created on : Jun 12, 2021, 1:47:58 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Only Funds</title>
    </head>
    <body>

        <!-- Navigation bar -->
        <c:import url="navbar.jsp"></c:import>  

        <!-- Main content -->
        <main class="mt-3">
            <div class="container" style="margin-top: 10vh;">
                <!-- Top -->
                <div class="row">
                    <div class="col-lg-6 p-3 my-auto text-center">
                        <span style="font-size: 50px; color:#B82481; font-family: Righteous;">ONLY FUNDS</span>
                        <p style="font-size: 40px; color:#69336D;">Create your own work and earn money!</p>
                    </div>
                    <div class="col-lg-6 p-0">
                        <img src="images/Wallpaper-2.jfif" alt="Insert image here" class="img-fluid">
                    </div>
                </div>
                <!-- Bottom -->
                <div class="row">
                    <span class="p-0 mb-5 mt-3"
                          style="font-size: 40px; font-weight: bold; border-bottom: 1px solid #B82481; width: 300px;">Popular
                        Creators</span>
                    <div id="carouselExampleDark" class="carousel carousel-dark slide p-0 m-0" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <div class="container carousel-item active" data-bs-interval="10000">
                                <div class="row m-0 p-0">
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Mike Johnson</h5>
                                                <p class="card-text">Creator of "The Life Cycle"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "I have dreamt of making somethinglike this a long, long time
                                                    ago"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Wjbu Lesor</h5>
                                                <p class="card-text">Creator of "From Straight To Gay - The Complete
                                                    Guide From My Experience"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "お兄ちゃんバカ変態"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Trần Khải Minh Khôi</h5>
                                                <p class="card-text">Creator of "How To Become The Golden Frog"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "Dăm ba con cóc vàng, game là dễ"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container carousel-item" data-bs-interval="2000">
                                <div class="row m-0 p-0">
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Mike Johnson</h5>
                                                <p class="card-text">Creator of "The Life Cycle"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "I have dreamt of making somethinglike this a long, long time
                                                    ago"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Wjbu Lesor</h5>
                                                <p class="card-text">Creator of "From Straight To Gay - The Complete
                                                    Guide From My Experience"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "お兄ちゃんバカ変態"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Trần Khải Minh Khôi</h5>
                                                <p class="card-text">Creator of "How To Become The Golden Frog"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "Dăm ba con cóc vàng, game là dễ"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container carousel-item" data-bs-interval="2000">
                                <div class="row m-0 p-0">
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Mike Johnson</h5>
                                                <p class="card-text">Creator of "The Life Cycle"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "I have dreamt of making somethinglike this a long, long time
                                                    ago"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Wjbu Lesor</h5>
                                                <p class="card-text">Creator of "From Straight To Gay - The Complete
                                                    Guide From My Experience"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "お兄ちゃんバカ変態"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 m-0 p-0">
                                        <div class="card mx-auto" style="width: 15rem;">
                                            <img src="images/Cat.jpg" class="card-img-top" alt="...">
                                            <div class="card-body">
                                                <h5 class="card-title">Trần Khải Minh Khôi</h5>
                                                <p class="card-text">Creator of "How To Become The Golden Frog"
                                                <hr class="dropdown-divider">
                                                <cite>
                                                    "Dăm ba con cóc vàng, game là dễ"
                                                </cite>
                                                </p>
                                            </div>
                                            <a href="#Author" class="stretched-link"></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br><br>
                        <div class="carousel-indicators carousel-indicators-round">
                            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0"
                                    class="active"></button>
                            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1"></button>
                            <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2"></button>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
