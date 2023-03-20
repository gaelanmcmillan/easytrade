import React from 'react';
import './Login.css';

export default function Login() {
    const handleLogin = e => {
        e.preventDefault();

        const userData = {
            username: e.target.email.value,
            password: e.target.password.value,
        };

        //API.login(userData, data => console.log("Logged in " + JSON.stringify(data)));
        console.log("Logged in " + JSON.stringify(userData));
    }

    return (
        <div className='homeform'>
            <form onSubmit={handleLogin}>
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
