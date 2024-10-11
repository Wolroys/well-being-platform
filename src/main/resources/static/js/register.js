document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.querySelector('form');
    const nameInput = document.getElementById('FirstName');
    const lastNameInput = document.getElementById('LastName');
    const emailInput = document.getElementById('Email');
    const passwordInput = document.getElementById('Password');

    registerForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const name = nameInput.value;
        const lastName = lastNameInput.value;
        const email = emailInput.value;
        const password = passwordInput.value;

        try {
            const response = await fetch('http://localhost:8080/user/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                    name: name,
                    lastName: lastName
                })
            });

            const data = await response.json();

            if (response.ok) {
                const responseData = await response.json();
                const token = responseData.token;
                // Сохраняем JWT токен в localStorage или sessionStorage
                localStorage.setItem('jwt', token);
                // Перенаправление на другую страницу или действие
                window.location.href = '/dashboard'; // Пример перенаправления
            } else {
                alert(data.message);
            }
        } catch (error) {
            alert("something went wrong");
        }
    });
});
