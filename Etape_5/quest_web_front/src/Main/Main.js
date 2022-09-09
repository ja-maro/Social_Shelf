import Login from "../Login/Login";

function Main({ isLog, setIsLog }) {
    return isLog ? (
        <div>Logged !</div>
    ) : (
        <Login isLog={isLog} setIsLog={setIsLog} />
    );
}

export default Main;
