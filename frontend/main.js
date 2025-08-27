// login form submit
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const backendUrl = 'http://localhost:8080/api/auth';

if (registerForm){
    registerForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const email = document.getElementById('email').value;

        try {
            const res = await fetch(`http://localhost:8080/api/auth/register`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({username,password,email}),
                credentials: 'include'
            });

            if (!res.ok) throw new Error('User already exists');
            const data = await res.json();
            console.log(data);

            // Token localStorage'a kaydediyoruz
            localStorage.setItem('accessToken', data.token);
            localStorage.setItem('userEmail', data.email);

            // Home sayfasına yönlendir
            window.location.href = 'login.html';
        } catch (err) {
            alert(err.message);
        }

    });
}

if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const res = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({email, password}),
                credentials: 'include'
            });

            if (!res.ok) throw new Error('Login failed');
            const data = await res.json();
            console.log(data);

            // Token localStorage'a kaydediyoruz
            localStorage.setItem('accessToken', data.token);
            localStorage.setItem('userEmail', data.email);

            // Home sayfasına yönlendir
            window.location.href = 'home.html';
        } catch (err) {
            alert(err.message);
        }
    });
}
