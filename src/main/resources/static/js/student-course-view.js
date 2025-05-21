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
    const container = document.getElementById("course-container");

    if (!userId) {
        console.error("❌ Token invalid: userId lipsă!");
        return;
    }

    try {
        const studentRes = await fetch(`/students/by-user-id/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });
        if (!studentRes.ok) throw new Error("Studentul nu a fost găsit");
        const student = await studentRes.json();
        const studentId = student.id;

        const coursesRes = await fetch(`/courses/by-student/${studentId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!coursesRes.ok) throw new Error("Cursurile nu au putut fi preluate");
        const courses = await coursesRes.json();

        container.innerHTML = "";

        if (courses.length === 0) {
            container.innerHTML = "<p>Nu ești înscris la niciun curs momentan.</p>";
            return;
        }

        for (const course of courses) {
            const card = document.createElement("div");
            card.className = "course-card";

            const title = document.createElement("div");
            title.className = "course-title";
            title.textContent = course.title;

            const detail1 = document.createElement("div");
            detail1.className = "course-detail";
            detail1.textContent = `Profesor: ${course.professor.firstName} ${course.professor.lastName}`;

            const detail2 = document.createElement("div");
            detail2.className = "course-detail";
            detail2.textContent = `Credite: ${course.credits}`;

            const detail3 = document.createElement("div");
            detail3.className = "course-detail";
            detail3.textContent = `Email profesor: ${course.professor.user.email}`;

            const detail4 = document.createElement("div");
            detail4.className = "course-detail";
            detail4.textContent = `Tip curs: ${course.type}`;

            card.appendChild(title);
            card.appendChild(detail1);
            card.appendChild(detail2);
            card.appendChild(detail3);
            card.appendChild(detail4);
            container.appendChild(card);
        }

    } catch (err) {
        console.error("❌ Eroare la încărcarea cursurilor:", err);
        container.innerHTML = "<p>A apărut o eroare la afișarea cursurilor.</p>";
    }
};
