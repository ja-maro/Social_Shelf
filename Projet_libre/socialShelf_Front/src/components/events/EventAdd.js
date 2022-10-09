import { useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import ShelfService from "../../services/shelf.service";
import AddressService from "../../services/address.service";
import { Alert } from "@mui/material";
import { useNavigate } from "react-router-dom";
import "../../styles/Login.scss";


const EventAdd = () => {
    const [title, setTitle] = useState("");
    const [pitch, setPitch] = useState("");
    const [minPlayer, setMinPlayer] = useState("");
    const [maxPlayer, setMaxPlayer] = useState("");
    const [duration, setDuration] = useState("");
    const [startDate, setStartDate] = useState("");
    const [place, setPlace] = useState("");
    const [game, setGame] = useState("");
    const [shelf, setShelf] = useState([]);
    const [addressList, setAddressList] = useState([]);

    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });

    useEffect(() => {
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
                    response.data.sort(
                        (a, b) => a.player.player_id - b.player.player_id
                    )
                );
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    let navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        await EventsService.add(
            title,
            pitch,
            minPlayer,
            maxPlayer,
            duration,
            startDate,
            place,
            game,
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
                    onChange={(event) => setStartDate(event.target.value)}
                />
            </label>

            <label>
                    Game :  
                    <select
                        name="game"
                        value={game}
                        onChange={(event) => setGame(event.target.value)}
                    >
                    {shelf.map((game) => (
                     <option key={game.id} value={game}>{game.name}</option>
                    ))}

                    </select>
                </label>


                <label>
                    Place :  
                    <select
                        name="place"
                        value={place}
                        onChange={(event) => setPlace(event.target.value)}
                    >
                    {addressList.map((place) => (
                     <option key={place.id} value={place}>{place.street} {place.city}</option>
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
