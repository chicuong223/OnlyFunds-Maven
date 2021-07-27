<%-- 
    Document   : tier_form
    Created on : Jun 25, 2021, 11:10:57 AM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="navbar.jsp"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:choose>
            <c:when test="${tier == null}">
                <title>Add Tier</title>
            </c:when>
            <c:otherwise>
                <title>Edit Tier</title>
            </c:otherwise>
        </c:choose>
    </head>
    <body>
        <header class="text-center">
            <h1>Tiers</h1>
            <h4>Create benefits for subscribed users</h4>
        </header>
        <main>
            <div class="w-50 mx-auto shadow p-3 my-1">
                <form method="POST" id="tierForm" action="AddTierServlet" >
                    <div class="form-group my-2">
                        <label for="title" class="label-form fw-bold">Title</label> <span class="text-danger fw-bold fs-5">*</span>   <span class="text-danger" id="titleError">${titleError}</span>
                        <input type="text" id="title" name="title" class="form-control" value="${title}"/>
                    </div>
                    <div class="form-group my-2">
                        <label for="price" class="label-form fw-bold">Price</label> <span class="text-danger fw-bold fs-5">*</span>  <span class="text-danger" id="priceError">${priceError}</span>
                        <input type="number" id="price" name="price" class="form-control" value="${price}"/>
                    </div>
                    <div class="form-group my-2">
                        <label for="desc" class="label-form fw-bold">Description</label>
                        <textarea id="desc" name="desc" class="form-control" rows="7">${desc}</textarea>
                    </div>
                    <div class="form-group mt-3 text-center">
                        <button type="submit" class="btn btn-success rounded-pill mx-5 px-4 py-2">Create</button>
                        <a href="#" class="btn btn-danger rounded-pill mx-5 px-4 py-2" onclick="history.back()">Cancel</a>
                    </div>
                </form>
            </div>
        </main>
        <script defer>
            const price = document.getElementById('price');
            const title = document.getElementById('title');
            const titleError = document.getElementById('titleError');
            const priceError = document.getElementById('priceError');
            const form = document.getElementById('tierForm');
            console.log(form);
            form.addEventListener('submit', event => {
                let error = false;
                if (!price.value.trim() || price.value.length < 1) {
                    error = true;
                    priceError.textContent = "Price is required";
                }
                if (!title.value.trim() || title.value.length < 1) {
                    error = true;
                    titleError.textContent = "Title is required";
                }
                if (error === true) {
                    event.preventDefault();
                }
            });
        </script>
    </body>
</html>
