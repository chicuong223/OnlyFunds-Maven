var change_password_form = document.querySelector("#change-password-form");
var change_password_btn = document.querySelector("#change-password-btn");
//var change_interest_form = document.querySelector("#change-interest-form");
//var change_interest_btn = document.querySelector("#change-interest-btn");

//toggle form
//change_interest_btn.addEventListener("click", function(){
//    if (change_interest_form.hidden === true){
//        change_interest_form.hidden = false;
//    }
//    else {
//        change_interest_form.hidden = true;
//    }
//});

console.log(document.getElementById('oldPass'));
digestMessage("123456789");

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

let new_ava = document.getElementById('new-avatar');
let img_ava = document.getElementById('img-avatar')
new_ava.onchange = evt => {
    const [file] = new_ava.files;
    if (file) {
        img_ava.src = URL.createObjectURL(file);
    }
    document.getElementById('update').classList.remove('disabled');
}

async function digestMessage(message) {             
    const msgUint8 = new TextEncoder().encode(message);     
    const hashBuffer = await crypto.subtle.digest('SHA-256', message);           // hash the message
    const hashArray = Array.from(new Uint8Array(hashBuffer));                     // convert buffer to byte array
    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join(''); // convert bytes to hex string
    return hashHex;
}

digestMessage()
    .then(digestHex => console.log(digestHex));