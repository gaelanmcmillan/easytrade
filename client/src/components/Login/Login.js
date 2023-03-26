import React from 'react';
import './Login.css';
import API from '../lib/API'

class Login extends React.Component {
    constructor ( setToken ) {
        super(setToken);
        this.setToken = setToken;
    }
    handleLogin = e => {
        e.preventDefault();

        const userData = {
            username: e.target.username.value,
            password: e.target.password.value,
        };
        API.login(userData, data => this.setToken.setToken(data.token));
        //console.log("Logged in " + JSON.stringify(userData));
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
