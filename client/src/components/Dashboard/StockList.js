import React from 'react'
import '../../index.css'

function StockList({ stocks }) {
  return (
    <div id='dashboard' class="stockContainer">
        {stocks.map(stock =>{
            if (stock.changeInPercent >= 0){
                return (
            <div class="stock-widget">
                <div class="symbol">${stock.symbol}</div>
                <div class="details">
                  <div class="price">${stock.currentOpen}</div>`
                  <div class="change-plus">%{stock.changeInPercent}%</div>
                </div>
            </div>)}
            else{
                return (
            <div class="stock-widget">
                <div class="symbol">${stock.symbol}</div>
                <div class="details">
                  <div class="price">${stock.currentOpen}</div>`
                  <div class="change-minus">%{stock.changeInPercent}%</div>
                </div>
            </div>)
            }
        }
        )
        }
    </div>
  );
}

export default StockList;
