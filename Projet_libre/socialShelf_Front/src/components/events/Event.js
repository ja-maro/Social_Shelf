import { useEffect, useState, useContext } from "react";
import EventsService from "../../services/events.service";
import { DataContext } from "../DataContext";
import { useParams, useNavigate } from "react-router-dom";
import EventListItem from "./EventListItem";

const Event = () => {
    const [event, setEvent] = useState({});
    const { id } = useParams();
    let navigate = useNavigate();
    const context = useContext(DataContext);

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
        <h1>Event</h1>
       
                <EventListItem event={event} />
                <br />
                <br />
                <br />
            </div>
    );
};

export default Event;




