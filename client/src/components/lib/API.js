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

const signup = async (userData, responseHandler) => {
  const endpoint = apiPrefix + "/auth/signup";
  fetch(endpoint, corsRequest("POST", userData))
    .then(res => res.json())
    .then(data => responseHandler(data));
}
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
