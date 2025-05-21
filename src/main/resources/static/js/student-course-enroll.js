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
    const courseSelect = document.getElementById("course-select");
    const enrollForm = document.getElementById("course-enroll-form");
    const message = document.getElementById("enroll-message");

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

        // ✅ Toate cursurile
        const allCoursesRes = await fetch("/courses", {
            headers: { "Authorization": "Bearer " + token }
        });
        const allCourses = await allCoursesRes.json();

        // ✅ Cursuri deja înscrise
        const enrolledRes = await fetch(`/courses/by-student/${studentId}`, {
            headers: { "Authorization": "Bearer " + token }
        });
        const enrolledCourses = await enrolledRes.json();

        const enrolledIds = new Set(enrolledCourses.map(c => c.id));

        // ✅ Cursuri disponibile (nu e înscris)
        const availableCourses = allCourses.filter(c => !enrolledIds.has(c.id));

        if (availableCourses.length === 0) {
            courseSelect.innerHTML = `<option value="">Nu există cursuri disponibile</option>`;
        } else {
            availableCourses.forEach(course => {
                const option = document.createElement("option");
                option.value = course.id;
                option.textContent = `${course.title} (${course.credits} credite)`;
                courseSelect.appendChild(option);
            });
        }

        enrollForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const selectedCourseId = courseSelect.value;
            if (!selectedCourseId) return;

            try {
                const enrollRes = await fetch(`/students/enroll?studentId=${studentId}&courseId=${selectedCourseId}`, {
                    headers: { "Authorization": "Bearer " + token }
                });

                const result = await enrollRes.text();
                message.textContent = result;

                // Disable select
                courseSelect.querySelector(`option[value="${selectedCourseId}"]`).remove();
                courseSelect.selectedIndex = 0;

            } catch (err) {
                console.error("❌ Eroare la înscriere:", err);
                message.textContent = "A apărut o eroare.";
            }
        });

    } catch (error) {
        console.error("❌ Eroare la încărcarea datelor:", error);
    }
};
