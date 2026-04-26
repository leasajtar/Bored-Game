const form = document.getElementById("userForm");

if (form) {
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        // rest of your code here
    });
}
form.addEventListener("submit", async function (e) {
    e.preventDefault(); // keep this so fetch works

    const formData = new FormData(form);

    // USER payload
    const userPayload = {
        username: formData.get("username"),
        password: formData.get("password"),
        email: formData.get("email"),
        ime: formData.get("ime"),
        prezime: formData.get("prezime"),
        gender: parseInt(formData.get("gender")) || 0,
        phone: formData.get("phone")
    };

    console.log("User:", userPayload);

    try {
        // 1️⃣ SEND USER
        const res = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userPayload)
        });

        const userText = await res.text();
        console.log(userText);

        // ✅ USER ALERT
        alert("User: " + userText);

    } catch (error) {
        console.error("User error:", error);
        alert("User saving failed!");
        return; // stop if user fails
    }

    // 2️⃣ EVENT payload
    const eventPayload = {
        game_name: "Catan",
        event_datetime: "2026-05-01T18:00:00",
        max_players: 4,
        status: "OPEN",
        cafe_id: { id: 1 },
        organizer_id: { id: 1 }
    };

    console.log("Event:", eventPayload);

    try {
        const res2 = await fetch("/organiziranje.html/events", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(eventPayload)
        });

        const eventText = await res2.text();
        console.log(eventText);

        // ✅ EVENT ALERT
        alert("Event: " + eventText);

    } catch (error) {
        console.error("Event error:", error);
        alert("Event saving failed!");
    }

    console.log("app.js loaded");
});