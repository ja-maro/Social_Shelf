import { useEffect, useState } from "react";
import GameListItem from "./GameListItem";
import GamesService from "../../services/games.service";

const GameList = () => {
    const [gameList, setGameList] = useState([]);

    useEffect(() => {
        GamesService.getAllGames().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGameList(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    }, []);

    return (
        <div className="wrapper">
            {/* <button onClick={() => navigate(`/createAddress/`)}>
                Add new address
            </button> */}
            <h1>All games</h1>
            <ul>
                {gameList.map((game) => (
                    <div key={game.id}>
                        <GameListItem key={game.id} game={game} />
                        {/* <button onClick={() => handleClickAddress(address)}>
                                Modify
                            </button> */}
                    </div>
                ))}
            </ul>
        </div>
    );
};

export default GameList;
