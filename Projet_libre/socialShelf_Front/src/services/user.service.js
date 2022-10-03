import axios from "axios";

const API_URL = "http://localhost:8090/";

const getUserDetails = () => {
    return sendRequest("me");
};

const getAll = () => {
    return sendRequest("player");
};

const getById = (id) => {
    return getRequestParam("player", id);
};

const update = (id, username, role, email) => {
    return putRequest("player/" + id, username, role, email);
};

const deleteUser = async (id) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .delete(API_URL + "player/" + id, {
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

const sendRequest = async (route) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + route, { headers: { Authorization: "Bearer " + token } })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.response;
        });
    return response;
};

const getRequestParam = async (route, id) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + route + "/" + id, {
            headers: { Authorization: "Bearer " + token },
        })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.response;
        });
    return response;
};

const putRequest = async (route, username, role, email) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .put(
            API_URL + route,
            {
                username,
                role,
                email,
            },
            { headers: { Authorization: "Bearer " + token } }
        )
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.toJSON();
        });
    console.log("Update of user");
    console.log(response);
    return response;
};

const UserService = {
    deleteUser,
    getAll,
    getById,
    getUserDetails,
    update,
};

export default UserService;
