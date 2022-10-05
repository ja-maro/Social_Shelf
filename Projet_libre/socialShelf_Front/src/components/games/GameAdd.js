import { useState } from "react";
import GamesService from "../../services/games.service";
import { Alert } from "@mui/material";
import { useNavigate } from "react-router-dom";
import "../../styles/Login.scss";

const GameAdd = () => {
    const [name, setName] = useState("");
    const [publisher, setPublisher] = useState("");
    const [description, setDescription] = useState("");
    const [minPlayer, setMinPlayer] = useState("");
    const [maxPlayer, setMaxPlayer] = useState("");
    const [averageDuration, setAverageDuration] = useState("");

    const [isAlert, setIsAlert] = useState(false);
    const [authMessage, setAuthMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });

    let navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        await GamesService.add(
            name,
            publisher,
            description,
            minPlayer,
            maxPlayer,
            averageDuration
        ).then((res) => (response = res));
        if (response.status === 201) {
            setIsAlert(true);
            setAuthMessage({
                severity: "success",
                message: "Created",
                status: "201",
            });
            navigate("/");
        } else if (response.status === 409) {
            setIsAlert(true);
            setAuthMessage({
                severity: "error",
                message: "Address already exists",
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
            <h1 className="title"> New game </h1>
            <label>
                Name :
                <input
                    type="text"
                    name="name"
                    placeholder="Dofus"
                    onChange={(event) => setName(event.target.value)}
                />
            </label>
            <label>
                Publisher :
                <input
                    type="text"
                    name="publisher"
                    placeholder="Ankama"
                    onChange={(event) => setPublisher(event.target.value)}
                />
            </label>
            <label>
                Description :
                <textarea
                    type="text"
                    name="description"
                    placeholder="Once upon a time ..."
                    onChange={(event) => setDescription(event.target.value)}
                />
            </label>
            <label>
                Minimum players :
                <input
                    type="text"
                    name="minPlayer"
                    placeholder="2"
                    onChange={(event) => setMinPlayer(event.target.value)}
                />
            </label>
            <label>
                Maximum players :
                <input
                    type="text"
                    name="maxPlayer"
                    placeholder="12"
                    onChange={(event) => setMaxPlayer(event.target.value)}
                />
            </label>
            <label>
                Average duration :
                <input
                    type="text"
                    name="averageDuration"
                    placeholder="45"
                    onChange={(event) => setAverageDuration(event.target.value)}
                />
                (in minutes)
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
};

export default GameAdd;
