import React from 'react';
import PropTypes from 'prop-types';
import API from '../lib/API'

import '../../index.css';

class Signup extends React.Component {
    constructor ( setToken ) {
        super(setToken);
        this.setToken = setToken;
    }
    handleSignup = async e => {
        e.preventDefault();

        const userData = {
            firstName: e.target.firstname.value,
            lastName: e.target.lastname.value,
            username: e.target.username.value,
            password: e.target.password.value,
        };

        // This line will store the decoded token
        //signup(userData, data => setToken(extractPayload(data.token)));
        // This line will store the raw token
        API.signup(userData, data => this.setToken.setToken(data.token));
    }
    render(){
        return (
            <div className='container'>
                <form onSubmit={this.handleSignup} class="card width-50 center">
                  <h1 class="center-text">Sign Up</h1>
                <div>
                    <label for="firstname">First Name</label>
                    <input name="firstname" placeholder="John" />
                </div>
                <div>
                    <label for="lastname">Last Name</label>
                    <input name="lastname" placeholder="Smith" />
                </div>
                <div>
                    <label for="username">Username</label>
                    <input name="username" placeholder="username" />
                </div>
                <div>
                    <label for="password">Password</label>
                    <input type="password" name="password" placeholder="" />
                </div>
                <div>
                  <button class="button" type="submit">Sign up</button>
                </div>
                </form>
            </div>
        );
    }
}
Signup.propTypes = {
    setToken: PropTypes.func.isRequired
}
export default Signup;
