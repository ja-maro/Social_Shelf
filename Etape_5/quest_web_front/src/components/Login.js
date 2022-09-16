import "../styles/Login.scss";
import { useState } from "react";
import AuthService from "../services/auth.service";
import { useNavigate } from "react-router-dom";

function Login({ isLog, setIsLog }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        const response = AuthService.login(username, password);
        console.log(response);
        setIsLog(true);
        navigate("/");
    };

    return (
        <div className="login">
            <form onSubmit={handleSubmit}>
                <h1 className="title"> Login </h1>
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

export default Login;
