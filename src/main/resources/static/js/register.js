document.getElementById("register-form").addEventListener("submit", async function(e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;

    const response = await fetch("/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password, role })
    });

    if (response.ok) {
        document.getElementById("register-message").textContent = "Înregistrare reușită! Te poți loga acum.";
        setTimeout(() => window.location.href = "/login.html", 2000); // redirect după 2 secunde
    } else {
        document.getElementById("register-message").textContent = "Eroare la înregistrare.";
    }
});
