
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

const API = {
  signup,
  login,
};

export default API;