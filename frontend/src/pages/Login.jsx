import { useState } from "react";
import axios from "axios";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

 const handleLogin = async () => {
  try {
    const res = await axios.post("http://localhost:8080/auth/login", {
      username,
      password,
    });

    const token = res.data;

    localStorage.setItem("token", token);

    // 🔥 decode role from token
    const payload = JSON.parse(atob(token.split(".")[1]));
    const role = payload.role;

    localStorage.setItem("role", role);

    // 🔥 redirect based on role
    if (role === "ADMIN" || role === "BRANCH_MANAGER") {
      window.location.href = "/admin";
    } else if (role === "TEACHER") {
      window.location.href = "/teacher";
    } else {
      window.location.href = "/student";
    }

  } catch (err) {
    console.log(err);
    alert("Login Failed ");
  }
};

  return (
    <div style={{ textAlign: "center", marginTop: "100px" }}>
      <h2>ERP Login</h2>

      <input
        type="text"
        placeholder="Username"
        onChange={(e) => setUsername(e.target.value)}
      />
      <br /><br />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />
      <br /><br />

      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default Login;