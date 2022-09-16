import "../styles/App.scss";
import Login from "./Login";
import { Routes, Route } from "react-router-dom";
import Register from "./Register";
import { useState } from "react";
import NavBar from "./NavBar";

function App() {
    const [isLog, setIsLog] = useState(false);

    return (
        <div>
            <NavBar isLog={isLog} />
            <Routes>
                <Route path="/login" element={<Login setIsLog={setIsLog} />} />
                <Route path="/register" element={<Register />} />
                <Route path="/logout" element={<Login />} />
            </Routes>
        </div>
    );
}

export default App;
