import { useEffect, useState } from "react";
import UserService from "../services/user.service";
import UserListItem from "./UserListItem";

const Users = () => {
    const [users, setUsers] = useState( [] );

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

    return(
        <div className="wrapper">
            <h1>User list</h1>
            <ul>
        {users.map(item => (
            <li>
            <UserListItem key={item.user_id} item={item} />
            </li>
        ))}
        </ul>
    </div> 
    );
};

export default Users;