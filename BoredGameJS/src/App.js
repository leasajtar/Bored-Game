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

  const [formData, setFormData] = useState({
  gender: null
});

const handleChangeGender = (e) => {
  const { name, value } = e.target;

  setFormData(prev => ({
    ...prev,
    [name]: Number(value)
  }));
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
      gender: parseInt(formData.gender) || 0,
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
    <div id="login_form" style={{ padding: "20px" }}>
      <h2>Login: </h2>

      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} /><br />
        <input name="password" placeholder="Password" onChange={handleChange} /><br />
        <input name="email" placeholder="Email" onChange={handleChange} /><br />
        <input name="ime" placeholder="Ime" onChange={handleChange} /><br />
        <input name="prezime" placeholder="Prezime" onChange={handleChange} /><br />
        <label>
  <input
    type="radio"
    name="gender"
    value={0} //Female
    onChange={handleChangeGender}
  />
  Female
</label>
<br/>

<label>
  <input
    type="radio"
    name="gender"
    value={1} //Male
    onChange={handleChangeGender}
  />
  Male
</label>
<br/>
<label>
  <input
    type="radio"
    name="gender"
    value={2} //Other
    onChange={handleChangeGender}
  />
  Other
</label>
<br/>
        <input name="phone" placeholder="Phone (optional)" onChange={handleChange} /><br />

        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default App;