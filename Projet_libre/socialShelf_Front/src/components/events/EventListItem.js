const EventListItem = ({ event }) => {

    // const FormatDate = (data) => {
    //     let dateTimeString =
    //       data.getDate() +
    //       '/' +
    //       (data.getMonth() + 1) +
    //       '/' +
    //       data.getFullYear() +
    //       ' ';
    
    //     let hours = data.getHours();
    //     let minutes = data.getMinutes();
    //     minutes = minutes < 10 ? '0' + minutes : minutes;
    //     dateTimeString = dateTimeString + hours + ':' + minutes;
    
    //     return dateTimeString; // 4/5/2021 15:34
    //   };

    return (
        <div>
             {event.cancelDate ? (
                        <h3>{event.title}     (CANCELED ON {event.cancelDate})</h3>
                    ) : (
                        <h3>{event.title}</h3>
                    )}
            <p>{event.startDate}</p>
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
