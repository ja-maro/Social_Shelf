import { useEffect, useState, useContext } from "react";
import GameListItem from "./GameListItem";
import GamesService from "../../services/games.service";
import { DataContext } from "../DataContext";
import { useNavigate } from "react-router-dom";

const GameList = () => {
    const [gameList, setGameList] = useState([]);

    useEffect(() => {
        console.log(context.isAdmin);
        GamesService.getAllGames().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGameList(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    }, []);

    const context = useContext(DataContext);
    let navigate = useNavigate();

    return (
        <div className="wrapper">
            {context.isAdmin === true ? (
                <button onClick={() => navigate("/games/new")}>New game</button>
            ) : (
                <div></div>
            )}
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
