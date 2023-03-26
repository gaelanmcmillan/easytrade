import React from 'react'
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

const allStock = async (demoData) => {
    const endpoint = apiPrefix + "/stock/all";
    fetch(endpoint, request(demoData))
        .then(res => {return res.json()})
        .then(data => {
            console.log(data);
            data.stocks.forEach(stock => {
              let markup = `<div class="stock-widget">
                <div class="symbol">${stock.symbol}</div>
                <div class="details">
                  <div class="price">$${stock.currentOpen}</div>`
                if(stock.changeInPercent < 0){
                  markup += `<div class="change-minus">${stock.changeInPercent}%</div>
                </div>`
                }else{
                  markup += `<div class="change-plus">${stock.changeInPercent}%</div>
                </div>`
                }

                document.getElementById('dashboard').insertAdjacentHTML('beforeend',markup);
            });
        }
        ).catch((error) =>{
                let markup = `<h1>FETCH ERROR ${error}</h1>`
                document.getElementById('dashboard').insertAdjacentHTML('beforeend',markup)});
};

class Dashboard extends React.Component {
    state = {
        stocks: ''
    }
    constructor ( getToken ) {
        super(getToken);
        this.getToken = getToken.getToken;
        const allStockData = {
            token: this.getToken(),
        }
        allStock(allStockData);
    }
    setStocks = (someState) => {
        this.setState(_ => ({
            stocks: someState
        }));
    };

    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }

    render () {
        return (
            <div id='dashboard' class='stockContainer'>
            </div>
        );
    }
}

export default Dashboard;
