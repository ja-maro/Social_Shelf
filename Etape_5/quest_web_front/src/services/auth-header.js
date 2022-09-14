function authHeader() {
    const token = JSON.parse(localStorage.getItem("token"));

    if (token) {
        return { Autorization: "Bearer " + token };
    } else {
        return {};
    }
}

export default authHeader;
