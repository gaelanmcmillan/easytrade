import React from 'react';
import PropTypes from 'prop-types';

import './Signup.css';

const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"

/**
 * Returns the payload object as extracted from the given token literal.
 * @param {*} tokenLiteral
 * @returns
 */
const extractPayload = (tokenLiteral) => {
  // JWT is of the form `header.payload.signature`
  const base64Payload = tokenLiteral.split('.')[1];
  console.log(`Base64 Payload: ${base64Payload}`)
  const jsonStringPayload = decodeURIComponent(window.atob(base64Payload))
  console.log(`As JSON String: ${jsonStringPayload}`)

  return JSON.parse(jsonStringPayload);
}

function corsRequest (method, userData) {
  return {
    method: method,
    mode: "cors",
    headers: { "Content-type": "application/json" },
    body: JSON.stringify(userData)
  }
}

/**
 * Send a request to the backend to sign the user up.
 *
 * @param {*} userData { firstName, lastName, email, password }
 * @param {*} responseHandler A callback function to use with the response. Cache username, token, expiry.
 */
const signup = async (userData, responseHandler) => {
  const endpoint = apiPrefix + "/auth/signup";
  fetch(endpoint, corsRequest("POST", userData))
    .then(res => res.json())
    .then(data => responseHandler(data));
}

export default function Signup({ setToken }) {
	const handleSignup = async e => {
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
		signup(userData, data => setToken(data.token));
	};

	return (
		<div className='homeform'>
			<form onSubmit={handleSignup}>
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
					<button type="submit">Sign Up</button>
				</div>
			</form>
		</div>
	);
}
Signup.propTypes = {
    setToken: PropTypes.func.isRequired
}
