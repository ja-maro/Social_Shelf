import { useEffect, useState } from "react";
import UserService from "../services/user.service";

const Profile = () => {
    const [userDetails, setUserDetails] = useState({
        username: "",
        email: "",
        role: "",
    });

    useEffect(() => {
        if (userDetails.username === "") {
            UserService.getUserDetails().then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setUserDetails({
                        username: response.data.username,
                        email: response.data.email,
                        role: response.data.role,
                    });
                } else if (response.status === 401) {
                    console.log(response);
                }
            });
        }
    });

    return (
        <div className="login">
            <h2>Profile</h2>
            <p>Username : {userDetails.username}</p>
            <p>Email : {userDetails.email}</p>
            <p>Role : {userDetails.role}</p>
        </div>
    );
};

export default Profile;
