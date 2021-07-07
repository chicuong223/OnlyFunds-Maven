var change_password_form = document.querySelector("#change-password-form");
var change_password_btn = document.querySelector("#change-password-btn");
//var change_interest_form = document.querySelector("#change-interest-form");
//var change_interest_btn = document.querySelector("#change-interest-btn");

//toggle form
change_password_btn.addEventListener("click", function(){
    if (change_password_form.hidden === true){
        change_password_form.hidden = false;
    }
    else {
        change_password_form.hidden = true;
    }
});

//toggle form
//change_interest_btn.addEventListener("click", function(){
//    if (change_interest_form.hidden === true){
//        change_interest_form.hidden = false;
//    }
//    else {
//        change_interest_form.hidden = true;
//    }
//});

change_password_form.addEventListener("submit", event => {
    const PASSWORD_LENGTH_MIN = 8;
    const PASSWORD_LENGTH_MAX = 32;
    var curPassword = change_password_form.querySelector('input[name="currentPassword"]');
    var newPassword = change_password_form.querySelector('input[name="newPassword"]');
    var confNewPassword = change_password_form.querySelector('input[name="confNewPassword"]');
      
    if (curPassword.value.length < PASSWORD_LENGTH_MIN || curPassword.value.length > PASSWORD_LENGTH_MAX) {
        console.log("password error");
        event.preventDefault();
        document.getElementById('passwordError').textContent = "Password length must be between 8 to 32 characters";
        return false;
    } 
    else {
        document.getElementById('passwordError').textContent = "";
    }
    
    if (newPassword.value.length < PASSWORD_LENGTH_MIN || newPassword.value.length > PASSWORD_LENGTH_MAX) {
        console.log("password error");
        event.preventDefault();
        document.getElementById('newPasswordError').textContent = "Password length must be between 8 to 32 characters";
        return false;
    } 
    else {
        document.getElementById('newPasswordError').textContent = "";
    }
    
    if (confNewPassword.value !== newPassword.value) {
        console.log("confpass error");
        event.preventDefault();
        document.getElementById('confPasswordError').textContent = "Confirm password does not match";
    } 
    else {
        document.getElementById('confPasswordError').textContent = "";
    } 
});