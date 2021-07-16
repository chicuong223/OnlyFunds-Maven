<%-- 
    Document   : creator_list
    Created on : Jul 14, 2021, 4:22:57 PM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url='navbar.jsp'></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:if test='${subList != null}'>
            <title>Subscribes</title>
        </c:if>
        <c:if test='${followList != null}'>
            <title>Follows</title>
        </c:if>
        <!--<title>JSP Page</title>-->
    </head>
    <body>
        <header>
            <h1 class='text-center'>
                <c:if test='${subList != null}'>
                    Creators that you have subscribed to
                </c:if>
                <c:if test='${followList != null}'>
                    Creators that you are following
                </c:if>
            </h1>
        </header>
        <main>
            <c:if test='${subList != null}'>
                <c:set var="lst" value="${subList}"></c:set>
            </c:if>
            <c:if test='${followList != null}'>
                <c:set var="lst" value="${followList}"></c:set>
            </c:if>
            <div class='container'>
                <div class='row gx-5'>
                    <c:forEach var="user" items="${lst}">
                        <div class='col-3 border mx-2'>
                            <!-- Creator's avatar -->
                            <img src='images/avatars/${user.avatarURL}' class='w-100'/>
                            <!-- Creators username -->
                            <p class='border-bottom'>
                                ${user.username}
                            </p>
                            <!-- Creator's bio -->
                            <p class='text-break'>
                                ${user.bio}
                            </p>
                            <a href='CreatorInfoServlet?username=${user.username}'>See more</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
    </body>
</html>
