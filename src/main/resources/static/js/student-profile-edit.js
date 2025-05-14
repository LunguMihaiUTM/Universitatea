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
    document.getElementById("firstName").value = student.firstName;
    document.getElementById("lastName").value = student.lastName;

    document.getElementById("profile-edit-form").addEventListener("submit", async function(e) {
        e.preventDefault();
        const updatedStudent = {
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            group: student.group
        };
        const res = await fetch(`/user/update-student/${user.id}`, {
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