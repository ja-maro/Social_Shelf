import axios from "axios";

const API_URL = "http://localhost:8090/";

const getShelf = async () => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .get(API_URL + "shelf", {
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

// const add = async (
//     name,
//     publisher,
//     description,
//     minPlayer,
//     maxPlayer,
//     averageDuration
// ) => {
//     let response;
//     const token = localStorage.getItem("token");
//     await axios
//         .post(
//             API_URL + "game/",
//             {
//                 name,
//                 publisher,
//                 description,
//                 minPlayer,
//                 maxPlayer,
//                 averageDuration,
//             },
//             {
//                 headers: { Authorization: "Bearer " + token },
//             }
//         )
//         .then((result) => {
//             response = result;
//         })
//         .catch((error) => {
//             response = error.toJSON();
//         });
//     return response;
// };

// const update = async (
//     id,
//     name,
//     publisher,
//     description,
//     minPlayer,
//     maxPlayer,
//     averageDuration
// ) => {
//     let response;
//     const token = localStorage.getItem("token");
//     await axios
//         .put(
//             API_URL + "game/" + id,
//             {
//                 name,
//                 publisher,
//                 description,
//                 minPlayer,
//                 maxPlayer,
//                 averageDuration,
//             },
//             {
//                 headers: { Authorization: "Bearer " + token },
//             }
//         )
//         .then((result) => {
//             response = result;
//         })
//         .catch((error) => {
//             response = error.toJSON();
//         });
//     return response;
// };

const remove = async (id) => {
    let response;
    const token = localStorage.getItem("token");
    await axios
        .delete(API_URL + "shelf/" + id, {
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

const GamesService = { getShelf, 
    // add, 
    remove };

export default GamesService;
