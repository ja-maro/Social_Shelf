import { useContext, useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import EventListItem from "./EventListItem";
import { DataContext } from "../DataContext";

const EventList = () => {
    const [eventList, setEventList] = useState([]);

    const context = useContext(DataContext);

    const handleClickevent = () => {
        console.log("Hello");
    };

    useEffect(() => {
        EventsService.getAll().then((response) => {
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
            <h1>Events</h1>
            {eventList.map((event) => (
                <div key={event.id}>
                    <EventListItem key={event.id} event={event} />
                    {event.organizer.playerId === context.playerId ? (
                        <button onClick={() => handleClickevent(event)}>
                            Modify
                        </button>
                    ) : (
                        <div></div>
                    )}
                    <br />
                    <br />
                    <br />
                </div>
            ))}
        </div>
    );
};

export default EventList;
