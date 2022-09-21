import { useEffect, useState } from "react";
import AuthService from "../services/auth.service";
import UserService from "../services/user.service";

const Profile = () => {
    const [userDetails, setUserDetails] = useState({
        username: "",
        role: "",
    });
    const [isDisconnected, setIsDisconnected] = useState(true);

    useEffect(() => {
        if (userDetails.username === "") {
            UserService.getUserDetails().then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setUserDetails({
                        username: response.data.username,
                        role: response.data.role,
                    });
                } else if (response.status === 401) {
                }
            });
        }
    });

    return (
        <div className="login">
            <h2>Profile</h2>
            <p>Username : {userDetails.username}</p>
            <p>Role : {userDetails.role}</p>
        </div>
    );
};

export default Profile;
