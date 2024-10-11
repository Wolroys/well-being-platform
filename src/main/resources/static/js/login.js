document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.querySelector('form');
    const emailInput = document.getElementById('Email');
    const passwordInput = document.getElementById('Password');

    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // Остановить стандартное поведение формы

        const email = emailInput.value;
        const password = passwordInput.value;

        try {
            const response = await fetch('http://localhost:8080/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            if (response.ok) {
                const responseData = await response.json();
                const token = responseData.token;
                // Сохраняем JWT токен в localStorage или sessionStorage
                localStorage.setItem('jwt', token);
                // Вывод успешного сообщения
                showSuccessAlert('Успешная авторизация! Добро пожаловать, ' + responseData.data.name);

                // Перенаправление на другую страницу или действие
                window.location.href = '/dashboard'; // Пример перенаправления
            } else {
                const errorData = await response.json();
                // Показать сообщение об ошибке
                showErrorAlert(errorData.error || "Login failed!");
            }
        } catch (error) {
            showErrorAlert("Something went wrong. Please try again.");
        }
    });

    function showSuccessAlert(message) {
        const successAlert = document.getElementById('success-alert');
        const successMessage = document.getElementById('success-message');
        successMessage.innerText = message;
        successAlert.classList.remove('hidden');
    }

    function showErrorAlert(message) {
        const errorAlert = document.getElementById('error-alert');
        const errorMessage = document.getElementById('error-message');
        errorMessage.innerText = message;
        errorAlert.classList.remove('hidden');
    }

    function dismissAlert(alertId) {
        document.getElementById(alertId).classList.add('hidden');
    }
});
