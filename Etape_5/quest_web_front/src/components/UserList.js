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
        <div>
        {users.map(item => (
            <UserListItem key={item.user_id} item={item} />
        ))}
    </div> 
    );
};

export default Users;