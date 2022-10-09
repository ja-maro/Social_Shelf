import { useEffect, useState, useContext } from "react";
import UserService from "../../services/user.service";
import { DataContext } from "../DataContext";
import { useNavigate } from "react-router-dom";

const Profile = () => {
    const [userDetails, setUserDetails] = useState({
        playerId: "",
        username: "",
        email: "",
        role: "",
    });
    let navigate = useNavigate();

    const context = useContext(DataContext);

    useEffect(() => {
        if (userDetails.username === "") {
            UserService.getUserDetails().then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    console.log(response.data.role);
                    if (response.data.role === "ROLE_ADMIN") {
                        context.setIsAdmin(true);
                    }
                    context.setPlayerId(response.data.playerId);
                    setUserDetails({
                        playerId: response.data.playerId,
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

    function handleClickDetails(item) {
        console.log("id : " + item.playerId);
        navigate(`/users/` + item.playerId);
    }

    return (
        <div className="login">
            <h2>Profile</h2>
            <p>Username : {userDetails.username}</p>
            <p>Email : {userDetails.email}</p>
            <p>Role : {userDetails.role}</p>
            <br />
            <button onClick={() => handleClickDetails(userDetails)}>
                            Edit
                        </button>
        </div>
    );
};

export default Profile;
