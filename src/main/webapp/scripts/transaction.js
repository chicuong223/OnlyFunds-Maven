
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
        getTransactions(start, start + 7, "");
        start += 8;
    }
    $(window).scroll(() => {
        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
            getTransactions(start, start, "");
            start += 1;
        }
    });
    function getTransactions(startNo, endNo, creator) {
        $.post('ViewTransactionHistory', {filter: filter, start: startNo, end: endNo, creator: creator}, function (response) {
            $('#billList').append(response);
        }, 'text');
    }

    $('#btnSearchTrans').click(() => {
        var creatorName = document.getElementById("creatorName").value;
        filter = "search";
        $('#billList').empty();
        start = 1;
        getTransactions(start, start + 7, creatorName);
        start += 8;
    });
});



