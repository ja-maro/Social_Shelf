import "./App.scss";
import Banner from "../Banner/Banner.js";
import Main from "../Main/Main";
import { useState } from "react";

function App() {
    const [isLog, setIsLog] = useState(false);
    const [state, updateState] = useState(String);

    return (
        <div>
            <Banner
                isLog={isLog}
                setIsLog={setIsLog}
                state={state}
                updateState={updateState}
            />
            <Main isLog={isLog} setIsLog={setIsLog} />
        </div>
    );
}

export default App;
