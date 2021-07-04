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
    if (error === true) {
        event.preventDefault();
    }
});