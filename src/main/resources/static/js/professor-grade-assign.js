function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}

document.addEventListener("DOMContentLoaded", async function () {
    const token = localStorage.getItem("jwt");
    if (!token) {
        alert("Neautorizat!");
        window.location.href = "/login.html";
        return;
    }

    const decoded = parseJwt(token);
    const userId = decoded?.userId;

    const form = document.getElementById("assign-grade-form");
    const message = document.getElementById("response-message");

    const studentSelect = document.getElementById("studentSelect");
    const courseSelect = document.getElementById("courseSelect");

    document.getElementById("logout-button")?.addEventListener("click", () => {
        localStorage.removeItem("jwt");
        window.location.href = "/login.html";
    });

    let professorCourses = [];

    // 🔁 Obține cursurile predate de profesor
    try {
        const res = await fetch("/schedule/get/all", {
            headers: { "Authorization": "Bearer " + token }
        });

        const schedules = await res.json();

        professorCourses = schedules
            .filter(s => s.course.professor?.user?.id === userId)
            .map(s => ({
                id: s.course.id,
                title: s.course.title
            }));

        // elimină duplicate
        const uniqueCourses = new Map();
        professorCourses.forEach(c => uniqueCourses.set(c.id, c.title));
        uniqueCourses.forEach((title, id) => {
            const opt = document.createElement("option");
            opt.value = id;
            opt.textContent = title;
            courseSelect.appendChild(opt);
        });

    } catch (err) {
        console.error("❌ Eroare la încărcarea cursurilor profesorului:", err);
    }

    // 👩‍🎓 Adaugă toți studenții disponibili
    try {
        const res = await fetch("/grades/get-grades", {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!res.ok) throw new Error("Eroare la încărcarea studenților");

        const gradeEntries = await res.json();
        const addedStudentIds = new Set();

        gradeEntries.forEach(entry => {
            const student = entry.student;
            if (!addedStudentIds.has(student.id)) {
                const opt = document.createElement("option");
                opt.value = student.id;
                opt.textContent = `${student.firstName} ${student.lastName}`;
                studentSelect.appendChild(opt);
                addedStudentIds.add(student.id);
            }
        });

    } catch (err) {
        console.error("❌ Eroare la adăugarea studenților:", err);
    }

    // 🚀 Submite formularul
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const studentId = studentSelect.value;
        const courseId = courseSelect.value;
        const grade = document.getElementById("grade").value.trim();
        const examDate = document.getElementById("examDate").value;

        if (!studentId || !courseId || !grade || !examDate) {
            showMessage("Completează toate câmpurile!", false);
            return;
        }

        const url = `/grades/assign?studentId=${studentId}&courseId=${courseId}&grade=${grade}&examDate=${examDate}`;

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                }
            });

            if (!response.ok) throw new Error("Eroare la trimiterea notei.");
            await response.text();
            showMessage("✅ Notă atribuită cu succes!", true);
            form.reset();
        } catch (err) {
            console.error(err);
            showMessage("❌ Eroare la trimitere.", false);
        }
    });

    function showMessage(msg, success) {
        message.textContent = msg;
        message.style.color = success ? "green" : "red";
    }
});
