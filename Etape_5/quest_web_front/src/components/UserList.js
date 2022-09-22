import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UserService from "../services/user.service";
import UserListItem from "./UserListItem";



const Users = () => {
    const [users, setUsers] = useState( [] );
    let navigate = useNavigate(); 
    const routeChange = () =>{ 
    let path = `/`; 
    navigate(path);
  }

    useEffect(() => {
        if (!users.length) {
            UserService.getAll().then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setUsers(response.data)
                } else if (response.status === 401) {

                }
            });
        }
    });

    function handleClick(item) {
        console.log("id : " + item.user_id)
        UserService.deleteUser(item.user_id);
        routeChange();
    }

    return(
        <div className="wrapper">
            <h1>User list</h1>
            <ul>
        {users.map(item => (
            <li>
            <UserListItem key={item.user_id} item={item} />
            <button onClick={() => handleClick(item)}>Delete user</button>
            </li>
        ))}
        </ul>
    </div> 
    );
};

export default Users;