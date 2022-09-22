import axios from "axios";

const API_URL = "http://localhost:8090/";

const getAll = () => {
  return sendRequest("address");
}

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

const AddressService = {
  getAll,
};

export default AddressService;
