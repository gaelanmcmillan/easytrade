import React from 'react';
import './Login.css';

class Login extends React.Component {
    handleLogin = e => {
        e.preventDefault();

        const userData = {
            username: e.target.username.value,
            password: e.target.password.value,
        };

        //API.login(userData, data => JSON.stringify(data));
        console.log("Logged in " + JSON.stringify(userData));
    }

    render () {
    return (
        <div className='homeform'>
        <p>####Login####</p>
            <form onSubmit={this.handleLogin}>
                <div>
                    <input name="username" placeholder="username" />
                </div>
                <div>
                    <input type="password" name="password" placeholder="" />
                </div>
                <div>
                    <button type="submit">Log In</button>
                </div>
            </form>
        </div>
    );
}
}

export default Login;
