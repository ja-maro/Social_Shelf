import { useEffect, useState } from "react";
import EventsService from "../../services/events.service";
import { useParams, useNavigate } from "react-router-dom";
import EventListItem from "./EventListItem";

const Event = () => {
    const [ready, setReady] = useState(false);
    const [event, setEvent] = useState({});
    const [participantsList, setparticipantsList] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

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
            <button onClick={() => navigate("/events/mine")}>Back to my events</button>
            <EventListItem key={event.id} event={event} />
            <br />
            <h2>Participants : </h2>
            {participantsList.map((player) => (
                    <div key={player.id}>
                        {player.username}
                    </div>
                ))}
            <br />
        </div>
    );
};

export default Event;
