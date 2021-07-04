/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const form = document.getElementById('form');
const email = document.getElementById('email');
const pass = document.getElementById('newPassword');
const confPass = document.getElementById('confPassword');

form.addEventListener('submit', event => {
    if (email !== null) {
        const emailRegex = /^[a-z][a-z0-9_\.]{5,32}@[a-z0-9]{2,}(\.[a-z0-9]{2,4}){1,2}$/;
        if (emailRegex.test(email.value) === false) {
            event.preventDefault();
            document.getElementById('error').textContent = "Invalid email";
        }
        return;
    }
    if (pass !== null) {
        if (pass.value === '') {
            event.preventDefault();
            document.getElementById('error').textContent = "Please enter password";
        } else if (pass.value.length < 8 || pass.value.length > 32) {
            event.preventDefault();
            document.getElementById('error').textContent = "Password must be between 8 to 32 characters in length";
        } else if (pass.value !== confPass.value) {
            event.preventDefault();
            document.getElementById('error').textContent = "Confirm password did not match";
        }
        return;
    }
});

function RestrictKey() {
    if (event.keyCode === 32) {
        return false;
    }
}

