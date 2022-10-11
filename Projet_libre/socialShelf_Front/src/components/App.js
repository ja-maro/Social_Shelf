import "../styles/App.scss";
import Login from "./authentication/Login";
import { Routes, Route } from "react-router-dom";
import Register from "./authentication/Register";
import React, { useState } from "react";
import NavBar from "./NavBar";
import Home from "./Home";
import NotFound from "./NotFound";
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
import Shelf from "./shelf/Shelf";
import ShelfAdd from "./shelf/ShelfAdd";
import EventList from "./events/EventList";
import Event from "./events/Event";
import MyEventList from "./events/MyEventList";
import MyPastEventList from "./events/MyPastEventList";
import EventJoinList from "./events/EventJoinList";
import EventAdd from "./events/EventAdd";

const App = () => {
    const [isLog, setIsLog] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const [playerId, setPlayerId] = useState(0);

    return (
        <div>
            <DataContext.Provider
                value={{
                    isLog,
                    setIsLog,
                    isAdmin,
                    setIsAdmin,
                    playerId,
                    setPlayerId,
                }}
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
                    <Route path="/shelf" element={<Shelf />} />
                    <Route path="/shelf/add" element={<ShelfAdd />} />
                    <Route path="/events" element={<EventList />} />
                    <Route path="/events/:id" element={<Event />} />
                    <Route path="/events/new" element={<EventAdd />} />
                    <Route path="/events/mine" element={<MyEventList />} />
                    <Route path="/events/past" element={<MyPastEventList />} />
                    <Route path="/events/join" element={<EventJoinList />} />
                    <Route path="*" element={<NotFound />} />
                </Routes>
            </DataContext.Provider>
        </div>
    );
};

export default App;
