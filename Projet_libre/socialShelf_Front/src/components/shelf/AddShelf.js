import { useEffect, useState } from "react";
import GameListItem from "../games/GameListItem";
import ShelfService from "../../services/shelf.service";
import { useNavigate } from "react-router-dom";

const AddShelf = () => {
    const [gamesNotOwned, setGamesNotOwned] = useState([]);
    let navigate = useNavigate();

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

    // useEffect(() => {
    //     ShelfService.getNotOwned().then((response) => {
    //         if (response.status === 200) {
    //             console.log(response);
    //             setGamesNotOwned(response.data);
    //         } else if (response.status === 401) {
    //             console.log(response);
    //         }
    //     });
    // }, []);

    const handleClickGame = (game) => {
        ShelfService.add(game.id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGamesNotOwned([]);
                navigate("/shelf");
            } else {
                console.log(response);
            }
        });  
    };

    return (
        <div className="wrapper">
           
            <h1>Games available :</h1>
            <ul>
                {gamesNotOwned.map((game) => (
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