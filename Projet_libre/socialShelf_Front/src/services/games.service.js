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
    return response;
};

const getById = async (id) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "game/" + id, {
            headers: { Authorization: "Bearer " + token },
        })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.toJSON();
        });
    return response;
};

const add = async (
    name,
    publisher,
    description,
    minPlayer,
    maxPlayer,
    averageDuration
) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .post(
            API_URL + "game/",
            {
                name,
                publisher,
                description,
                minPlayer,
                maxPlayer,
                averageDuration,
            },
            {
                headers: { Authorization: "Bearer " + token },
            }
        )
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.toJSON();
        });
    return response;
};

const GamesService = { getAllGames, getById, add };

export default GamesService;
