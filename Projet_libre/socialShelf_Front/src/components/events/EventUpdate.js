import { useContext, useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import { Alert } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import "../../styles/Login.scss";
import { DataContext } from "../DataContext";
import AddressService from "../../services/address.service";
import ShelfService from "../../services/shelf.service";

const EventUpdate = () => {
    const [title, setTitle] = useState("");
    const [pitch, setPitch] = useState("");
    const [minPlayer, setMinPlayer] = useState("");
    const [maxPlayer, setMaxPlayer] = useState("");
    const [duration, setDuration] = useState("");
    const [startDate, setStartDate] = useState("");
    const [place, setPlace] = useState({});
    const [game, setGame] = useState({});
    const [shelf, setShelf] = useState([]);
    const [addressList, setAddressList] = useState([]);
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });
    let navigate = useNavigate();
    const { id } = useParams();
    const [events, setEvents] = useState({});
    const [durationslice, setSlice] = useState("");
    const context = useContext(DataContext);

    useEffect(() => {
        getEvent();
        getShelf();
        getAddress();
    }, []);

    const getShelf = () => {
        ShelfService.getShelf().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setShelf(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };
    const getAddress = () => {
        AddressService.getAll().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setAddressList(
                    response.data.filter(
                        (address) =>
                            address.player.playerId === context.playerId
                    )
                );
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    const getEvent = () => {
        EventsService.getById(id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setEvents(response.data);
                setSlice(response.data.startDate.slice(0, 16));
                setDefault(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    const setDefault = (data) => {
        setTitle(data.title);
        setPitch(data.pitch);
        setMinPlayer(data.minPlayer);
        setMaxPlayer(data.maxPlayer);
        setDuration(data.duration);
        setStartDate(data.startDate);
        setGame(data.game);
        setPlace(data.place);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        let response;
        let event = {
            id,
            title,
            pitch,
            minPlayer,
            maxPlayer,
            duration,
            startDate,
            place,
            game,
        };
        await EventsService.update(event).then((res) => (response = res));
        if (response.status === 200) {
            setIsAlert(true);
            setAlertMessage({
                severity: "success",
                message: "Created",
                status: "201",
            });
            navigate("/");
        } else if (response.status === 409) {
            setIsAlert(true);
            setAlertMessage({
                severity: "error",
                message: "Event already exists",
                status: "409",
            });
        } else {
            setIsAlert(true);
            setAlertMessage({
                severity: "error",
                message: "Error",
                status: "",
            });
        }
    };

    const form = (
        <form onSubmit={handleSubmit}>
            <h1 className="title"> Modify Event </h1>
            <label>
                Title :
                <input
                    type="text"
                    name="title"
                    placeholder="Title ..."
                    defaultValue={events.title}
                    onChange={(event) => setTitle(event.target.value)}
                />
            </label>
            <label>
                Pitch :
                <input
                    type="text"
                    name="pitch"
                    placeholder="Pitch ..."
                    defaultValue={events.pitch}
                    onChange={(event) => setPitch(event.target.value)}
                />
            </label>
            <label>
                Minimum players :
                <input
                    type="text"
                    onKeyPress={(event) => {
                        if (!/[0-9]/.test(event.key)) {
                            event.preventDefault();
                        }
                    }}
                    defaultValue={events.minPlayer}
                    name="minPlayer"
                    placeholder="2"
                    onChange={(event) => setMinPlayer(event.target.value)}
                />
            </label>
            <label>
                Maximum players :
                <input
                    type="text"
                    onKeyPress={(event) => {
                        if (!/[0-9]/.test(event.key)) {
                            event.preventDefault();
                        }
                    }}
                    defaultValue={events.maxPlayer}
                    name="maxPlayer"
                    placeholder="12"
                    onChange={(event) => setMaxPlayer(event.target.value)}
                />
            </label>
            <label>
                Duration :
                <input
                    type="text"
                    onKeyPress={(event) => {
                        if (!/[0-9]/.test(event.key)) {
                            event.preventDefault();
                        }
                    }}
                    defaultValue={events.duration}
                    name="duration"
                    placeholder="45"
                    onChange={(event) => setDuration(event.target.value)}
                />
                (in minutes)
            </label>

            <label>
                Date :
                <input
                    type="datetime-local"
                    name="startDate"
                    defaultValue={durationslice}
                    onChange={(event) =>
                        setStartDate(event.target.value + ":00Z")
                    }
                />
            </label>

            <label>
                Game :
                <select
                    name="game"
                    onChange={(event) => {
                        const selectedGame = shelf.find(
                            (game) => game.id === parseInt(event.target.value)
                        );
                        setGame(selectedGame);
                        console.log(game);
                    }}
                >
                    <option key="selectGame" disabled value="none">
                        -- select an option --
                    </option>
                    {shelf.map((game, index) =>
                        events.game.id === game.id ? (
                            <option key={index} value={game.id} selected>
                                {game.name}
                            </option>
                        ) : (
                            <option key={index} value={game.id}>
                                {game.name}
                            </option>
                        )
                    )}
                </select>
            </label>

            <label>
                Place :
                <select
                    name="place"
                    onChange={(event) => {
                        const selectedPlace = addressList.find(
                            (address) =>
                                (address.id = parseInt(event.target.value))
                        );
                        setPlace(selectedPlace);
                        console.log(place);
                    }}
                >
                    <option key="selectPlace" disabled selected value="none">
                        -- select an option --
                    </option>
                    {addressList.map((place, index) =>
                        events.place.id === place.id ? (
                            <option key={index} value={place.id} selected>
                                {place.street}, {place.city}
                            </option>
                        ) : (
                            <option key={index} value={place.id}>
                                {place.street}, {place.city}
                            </option>
                        )
                    )}
                </select>
            </label>
            <input type="submit" value="Update" />
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
                    severity={alertMessage.severity}
                >
                    {alertMessage.message}
                </Alert>
            </div>
        );
    }
};

export default EventUpdate;
