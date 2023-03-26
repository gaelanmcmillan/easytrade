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
  fetch(endpoint, request(demoData))
    .then(res => responseHandler(res))
    .then(data => console.log(data.text()));
}

class Dashboard extends React.Component {
    state = {
        stocks: ''
    }
    setStocks = (someState) => {
        console.log(someState);
        this.setState(_ => ({
            stocks: someState
        }));
    };

    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }
    constructor ( getToken ) {
        super(getToken);
        this.getToken = getToken.getToken;
        const demoData = {
            token: this.getToken(),
        };
        demo(demoData, data => this.setStocks(data));
    }
    render () {
        return (
            <div className='dashboard'>
                <p>####Dashbard####</p>
                <p> {this.state.stocks} </p>
            </div>
        );
    }
}

export default Dashboard;
