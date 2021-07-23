function submitReport() {
    alert("submitReport called")
    event.preventDefault();
    let objectId = document.querySelector('#reportForm-objectId').value;
    let type = document.querySelector('#reportForm-type').value;
    let title = document.querySelector('#reportForm-title').value;
    let description = document.querySelector('#reportForm-description').value;
    alert(objectId);
    alert(type);
    alert(title);
    alert(description);
    $.ajax({
        type: "POST",
        url: 'Report',
        data: {
            objectId: objectId,
            type: type,
            title: title,
            description: description
        },
        cache: false,
        success: function () {
            alert("Your report has been submitted");
            if(type == 'post') {
                let report_post_btn = document.getElementById('report-post-btn');
                report_post_btn.setAttribute('data-bs-toggle', 'tooltip');
                report_post_btn.setAttribute('data-bs-placement','top');
                report_post_btn.setAttribute('title', 'You have already reported this post!')
                report_post_btn.onclick = ()=> {
                    return false;
                };
            }
            

        }
    });
}

function openFormReport(objectId, type) {
    alert("openFormReport called");
    document.querySelector('#reportForm-objectId').value = objectId;
    document.querySelector('#reportForm-type').value = type;
}