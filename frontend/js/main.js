// login form submit
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const backendUrl = 'http://localhost:8080/api/auth';



// --- Sabitler ---
const CURRENT_USER_ID = 123; // Backend’den gerçek userId al

const socket = new SockJS("http://localhost:8080/ws");
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log("Connected: " + frame);

    // Arkadaş ekleme bildirimi
    stompClient.subscribe("/topic/friends/" + CURRENT_USER_ID, (msg) => {
        console.log("Yeni arkadaş mesajı geldi:", msg.body);
        const friend = JSON.parse(msg.body); // UserResponseDTO
        addFriendToList(friend); // Listeye ekle
    });

    // Sayfa yüklendiğinde mevcut arkadaşları çek
    fetchFriends();
}, function(error) {
    console.error("WebSocket bağlantı hatası:", error);
});

// --- Arkadaşları listeye ekleme ---
function addFriendToList(friend) {
    const friendsList = document.getElementById("friendsList");

    if ([...friendsList.children].some(div => div.dataset.id == friend.id)) return;

    const div = document.createElement("div");
    div.classList.add("discussion");
    div.dataset.id = friend.id;

    div.innerHTML = `
        <div class="photo d-flex justify-content-center align-items-center" style="width:50px; height:50px; border-radius:50%; background-color:#e9ecef;">
            <i class="fa fa-user"></i>
            <div class="online"></div>
        </div>
        <div class="desc-contact">
            <p class="name">${friend.username}</p>
            <p class="message">${friend.email || ''}</p>
        </div>
    `;

    friendsList.appendChild(div);
}

async function handleRequest(requestId, action) {
    try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(`http://localhost:8080/api/friends/${requestId}/${action}`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`İstek başarısız! Status: ${response.status}`);
        }

        // Kartı DOM'dan kaldır
        const card = document.getElementById(`request-${requestId}`);
        if (card) {
            card.remove();
        }

        // Eğer liste boş kaldıysa mesaj göster
        const listContainer = document.getElementById("friendRequestList");
        if (listContainer.children.length === 0) {
            listContainer.innerHTML = `<p class="text-muted">Hiç arkadaşlık isteğin yok.</p>`;
        }
        window.location.reload();

    } catch (err) {
        console.error("Hata:", err);
        alert("Bir hata oluştu!");
    }
}

// --- Mevcut arkadaşları backend'den çek ---
async function fetchFriends() {
    try {
        const token = localStorage.getItem("accessToken");
        const res = await fetch(`http://localhost:8080/api/friends/list`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error("Arkadaş listesi alınamadı");
        const friends = await res.json();

        friends.forEach(friend => addFriendToList(friend));
    } catch (err) {
        console.error("Arkadaş listesi fetch hatası:", err);
    }
}

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

async function loadProfile() {
    const token = localStorage.getItem('token');
    console.log("Token:", token);
    console.log(localStorage);
    if (!token) {
        alert("Token Not Found")
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/auth/me`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Profil Alınamadı Status: " + response.status);

        }

        const user = await response.json();
        console.log("Dönen JSON:", JSON.stringify(user, null, 2));

        document.getElementById("profileUsername").textContent = user.username;
        document.getElementById("profileEmail").textContent = user.email;
        localStorage.setItem('userId', data.id);

    } catch (error) {
        console.error("Profil yüklenirken hata: ", error);
    }

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
            localStorage.setItem('accessToken', data.accessToken);
            localStorage.setItem('token', data.accessToken)
            localStorage.setItem('refreshToken', data.refreshToken);
            console.log(data.token);

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
        if (document.getElementById('profileModal').classList.contains('show')) {
            closeProfileModal();
        } else {
            closeFriendsModal();
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
    loadProfile();
    document.getElementById("profileModal").style.display = "block";
    document.getElementById("profileOverlay").style.display = "block";
    loadFriendRequests();
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
    // document.getElementById("profileModal").style.display = "none";
    document.getElementById("profileOverlay").style.display = "none";
}

function logout() {
    // deleting tokens
    localStorage.removeItem('accessToken')
    localStorage.removeItem('userEmail')
    window.location.href = 'login.html';
}

async function loadFriendRequests() {
    try {
        const token = localStorage.getItem("accessToken");
        const response = await fetch("http://localhost:8080/api/friends/received", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`İstek başarısız! Status: ${response.status}`);
        }

        const requests = await response.json();

        // ❗ sadece PENDING olanları al
        const pendingRequests = requests.filter(req => req.status === "PENDING");

        const listContainer = document.getElementById("friendRequestList");
        listContainer.innerHTML = "";

        if (pendingRequests.length === 0) {
            listContainer.innerHTML = `<p class="text-muted">Hiç arkadaşlık isteğin yok.</p>`;
            return;
        }

        pendingRequests.forEach(req => {
            const card = document.createElement("div");
            card.className = "card mb-2 shadow-sm";
            card.id = `request-${req.id}`;

            card.innerHTML = `
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-1">${req.sender.username}</h5>
                        <p class="card-text mb-0">${req.sender.email}</p>
                    </div>
                    <div>
                        <button class="btn btn-success btn-sm me-2" onclick="handleRequest(${req.id}, 'accept')">Kabul Et</button>
                        <button class="btn btn-danger btn-sm" onclick="handleRequest(${req.id}, 'reject')">Reddet</button>
                    </div>
                </div>
            `;
            listContainer.appendChild(card);
        });
    } catch (err) {
        console.error("Hata:", err);
    }
}



function toggleSearch() {
    const container = document.getElementById("searchContainer");
    container.classList.toggle("d-none");
    document.getElementById("userSearchInput").focus();
}

async function searchUsers(query) {
    const resultsDiv = document.getElementById("searchResults");
    resultsDiv.innerHTML = "";

    if (query.length < 2) return; // en az 2 harften sonra arama yap

    try {
        const token = localStorage.getItem("accessToken");
        const response = await fetch(`http://localhost:8080/api/friends/search?username=${encodeURIComponent(query)}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Arama başarısız!");
        }

        const users = await response.json();

        if (users.length === 0) {
            resultsDiv.innerHTML = `<div class="list-group-item text-muted">Kullanıcı bulunamadı</div>`;
            return;
        }

        users.forEach(user => {
            const item = document.createElement("div");
            item.className = "list-group-item d-flex justify-content-between align-items-center";

            item.innerHTML = `
        <div class="d-flex flex-column">
            <h6 class="text-dark text-start d-block">${user.username}</h6>
            <small class="text-muted2">${user.email}</small>
        </div>
        <button class="btn btn-outline-primary btn-sm add-btn"
        onclick="sendFriendRequest('${user.username}')">
        <i class="fa fa-user-plus"></i>
        </button>
    `;

            resultsDiv.appendChild(item);
        });
    } catch (err) {
        console.error("Hata:", err);
    }
}

async function sendFriendRequest(username) {
    try {
        const token = localStorage.getItem("accessToken");
        const response = await fetch(`http://localhost:8080/api/friends/send/username/${username}`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Arkadaşlık isteği gönderilemedi!");
        }

        alert(`Arkadaşlık isteği gönderildi: ${username}`);
        document.getElementById("searchResults").innerHTML = ""; // listeyi temizle
        document.getElementById("userSearchInput").value = ""; // inputu temizle
    } catch (err) {
        console.error("Hata:", err);
        alert("Bir hata oluştu!");
    }
}