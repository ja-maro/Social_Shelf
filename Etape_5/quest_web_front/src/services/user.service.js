import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8090/";

const getUserDetails = () => {
  return axios.get(API_URL + "me", { headers: authHeader });
};

const userService = {
  getUserDetails,
};

export default userService;
