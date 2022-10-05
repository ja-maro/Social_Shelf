import "../styles/App.scss";
import Login from "./authentication/Login";
import { Routes, Route } from "react-router-dom";
import Register from "./authentication/Register";
import React, { useState } from "react";
import NavBar from "./NavBar";
import Home from "./Home";
import Profile from "./authentication/Profile";
import UserList from "./user/UserList";
import User from "./user/User";
import AddressList from "./address/AddressList";
import CreateAddress from "./address/CreateAddress";
import Address from "./address/Address";
import { DataContext } from "./DataContext";
import GameList from "./games/GameList";
import GameAdd from "./games/GameAdd";
import Game from "./games/Game";

const App = () => {
    const [isLog, setIsLog] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);

    return (
        <div>
            <DataContext.Provider
                value={{ isLog, setIsLog, isAdmin, setIsAdmin }}
            >
                <NavBar />
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/logout" element={<Login />} />
                    <Route path="/" element={<Home />} />
                    <Route path="/profile" element={<Profile />} />
                    <Route path="/users" element={<UserList />} />
                    <Route path="/users/:id" element={<User />} />
                    <Route path="/address/:id" element={<Address />} />
                    <Route path="/address" element={<AddressList />} />
                    <Route path="/createAddress" element={<CreateAddress />} />
                    <Route path="/games" element={<GameList />} />
                    <Route path="/games/new" element={<GameAdd />} />
                    <Route path="/games/:id" element={<Game />} />
                </Routes>
            </DataContext.Provider>
        </div>
    );
};

export default App;
