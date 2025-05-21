function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch (e) {
        return null;
    }
}

function fetchAndRenderGrades() {
    const token = localStorage.getItem("jwt");
    if (!token) {
        alert("Neautorizat! Vă rugăm să vă autentificați.");
        window.location.href = "/login.html";
        return;
    }

    fetch("/grades/get-grades", {
        method: "GET",
        headers: {
            "Authorization": token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Eroare la încărcarea notelor.");
            }
            return response.json();
        })
        .then(data => renderGrades(data))
        .catch(err => {
            console.error(err);
            alert("A apărut o eroare la preluarea notelor.");
        });
}

function renderGrades(grades) {
    const tbody = document.getElementById("grades-body");
    tbody.innerHTML = "";

    grades.forEach(entry => {
        const tr = document.createElement("tr");

        const studentName = `${entry.student.firstName} ${entry.student.lastName}`;
        const email = entry.student.email;
        const course = entry.course.title;
        const credits = entry.course.credits;
        const type = entry.course.type;
        const grade = entry.grade;
        const date = entry.examDate;

        tr.innerHTML = `
            <td>${studentName}</td>
            <td>${email}</td>
            <td>${course}</td>
            <td>${credits}</td>
            <td>${type}</td>
            <td>${grade}</td>
            <td>${date}</td>
        `;

        tbody.appendChild(tr);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    fetchAndRenderGrades();
});
