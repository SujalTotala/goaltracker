const API = "http://localhost:8081";

function loadGoals() {
    fetch(API + "/goals")
        .then(res => res.json())
        .then(data => {

            const list = document.getElementById("goalList");
            list.innerHTML = "";

            let today = new Date().toISOString().split("T")[0];

            let total = 0;
            let completed = 0;

            data.forEach(g => {

                if (g.date === today) {

                    total++;

                    if (g.completed) completed++;

                    const li = document.createElement("li");

                    li.innerHTML = `
                        <input type="checkbox" ${g.completed ? "checked" : ""}
                        onchange="toggleComplete(${g.id}, '${g.title}', ${g.completed})">

                        ${g.title} (${g.badge})

                        <button onclick="deleteGoal(${g.id})">Delete</button>
                    `;

                    list.appendChild(li);
                }
            });

            // progress
            let percent = total === 0 ? 0 : (completed / total) * 100;

            document.getElementById("dailyProgress").innerText =
                `Today: ${completed}/${total} (${percent.toFixed(0)}%)`;

            document.getElementById("progressBar").style.width = percent + "%";

            generateHeatmap(data);
            calculateMonthly(data);
            showStreak(data);
        });
}

function addGoal() {
    const title = document.getElementById("title").value;

    fetch(API + "/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            title: title,
            completed: false
        })
    }).then(() => loadGoals());
}

function deleteGoal(id) {
    fetch(API + "/goals/" + id, {
        method: "DELETE"
    }).then(() => loadGoals());
}

function toggleComplete(id, title, completed) {
    fetch(API + "/goals/" + id, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            title: title,
            completed: !completed
        })
    }).then(() => loadGoals());
}