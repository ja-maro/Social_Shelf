import axios from "axios";

const API_URL = "http://localhost:8090/";

const getAllGames = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "game", {
            headers: { Authorization: "Bearer " + token },
        })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.toJSON();
        });
    console.log(response);
    return response;
};

const GamesService = { getAllGames };

export default GamesService;
