import { useState } from "react";

function App() {
  const [form, setForm] = useState({
    username: "",
    password: "",
    email: "",
    ime: "",
    prezime: "",
    gender: "",
    phone: ""
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(form);
    const payload = {
      username: form.username,
      password: form.password, 
      email: form.email,
      ime: form.ime,
      prezime: form.prezime,
      gender: parseInt(form.gender) || 0,
      phone: form.phone
    };
    const res = await fetch("http://localhost:8080/api/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(payload)
    });

    const text = await res.text();
    alert(text);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Login: </h2>

      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} /><br />
        <input name="password" placeholder="Passwordword" onChange={handleChange} /><br />
        <input name="email" placeholder="Email" onChange={handleChange} /><br />
        <input name="ime" placeholder="Ime" onChange={handleChange} /><br />
        <input name="prezime" placeholder="Prezime" onChange={handleChange} /><br />
        <input name="gender" placeholder="Gender (number)" onChange={handleChange} /><br />
        <input name="phone" placeholder="Phone (optional)" onChange={handleChange} /><br />

        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default App;