
import React from 'react'
import '../../index.css'

function BoughtList({ userdata }) {
  const add = (a,b) => a+b;
  const formatPrice = (val) => `$${val.toFixed(2)}`;
  const totalPrice = (investment) => investment.stock.currentOpen * investment.quantity;
  userdata.investments.sort((a,b) => totalPrice(b) - totalPrice(a));

  return (
    <div id='dashboard' class="stockContainer">
      <table>
        <thead>
          <tr>
            <th>Stock</th>
            <th>Share Quantity</th>
            <th>Share Value</th>
            <th>Total Value</th>
          </tr>
        </thead>
        <tbody>
          {userdata.investments.map(investment => 
            <tr>
              <td>{investment.stock.symbol}</td>
              <td>{investment.quantity}</td>
              <td>{formatPrice(investment.stock.currentOpen)}</td>
              <td>{formatPrice(totalPrice(investment))}</td>
            </tr>
          )}
        </tbody>
        <tfoot>
          <tr>
            <td>--</td>
            <td>--</td>
            <td>--</td>
            <td>{formatPrice(
              userdata.investments.map(totalPrice).reduce(add, 0)
              )}</td>
          </tr>
        </tfoot>
      </table>
        {/* {userdata.investments.map(stock =>(
          <div class="stock-widget">
            <div class="symbol">${stock.stock.symbol}</div>
            <div class="details">
              <div class="price"># of shares: ${stock.quantity}</div>
            </div>
          </div>
        ))} */}
    </div>
  );
}

export default BoughtList;
