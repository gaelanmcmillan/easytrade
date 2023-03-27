import React from 'react';
import './Dashboard.css';
const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"
function request (data) {
  return {
    credentials: "omit",
    method: "GET",
    mode: "cors",
    headers: { "Content-Type":"application/json"},
    body: JSON.stringify(data)
  }
}

const portfolio = async (portfolioData) => {
    console.log(portfolioData);
    const endpoint = apiPrefix + "/user/portfolio";
    fetch(endpoint, request(portfolioData))
        .then(res => {return res.json()})
        .then(data => console.log(data))
        .catch((error) =>{
                let markup = `<h1>FETCH ERROR ${error}</h1>`
                document.getElementById('portfolioStatus').insertAdjacentHTML('beforeend',markup)});
};

const extractPayload = (tokenLiteral) => {
  // JWT is of the form `header.payload.signature`
  const base64Payload = tokenLiteral.split('.')[1];
  console.log(`Base64 Payload: ${base64Payload}`)
  const jsonStringPayload = decodeURIComponent(window.atob(base64Payload))
  console.log(`As JSON String: ${jsonStringPayload}`)

  return JSON.parse(jsonStringPayload);
}

class Portfolio extends React.Component {
    getPortfolio = () => {
        const portfolioData = {
            username: extractPayload(this.getToken()).sub
        };
        portfolio(portfolioData);
    };
    constructor ( getToken ) {
        super(getToken);
        this.getToken = getToken.getToken;
        this.getPortfolio();
    }
    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }

    render () {
        return (
            <div id='portfolioStatus' className='container'>
            </div>
        );
    }
}

export default Portfolio;
