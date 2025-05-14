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
    const email = decoded?.sub;
    const usersRes = await fetch("/user/all", {
        headers: { "Authorization": "Bearer " + token }
    });
    const users = await usersRes.json();
    const user = users.find(u => u.email === email);
    if (!user) return;
    const studentRes = await fetch(`/students/by-user-id/${user.id}`, {
        headers: { "Authorization": "Bearer " + token }
    });
    const student = await studentRes.json();
    document.getElementById("student-name").textContent = student.firstName + " " + student.lastName;

    const scheduleRes = await fetch(`/schedule/by-student/${student.id}`, {
        headers: { "Authorization": "Bearer " + token }
    });
    const schedule = await scheduleRes.json();
    const days = ['Luni', 'Marți', 'Miercuri', 'Joi', 'Vineri', 'Sâmbătă', 'Duminică'];
    const grid = document.getElementById("calendar-grid");
    grid.innerHTML = "";
    days.forEach(day => {
        const col = document.createElement("div");
        col.className = "day-column";
        col.innerHTML = `<h4>${day}</h4>`;
        schedule.filter(e => e.day === day).forEach(ev => {
            const box = document.createElement("div");
            box.className = "event";
            box.textContent = `${ev.subject} (${ev.time})`;
            col.appendChild(box);
        });
        grid.appendChild(col);
    });

    const courseRes = await fetch(`/courses/by-student/${student.id}`, {
        headers: { "Authorization": "Bearer " + token }
    });
    const courses = await courseRes.json();
    const container = document.getElementById("courses-container");
    container.innerHTML = "";
    courses.forEach(course => {
        const card = document.createElement("div");
        card.className = "card";
        card.innerHTML = `<strong>${course.name}</strong><p>${course.description || ""}</p>`;
        container.appendChild(card);
    });
}
window.onload = loadDashboard;