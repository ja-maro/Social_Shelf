import "../styles/Login.scss";
import React, { useState } from "react";
import AuthService from "../services/auth.service";
import { Alert } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { DataContext } from "./DataContext";

function Login(props) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isAlert, setIsAlert] = useState(false);
    const [authMessage, setAuthMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });
    let navigate = useNavigate();
    const context = React.useContext(DataContext);
    const { setIsLog } = React.useContext(DataContext);

    const routeChange = () => {
        let path = `/profile`;
        navigate(path);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        await AuthService.login(username, password).then(
            (res) => (response = res)
        );
        if (response.status === 200) {
            setIsAlert(true);
            setAuthMessage({
                severity: "success",
                message: "Connected",
                status: "200",
            });
            setIsLog(true);
            props.setIsLog(true);
            console.log(context.isLog);
            routeChange();
        } else if (response.status === 401) {
            console.log("Error : " + response.status);
            setIsAlert(true);
            setAuthMessage({
                severity: "error",
                message: "Wrong Username/Password",
                status: "401",
            });
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

export default Login;
