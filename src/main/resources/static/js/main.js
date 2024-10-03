const token = localStorage.getItem('token');
if (!token) {
    window.location.href = 'login.html';
} else {
    fetch('http://localhost:8080/user', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('username').textContent = data.username;
            document.getElementById('email').textContent = data.email;
        });
}
