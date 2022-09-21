import "../styles/Login.scss";
import { useState } from "react";
import AuthService from "../services/auth.service";
import { Alert } from "@mui/material";

function Login(props) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isAlert, setIsAlert] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        await AuthService.login(username, password).then(
            (res) => (response = res)
        );
        console.log(response);
        if (response.status === 200) {
            setIsAlert(true);
            props.setIsLog(true);
        } else {
            console.log(response);
        }
    };

    const form = (
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
    );

    if (!isAlert) {
        return <div className="login">{form}</div>;
    }
    if (isAlert && !props.isLog) {
        return (
            <div className="login">
                {form}
                <Alert className="alert" variant="filled" severity="success">
                    success !
                </Alert>
            </div>
        );
    }
}

export default Login;
