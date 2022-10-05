const GameListItem = ({ game }) => {
    return (
        <li>
            <div className="game">
                <div className="game_details">
                    <div>{game.id}</div>
                    <div>
                        {game.name} ({game.publisher})
                    </div>
                    <div>{game.description}</div>
                    <div>
                        Players : from {game.minPlayer} to {game.maxPlayer}
                    </div>
                    <div>Average duration : {game.averageDuration} min</div>
                    <div>Type(s) :</div>
                    <ul>
                        {game.gameType.map((type) => (
                            <li>{type.name}</li>
                        ))}
                    </ul>
                    <br />
                </div>
            </div>
        </li>
    );
};

export default GameListItem;
