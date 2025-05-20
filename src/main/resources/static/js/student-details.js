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
        const studentRes = await fetch(`/students/by-user-id/${userId}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!studentRes.ok) throw new Error("Student not found");
        const student = await studentRes.json();

        document.getElementById("firstName").textContent = student.firstName || "-";
        document.getElementById("lastName").textContent = student.lastName || "-";

        if (student.birthDate) {
            const birth = new Date(student.birthDate);
            document.getElementById("birthDate").textContent = birth.toLocaleDateString();
        } else {
            document.getElementById("birthDate").textContent = "-";
        }

        document.getElementById("group").textContent = student.group?.groupCode || "-";
        document.getElementById("email").textContent = student.email || "-"; // Email este direct în student acum
    } catch (error) {
        console.error("Eroare la încărcarea detaliilor:", error);
    }
};
