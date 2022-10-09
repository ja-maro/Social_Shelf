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

const EventsService = { getAll };

export default EventsService;
