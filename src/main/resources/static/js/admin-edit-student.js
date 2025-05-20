document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-button");
    const form = document.getElementById("edit-student-form");
    const message = document.getElementById("response-message");
    const groupSelect = document.getElementById("groupSelect");
    let allGroups = [];

    // Logout
    logoutBtn?.addEventListener("click", () => {
        localStorage.removeItem("jwt");
        window.location.href = "/login.html";
    });

    // Încarcă grupele disponibile
    fetch("/groups/all")
        .then(res => res.json())
        .then(groups => {
            allGroups = groups;
            groups.forEach(group => {
                const option = document.createElement("option");
                option.value = group.groupCode;
                option.textContent = `${group.groupCode} (an ${group.year}, ${group.specialization})`;
                groupSelect.appendChild(option);
            });
        })
        .catch(() => showMessage("Eroare la încărcarea grupelor", false));

    // Submit form
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const token = localStorage.getItem("jwt");
        const userId = document.getElementById("userId").value.trim();
        const firstName = document.getElementById("firstName").value.trim();
        const lastName = document.getElementById("lastName").value.trim();
        const birthDate = document.getElementById("birthDate").value;
        const selectedGroupCode = groupSelect.value;

        if (!userId || !firstName || !lastName || !birthDate || !selectedGroupCode) {
            showMessage("Completați toate câmpurile!", false);
            return;
        }

        const selectedGroup = allGroups.find(g => g.groupCode === selectedGroupCode);
        if (!selectedGroup) {
            showMessage("Grupa selectată nu este validă!", false);
            return;
        }

        const payload = {
            id: 0,
            firstName,
            lastName,
            birthDate,
            group: {
                id: 0,
                groupCode: selectedGroup.groupCode,
                year: selectedGroup.year,
                specialization: selectedGroup.specialization,
                faculty: {
                    id: selectedGroup.faculty.id,
                    name: selectedGroup.faculty.name
                }
            },
            user: {
                id: parseInt(userId),
                email: "", // Nu este necesar pentru update
                password: "", // Nu este necesar pentru update
                role: "STUDENT",
                username: "",
                enabled: true,
                accountNonLocked: true,
                accountNonExpired: true,
                credentialsNonExpired: true
            }
        };

        try {
            const response = await fetch(`/user/update-student/${userId}`, {
                method: "PUT",
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) throw new Error("Eșec la actualizare");

            showMessage("Student actualizat cu succes!", true);
        } catch (err) {
            console.error(err);
            showMessage("Eroare la actualizarea studentului.", false);
        }
    });

    function showMessage(text, success) {
        message.textContent = text;
        message.style.color = success ? "green" : "red";
    }
});
