const EventListItem = ({ event }) => {
    return (
        <div>
            <h3>Title : {event.title}</h3>
            <p>{event.pitch}</p>
            <p>Game : {event.game.name}</p>
            <p>
                Players : {event.minPlayer} to {event.maxPlayer}
            </p>
            <p>Organizer : {event.organizer.username}</p>
            <p>
                Place : {event.place.street} {event.place.postalCode}{" "}
                {event.place.city}
            </p>
        </div>
    );
};

export default EventListItem;
