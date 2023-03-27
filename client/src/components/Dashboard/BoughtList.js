
import React from 'react'
import '../../index.css'

function BoughtList({ userdata }) {
  return (
    <div id='dashboard' class="stockContainer">
        {userdata.investments.map(stock =>(
          <div class="stock-widget">
            <div class="symbol">${stock.stock.symbol}</div>
            <div class="details">
              <div class="price"># of shares: ${stock.quantity}</div>
            </div>
          </div>
        ))}
    </div>
  );
}

export default BoughtList;
