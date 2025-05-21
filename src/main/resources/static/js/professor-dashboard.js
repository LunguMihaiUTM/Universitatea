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

// Funcție de inițializare a dashboard-ului profesor
function loadProfessorDashboard() {
    const token = localStorage.getItem("jwt");

    // Verificare token JWT
    if (!token) {
        alert("Neautorizat! Vă rugăm să vă autentificați.");
        window.location.href = "/login.html";
        return;
    }

    const decoded = parseJwt(token);
    if (!decoded || decoded.role !== "PROFESSOR") {
        alert("Acces interzis: doar pentru profesori.");
        window.location.href = "/login.html";
        return;
    }

    // Afișare nume (dacă există în token)
    const firstName = decoded.firstName || "Profesor";
    const lastName = decoded.lastName || "";
    const fullName = `${firstName} ${lastName}`.trim();

    const nameEl = document.getElementById("professor-name");
    if (nameEl) {
        nameEl.textContent = fullName;
    }
}

// Funcție logout
function setupLogoutButton() {
    const logoutButton = document.getElementById("logout-button");
    if (logoutButton) {
        logoutButton.addEventListener("click", function () {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }
}

// Inițializare dashboard
document.addEventListener("DOMContentLoaded", function () {
    loadProfessorDashboard();
    setupLogoutButton();
});
