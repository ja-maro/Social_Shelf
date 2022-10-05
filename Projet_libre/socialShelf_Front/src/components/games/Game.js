import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import GamesService from "../../services/games.service";

const Game = () => {
    const [game, setGame] = useState({});
    const { id } = useParams();
    const navigate = useNavigate();

    const handleSubmit = () => {
        GamesService.update(
            game.id,
            game.name,
            game.publisher,
            game.description,
            game.minPlayer,
            game.maxPlayer,
            game.averageDuration
        );
        navigate("/");
    };

    useEffect(() => {
        GamesService.getById(id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setGame(response.data);
            } else {
                console.log(response);
            }
        });
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setGame((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        console.log();
    };

    const deleteGame = () => {
        GamesService.deleteGame(game.id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                navigate("/");
            } else {
                console.log(response);
            }
        });
    };

    const form = (
        <div className="update">
            <form onSubmit={handleSubmit}>
                <label>
                    Name :
                    <input
                        type="text"
                        name="name"
                        defaultValue={game.name}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Publisher :
                    <input
                        type="text"
                        name="publisher"
                        placeholder="Ankama"
                        defaultValue={game.publisher}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Description :
                    <textarea
                        type="text"
                        name="description"
                        placeholder="Once upon a time ..."
                        defaultValue={game.description}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Minimum players :
                    <input
                        type="text"
                        name="minPlayer"
                        placeholder="2"
                        defaultValue={game.minPlayer}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Maximum players :
                    <input
                        type="text"
                        name="maxPlayer"
                        placeholder="12"
                        defaultValue={game.maxPlayer}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Average duration :
                    <input
                        type="text"
                        name="averageDuration"
                        placeholder="45"
                        defaultValue={game.averageDuration}
                        onChange={handleChange}
                    />
                    (in minutes)
                </label>
                <input type="submit" value="Modify" />
            </form>
            <button onClick={deleteGame}>Delete</button>
        </div>
    );

    return form;
};

export default Game;
