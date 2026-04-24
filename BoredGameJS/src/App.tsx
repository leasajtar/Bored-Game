import { useState } from 'react';
//import reactLogo from './assets/react.svg'
//import viteLogo from './assets/vite.svg'
//import heroImg from './assets/hero.png'
import './App.css';

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

  const handleChange = (e: { target: { name: any; value: any } }) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const [formData, setFormData] = useState<{ gender: number | null }>({
  gender: null
});

const handleChangeGender = (e: { target: { name: any; value: any } }) => {
  const { name, value } = e.target;

  setFormData((prev: any) => ({
    ...prev,
    [name]: Number(value)
  }));
};

  const handleSubmit = async (e: { preventDefault: () => void }) => {
    e.preventDefault();
    console.log(form);
    const payload = {
      username: form.username,
      password: form.password, 
      email: form.email,
      ime: form.ime,
      prezime: form.prezime,
      gender: formData.gender ?? 0,
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
    <>
    <section id="center">
      <div className="login_form">
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
          <br />

          <label>
            <input
              type="radio"
              name="gender"
              value={1} //Male
              onChange={handleChangeGender}
            />
            Male
          </label>
          <br />
          <label>
            <input
              type="radio"
              name="gender"
              value={2} //Other
              onChange={handleChangeGender}
            />
            Other
          </label>
          <br />
          <input name="phone" placeholder="Phone (optional)" onChange={handleChange} /><br />

          <button type="submit">Submit</button>
        </form>
      </div>
    </section>
      {/* <section id="center">
        <div className="hero">
          <img src={heroImg} className="base" width="170" height="179" alt="" />
          <img src={reactLogo} className="framework" alt="React logo" />
          <img src={viteLogo} className="vite" alt="Vite logo" />
        </div>
        <div>
          <h1>Get started</h1>
          <p>app</p>
          <p>
            Edit <code>src/App.tsx</code> and save to test <code>HMR</code>
          </p>
        </div>
        <button
          className="counter"
          onClick={() => setCount((count) => count + 1)}
        >
          Count is {count}
        </button>
      </section>

      <div className="ticks"></div>

      <section id="next-steps">
        <div id="docs">
          <svg className="icon" role="presentation" aria-hidden="true">
            <use href="/icons.svg#documentation-icon"></use>
          </svg>
          <h2>Documentation</h2>
          <p>Your questions, answered</p>
          <ul>
            <li>
              <a href="https://vite.dev/" target="_blank">
                <img className="logo" src={viteLogo} alt="" />
                Explore Vite
              </a>
            </li>
            <li>
              <a href="https://react.dev/" target="_blank">
                <img className="button-icon" src={reactLogo} alt="" />
                Learn more
              </a>
            </li>
          </ul>
        </div>
        <div id="social">
          <svg className="icon" role="presentation" aria-hidden="true">
            <use href="/icons.svg#social-icon"></use>
          </svg>
          <h2>Connect with us</h2>
          <p>Join the Vite community</p>
          <ul>
            <li>
              <a href="https://github.com/vitejs/vite" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#github-icon"></use>
                </svg>
                GitHub
              </a>
            </li>
            <li>
              <a href="https://chat.vite.dev/" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#discord-icon"></use>
                </svg>
                Discord
              </a>
            </li>
            <li>
              <a href="https://x.com/vite_js" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#x-icon"></use>
                </svg>
                X.com
              </a>
            </li>
            <li>
              <a href="https://bsky.app/profile/vite.dev" target="_blank">
                <svg
                  className="button-icon"
                  role="presentation"
                  aria-hidden="true"
                >
                  <use href="/icons.svg#bluesky-icon"></use>
                </svg>
                Bluesky
              </a>
            </li>
          </ul>
        </div>
      </section>

      <div className="ticks"></div>
      <section id="spacer"></section> */}
    </>
  )
}

export default App
