function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-button");
    const form = document.getElementById("professor-edit-form");
    const message = document.getElementById("response-message");

    // Logout
    logoutBtn?.addEventListener("click", () => {
        localStorage.removeItem("jwt");
        window.location.href = "/login.html";
    });

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const token = localStorage.getItem("jwt");
        const decoded = parseJwt(token);
        const userId = decoded?.userId;

        const firstName = document.getElementById("firstName").value.trim();
        const lastName = document.getElementById("lastName").value.trim();

        if (!firstName || !lastName) {
            showMessage("Completează toate câmpurile!", false);
            return;
        }

        const payload = {
            id: 5, // ← setează ID-ul profesorului corect aici
            firstName,
            lastName,
            department: {
                id: 2, // ← ID-ul departamentului asociat
                name: "", // poate fi lăsat gol, backendul îl completează
                faculty: {
                    id: 1,
                    name: "Facultatea de Calculatoare, Informatică și Microelectronică"
                }
            },
            type: "LECTURER",
            user: {
                id: userId, // ← extragem din token
                email: "",
                password: "",
                role: "PROFESSOR",
                username: "",
                authorities: [],
                enabled: true,
                accountNonLocked: true,
                accountNonExpired: true,
                credentialsNonExpired: true
            }
        };

        try {
            const response = await fetch("/user/update-professor", {
                method: "PUT",
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) throw new Error("Eroare la actualizare.");

            showMessage("Modificări salvate cu succes.", true);
        } catch (err) {
            console.error(err);
            showMessage("Eroare la actualizare.", false);
        }
    });

    function showMessage(msg, success) {
        message.textContent = msg;
        message.style.color = success ? "green" : "red";
    }
});
