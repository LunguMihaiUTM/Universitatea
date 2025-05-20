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
        console.error("Token invalid: userId lipsă!");
        return;
    }

    try {
        // 🔁 Obține studentul direct după userId
        const studentRes = await fetch(`/students/by-user-id/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!studentRes.ok) throw new Error("Student not found");
        const student = await studentRes.json();

        const gradesRes = await fetch(`/students/${student.id}/grades`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!gradesRes.ok) throw new Error("Grades not found");
        const grades = await gradesRes.json();

        const sortedEntries = Object.entries(grades).sort(([a], [b]) => a.localeCompare(b));

        const tbody = document.getElementById("grades-body");
        tbody.innerHTML = "";

        for (const [subject, grade] of sortedEntries) {
            const row = document.createElement("tr");

            const subjectCell = document.createElement("td");
            subjectCell.textContent = subject;

            const gradeCell = document.createElement("td");
            gradeCell.textContent = grade.toFixed(2);

            row.appendChild(subjectCell);
            row.appendChild(gradeCell);
            tbody.appendChild(row);
        }

        // 🧾 Export PDF
        document.getElementById("export-pdf").addEventListener("click", function () {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();

            const studentName = `${student.firstName} ${student.lastName}`;
            const groupCode = student.group?.groupCode || "N/A";

            doc.setFontSize(14);
            doc.text(`Notele studentului: ${studentName} (Grupa: ${groupCode})`, 20, 20);

            let startY = 30;
            sortedEntries.forEach(([disciplina, nota]) => {
                doc.text(`• ${disciplina}: ${nota.toFixed(2)}`, 20, startY);
                startY += 10;
            });

            doc.save("note_student.pdf");
        });

        // 📤 Export CSV
        document.getElementById("export-csv").addEventListener("click", function () {
            let csvContent = "data:text/csv;charset=utf-8,Disciplina,Nota\n";

            sortedEntries.forEach(([disciplina, nota]) => {
                csvContent += `"${disciplina}",${nota.toFixed(2)}\n`;
            });

            const encodedUri = encodeURI(csvContent);
            const link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "note_student.csv");
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        });

    } catch (error) {
        console.error("❌ Eroare generală:", error);
    }
};
