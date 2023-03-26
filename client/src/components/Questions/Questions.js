import React from 'react'
import '../../index.css';

class Questions extends React.Component {
    render () {
        return (
        <div class="center width-50">
          <h1 class="center-text">FAQ</h1>
        <div class="faq-item">
            <h1 id="question">who are we?</h1>
            <p id="answer">EasyTrade platfrm was developed for the </p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is High price</h1>
        <p id="answer">High price is a stock highest value of the current trading day.</p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is Low price</h1>
        <p id="answer">Low price is a stock lowest value of the current trading day.</p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is Open price?</h1>
        <p id="answer">Every week day, a stock will be traded in the stock market.
             open price represent the initial price of the stock for the day.
             Notice that open price may differ from the closing price of the previous day. </p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is close price?</h1>
        <p id="answer">At the end of every tradying day, when transactions cannot occur,
            the</p>
          </div>
          <div class="faq-item">
        <h1 id="question">What is a share?</h1>
        <p id="answer">A share is a type if a security,
            and is the smallest unit of a company ownership. Shares are traded in the stock market and can be purchase by any buyer.  </p>
          </div>

          <div class="faq-item">
        <h1 id="question">what is a security?</h1>
        <p id="answer">In the investment world a security is a financial instrument, that has a value and can be traded.
            For example, a stock.
        </p>
          </div>
        </div>
        );
    }
}

export default Questions;
