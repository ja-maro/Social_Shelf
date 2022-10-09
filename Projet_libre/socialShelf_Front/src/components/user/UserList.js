import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../../services/auth.service";
import UserService from "../../services/user.service";
import UserListItem from "./UserListItem";
import { DataContext } from "../DataContext";

const Users = () => {
    const [users, setUsers] = useState([]);
    let navigate = useNavigate();
    const context = useContext(DataContext);

    useEffect(() => {
        UserService.getAll().then((response) => {
            if (response.status === 200) {
                console.log(response);
                setUsers(response.data);
            } else if (response.status === 401) {
                AuthService.logout();
            }
        });
    }, []);

    function handleClickDetails(item) {
        console.log("id : " + item.playerId);
        navigate(`/users/` + item.playerId);
    }

    return (
        <div className="wrapper">
            <h1>All Players</h1>
            <ul>
                {users.map((item) => (
                    <li key={item.playerId}>
                        <UserListItem key={item.playerId} item={item} />
                        {context.isAdmin === true ? (
                            <button onClick={() => handleClickDetails(item)}>
                            More
                            </button>
                        ) : (
                            <div></div>
                        )}
                        
                        <br />
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Users;
