document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-button");
    const form = document.getElementById("edit-professor-form");
    const message = document.getElementById("response-message");

    // Logout logic
    logoutBtn?.addEventListener("click", () => {
        localStorage.removeItem("jwt");
        window.location.href = "/login.html";
    });

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const token = localStorage.getItem("jwt");

        const professorId = parseInt(document.getElementById("professorId").value.trim());
        const firstName = document.getElementById("firstName").value.trim();
        const lastName = document.getElementById("lastName").value.trim();
        const departmentId = parseInt(document.getElementById("departmentId").value.trim());

        if (!professorId || !firstName || !lastName || !departmentId) {
            showMessage("Completați toate câmpurile!", false);
            return;
        }

        const payload = {
            id: professorId,
            firstName,
            lastName,
            department: {
                id: departmentId,
                name: "", // backendul va prelua detaliile complete pe baza id-ului
                faculty: {
                    id: 1,
                    name: "Facultatea de Calculatoare, Informatică și Microelectronică"
                }
            },
            type: "LECTURER",
            user: {
                id: 0, // backendul va folosi id-ul profesorului pentru a găsi contul
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

            showMessage("Profesor actualizat cu succes.", true);
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
