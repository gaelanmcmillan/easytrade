import React from 'react'
import Chart from '../Chart/Chart'
import Coin from "../../assets/coin_small.gif";

//Request function that will get the stocks
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
    //Grab the token such that there is valid authentication
    constructor ( getToken ) {
        super(getToken);
        this.state = {
            isLoading: true,
            stocks: [],
        };
        this.getToken = getToken.getToken;
    }
    // On mount grab stocks from endpoint and set loading to false
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
        //Print loading when loading grabbing data
        if (isLoading) {
          return <div><img src={Coin}></img></div>;
        }

        //Return stocks when the data is recieved
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
