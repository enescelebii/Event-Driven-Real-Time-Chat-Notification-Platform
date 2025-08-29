// login form submit
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const backendUrl = 'http://localhost:8080/api/auth';

if (registerForm) {
    registerForm.addEventListener('submit', async(event) => {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const email = document.getElementById('email').value;

        try {
            const res = await fetch(`http://localhost:8080/api/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password, email }),
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
    loginForm.addEventListener('submit', async(e) => {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const res = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password }),
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

function openFriendsModal() {
    const modal = document.getElementById('friendsModal');
    const overlay = document.getElementById('friendsOverlay');

    modal.classList.add('show');
    overlay.classList.add('show');
    document.body.style.overflow = 'hidden';

    // Overlay tıklayınca modal kapanır
    overlay.addEventListener('click', closeFriendsModal);
}

function closeFriendsModal() {
    const modal = document.getElementById('friendsModal');
    const overlay = document.getElementById('friendsOverlay');

    // Kapanış animasyonu
    modal.style.opacity = 0;
    modal.style.transform = 'translateX(-50%) translateY(0) scale(0.8)';

    overlay.style.opacity = 0;

    setTimeout(() => {
        modal.classList.remove('show');
        overlay.classList.remove('show');
        document.body.style.overflow = '';
        modal.style.opacity = '';
        modal.style.transform = '';
        overlay.style.opacity = '';

        overlay.removeEventListener('click', closeFriendsModal);
    }, 200); // Animasyon süresi ile uyumlu
}

// ESC tuşu ile kapatma
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeFriendsModal();
    }
});

// Arkadaş ekleme işlevselliği
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('btn-primary')) {
        const button = event.target;
        const originalText = button.textContent;

        if (originalText === 'Ekle') {
            button.textContent = 'Eklendi';
            button.classList.remove('btn-primary');
            button.classList.add('btn-success');
            button.disabled = true;

            // Başarı mesajını göster
            setTimeout(() => {
                button.textContent = 'Arkadaş';
            }, 1000);
        }
    }
});

function openProfileModal() {
    const modal = document.getElementById('profileModal');
    const overlay = document.getElementById('profileOverlay');

    modal.classList.add('show');
    overlay.classList.add('show');
    document.body.style.overflow = 'hidden';

    // Overlay tıklayınca modal kapanır
    overlay.addEventListener('click', closeFriendsModal);
}

function closeProfileModal() {
    const modal = document.getElementById('profileModal');
    const overlay = document.getElementById('profileOverlay');

    // Kapanış animasyonu
    modal.style.opacity = 0;
    modal.style.transform = 'translateX(-50%) translateY(0) scale(0.8)';

    overlay.style.opacity = 0;

    setTimeout(() => {
        modal.classList.remove('show');
        overlay.classList.remove('show');
        document.body.style.overflow = '';
        modal.style.opacity = '';
        modal.style.transform = '';
        overlay.style.opacity = '';

        overlay.removeEventListener('click', closeProfileModal);
    }, 200); // Animasyon süresi ile uyumlu
}