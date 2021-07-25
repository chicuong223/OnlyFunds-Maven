// var change_interest_form = document.querySelector("#change-interest-form");
// var change_interest_btn = document.querySelector("#change-interest-btn");

// //toggle form
// change_interest_btn.addEventListener("click", function(){
//     if (change_interest_form.hidden === true){
//         change_interest_form.hidden = false;
//     }
//     else {
//         change_interest_form.hidden = true;
//     }
// });

function toggle(parameter) {
    let list = document.querySelector(parameter);
    if (list.classList.contains('active')) {
        list.classList.remove('active');
    }
    else {
        list.classList.add('active');
    }
}
const catList = document.querySelectorAll(".cat-label");
console.log(catList);
catList.forEach(catLabel => {
    catLabel.addEventListener('click', () => {
        catLabel.parentNode.classList.toggle("selected");
    });
});
