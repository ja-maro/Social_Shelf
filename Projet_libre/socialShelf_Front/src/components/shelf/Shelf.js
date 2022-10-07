import { useEffect, useState } from "react";
import GameListItem from "../games/GameListItem";
import ShelfService from "../../services/shelf.service";
import { useNavigate } from "react-router-dom";

const Shelf = () => {
    const [shelf, setShelf] = useState([]);

    useEffect(() => {
        ShelfService.getShelf().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setShelf(response.data);
            } else if (response.status === 401) {
                console.log(response);
            }
        });
    }, []);

    let navigate = useNavigate();

    const handleClickGame = (game) => {
        setShelf([]);
    };

    const handleClickAddGame = () => {
        setShelf(null);
        console.log(shelf);
        navigate("/shelf/add");
    };

    return (
        <div className="wrapper">
            <h1>My Shelf</h1>
            <button onClick={() => handleClickAddGame()}>Add game</button>
            <ul>
                {shelf.map((game) => (
                    <div key={game.id}>
                        <GameListItem key={game.id} game={game} />
                        <button onClick={() => handleClickGame(game)}>
                            Remove
                        </button>
                    </div>
                ))}
            </ul>
        </div>
    );
};

export default Shelf;
