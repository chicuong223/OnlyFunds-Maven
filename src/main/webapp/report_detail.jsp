<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Report details</title>
            <link rel="stylesheet" href="styles/report_details.css">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
                crossorigin="anonymous">
            <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
        </head>

        <body>
            <main class="main-container">
                <!-- Vertical navbar -->
                <c:if test="${sessionScope.staff != null}">
                    <c:import url="staff_navbar.jsp"></c:import>
                </c:if>
                <c:if test="${sessionScope.admin != null}">
                    <c:import url="admin_vertical_navbar.jsp"></c:import>
                </c:if>
                <div class="main-content" id="main-content">
                    <div class="row ps-3 pe-3 pt-5">
                        <div class="col-lg-8">
                            <div class="row text-center">
                                <h3 class="fw-bold">Report details</h3>
                            </div>
                            <div class="row d-flex justify-content-center p-3">
                                <div class="report-body border rounded-3 p-3 shadow">
                                    <div class="container p-3">
                                        <div class="row">
                                            <div class="col-4 fw-bold">Title:</div>
                                            <div class="col-8 pe-0">
                                                <input class=" form-control form-control-sm" type="text"
                                                    value="${report.title}" disabled readonly>
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col-4 fw-bold">User report:</div>
                                            <div class="col-8 fw-bold">
                                                ${report.reportUser.username}
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col-4 fw-bold">Type:</div>
                                            <div class="col-8 fw-bold">
                                                ${report.type}
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col-4 fw-bold">
                                                <label for="description">Description: </label>
                                            </div>
                                            <div class="col-8 pe-0">
                                                <textarea class="form-control description"
                                                    readonly>${report.description}</textarea>
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col-12 text-center fw-bold h3">Reported object</div>
                                            <div class="col-12 border rounded-3 p-5 pt-3 pb-3 reported object">
                                                <!-- Reported object is post or comment -->
                                                <c:if test="${!empty post}">
                                                    <div class="h4 text-center mb-0 fw-bold">${post.title}</div>
                                                    <div class="text-center col ps-0 mb-3" style="font-size: 18px;">
                                                        Author: ${post.uploader.username}
                                                    </div>
                                                    <div class="row mb-2">
                                                        <!-- Date here -->
                                                        <div class="col ps-0 fw-bold" style="font-size: 17px;">
                                                            ${post.uploadDate}
                                                        </div>
                                                        <!-- View count here -->
                                                        <div class="col pe-0 fw-bold">
                                                            <span class="float-end" style="font-size: 17px;">
                                                                <i class="far fa-eye"></i>
                                                                ${post.viewCount}
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div class="row border border-dark rounded p-3">
                                                        <div class="col-12 pb-3" id="post-content">
                                                            ${post.description}
                                                        </div>
                                                        <div class="col-12 pb-3">
                                                            <i class="fas fa-paperclip"
                                                                style="transform: scale(0.8);"></i>
                                                            <a href="post_file/${requestScope.post.attachmentURL}"
                                                                class="add-on"
                                                                style="font-size: 17px; color: red; text-decoration: underline;">
                                                                ${requestScope.post.attachmentURL}
                                                            </a>
                                                        </div>
                                                        <c:if test="${!empty comment}">
                                                            <div class="comment-list">
                                                                <div class="comment mb-3">
                                                                    <div class="comment-ava">
                                                                        <img src="images/avatars/${comment.user.avatarURL}"
                                                                            alt="avatar">
                                                                    </div>
                                                                    <div class="comment-body">
                                                                        <div
                                                                            class="main-content border rounded p-2 pt-1">
                                                                            <!-- Comment's author -->
                                                                            <div class="comment-name">
                                                                                <span href="#author-page"
                                                                                    class="info fw-bold"
                                                                                    id="author-link">${comment.user.username}</span>
                                                                            </div>
                                                                            <!-- Comment's content -->
                                                                            <p class="mb-0">
                                                                                ${comment.content}
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                    <div class="line"></div>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </c:if>
                                                <c:if test="${!empty user}">
                                                    <div>Username: ${user.username}</div>
                                                    <div>Avatar: ${user.avatarURL}</div>
                                                    <div>Bio: ${user.bio}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="row text-center">
                                <h3 class="fw-bold">Similar reports</h3>
                            </div>
                            <div class="row p-3 pb-0" id="similar">
                                <table class="table border shadow mb-0">
                                    <thead>
                                        <tr>
                                            <th scope="col">Username</th>
                                            <th scope="col">Title</th>
                                            <th scope="col">Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="other" items="${otherReports}">
                                            <tr>
                                                <td>${other.reportUser.username}</td>
                                                <td>${other.title}</td>
                                                <td>${other.report_date}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="row p-3">
                                <form action="SolveReportServlet" method="POST" class="p-0 pe-1">
                                    <div class="wrapper p-3 border rounded-3 shadow">
                                        <div class="row">
                                            <c:if test='${report.status=="pending"}'>
                                                <div class="col-5 fw-bold">Choose action:</div>
                                                <div class="col-7">
                                                    <select class="form-select form-select-sm" name="action"
                                                        id="action">
                                                        <option value="approved" selected>Approve</option>
                                                        <option value="declined">Decline</option>
                                                    </select>
                                                </div>
                                                <div class="col-12 mt-2">
                                                    <div class="reminder pb-2">
                                                        <i class="far fa-minus-square me-3"></i>
                                                        <span class="fw-bold">All similar report from table above will
                                                            also
                                                            be
                                                            solved after you take action with this report.</span>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary w-100">Confirm</button>
                                                </div>
                                            </c:if>
                                            <c:if test='${report.status!="pending"}'>
                                                <div class="col-5 fw-bold">Choose action:</div>
                                                <div class="col-7">
                                                    <select class="form-select form-select-sm" name="action" id="action"
                                                        disabled>
                                                        <option value="approved">Approve</option>
                                                        <option value="declined">Decline</option>
                                                    </select>
                                                </div>
                                                <div class="col-12 mt-2">
                                                    <div class="reminder pb-2">
                                                        <i class="far fa-minus-square me-3"></i>
                                                        <span class="fw-bold">All similar report from table above will
                                                            also
                                                            be
                                                            solved after you take action with this report.</span>
                                                    </div>
                                                    <div class="pb-2">
                                                        <i class="far fa-minus-square me-2"></i>
                                                        <span class="fw-bold text-danger">This report has been
                                                            solved!</span>
                                                    </div>
                                                    <a href="#" class="btn btn-success w-100"
                                                        onclick="window.history.back()">Solved</a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <!-- JavaScript Bundle with Popper -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
                crossorigin="anonymous"></script>
        </body>

        </html>