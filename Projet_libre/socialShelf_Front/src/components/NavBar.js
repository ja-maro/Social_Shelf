import React from "react";
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";
import { DataContext } from "./DataContext";

const NavBar = () => {
    const logout = () => {
        AuthService.logout();
        context.setIsLog(false);
        context.setIsAdmin(false);
    };
    const context = React.useContext(DataContext);

    return (
        <DataContext.Consumer>
            {(context) =>
                context.isLog ? (
                    <nav className="navbar">
                        <div className="navbar-left">
                            <Link to="/" className="link">
                                <h2>Social Shelf</h2>
                            </Link>
                            <Link to="/users" className="link">
                                <h4>Players</h4>
                            </Link>
                            <Link to="/games" className="link">
                                <h4>Game catalog</h4>
                            </Link>
                            <Link to="/shelf" className="link">
                                <h4>My shelf</h4>
                            </Link>
                            <Link to="/events/join" className="link">
                                <h4>Events (Join)</h4>
                            </Link>
                            <Link to="/events/mine" className="link">
                                <h4>My Events</h4>
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
                            <Link to="/" className="link">
                                <h2>Social Shelf</h2>
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
                )
            }
        </DataContext.Consumer>
    );
};

export default NavBar;
