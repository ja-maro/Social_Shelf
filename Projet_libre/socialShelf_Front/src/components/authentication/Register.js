import "../../styles/Login.scss";
import AuthService from "../../services/auth.service";
import { useState } from "react";
import { Alert } from "@mui/material";

function Register() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [isAlert, setIsAlert] = useState(false);
    const [authMessage, setAuthMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        await AuthService.register(username, email, password).then(
            (res) => (response = res)
        );
        if (response.status === 201) {
            setIsAlert(true);
            setAuthMessage({
                severity: "success",
                message: "Created",
                status: "201",
            });
        } else if (response.status === 409) {
            setIsAlert(true);
            setAuthMessage({
                severity: "error",
                message: "Username/Email already exist",
                status: "409",
            });
        } else {
            setIsAlert(true);
            setAuthMessage({
                severity: "error",
                message: "Error",
                status: "",
            });
        }
    };

    const form = (
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
                Email :
                <input
                    type="email"
                    name="email"
                    placeholder="John@Wick.fr"
                    onChange={(event) => setEmail(event.target.value)}
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
    );

    if (!isAlert) {
        return <div className="login">{form}</div>;
    } else {
        return (
            <div className="login">
                {form}
                <Alert
                    className="alert"
                    variant="filled"
                    severity={authMessage.severity}
                >
                    {authMessage.message}
                </Alert>
            </div>
        );
    }
}
export default Register;
