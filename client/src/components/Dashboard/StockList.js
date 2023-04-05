import React from 'react'
import '../../index.css'

function StockList({ stocks }) {
  return (
    <div id='dashboard' class="stockContainer">
        {stocks.map(stock => {
          const changeInPercentClass = stock.changeInPercent >= 0 ? "change-plus" : "change-minus";

          return (
            <div class="stock-widget">
                <div class="symbol">{stock.symbol}</div>
                <div class="details">
                  <div class="price">${stock.currentOpen}</div>`
                  <div class={changeInPercentClass}>{stock.changeInPercent}%</div>
                </div>
            </div>
          );
        })}
    </div>
  );
}

export default StockList;
