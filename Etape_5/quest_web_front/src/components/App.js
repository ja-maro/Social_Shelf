import "../styles/App.scss";
import Login from "./Login";
import { Routes, Route } from "react-router-dom";
import Register from "./Register";
import { useState } from "react";
import NavBar from "./NavBar";
import Home from "./Home";
import Profile from "./Profile";
import UserList from "./UserList";
import AddressList from "./AddressList";

const App = () => {
    const [isLog, setIsLog] = useState(false);
    return (
        <div>
            <NavBar isLog={isLog} setIsLog={setIsLog} />
            <Routes>
                <Route path="/login" element={<Login setIsLog={setIsLog} />} />
                <Route path="/register" element={<Register />} />
                <Route path="/logout" element={<Login />} />
                <Route path="/" element={<Home />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/users" element={<UserList />} />
                <Route path="/address" element={<AddressList />} />
            </Routes>
        </div>
    );
};

export default App;
