import React from 'react';
import './Dashboard.css';

const hostname = "http://localhost:8080"
const apiPrefix = hostname + "/api/v1"

function request (data) {
  const token = data.token;
  delete data.token;
  return {
    credentials: "omit",
    method: "PATCH",
    mode: "cors",
    headers: { "Authorization":"Bearer " + token, "Content-Type":"application/json"},
    body: JSON.stringify(data)
  }
}

const sell = async (sellData) => {
    const endpoint = apiPrefix + "/stock/sell";
    fetch(endpoint, request(sellData))
        .then(res => {
            if (res.status == 204) {
                let markup = `<p1>-${sellData.quantity}: ${sellData.symbol}</p1><br />`;
                document.getElementById('sellStatus').insertAdjacentHTML('beforeend',markup);
            }
            else
            {
                let markup = `<p1>The sell Failed</p1><br />`;
                document.getElementById('sellStatus').insertAdjacentHTML('beforeend',markup);
            }
        })
        .catch((error) =>{
                let markup = `<h1>FETCH ERROR ${error}</h1><br />`
                document.getElementById('sellStatus').insertAdjacentHTML('beforeend',markup)});
};

class Sell extends React.Component {
    state = {
        stocks: ''
    }
    constructor ( getToken ) {
        super(getToken);
        this.getToken = getToken.getToken;
    }
    handleSell = async e => {
        e.preventDefault();

        const sellData = {
            token: this.getToken(),
            symbol: e.target.symbol.value,
            quantity: e.target.quantity.value,
        };

        // This line will store the decoded token
        //signup(userData, data => setToken(extractPayload(data.token)));
        // This line will store the raw token
        sell(sellData, data => console.log(data));
    }
    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }

    render () {
        return (
            <div className='container'>
                <form onSubmit={this.handleSell} class="card width-50 center">
                  <h1 class="center-text">Sell</h1>
                <div>
                    <label for="symbol">Symbol</label>
                    <input name="symbol" placeholder="AAPL" />
                </div>
                <div>
                    <label for="quantity">Quantity</label>
                    <input name="quantity" placeholder="15" />
                </div>
                <div>
                  <button class="button" type="submit">SELL!</button>
                </div>
                </form>
                <div id='sellStatus'>
                    <h1>LOG</h1>
                </div>
            </div>
        );
    }
}

export default Sell;
