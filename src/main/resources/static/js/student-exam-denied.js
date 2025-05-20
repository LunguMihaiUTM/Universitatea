function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}

window.onload = async function () {
    const token = localStorage.getItem("jwt");
    if (!token) {
        alert("Neautorizat!");
        window.location.href = "/login.html";
        return;
    }

    const decoded = parseJwt(token);
    const userId = decoded?.userId;

    if (!userId) {
        console.error("❌ Token invalid: userId lipsă!");
        return;
    }

    try {
        // 🔁 Obține studentul prin userId
        const studentRes = await fetch(`/students/by-user-id/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!studentRes.ok) throw new Error("Student not found");
        const student = await studentRes.json();

        // 🔁 Obține notele
        const gradesRes = await fetch(`/students/${student.id}/grades`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!gradesRes.ok) throw new Error("Grades not found");
        const grades = await gradesRes.json();

        // ❌ Filtrare: discipline neadmise (nota < 5)
        const deniedEntries = Object.entries(grades)
            .filter(([_, grade]) => grade < 5)
            .sort(([a], [b]) => a.localeCompare(b));

        const tbody = document.getElementById("exam-denied-body");
        tbody.innerHTML = "";

        if (deniedEntries.length === 0) {
            tbody.innerHTML = "<tr><td colspan='2'>Toate disciplinele sunt promovate!</td></tr>";
            return;
        }

        for (const [subject, grade] of deniedEntries) {
            const row = document.createElement("tr");

            const subjectCell = document.createElement("td");
            subjectCell.textContent = subject;

            const gradeCell = document.createElement("td");
            gradeCell.textContent = grade.toFixed(2);

            row.appendChild(subjectCell);
            row.appendChild(gradeCell);
            tbody.appendChild(row);
        }

    } catch (error) {
        console.error("❌ Eroare la încărcarea datelor:", error);
    }
};
