import axios from "axios";

const API_URL = "http://localhost:8090/";

const getShelf = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "shelf", {
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

const getNotOwned = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "shelf/notowned", {
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



const add = async (id) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .put(API_URL + "shelf/" + id, {
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



const remove = async (id) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .delete(API_URL + "shelf/" + id, {
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

const GamesService = { getShelf, getNotOwned, add, remove };

export default GamesService;
