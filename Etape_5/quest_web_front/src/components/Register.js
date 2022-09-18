import "../styles/Login.scss";
import AuthService from "../services/auth.service";
import { useState } from "react";
import { Navigate } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function Register() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = () => {
        const response = AuthService.register(username, password);
        console.log(response);
        navigate("/");
    };

    return (
        <div className="login">
            <form onSubmit={handleSubmit}>
                <h1 className="title"> Register </h1>
                <label>
                    Username :
                    <input
                        type="text"
                        name="username"
                        placeholder="John"
                        onChange={(event) => setUsername(event.target.value)}
                    />
                </label>
                <label>
                    Password :
                    <input
                        type="password"
                        name="password"
                        placeholder="Wick"
                        onChange={(event) => setPassword(event.target.value)}
                    />
                </label>
                <input type="submit" value="Send" />
            </form>
        </div>
    );
}
export default Register;
