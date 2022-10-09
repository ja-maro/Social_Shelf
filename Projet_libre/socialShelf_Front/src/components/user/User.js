import { useEffect, useState } from "react";
import UserService from "../../services/user.service";
import { useParams, useNavigate } from "react-router-dom";

const User = () => {
    const [user, setUser] = useState({});
    const { id } = useParams();
    let navigate = useNavigate();

    const handleSubmit = () => {
        console.log("OK");
        UserService.update(user.playerId, user.username, user.role, user.email);
        navigate("/");
    };

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

    function handleDelete(item) {
        console.log("delete id : " + item.playerId);
        UserService.deleteUser(item.playerId);
        navigate("/users");
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        console.log("change");
        console.log(user.username + user.role);
    };

    const form = (
        <div className="update">
            <h1>Détails de l'utilisateur</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Username :
                    <input
                        type="text"
                        name="username"
                        defaultValue={user.username}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Email :
                    <input
                        type="text"
                        name="email"
                        defaultValue={user.email}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Role :  
                    <select
                        name="role"
                        value={user.role}
                        onChange={handleChange}
                    >
                        <option value="ROLE_USER">User</option>
                        <option value="ROLE_ADMIN">Admin</option>
                    </select>
                </label>
                <br />
                <button type="submit">Modify</button>
            </form>
            <button onClick={() => handleDelete(user)}>Delete user</button>
        </div>
    );

    return form;
};

export default User;
