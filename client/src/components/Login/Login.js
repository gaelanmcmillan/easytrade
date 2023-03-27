import React from "react";
import "../../index.css";
import API from "../lib/API";
import Graph from "../../assets/graph_small.gif";

class Login extends React.Component {
  constructor(setToken) {
    super(setToken);
    this.setToken = setToken;
  }
  handleLogin = (e) => {
    e.preventDefault();

    const userData = {
      username: e.target.username.value,
      password: e.target.password.value,
    };
    API.login(userData, (data) => this.setToken.setToken(data.token));
    //console.log("Logged in " + JSON.stringify(userData));
  };

  render() {
    return (
      <div>
        <div className="container">
          <form onSubmit={this.handleLogin} class="card width-50 center">
            <h1 class="center-text">Log in</h1>
            <div>
              <label for="username">Username</label>
              <input name="username" placeholder="username" />
            </div>
            <div>
              <label for="password">Password</label>
              <input type="password" name="password" placeholder="" />
            </div>
            <button class="button" type="submit">
              Log in
            </button>
          </form>
        </div>
        <div>
          <img src={Graph} class="bottom-right" />
        </div>
      </div>
    );
  }
}

export default Login;
