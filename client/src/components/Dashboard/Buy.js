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

const buy = async (buyData) => {
    const endpoint = apiPrefix + "/stock/buy";
    fetch(endpoint, request(buyData))
        .then(res => {
            if (res.status == 204) {
                let markup = `<p1>+${buyData.quantity}: ${buyData.symbol}</p1><br />`;
                document.getElementById('buyStatus').insertAdjacentHTML('beforeend',markup);
            }
            else
            {
                let markup = `<p1>The buy Failed</p1><br />`;
                document.getElementById('buyStatus').insertAdjacentHTML('beforeend',markup);
            }
        })
        .catch((error) =>{
                let markup = `<h1>FETCH ERROR ${error}</h1><br />`
                document.getElementById('buyStatus').insertAdjacentHTML('beforeend',markup)});
};

class Buy extends React.Component {
    state = {
        stocks: ''
    }
    constructor ( getToken ) {
        super(getToken);
        this.getToken = getToken.getToken;
    }
    handleBuy = async e => {
        e.preventDefault();

        const buyData = {
            token: this.getToken(),
            symbol: e.target.symbol.value,
            quantity: e.target.quantity.value,
        };

        // This line will store the decoded token
        //signup(userData, data => setToken(extractPayload(data.token)));
        // This line will store the raw token
        buy(buyData, data => console.log(data));
    }
    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }

    render () {
        return (
            <div className='container'>
                <form onSubmit={this.handleBuy} class="card width-50 center">
                  <h1 class="center-text">Buy</h1>
                <div>
                    <label for="symbol">Symbol</label>
                    <input name="symbol" placeholder="AAPL" />
                </div>
                <div>
                    <label for="quantity">Quantity</label>
                    <input name="quantity" placeholder="15" />
                </div>
                <div>
                  <button class="button" type="submit">BUY!</button>
                </div>
                </form>
                <div id='buyStatus'>
                    <h1>LOG</h1>
                </div>
            </div>
        );
    }
}

export default Buy;
