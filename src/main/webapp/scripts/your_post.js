let currPage = document.getElementById("currPage");
if (currPage.value < 2) {
    document.getElementById("1-page").classList.add('active');
}
function enableOrDisable(action, postID){
    console.log(postID);
    var btn = $('#disable-or-enable-btn-' + postID).text();
    console.log(btn);
    if(btn === 'Disable'){
        $.post('enable_or_disable', {action:"deactivate", postID:postID}, function(){
            if(action === 'all')
                $('#disable-or-enable-btn-' + postID).text('Enable');
            else if(action === 'active' || action === 'disabled')
                location.reload();
        });
    }
    else if(btn === 'Enable'){
        $.post('enable_or_disable', {action:"activate", postID:postID}, function(){
            if(action === 'all')
                $('#disable-or-enable-btn-' + postID).text('Disable');
            else if(action === 'active' || action === 'disabled')
                location.reload();
        });
    }
}