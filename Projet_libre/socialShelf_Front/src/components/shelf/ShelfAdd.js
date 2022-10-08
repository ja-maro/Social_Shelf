import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ShelfService from "../../services/shelf.service";
import GameListItem from "../games/GameListItem";

const ShelfAdd = () => {
    const [games, setGames] = useState([]);
    const navigate = useNavigate();

    const handleClickAddGame = async (game) => {
        console.log(game.id);
        await ShelfService.add(game.id).then((response) => {
            console.log(response);
        });
        navigate("/shelf");
    };

    useEffect(() => {
        ShelfService.notOwned().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGames(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    }, []);

    return (
        <div>
            <h1>Add game to my Shelf</h1>
            <ul>
                {games.map((game) => (
                    <div key={game.id}>
                        <GameListItem key={game.id} game={game} />
                        <button onClick={() => handleClickAddGame(game)}>
                            Add
                        </button>
                    </div>
                ))}
            </ul>
        </div>
    );
};

export default ShelfAdd;
