import { useEffect, useState } from "react";
import AuthService from "../services/auth.service";
import UserService from "../services/user.service";

const Profile = () => {
    const [userDetails, setUserDetails] = useState({
        username: "",
        role: "",
    });
    useEffect(() => {
        if (userDetails.username === "") {
            UserService.getUserDetails().then((response) => {
                setUserDetails({
                    username: response.data.username,
                    role: response.data.role,
                });
            });
        }
    });

    return (
        <div>
            <h2>Profile</h2>
            <p>Username : {userDetails.username}</p>
            <p>Role : {userDetails.role}</p>
        </div>
    );
};

export default Profile;
