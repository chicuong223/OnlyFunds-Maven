var change_interest_form = document.querySelector("#change-interest-form");
var change_interest_btn = document.querySelector("#change-interest-btn");

//toggle form
change_interest_btn.addEventListener("click", function(){
    if (change_interest_form.hidden === true){
        change_interest_form.hidden = false;
    }
    else {
        change_interest_form.hidden = true;
    }
});

