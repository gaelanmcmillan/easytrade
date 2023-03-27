import React from 'react';
import BoughtList from './BoughtList'
import './Dashboard.css';
const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"
function request () {
  return {
    credentials: "omit",
    method: "GET",
    mode: "cors",
    headers: { "Content-Type":"application/json"},
  }
}


const extractPayload = (tokenLiteral) => {
  // JWT is of the form `header.payload.signature`
  const base64Payload = tokenLiteral.split('.')[1];
  console.log(`Base64 Payload: ${base64Payload}`)
  const jsonStringPayload = decodeURIComponent(window.atob(base64Payload))
  console.log(`As JSON String: ${jsonStringPayload}`)

  return JSON.parse(jsonStringPayload);
}

class Portfolio extends React.Component {
    componentDidMount() {
        const portfolioData = {
            username: extractPayload(this.getToken()).sub
        };
        console.log(portfolioData);
        const endpoint = apiPrefix + "/user/portfolio/"+portfolioData.username;
        fetch(endpoint, request(portfolioData))
            .then(res => {return res.json()})
            .then(data => {
                console.log(data);
                this.setState({ isLoading: false, userData: data });
            })
            .catch((error) =>{
                    let markup = `<h1>FETCH ERROR ${error}</h1>`
                    document.getElementById('portfolioStatus').insertAdjacentHTML('beforeend',markup)});
    };
    constructor ( getToken ) {
        super(getToken);
        this.state = {
            isLoading: true,
            userData: [],
        };
        this.getToken = getToken.getToken;
    }
    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }

    render () {
        const { isLoading, userData } = this.state;
        if (isLoading) {
          return <div>Loading...</div>;
        }
        return (
            <div id='portfolioStatus' className='container'>
            <BoughtList userdata={userData}/>
            </div>
        );
    }
}

export default Portfolio;
