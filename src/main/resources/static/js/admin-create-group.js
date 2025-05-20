document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-button");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }

    const form = document.getElementById("clone-form");
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const token = localStorage.getItem("jwt");

        const groupId = document.getElementById("groupId").value;
        const groupCode = document.getElementById("groupCode").value;
        const year = document.getElementById("year").value;
        const specialization = document.getElementById("specialization").value;
        const facultyId = document.getElementById("facultyId").value;

        const requestBody = {
            groupCode,
            year: parseInt(year),
            specialization,
            faculty: {
                id: parseInt(facultyId),
                name: "" // backendul probabil îl ignoră; îl poți omite dacă nu e necesar
            }
        };

        try {
            const response = await fetch(`/groups/clone-and-updated?groupId=${groupId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(requestBody)
            });

            const msg = document.getElementById("response-message");
            if (response.ok) {
                msg.style.color = "green";
                msg.textContent = "Grupa a fost clonată și creată cu succes!";
                form.reset();
            } else {
                const errText = await response.text();
                msg.style.color = "red";
                msg.textContent = `Eroare: ${errText}`;
            }
        } catch (err) {
            document.getElementById("response-message").textContent = "Eroare la trimiterea datelor.";
            console.error(err);
        }
    });
});
