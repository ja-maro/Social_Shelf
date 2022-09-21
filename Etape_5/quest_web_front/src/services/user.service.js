import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8090/";

const getUserDetails = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "me", { headers: { Authorization: "Bearer " + token } })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.response;
        });
    return response;
};

const UserService = {
    getUserDetails,
};

export default UserService;
