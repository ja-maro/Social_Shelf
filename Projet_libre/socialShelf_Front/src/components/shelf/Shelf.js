import { useEffect, useState, useContext } from "react";
import GameListItem from "../games/GameListItem";
import ShelfService from "../../services/shelf.service";
import { DataContext } from "../DataContext";
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

    const context = useContext(DataContext);
    let navigate = useNavigate();

    const handleClickGame = (game) => {
        setShelf([]);
    };

    return (
        <div className="wrapper">
           
            <h1>My Shelf</h1>
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