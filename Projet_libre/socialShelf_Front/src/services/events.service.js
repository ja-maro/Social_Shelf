import axios from "axios";

const API_URL = "http://localhost:8090/event/";

const getAll = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL, {
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

const getFutureMine = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "future", {
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

const getPastMine = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "past", {
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

const getAllJoin = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "join", {
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
        .get(API_URL + id, {
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
    title,
    pitch,
    minPlayer,
    maxPlayer,
    duration,
    startDate,
    place,
    game,
    organizer
) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .post(
            API_URL,
            {
                title,
                pitch,
                minPlayer,
                maxPlayer,
                duration,
                startDate,
                place,
                game,
                organizer,
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

const EventsService = {
    add,
    getById,
    getAll,
    getPastMine,
    getFutureMine,
    getAllJoin,
};

export default EventsService;
