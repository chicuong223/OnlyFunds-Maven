$(document).ready(function () {
    $('#follow-btn').on('click', function () {
        console.log("Test Follow");
        var creator = $("#creator-username").text().trim();
        if ($('#follow-btn').text().trim() === 'Follow')
            addFollow(creator);
        else
            deleteFollow(creator);
    });

    function addFollow(creator) {
        var followCount = parseInt($('#follow-count').html());
        $.post("follow_manage", {creator: creator, action: "add"}, function () {
            $('#follow-btn').text("Following");
            $('#follow-btn').css('background-color', '#cecece');
            $('#follow-tbn').css('border-color', '#cecece');
            followCount += 1;
            $('#follow-count').html(followCount);
        });
    }
    function deleteFollow(creator) {
        var followCount = parseInt($('#follow-count').html());
        $.post("follow_manage", {creator: creator, action: "delete"}, function () {
            $('#follow-btn').text("Follow");
            $('#follow-btn').css('background-color', '#ce68a8');
            $('#follow-btn').css('border-color', '#ce68a8');
            followCount -= 1;
            $('#follow-count').html(followCount);
        });
    }
});

