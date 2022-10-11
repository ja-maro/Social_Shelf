import { useContext, useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import ShelfService from "../../services/shelf.service";
import AddressService from "../../services/address.service";
import { Alert } from "@mui/material";
import { useNavigate } from "react-router-dom";
import "../../styles/Login.scss";
import UserService from "../../services/user.service";
import { DataContext } from "../DataContext";

const EventAdd = () => {
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
    const [organizer, setOrganizer] = useState({});
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });

    useEffect(() => {
        getShelf();
        getAddress();
        getUser();

    }, []);

    const context = useContext(DataContext);

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
                    response.data.filter(address => 
                        address.player.playerId === context.playerId)
                );
                console.log(addressList);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    const getUser = () => {
        UserService.getById(context.playerId).then((res) => {
            setOrganizer(res.data);
        });
    };

    let navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        console.log(game);
        console.log(organizer);
        console.log(place);
        await EventsService.add(
            title,
            pitch,
            minPlayer,
            maxPlayer,
            duration,
            startDate,
            place,
            game,
            organizer
        ).then((res) => (response = res));
        if (response.status === 201) {
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
            <h1 className="title"> New Event </h1>
            <label>
                Title :
                <input
                    type="text"
                    name="title"
                    placeholder="Title ..."
                    onChange={(event) => setTitle(event.target.value)}
                />
            </label>
            <label>
                Pitch :
                <input
                    type="text"
                    name="pitch"
                    placeholder="Pitch ..."
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

                        const selectedGameId = event.target.value;
                        const selectedGame = shelf.filter((d) => d.id == selectedGameId)[0];
                        console.log("event value : " + event.target.value)
                        console.log("filter : " + selectedGame.name);
                        setGame(selectedGame);
                    }}
                >
                    <option key="selectGame" disabled selected value="none">
                        -- select an option --
                    </option>
                    {shelf.map((game) => (
                        <option key={game.id} value={game.id}>
                            {game.name}
                        </option>
                    ))}
                </select>
            </label>

            <label>
                Place :
                <select
                    name="place"
                    onChange={(e) => {
                        const selectedPlaceId = e.target.value;
                        const selectedPlace = addressList.filter((d) => d.id == selectedPlaceId)[0];
                        console.log("event value : " + e.target.value)
                        console.log("filter : " + selectedPlace.street);
                        setPlace(selectedPlace);
                    }}
                >
                    <option key="selectPlace" disabled selected value="none">
                        -- select an option --
                    </option>
                    {addressList.map((place) => (
                        <option key={place.id} value={place.id}>
                            {place.street}, {place.city}
                        </option>
                    ))}
                </select>
            </label>
            <input type="submit" value="Create" />
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

export default EventAdd;
