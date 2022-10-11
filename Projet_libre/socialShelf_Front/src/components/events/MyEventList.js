import { useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import EventListItem from "./EventListItem";
import { useNavigate } from "react-router-dom";

const MyEventList = () => {
    const [eventList, setEventList] = useState([]);
    let navigate = useNavigate();

    function handleClickDetails(event) {
        console.log("id : " + event.id);
        navigate(`/events/` + event.id);
    }

    useEffect(() => {
        EventsService.getFutureMine().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setEventList(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    }, []);

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
                    <br />
                    <br />
                </div>
            ))}
        </div>
    );
};

export default MyEventList;