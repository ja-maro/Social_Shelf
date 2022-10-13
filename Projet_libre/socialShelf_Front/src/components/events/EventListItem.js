const EventListItem = ({ event }) => {
    return (
        <div>
            {event.cancelDate ? (
                <h3>
                    {event.title} (CANCELED ON {event.cancelDate})
                </h3>
            ) : (
                <h3>{event.title}</h3>
            )}
            <p>
                Date : {event.startDate} ({event.duration} min)
            </p>
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
