import { useEffect, useState, useContext } from "react";
import GameListItem from "../games/GameListItem";
import ShelfService from "../../services/shelf.service";
import { DataContext } from "../DataContext";
import { useNavigate } from "react-router-dom";

const AddShelf = () => {
    const [games, setGames] = useState([]);

    useEffect(() => {
        ShelfService.getNotOwned().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGames(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    }, []);

    const context = useContext(DataContext);
    let navigate = useNavigate();

    const handleClickGame = (game) => {
        setGames([]);
    };

    return (
        <div className="wrapper">
           
            <h1>Games available :</h1>
            <ul>
                {games.map((game) => (
                    <div key={game.id}>
                        <GameListItem key={game.id} game={game} />
                            <button onClick={() => handleClickGame(game)}>
                                Add
                            </button>
                    </div>
                ))}
            </ul>
        </div>
    );
};

export default AddShelf;