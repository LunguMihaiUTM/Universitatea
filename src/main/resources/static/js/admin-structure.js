document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.getElementById("logout-button");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }

    document.getElementById("structure-form").addEventListener("submit", async function (e) {
        e.preventDefault();

        const facultyId = document.getElementById("facultyId").value;
        const token = localStorage.getItem("jwt");

        const errorMsg = document.getElementById("structure-error");
        const container = document.getElementById("structure-container");
        container.innerHTML = "";
        errorMsg.textContent = "";

        if (!facultyId || isNaN(facultyId)) {
            errorMsg.textContent = "Introduceți un ID valid.";
            return;
        }

        try {
            const response = await fetch(`/university/get-faculty-structure/${facultyId}`, {
                headers: {
                    "Authorization": "Bearer " + token
                }
            });

            if (!response.ok) {
                throw new Error("Structura nu a putut fi obținută.");
            }

            const data = await response.json();
            const rendered = renderNode(data);
            container.appendChild(rendered);
        } catch (err) {
            errorMsg.textContent = "Eroare la obținerea structurii.";
            console.error(err);
        }
    });

    function renderNode(node) {
        const div = document.createElement("div");
        div.className = "structure-node";
        div.innerHTML = `<strong>${node.name}</strong>`;

        if (node.children && node.children.length > 0) {
            node.children.forEach(child => {
                const childElement = renderNode(child);
                div.appendChild(childElement);
            });
        }

        return div;
    }

    // ✅ Export PDF legat corect de DOMContentLoaded
    const exportBtn = document.getElementById("export-pdf");
    if (exportBtn) {
        exportBtn.addEventListener("click", function () {
            const structure = document.getElementById("structure-container");

            if (!structure.innerText.trim()) {
                alert("Nu există structură afișată pentru a fi exportată.");
                return;
            }

            const opt = {
                margin:       0.5,
                filename:     'structura_facultatii.pdf',
                image:        { type: 'jpeg', quality: 0.98 },
                html2canvas:  { scale: 2 },
                jsPDF:        { unit: 'in', format: 'a4', orientation: 'portrait' }
            };

            html2pdf().set(opt).from(structure).save();
        });
    }
});
