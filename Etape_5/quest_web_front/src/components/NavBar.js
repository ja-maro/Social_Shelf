import { Link } from "react-router-dom";

const NavBar = ({ isLog, setIsLog }) => {
    const logout = () => {
        setIsLog(false);
    };

    return isLog ? (
        <nav className="navbar">
            <div className="navbar-left">
                <h2>Quest Web</h2>
                <Link to="/" className="link">
                    <h4>Home</h4>
                </Link>
            </div>

            <div className="navbar-log">
                <Link to="/me" className="link">
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
