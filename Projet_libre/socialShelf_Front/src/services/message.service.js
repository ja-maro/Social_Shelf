import axios from "axios";

const API_URL = "http://localhost:8090/";

const getByEventId = async (eventId) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "message/" + eventId, {
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
    content,
    eventId,
) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .post(
            API_URL + "message/" + eventId,
            {
                content,
            },
            {
                headers: { Authorization: "Bearer " + token },
            }
        )
        .then((result) => {
            response = result;
        });
    return response;
};

const MessageService = { add, getByEventId };

export default MessageService;
