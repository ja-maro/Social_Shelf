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
import AddressList from "./adress/AddressList";
import CreateAddress from "./adress/CreateAddress";
import Address from "./adress/Address";
import { DataContext } from "./DataContext";

const App = () => {
    const [isLog, setIsLog] = useState(false);

    return (
        <div>
            <DataContext.Provider value={{ isLog, setIsLog }}>
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
                </Routes>
            </DataContext.Provider>
        </div>
    );
};

export default App;
