import { useContext, useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import EventListItem from "./EventListItem";
import { useNavigate } from "react-router-dom";
import { DataContext } from "../DataContext";

const MyEventList = () => {
    const [eventList, setEventList] = useState([]);
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
            <h1>Your events to come </h1>
            {eventList.map((event) => (
                <div key={event.id}>
                    <EventListItem key={event.id} event={event} />
                    <br />
                    <button onClick={() => handleClickDetails(event)}>
                        More info
                    </button>
                    {context.playerId === event.organizer.playerId ? (
                        <div></div>
                    ) : (
                        <button
                            onClick={() => {
                                handleClickQuit(event);
                            }}
                        >
                            Quit
                        </button>
                    )}
                    <br />
                    <br />
                </div>
            ))}
        </div>
    );
};

export default MyEventList;
