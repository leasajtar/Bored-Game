const form = document.getElementById("userForm");

form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const formData = new FormData(form);

    const payload = {
        username: formData.get("username"),
        password: formData.get("password"),
        email: formData.get("email"),
        ime: formData.get("ime"),
        prezime: formData.get("prezime"),
        gender: parseInt(formData.get("gender")) || 0,
        phone: formData.get("phone")
    };

    console.log(payload);

    const res = await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    });

    const text = await res.text();
    alert(text);
    console.log("app.js loaded");
});