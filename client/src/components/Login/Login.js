import React from 'react';
import './Login.css';
const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"
function corsRequest (method, userData) {
  return {
    method: method,
    mode: "cors",
    headers: { "Content-type": "application/json" },
    body: JSON.stringify(userData)
  }
}
/**
 * Send a request to the backend to log the user in.
 *
 * @param {*} userData { email, password }
 * @param {*} responseHandler A callback function to use with the response. Cache username, token, expiry.
 */
const login = async (userData, responseHandler) => {
  const endpoint = apiPrefix + "/auth/login";
  fetch(endpoint, corsRequest("POST", userData))
    .then(res => res.json())
    .then(data => responseHandler(data));
}

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
        login(userData, data => this.setToken.setToken(data.token));
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
