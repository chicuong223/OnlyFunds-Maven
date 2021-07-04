function startTimer(duration, display) {
    let count = setInterval(() => {
        display.textContent = duration;
        duration--;
        if (duration < 0) {
            duration = 0;
            clearInterval(count);
            generateForm(document.getElementById('resend'));
        }
    }, 1000);
}

/* <form method="GET" action="passwordEmail" class="mt-3" style='visibility: hidden' id='form'>
    <p class='text-primary'>Did not receive your OTP?</p>
    <button type="submit" disabled id='resendBtn' class='btn btn-danger'>Resend</button>
</form>*/

function generateForm(parent){
    const formEl = document.createElement('form');
    formEl.setAttribute('method', "GET");
    formEl.setAttribute('action', 'passwordEmail');
    formEl.setAttribute('class', 'mt-3');
    const pEl = document.createElement('p');
    pEl.setAttribute('class', 'text-primary');
    pEl.textContent = 'Did not receive your OTP?';
    const btnEl = document.createElement('button');
    btnEl.setAttribute('type', 'submit');
    btnEl.setAttribute('class', 'btn btn-danger');
    btnEl.setAttribute('name', 'resend');
    btnEl.setAttribute('value', 'rs');
    btnEl.textContent = 'Resend';
    formEl.appendChild(pEl);
    formEl.appendChild(btnEl);
    parent.appendChild(formEl);
}

window.onload = () => {
    let duration = parseInt(document.getElementById("time").textContent);
//    setInterval(() => {
//        console.log(duration);
//    }
//    , 1000);
    let display = document.querySelector('#time');
    startTimer(duration, display);
};