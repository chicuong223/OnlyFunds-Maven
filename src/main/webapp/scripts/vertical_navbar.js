function toggle(parameter) {
    let list = document.querySelector(parameter);
    if (list.classList.contains('active')) {
        list.classList.remove('active');
    } else {
        list.classList.add('active');
    }
}
//li và ul đều là active
const servletContext = document.getElementById('isActive');
console.log(servletContext.value);
let pages = document.querySelectorAll('.title');
pages.forEach(element => {
    if (element.id == servletContext.value) {
        console.log(servletContext.value);
        element.parentNode.parentNode.classList.add("active");
        element.parentNode.parentNode.parentNode.classList.add("active");
    }
});