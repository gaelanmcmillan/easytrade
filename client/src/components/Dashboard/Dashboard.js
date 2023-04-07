import React from 'react'
import './Dashboard.css';
import Buy from './Buy';
import Sell from './Sell';
import Portfolio from './Portfolio';
import StockList from './StockList';
import Piggybank from "../../assets/piggybank.gif";
import PiggybankFrame from "../../assets/piggybank_frame.png";
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


class Dashboard extends React.Component {
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

    showAnim = () => {
        const target = document.getElementById("piggybank");
        // set to first frame to reset the gif
        target.src = PiggybankFrame;
        // wait a bit while showing first frame
        setTimeout(() => {
            target.src = Piggybank;
        }, 50);
        // display gif for 900 ms
        setTimeout(() => {
            target.src = PiggybankFrame;
        }, 900);
    }

    render () {
        const { isLoading, stocks } = this.state;
        if (isLoading) {
          return <div>Loading...</div>;
        }

        return (
            <div class='containerDashboard'> 
                <div id='dashboard' class='stockContainer'>
                <div>
                <h1>Stocks Dashboard</h1>
                <StockList stocks={stocks} />
                </div>
                </div>
                <img id="piggybank" src={PiggybankFrame} class="width-25 piggybank" />
                <Buy getToken={this.getToken} playAnim={this.showAnim}/>
                <Sell getToken={this.getToken}/>
                <Portfolio getToken={this.getToken}/>
            </div>
        );
    }
}

export default Dashboard;
