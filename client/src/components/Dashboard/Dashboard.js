import React from 'react';
import './Dashboard.css';
const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"
function request (data) {
  return {
    credentials: "omit",
    method: "GET",
    mode: "cors",
    headers: { "Authorization":"Bearer " + data.token},
  }
}
/**
 * Send a request to the backend to log the user in.
 *
 * @param {*} userData { email, password }
 * @param {*} responseHandler A callback function to use with the response. Cache username, token, expiry.
 */
const demo = async (demoData, responseHandler) => {
  const endpoint = apiPrefix + "/demo";
  console.log(endpoint);
  fetch(endpoint, request(demoData))
    .then(res => console.log(res))
    .then(data => console.log(data));
}

class Dashboard extends React.Component {
    constructor ( getToken ) {
        super(getToken);
        this.getToken = getToken.getToken;
        const demoData = {
            token: this.getToken(),
        };
        demo(demoData, data => console.log(data));
    }
    render () {
        return (
            <div className='dashboard'>
                <p>####Dashbard####</p>
            </div>
        );
    }
}

export default Dashboard;
