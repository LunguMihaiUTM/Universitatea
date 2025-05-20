document.addEventListener("DOMContentLoaded", async function () {
    const token = localStorage.getItem("jwt");
    const tbody = document.getElementById("grades-body");
    const filterInput = document.getElementById("filter-input");
    const filterType = document.getElementById("filter-type");
    const table = document.getElementById("grades-table");

    let allGrades = [];
    let currentSort = { key: null, asc: true };

    const logoutBtn = document.getElementById("logout-button");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("jwt");
            window.location.href = "/login.html";
        });
    }

    async function fetchGrades() {
        tbody.innerHTML = "<tr><td colspan='4'>Se încarcă...</td></tr>";

        try {
            const response = await fetch("/grades/get-grades", {
                headers: {
                    "Authorization": "Bearer " + token
                }
            });

            if (!response.ok) throw new Error("Eroare la încărcarea notelor");

            allGrades = await response.json();
            renderTable(allGrades);
        } catch (error) {
            console.error("Eroare:", error);
            tbody.innerHTML = "<tr><td colspan='4'>Eroare la încărcare.</td></tr>";
        }
    }

    function renderTable(data) {
        if (!data.length) {
            tbody.innerHTML = "<tr><td colspan='4'>Nu există note înregistrate.</td></tr>";
            return;
        }

        tbody.innerHTML = "";
        data.forEach(entry => {
            const studentName = `${entry.student.firstName} ${entry.student.lastName}`;
            const course = entry.course?.title || "N/A";
            const grade = entry.grade ?? "-";
            const date = entry.examDate || "-";

            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${studentName}</td>
                <td>${course}</td>
                <td>${grade}</td>
                <td>${date}</td>
            `;
            tbody.appendChild(row);
        });
    }

    function applyFilterAndSort() {
        const type = filterType.value;
        const query = filterInput.value.toLowerCase();

        let filtered = allGrades.filter(entry => {
            if (type === "student") {
                const name = `${entry.student.firstName} ${entry.student.lastName}`.toLowerCase();
                return name.includes(query);
            } else if (type === "course") {
                return entry.course?.title.toLowerCase().includes(query);
            }
            return true;
        });

        if (currentSort.key) {
            filtered.sort((a, b) => {
                let aVal, bVal;
                if (currentSort.key === "student") {
                    aVal = `${a.student.firstName} ${a.student.lastName}`.toLowerCase();
                    bVal = `${b.student.firstName} ${b.student.lastName}`.toLowerCase();
                } else if (currentSort.key === "course") {
                    aVal = a.course?.title?.toLowerCase() || "";
                    bVal = b.course?.title?.toLowerCase() || "";
                } else if (currentSort.key === "grade") {
                    aVal = parseFloat(a.grade) || 0;
                    bVal = parseFloat(b.grade) || 0;
                }

                if (aVal < bVal) return currentSort.asc ? -1 : 1;
                if (aVal > bVal) return currentSort.asc ? 1 : -1;
                return 0;
            });
        }

        renderTable(filtered);
    }

    // Events
    filterInput.addEventListener("input", applyFilterAndSort);
    filterType.addEventListener("change", applyFilterAndSort);

    table.querySelectorAll("th[data-sort]").forEach(th => {
        th.addEventListener("click", () => {
            const key = th.getAttribute("data-sort");
            const isSame = currentSort.key === key;
            currentSort = { key, asc: isSame ? !currentSort.asc : true };
            applyFilterAndSort();
        });
    });

    await fetchGrades();
});
