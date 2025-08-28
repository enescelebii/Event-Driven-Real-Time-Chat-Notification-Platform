(function() {
    const token = localStorage.getItem("accessToken");
    if (!token) {
        window.location.href = "/pages/login.html";
    }
})();