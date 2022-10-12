import { useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import EventListItem from "./EventListItem";
import { useNavigate } from "react-router-dom";

const EventJoinList = () => {
    const [eventList, setEventList] = useState([]);
    let navigate = useNavigate();

    const handleClickJoin = async (event) => {
        console.log("join id : " + event.id);
        await EventsService.join(event.id).then((response) => {
            console.log(response);
        });
        refresh();
    };

    const refresh = () => {
        EventsService.getAllJoin().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setEventList(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    useEffect(() => {
        refresh();
    }, []);

    return (
        <div>
            <h1>Events you can join</h1>
            {eventList.map((event) => (
                <div key={event.id}>
                    <EventListItem key={event.id} event={event} />
                    <button onClick={() => handleClickJoin(event)}>
                        Join event
                    </button>
                    <br />
                    <br />
                </div>
            ))}
        </div>
    );
};

export default EventJoinList;
