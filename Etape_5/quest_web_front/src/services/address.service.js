import axios from "axios";

const API_URL = "http://localhost:8090/";

const getAll = () => {
  return getRequest("address");
}

const create = (street, postalCode, city, country) => {
    return postRequest("address", street, postalCode, city, country);
}

const getRequest = async (route) => {
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

const postRequest = async (route, street, postalCode, city, country) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .post(API_URL + route, {
            street,
            postalCode,
            city,
            country,
        }, { headers: { Authorization: "Bearer " + token } })
        .then((result) => {
            response = result;
        })
        .catch((error) => {
            response = error.toJSON();
        });
    console.log(response);
    return response;
};

const AddressService = {
    create,
    getAll,
};

export default AddressService;
