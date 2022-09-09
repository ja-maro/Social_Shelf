import "./Banner.scss";

function Banner({ isLog, setIsLog, state, updateState }) {
    return isLog ? (
        <nav className="navbar">
            <h2>Quest Web</h2>
            <div className="navbar-log">
                <button>Profile</button>
                <button onClick={() => setIsLog(false)}>Logout</button>
            </div>
        </nav>
    ) : (
        <nav className="navbar">
            <h2>Quest Web</h2>
            <div className="navbar-log">
                <button>Register</button>
                <button>Login</button>
            </div>
        </nav>
    );
}

export default Banner;
