document.getElementById("login-form").addEventListener("submit", async function(e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem("jwt", data.token);
        alert("Login reușit!");
        window.location.href = "/index.html"; // sau alta pagină de după login
    } else {
        document.getElementById("error").textContent = "Autentificare eșuată.";
    }
});