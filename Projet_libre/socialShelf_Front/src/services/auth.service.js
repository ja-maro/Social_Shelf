import axios from "axios";

const API_URL = "http://localhost:8090/";

const register = async (username, email, password) => {
    let response;
    await axios
        .post(API_URL + "register", {
            username,
            email,
            password,
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

const login = async (username, password) => {
    let response;
    await axios
        .post(API_URL + "authenticate", {
            username,
            password,
        })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.toJSON();
        });
    if (response.status === 200) {
        localStorage.setItem("token", response.data.token);
    }
    return response;
};

const logout = () => {
    console.log(localStorage.getItem("token"));
    localStorage.removeItem("token");
};

const AuthService = {
    register,
    login,
    logout,
};

export default AuthService;
