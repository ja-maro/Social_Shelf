import { useEffect, useState } from "react";
import AuthService from "../services/auth.service";
import UserService from "../services/user.service";
import UserListitem from "./UserListItem";

const List = [
    {
        "username": "Brice",
        "role": "ROLE_USER",
        "user_id": 1
    },
    {
        "username": "JeanAntoine",
        "role": "ROLE_ADMIN",
        "user_id": 2
    },
    {
        "username": "test",
        "role": "ROLE_USER",
        "user_id": 3
    },
  ];

const UserList = () => (
    <ul>
        {List.map(item => (
            <UserListitem key={item.user_id} item={item} />
        ))}
    </ul>
);

// const Users = () => {
//     const [Users, setUsers] = useState(true);

//     useEffect(() => {

//     });
// };

export default UserList;