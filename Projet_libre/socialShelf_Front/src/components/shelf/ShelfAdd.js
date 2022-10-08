import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import ShelfService from "../../services/shelf.service";
import GameListItem from "../games/GameListItem";
import { DataContext } from "../DataContext";

const ShelfAdd = () => {
    const [gamesNotOwned, setGamesNotOwned] = useState([]);
    const navigate = useNavigate();
    const context = useContext(DataContext);

    const handleClickAddGame = async (game) => {
        console.log(game.id);
        await ShelfService.add(game.id).then((response) => {
            console.log(response);
        });
        refresh();
    };

    useEffect(() => {
        refresh();
    }, []);

    const refresh = () => {
        ShelfService.getNotOwned().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGamesNotOwned(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    };

    return (
        <div>
            <h1>Add game to my Shelf</h1> 
            {context.isAdmin === true ? (
                            <div></div>
                        ) : (
                            <div>One of your games is missing ? Ask your favorite admin to add it to the list !</div> 
                        )}
            <button onClick={() => navigate("/shelf")}>Back to my shelf</button>
            <ul>
                {gamesNotOwned.map((game) => (
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
