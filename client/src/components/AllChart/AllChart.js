import React from 'react'
import Chart from '../Chart/Chart'

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


class AllChart extends React.Component {
    constructor ( getToken ) {
        super(getToken);
        this.state = {
            isLoading: true,
            stocks: [],
        };
        this.getToken = getToken.getToken;
    }
    componentDidMount() {
        const demoData = {
            token: this.getToken()
        }
        const endpoint = apiPrefix + "/stock/all";
        fetch(endpoint, request(demoData))
            .then(res => {return res.json()})
            .then(data => {
                console.log(data);
                this.setState({ isLoading: false, stocks: data.stocks });
            })
            .catch((error) =>{
                let markup = `<h1>FETCH ERROR ${error}</h1>`
                document.getElementById('dashboard').insertAdjacentHTML('beforeend',markup)});
    };

    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }
    render () {
        const { isLoading, stocks } = this.state;
        console.log(stocks[0]);
        if (isLoading) {
          return <div>Loading...</div>;
        }

        return (
            <div class='containerDashboard'>
                <div id='dashboard' class='stockContainer'>
                {stocks.map(stock => {return (<div><Chart data={stock}/></div>)})}
                </div>
            </div>
        );
    }
}

export default AllChart;
