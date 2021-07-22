/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var start = 1;
    function getSubscriptions(start, end) {
        $.post("ManageSubscriptions", {start: start, end: end}, function (response) {
            $("#subscriptionsList").append(response);
//            var obj = JSON.parse(response);
//            obj.forEach(o => {
//                //get data to display on screen
//                var endDate = o["endDate"];
//                var startDate = o["startDate"];
//                var avatar = "images/avatars/" + o["tier"]["creator"]["avatarURL"];
//                var creator = o["tier"]["creator"]["username"];
//                var tier = o["tier"]["tierTitle"];
//                var price = o["tier"]["price"];
//                var id = o["subscriptionId"];
//
//                //create table row
//                var row = document.createElement("tr");
//                row.id = `sub-${id}`;
//                //td img
//                var tdImg = document.createElement("td");
//                tdImg.innerHTML = ``;
//                row.appendChild(tdImg);
//
//                //td description
//                var tdDesc = document.createElement("td");
//                tdDesc.innerHTML = `
//                <h3 class="my-0 fw-bold">${creator}</h3>
//                <p class="my-0"><span class="fw-bold">Tier:</span> ${tier}</p>
//                 <p class="my-0"><span class="fw-bold">Price:</span> $${price}</p>
//                <p class="my-0"><span class="fw-bold">Start date:</span> ${startDate}</p>
//                 <p class="my-0"><span class="fw-bold">End date:</span> ${endDate}</p>
//                `;
//                row.appendChild(tdDesc);
//
//                //td Cancel button
//                var tdCancel = document.createElement("td");
//                tdCancel.innerHTML = `
//                <a href="#" class="link-danger" data-bs-toggle="modal" data-bs-target="#modal-${id}">Cancel</a>
//                <div class="modal" id="modal-${id}">
//                    <div class="modal-dialog modal-dialog-centered">
//                        <div class="modal-content">
//                            <div class="modal-header">
//                                <h5 class="modal-title">Cancel subscription</h5>
//                            </div>
//                            <div class="modal-body">
//                                <form id="form-${id}" action="CancelSubscriptionServlet" method="POST">
//                                    <input type="hidden" name="id" value="${id}"/>
//                                    <p>You will not receive refund after cancelling this subscription. Are you sure?</p>
//                                </form>
//                            </div>
//                            <div class="modal-footer">
//                                <button type="button" class="btn btn-danger del-btn" value="Cancel" id="del-${id}">Cancel</button>
//                                <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
//                            </div>
//                        </div>
//                </div>
//                `;
//                row.appendChild(tdCancel);
//
//                $("#subscriptionsList").append(row);
//            });
        }, "text");
    }
    getSubscriptions(start, start + 3);
    start += 4;
    $(window).scroll(() => {
        if ($(window).scrollTop() === $(document).height() - $(window).height()) {
            getSubscriptions(start, start + 1);
            start += 2;
        }
    });
    var delBtns = document.getElementsByClassName("del-btn");
    for(var i = 0; i< delBtns.length; i++){
        console.log(delBtns[i]);
    }
});








