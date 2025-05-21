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

        if (!studentRes.ok) throw new Error("Student not found");
        const student = await studentRes.json();

        // 🔁 Obține notele prin Strategy Pattern
        const gradesRes = await fetch(`/grades/get-grades`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!gradesRes.ok) throw new Error("Grades not found");
        const studentCourses = await gradesRes.json();

        // Construiește mapă cu disciplina => nota
        const gradesMap = {};
        for (const entry of studentCourses) {
            const courseTitle = entry.course.title;
            const grade = entry.grade;
            gradesMap[courseTitle] = grade;
        }

        // ✅ Filtrare: doar disciplinele cu nota >= 9
        const filteredEntries = Object.entries(gradesMap)
            .filter(([_, grade]) => grade >= 9)
            .sort(([a], [b]) => a.localeCompare(b));

        const tbody = document.getElementById("exam-release-body");
        tbody.innerHTML = "";

        if (filteredEntries.length === 0) {
            tbody.innerHTML = "<tr><td colspan='2'>Nu există discipline eligibile pentru eliberare.</td></tr>";
            return;
        }

        for (const [subject, grade] of filteredEntries) {
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
