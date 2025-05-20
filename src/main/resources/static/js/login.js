document.getElementById("login-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem("jwt", data.token);

        const decoded = parseJwt(data.token);
        const role = decoded?.role;

        // Redirecționare în funcție de rol
        if (role === "STUDENT") {
            window.location.href = "/student-dashboard.html";
        } else if (role === "PROFESSOR") {
            window.location.href = "/professor-dashboard.html";
        } else if (role === "ADMIN") {
            window.location.href = "/admin-dashboard.html";
        } else {
            alert("Rol necunoscut. Nu s-a putut redirecționa.");
        }
    } else {
        document.getElementById("error").textContent = "Autentificare eșuată.";
    }
});

function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}
