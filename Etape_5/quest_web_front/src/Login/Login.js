import "./Login.scss";

function Login({ isLog, setIsLog }) {
    return (
        <div className="login">
            <form>
                <label>
                    Username :
                    <input type="text" name="username" placeholder="John" />
                </label>
                <label>
                    Password :
                    <input type="password" name="password" placeholder="Wick" />
                </label>
                <input type="submit" />
            </form>
            <div>
                No account ? <a href="http://localhost:3000/">Register</a>
            </div>
        </div>
    );
}

export default Login;
