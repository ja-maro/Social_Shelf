function authHeader() {
    const token = localStorage.getItem("token");

    if (token) {
        return {
            Autorization: "Bearer " + token,
        };
    } else {
        return {};
    }
}

export default authHeader;
