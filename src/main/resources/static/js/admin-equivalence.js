document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("jwt");
    const button = document.getElementById("import-button");
    const container = document.getElementById("import-result");

    const logoutBtn = document.getElementById("logout-button");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }

    button.addEventListener("click", async () => {
        container.innerHTML = "Se echivalează notele...";

        try {
            const response = await fetch("/grades/import-from-external", {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                }
            });

            if (!response.ok) throw new Error("Eroare la importul notelor.");

            const data = await response.json();

            if (data.length === 0) {
                container.innerHTML = "<p>Nu au fost importate note noi.</p>";
                return;
            }

            // Render table
            let html = `
                <h3>Note echivalate:</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Disciplina</th>
                            <th>Nota</th>
                            <th>Data examenului</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            data.forEach(entry => {
                const student = `${entry.student.firstName} ${entry.student.lastName}`;
                const course = entry.course?.title || "-";
                const grade = entry.grade ?? "-";
                const date = entry.examDate || "-";

                html += `
                    <tr>
                        <td>${student}</td>
                        <td>${course}</td>
                        <td>${grade}</td>
                        <td>${date}</td>
                    </tr>
                `;
            });

            html += `</tbody></table>`;
            container.innerHTML = html;

        } catch (err) {
            console.error(err);
            container.innerHTML = "<p style='color: red;'>A apărut o eroare la echivalare.</p>";
        }
    });
});
