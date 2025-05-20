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
        console.error("Token invalid: lipsește userId");
        return;
    }

    const studentRes = await fetch(`/students/by-user-id/${userId}`, {
        headers: { "Authorization": "Bearer " + token }
    });

    if (!studentRes.ok) {
        console.error("❌ Student inexistent:", await studentRes.text());
        return;
    }

    const student = await studentRes.json();

    // ✅ Precompletare
    if (student.firstName) document.getElementById("firstName").value = student.firstName;
    if (student.lastName) document.getElementById("lastName").value = student.lastName;
    if (student.birthDate) {
        const birthDate = new Date(student.birthDate);
        const formatted = birthDate.toISOString().split("T")[0];
        document.getElementById("birthDate").value = formatted;
    }

    document.getElementById("profile-edit-form").addEventListener("submit", async function (e) {
        e.preventDefault();

        const firstName = document.getElementById("firstName").value || student.firstName;
        const lastName = document.getElementById("lastName").value || student.lastName;
        const birthDate = document.getElementById("birthDate").value || student.birthDate;

        const updatedStudent = { firstName, lastName, birthDate };

        const res = await fetch(`/user/update-student/${userId}`, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedStudent)
        });

        const msg = document.getElementById("response-message");
        if (res.ok) {
            msg.style.color = "green";
            msg.textContent = "Profil actualizat cu succes.";
        } else {
            msg.style.color = "red";
            msg.textContent = "Eroare la actualizarea profilului.";
        }
    });
};
