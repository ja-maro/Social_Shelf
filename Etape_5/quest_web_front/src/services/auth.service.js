import axios from "axios";

const API_URL = "http://localhost:8090/";

const register = async (username, password) => {
    const response = await axios.post(API_URL + "register", {
        username,
        password,
    });
    return response.status;
};

const login = async (username, password) => {
    let response;
    await axios
        .post(API_URL + "authenticate", {
            username,
            password,
        })
        .catch(function (error) {
            response = error.response;
        })
        .then((result) => {
            response = result;
        });
    if (response.data.token) {
        localStorage.setItem("token", response.data.token);
    }
    console.log(response);
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
