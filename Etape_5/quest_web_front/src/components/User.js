import { useEffect, useState } from "react";
import UserService from "../services/user.service";
import UserListItem from "./UserListItem";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const User = () => {
    const [user, setUser] = useState({});
    const { id } = useParams();
    let navigate = useNavigate();

    useEffect(() => {
        UserService.getById(id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setUser(response.data);
            } else {
                console.log(response);
            }
        });
    }, [id]);

    function handleClick(item) {
        console.log("delete id : " + item.user_id);
        UserService.deleteUser(item.user_id);
        navigate("/users");
    }

    return (
        <div>
            <h1>Détails de l'utilisateur</h1>
            <UserListItem item={user} />
            <button onClick={() => handleClick(user)}>Delete user</button>
        </div>
    );
};

export default User;
