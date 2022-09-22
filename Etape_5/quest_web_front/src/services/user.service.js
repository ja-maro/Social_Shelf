import axios from "axios";

const API_URL = "http://localhost:8090/";

const getUserDetails = () => {
  return sendRequest("me");
};

const getAll = () => {
  return sendRequest("user");
};

const deleteUser = async (id) => {
  let response;
  const token = localStorage.getItem("token");
  await axios
      .delete(API_URL + "user" + "/" + id, 
        { headers: { Authorization: "Bearer " + token } })
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

const UserService = {
  deleteUser,
  getAll,
  getUserDetails,
};

export default UserService;
