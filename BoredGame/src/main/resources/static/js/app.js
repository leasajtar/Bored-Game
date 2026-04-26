console.log("app.js loaded");

// GAME SELECT FUNCTION - must be global
function pickGame(gameName, element) {
    const gameInput = document.getElementById("game_name");

    if (!gameInput) {
        console.error("Hidden input #game_name not found");
        return;
    }

    gameInput.value = gameName;

    document.querySelectorAll(".list-item").forEach(item => {
        item.classList.remove("selected-game");
    });

    element.classList.add("selected-game");

    console.log("Selected game:", gameName);
}

// USER FORM
const userForm = document.getElementById("userForm");

if (userForm) {
    userForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const formData = new FormData(userForm);

        const userPayload = {
            username: formData.get("username"),
            password: formData.get("password"),
            email: formData.get("email"),
            ime: formData.get("ime"),
            prezime: formData.get("prezime"),
            gender: parseInt(formData.get("gender")) || 0,
            phone: formData.get("phone")
        };

        const res = await fetch("/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userPayload)
        });

        const text = await res.text();
        alert(text);
    });
}

// EVENT FORM
const eventForm = document.getElementById("eventForm");

if (eventForm) {
    eventForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const formData = new FormData(eventForm);

        const payload = {
            game_name:   formData.get("game_name"),
            max_players: Number(formData.get("max_players")),
            status: "OPEN",
            event_datetime: formData.get("event_datetime"),
            cafe_id:     Number(formData.get("cafe_id")),
            level:       formData.get("level")
        };

        console.log("Event payload:", payload);

        if (!payload.cafe_id) {
            alert("Odaberi kafić.");
            return;
        }
        if (!payload.game_name) {
            alert("Odaberi igru.");
            return;
        }
        if (!payload.level) {
            alert("Odaberi razinu.");
            return;
        }

        const res = await fetch("/organiziranje/events", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const text = await res.text();
        if (res.ok) {
            window.location.href = "/find";  // redirect on success
        } else {
            alert("Greška: " + text);
        }
    });
}