const title = document.getElementById("title");
const desc = document.getElementById("desc");
const form = document.getElementById("postForm");
form.addEventListener('submit', event => {
    let error = false;
    if (title.value.length < 1 || !title.value.trim()) {
        document.getElementById('titleError').textContent = 'Title is required';
        error = true;
    }
    if (!desc.value.trim() || desc.value.length < 1) {
        document.getElementById('descError').textContent = 'Description is required';
        error = true;
    }
    if (title.value.length > 30) {
        document.getElementById('titleError').textContent = 'Maximum 30 characters';
        error = true;
    }
    if (desc.value.length > 1000) {
        document.getElementById('descError').textContent = 'Maximum 1000 characters';
        error = true;
    }
    console.log(error);
    if (error === true) {
        console.log(error);
        alert('Try again');
        event.preventDefault();
    }
});

$('#postForm').submit(function (event) {
    var fileInput = document.getElementById('attachment');
    var fileSize = (fileInput.files[0].size / 1024 / 1024).toFixed(2);
    if (fileSize > 100) {
        event.preventDefault();
        document.getElementById('fileError').textContent = 'Maximum file size: 100 mb';
    }
});
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
catList.forEach(catName => {
    catName.addEventListener('click', () => {
        catName.parentNode.classList.toggle("selected");
    });
});