import { useContext, useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import EventListItem from "./EventListItem";
import { useNavigate } from "react-router-dom";
import { DataContext } from "../DataContext";

const MyEventList = () => {
    const [eventList, setEventList] = useState([]);
    const [hideCancelled, setHideCancelled] = useState(true);
    let navigate = useNavigate();
    const context = useContext(DataContext);

    function handleClickDetails(event) {
        console.log("id : " + event.id);
        navigate(`/events/` + event.id);
    }

    const handleClickQuit = async (event) => {
        await EventsService.quit(event.id).then((response) => {
            console.log(response);
        });
        refresh();
    };

    const handleClickCancel = async (event) => {
        await EventsService.cancel(event.id).then((response) => {
            console.log(response);
        });
        refresh();
    };

    const handleClickUpdate = async (event) => {
        navigate("/events/update/" + event.id);
    };

    const handleClickToggleCancelled = async () => {
        setHideCancelled(hideCancelled ? false : true);
        refresh();
    };

    useEffect(() => {
        refresh();
    }, []);

    const refresh = () => {
        EventsService.getFutureMine().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setEventList(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    return (
        <div>
             <button onClick={() => navigate("/events/new")}>Create a new event</button>
            <h1>Your events to come </h1>
            <button onClick={() => handleClickToggleCancelled()}>
                {hideCancelled ? "Show" : "Hide"} cancelled
            </button>
            {eventList.map((event) => (
                <div key={event.id}>
                    {hideCancelled && event.cancelDate ? (
                        <div></div>
                    ) : (
                        <div>
                            <EventListItem key={event.id} event={event} />
                            <br />
                            <button onClick={() => handleClickDetails(event)}>
                                More info
                            </button>
                            {context.playerId === event.organizer.playerId ? (
                                <div>
                                    <button
                                        onClick={() => {
                                            handleClickCancel(event);
                                        }}
                                    >
                                        Cancel
                                    </button>
                                    <button
                                        onClick={() => {
                                            handleClickUpdate(event);
                                        }}
                                    >
                                        Update
                                    </button>
                                </div>
                            ) : (
                                <div>
                                    <button
                                        onClick={() => {
                                            handleClickQuit(event);
                                        }}
                                    >
                                        Quit
                                    </button>
                                </div>
                            )}
                            <br />
                            <br />
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
};

export default MyEventList;
