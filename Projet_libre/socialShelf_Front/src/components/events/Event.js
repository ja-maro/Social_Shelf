import { useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import { useParams, useNavigate } from "react-router-dom";
import EventListItem from "./EventListItem";

const Event = () => {
    const [event, setEvent] = useState({});
    const { id } = useParams();

    useEffect(() => {
        EventsService.getById(id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setEvent(response.data);
            } else {
                console.log(response);
            }
        });
    }, [id]);


    return (
        <div>
        <h1>Event (details)</h1>
       
                <EventListItem event={event} />
                <br />
                <br />
                <br />
            </div>
    );
};

export default Event;
