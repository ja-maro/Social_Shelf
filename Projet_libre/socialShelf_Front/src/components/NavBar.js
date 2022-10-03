import React from "react";
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";
import { DataContext } from "./DataContext";

const NavBar = () => {
    const logout = () => {
        AuthService.logout();
        setIsLog(false);
    };
    const context = React.useContext(DataContext);
    const { setIsLog } = React.useContext(DataContext);

    return context.isLog ? (
        <nav className="navbar">
            <div className="navbar-left">
                <h2>Quest Web</h2>
                <Link to="/" className="link">
                    <h4>Home</h4>
                </Link>
                <Link to="/users" className="link">
                    <h4>Users</h4>
                </Link>
                <Link to="/address" className="link">
                    <h4>Addresses</h4>
                </Link>
            </div>

            <div className="navbar-log">
                <Link to="/profile" className="link">
                    <h4>Profile</h4>
                </Link>
                <Link to="/login" className="link" onClick={logout}>
                    <h4>Logout</h4>
                </Link>
            </div>
        </nav>
    ) : (
        <nav className="navbar">
            <div className="navbar-left">
                <h2>Quest Web</h2>
                <Link to="/" className="link">
                    <h4>Home</h4>
                </Link>
            </div>

            <div className="navbar-log">
                <Link to="/login" className="link">
                    <h4>Login</h4>
                </Link>
                <Link to="/register" className="link">
                    <h4>Register</h4>
                </Link>
            </div>
        </nav>
    );
};

export default NavBar;
