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
    const response = await axios.post(API_URL + "authenticate", {
        username,
        password,
    });
    if (response.data.token) {
        localStorage.setItem("token", JSON.stringify(response.data));
    }
    return response.data;
};

const logout = () => {
    localStorage.removeItem("user");
};

const AuthService = {
    register,
    login,
    logout,
};

export default AuthService;
