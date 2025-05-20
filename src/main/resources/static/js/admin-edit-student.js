document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-button");
    const form = document.getElementById("edit-student-form");
    const message = document.getElementById("response-message");
    const groupSelect = document.getElementById("groupSelect");
    const userIdInput = document.getElementById("userId");
    let allGroups = [];

    // Logout logic
    logoutBtn?.addEventListener("click", () => {
        localStorage.removeItem("jwt");
        window.location.href = "/login.html";
    });

    // Mapare hardcodată groupCode -> id
    const groupIdMap = {
        "SI-221": 1,
        "TI-222": 2,
        "CI-211": 3,
        "EA-211": 4,
        "IA-222": 5,
        "Default group": 6,
        "TI-221": 7
    };

    // Încarcă grupele
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

    // Form submit
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const token = localStorage.getItem("jwt");

        const userId = userIdInput.value.trim();
        const firstName = document.getElementById("firstName").value.trim();
        const lastName = document.getElementById("lastName").value.trim();
        const birthDate = document.getElementById("birthDate").value;
        const groupCode = groupSelect.value;

        if (!userId || !firstName || !lastName || !birthDate || !groupCode) {
            showMessage("Completați toate câmpurile!", false);
            return;
        }

        const selectedGroup = allGroups.find(g => g.groupCode === groupCode);
        if (!selectedGroup) {
            showMessage("Grupa selectată este invalidă!", false);
            return;
        }

        const groupId = groupIdMap[groupCode] || 0;

        const payload = {
            id: 0,
            firstName,
            lastName,
            birthDate,
            group: {
                id: groupId,
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
                email: "",
                password: "",
                role: "STUDENT",
                username: "",
                authorities: [],
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

            if (!response.ok) throw new Error("Update eșuat");
            showMessage("Student actualizat cu succes.", true);
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
