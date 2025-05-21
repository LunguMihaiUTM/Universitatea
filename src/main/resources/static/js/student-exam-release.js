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
        // 🔁 Obține studentul după userId
        const studentRes = await fetch(`/students/by-user-id/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!studentRes.ok) throw new Error("Studentul nu a fost găsit");
        const student = await studentRes.json();

        const studentId = student.id;

        // ✅ Folosește noul endpoint pentru note ≥ 9
        const gradesRes = await fetch(`/students/${studentId}/grades/filtered?threshold=9`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!gradesRes.ok) throw new Error("Notele nu au fost găsite");
        const grades = await gradesRes.json();

        const entries = Object.entries(grades).sort(([a], [b]) => a.localeCompare(b));

        const tbody = document.getElementById("exam-release-body");
        tbody.innerHTML = "";

        if (entries.length === 0) {
            tbody.innerHTML = "<tr><td colspan='2'>Nu există discipline cu nota ≥ 9.</td></tr>";
            return;
        }

        for (const [subject, grade] of entries) {
            const row = document.createElement("tr");

            const subjectCell = document.createElement("td");
            subjectCell.textContent = subject;

            const gradeCell = document.createElement("td");
            gradeCell.textContent = parseFloat(grade).toFixed(2);

            row.appendChild(subjectCell);
            row.appendChild(gradeCell);
            tbody.appendChild(row);
        }

    } catch (error) {
        console.error("❌ Eroare la încărcarea datelor:", error);
    }
};
