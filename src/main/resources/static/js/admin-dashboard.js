// Funcție de decodare a tokenului JWT
function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}

// Funcție de inițializare a dashboard-ului admin
function loadAdminDashboard() {
    const token = localStorage.getItem("jwt");

    // Dacă nu există token => redirecționează la login
    if (!token) {
        alert("Neautorizat! Vă rugăm să vă autentificați.");
        window.location.href = "/login.html";
        return;
    }

    const decoded = parseJwt(token);
    if (!decoded || !decoded.role || decoded.role !== "ADMIN") {
        alert("Acces interzis: rol neautorizat.");
        window.location.href = "/login.html";
        return;
    }

    // Afișează numele din token, dacă e disponibil
    const firstName = decoded.firstName || "Admin";
    const lastName = decoded.lastName || "";
    const fullName = `${firstName} ${lastName}`.trim();

    const nameEl = document.getElementById("admin-name");
    if (nameEl) {
        nameEl.textContent = fullName;
    }
}

// Funcție de logout
function setupLogoutButton() {
    const logoutButton = document.getElementById("logout-button");
    if (logoutButton) {
        logoutButton.addEventListener("click", function () {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }
}

// Inițializare
document.addEventListener("DOMContentLoaded", function () {
    loadAdminDashboard();
    setupLogoutButton();
});
