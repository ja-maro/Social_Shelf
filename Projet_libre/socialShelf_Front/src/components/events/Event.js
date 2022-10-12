import { useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import { useParams } from "react-router-dom";
import EventListItem from "./EventListItem";

const Event = () => {
    const [ready, setReady] = useState(false);
    const [event, setEvent] = useState({});
    const { id } = useParams();

    useEffect(() => {
        EventsService.getById(id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setEvent(response.data);
                setReady(true);
            } else {
                console.log(response);
            }
        });
    }, []);

    return ready === false ? null : (
        <div>
            <h1>Event (details)</h1>

            <EventListItem key={event.id} event={event} />
            <br />
            <br />
            <br />
        </div>
    );
};

export default Event;
