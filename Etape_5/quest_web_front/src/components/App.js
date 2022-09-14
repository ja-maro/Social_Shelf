import "../styles/App.scss";
import Login from "./Login";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

function App() {
    return (
        <div>
            <BrowserRouter>
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
                <Routes>
                    <Route path="/login" element={<Login />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
