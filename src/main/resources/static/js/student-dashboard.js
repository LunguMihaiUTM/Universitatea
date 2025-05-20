function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}

async function loadDashboard() {
    const token = localStorage.getItem("jwt");
    if (!token) {
        alert("Neautorizat!");
        window.location.href = "/login.html";
        return;
    }

    const decoded = parseJwt(token);
    const userId = decoded?.userId;

    if (!userId) {
        alert("Token invalid: userId lipsă!");
        return;
    }

    // 🔎 Obține studentId pe baza userId
    let studentId;
    try {
        const studentRes = await fetch(`/students/by-user-id/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!studentRes.ok) throw new Error("Student not found");
        const student = await studentRes.json();
        studentId = student.id;

        // Nume afișat
        const fullName = `${student.firstName} ${student.lastName}`;
        console.log("🎓 Nume student obținut:", fullName);
        document.getElementById("student-name").textContent = fullName;

    } catch (err) {
        console.error("Eroare la obținerea studentului:", err);
        document.getElementById("student-name").textContent = "Student necunoscut";
        return;
    }

    // 🗓️ Obține și afișează orarul
    try {
        const scheduleRes = await fetch(`/schedule/by-student/${studentId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!scheduleRes.ok) throw new Error(`Schedule not found (${scheduleRes.status})`);
        const schedule = await scheduleRes.json();

        const days = ['Luni', 'Marți', 'Miercuri', 'Joi', 'Vineri', 'Sâmbătă', 'Duminică'];
        const grid = document.getElementById("calendar-grid");
        grid.innerHTML = "";

        if (Array.isArray(schedule)) {
            days.forEach(day => {
                const col = document.createElement("div");
                col.className = "day-column";
                col.innerHTML = `<h4>${day}</h4>`;

                schedule
                    .filter(ev => ev.dayOfWeek === day)
                    .sort((a, b) => a.startTime.localeCompare(b.startTime)) // 🟡 sortare după startTime
                    .forEach(ev => {
                        const box = document.createElement("div");
                        box.className = "event";

                        const subjectName = ev.course?.title || "N/A";
                        const timeRange = `${ev.startTime?.slice(0, 5)} - ${ev.endTime?.slice(0, 5)}`;
                        const lectureType = ev.lectureType || "";

                        const teacherFirst = ev.course?.professor?.firstName || "";
                        const teacherLast = ev.course?.professor?.lastName || "";
                        const teacherName = `${teacherFirst} ${teacherLast}`.trim();

                        box.innerHTML = `${subjectName} (${lectureType}, ${timeRange})<br><small>Prof.: ${teacherName || "N/A"}</small>`;
                        col.appendChild(box);
                    });

                grid.appendChild(col);
            });
        } else {
            grid.innerHTML = "<p>Nu există orar disponibil.</p>";
        }
    } catch (error) {
        console.error("Eroare la încărcarea orarului:", error);
    }
}

// ⚙️ Adaugă funcționalitatea pentru logout
document.addEventListener("DOMContentLoaded", function () {
    loadDashboard();

    const logoutButton = document.getElementById("logout-button");
    if (logoutButton) {
        logoutButton.addEventListener("click", function () {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }
});
