$(document).ready(function () {
    $('#follow').click(function () {
        var creator = $("#creator-username").text().trim();
        if ($('#follow').text().trim() === 'Follow')
            addFollow(creator);
        else
            deleteFollow(creator);
    });

    function addFollow(creator) {
        var followCount = parseInt($('#follow-count').html());
        $.post("follow_manage", {creator: creator, action: "add"}, function () {
            $('#follow').text("Following");
            $('#follow').css('background-color', '#cecece');
            $('#follow').css('border-color', '#cecece');
            followCount += 1;
            $('#follow-count').html(followCount);
        });
    }
    function deleteFollow(creator) {
        var followCount = parseInt($('#follow-count').html());
        $.post("follow_manage", {creator: creator, action: "delete"}, function () {
            $('#follow').text("Follow");
            $('#follow').css('background-color', '#ce68a8');
            $('#follow').css('border-color', '#ce68a8');
            followCount -= 1;
            $('#follow-count').html(followCount);
        });
    }
});

