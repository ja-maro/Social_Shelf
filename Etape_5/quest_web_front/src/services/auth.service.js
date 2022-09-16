import axios from "axios";

const API_URL = "http://localhost:8090/";

const register = (username, password) => {
    axios.post(API_URL + "register", {
        username,
        password,
    });
    return login(username, password);
};

const login = (username, password) => {
    return axios
        .post(API_URL + "authenticate", {
            username,
            password,
        })
        .then((response) => {
            if (response.data.token) {
                localStorage.setItem("token", JSON.stringify(response.data));
            }
            return response.data;
        });
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
