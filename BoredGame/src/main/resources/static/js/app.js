console.log("app.js loaded");

// GAME SELECT FUNCTION
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

function toggleMenu() {
    document.getElementById("navMenu").classList.toggle("show");
}

// FILTERS FOR FIND PAGE

function filterEvents() {
    console.log("Primijeni filtere clicked");

    const filterCafe = document.getElementById("filterCafe");
    const filterLevel = document.getElementById("filterLevel");
    const filterGame = document.getElementById("filterGame");
    const filterDate = document.getElementById("filterDate");

    const cafeValue = filterCafe ? filterCafe.value : "";
    const levelValue = filterLevel ? filterLevel.value : "";
    const gameValue = filterGame ? filterGame.value : "";
    const dateValue = filterDate ? filterDate.value : "";

    const cards = document.querySelectorAll(".event-card");

    cards.forEach(card => {
        const cardCafe = card.getAttribute("data-cafe-id");
        const cardLevel = card.getAttribute("data-level");
        const cardGame = card.getAttribute("data-game");
        const cardDate = card.getAttribute("data-date");

        const matchesCafe = cafeValue === "" || cardCafe === cafeValue;
        const matchesLevel = levelValue === "" || cardLevel === levelValue;
        const matchesGame = gameValue === "" || cardGame === gameValue;
        const matchesDate = dateValue === "" || cardDate === dateValue;

        if (matchesCafe && matchesLevel && matchesGame && matchesDate) {
            card.style.display = "flex";
        } else {
            card.style.display = "none";
        }
    });
}

function openFilterPopup() {
    const popup = document.getElementById("filterPopup");
    if (popup) {
        popup.classList.add("show");
    }
}

function closeFilterPopup() {
    const popup = document.getElementById("filterPopup");
    if (popup) {
        popup.classList.remove("show");
    }
}

// close popup if user clicks outside the box
document.addEventListener("click", function (e) {
    const popup = document.getElementById("filterPopup");
    const content = document.querySelector(".filter-popup-content");
    const openBtn = document.querySelector(".filter-open-btn");

    if (!popup || !popup.classList.contains("show")) return;

    if (
        content &&
        !content.contains(e.target) &&
        openBtn &&
        !openBtn.contains(e.target)
    ) {
        closeFilterPopup();
    }
});

function clearFilters() {
    const filterCafe = document.getElementById("filterCafe");
    const filterLevel = document.getElementById("filterLevel");
    const filterGame = document.getElementById("filterGame");
    const filterDate = document.getElementById("filterDate");

    if (filterCafe) filterCafe.value = "";
    if (filterLevel) filterLevel.value = "";
    if (filterGame) filterGame.value = "";
    if (filterDate) filterDate.value = "";

    document.querySelectorAll(".event-card").forEach(card => {
        card.style.display = "flex";
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

function pickCompetitionGame(gameName, element) {
    const gameInput = document.getElementById("game_type");

    if (!gameInput) {
        console.error("Hidden input #game_type not found");
        return;
    }

    gameInput.value = gameName;

    document.querySelectorAll(".list-item").forEach(item => {
        item.classList.remove("selected-game");
    });

    element.classList.add("selected-game");

    console.log("Selected competition game:", gameName);

    let formToDelete = null;

    document.addEventListener("DOMContentLoaded", function () {
        const deleteButtons = document.querySelectorAll(".delete-open-btn");
        const popup = document.getElementById("deletePopup");
        const confirmBtn = document.getElementById("confirmDeleteBtn");
        const cancelBtn = document.getElementById("cancelDeleteBtn");

        deleteButtons.forEach(button => {
            button.addEventListener("click", function () {
                formToDelete = this.closest("form");

                if (popup) {
                    popup.classList.add("show");
                }
            });
        });

        if (cancelBtn) {
            cancelBtn.addEventListener("click", function () {
                formToDelete = null;

                if (popup) {
                    popup.classList.remove("show");
                }
            });
        }

        if (confirmBtn) {
            confirmBtn.addEventListener("click", function () {
                if (formToDelete) {
                    formToDelete.submit();
                }
            });
        }

        if (popup) {
            popup.addEventListener("click", function (e) {
                if (e.target === popup) {
                    formToDelete = null;
                    popup.classList.remove("show");
                }
            });
        }
    });
}