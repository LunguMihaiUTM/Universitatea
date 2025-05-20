document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("jwt");

    // Logout handler
    const logoutBtn = document.getElementById("logout-button");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }

    const form = document.getElementById("schedule-form");
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const requestBody = {
            courseId: parseInt(document.getElementById("courseId").value),
            groupId: parseInt(document.getElementById("groupId").value),
            dayOfWeek: document.getElementById("dayOfWeek").value,
            startTime: document.getElementById("startTime").value,
            endTime: document.getElementById("endTime").value,
            lectureType: document.getElementById("lectureType").value
        };

        try {
            const res = await fetch("/schedule/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(requestBody)
            });

            const msg = document.getElementById("response-message");
            if (res.ok) {
                msg.style.color = "green";
                msg.textContent = "Perechea a fost adăugată cu succes!";
                form.reset();
            } else {
                const errorText = await res.text();
                msg.style.color = "red";
                msg.textContent = `Eroare: ${errorText}`;
            }
        } catch (error) {
            document.getElementById("response-message").textContent = "Eroare la trimiterea datelor.";
        }
    });
});
