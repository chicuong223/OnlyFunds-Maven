/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var start = 1;
    var end = 4;
//    console.log($("#main-container"));
    getPostData();
    start += 4;
    end += 4;
    $(window).scroll(function () {
        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
            getPostData();
            start += 4;
            end += 4;
        }
    });
    function getPostData() {
        $.get('WelcomePageServlet', {start: start, end: end, action: 'load'}, function (response) {
            $('#row').append(response);
//            var obj = JSON.parse(response);
////            console.log(typeof (obj));
//            $.each(obj, (key, value) => {
//                console.log(key + "," + value);
//            });
//            obj.forEach(post => {
////                console.log(post['title']);
//                var el = document.createElement("div");
//                el.setAttribute("class", "col-lg-3 mb-2");
//                el.innerHTML = `
//                                <div class="card" id="post">
//                                     <a href="PostDetailServlet?id=${post['postId']}" class="stretched-link"></a>
//                             <div class="card-header p-2 pt-1">
//                                 <h4 class="card-title fw-bold">${post['title']}</h4>
//                                 <h6 class="card-subtitle text-muted" style="font-size: 16px;">${post['uploader']['username']}</h6>
//                             </div>
//                             <div class="card-body p-2 pt-1">
//                                 <a href="PostDetailServlet?id=${post['postId']}" class="stretched-link"></a>
//                                 <p class="card-text">
//                                 ${post['description']}
//                                 </p>
//                             </div>
//                             <div class="card-footer p-2 pt-1 pb-1">
//                                 <small><i class="fas fa-thumbs-up"></i> 1234</small>
//                                 <small><i class="fas fa-comment"></i> 1234</small>
//                                 <small><i class="far fa-eye"></i> 1234</small>
//                             </div>
//                         </div>
//                                `;
//                console.log(start);
//                console.log(end);
//                $("#row").append(el);
//            });
        }, 'text');
    }
})
        ;

