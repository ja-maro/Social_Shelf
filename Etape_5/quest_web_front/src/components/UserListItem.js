import { useEffect, useState } from "react";
import AuthService from "../services/auth.service";
import UserService from "../services/user.service";

const UserListItem = ({ item }) => (
    <div>
        <div>{item.user_id}</div>
        <div>{item.username}</div>
        <div>{item.role}</div>
        <br />
    </div> 
);

export default UserListItem;