<%-- 
    Document   : transactions
    Created on : Jul 6, 2021, 10:25:56 AM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <title>Transactions History</title>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        </head>
        <body>
            <script>
                $(document).ready(function () {
                    var start = 1;
                    var filter = "All";
                    //if user changes filter
                    //empty out the table
                    //reset start = 1
                    //get transactions according to filter
                    function changeFilter(content) {
                        filter = content;
                        $('#billList').empty();
                        start = 1;
                        getTransactions(start, start + 7);
                        start += 8;
                    }

                    //add events to all filter buttons
                    $('.filter').each(function () {
                        $(this).click(() => {
                            changeFilter($(this).text());
                        });
                    });

                    if ($(document).height() === $(window).height()) {
                        getTransactions(start, start + 7);
                        start += 8;
                    }
                    $(window).scroll(() => {
                        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
                            getTransactions(start, start);
                            start += 1;
                        }
                    });
                    function getTransactions(startNo, endNo) {
                        $.post('ViewTransactionHistory', {filter: filter, start: startNo, end: endNo}, function (response) {
                            $('#billList').append(response);
                        }, 'text');
                    }
                });
            </script>
            <header>
                <h1>Transactions History</h1>
            </header>
            <main>
            <c:set var="user" value="${sessionScope.user}"></c:set>
            <button class="btn btn-primary filter">All</button>
            <button class="btn btn-danger filter">Sent</button>
            <button class="btn btn-success filter">Received</button>
            <table class="table table-bordered table-hover w-75 mx-auto">
                <thead class="table-primary">
                    <tr>
                        <th>Content</th>
                        <th>Sender</th>
                        <th>Recipient</th>
                        <th>Amount</th>
                        <th>Transaction Date</th>
                    </tr>
                </thead>
                <tbody class="table-success" id="billList">

                </tbody>
            </table>
        </main>
            <footer>
                <nav>
                    <ul class="pagination" id="pages">
                        
                    </ul>
                </nav>
            </footer>
    </body>
</html>
