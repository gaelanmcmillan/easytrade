import React from 'react'
import '../../index.css';

class Questions extends React.Component {
    render () {
        return (
        <div class="center width-50">
          <h1 class="center-text">FAQ</h1>
        <div class="faq-item">
            <h1 id="question">Who are we?</h1>
            <p id="answer">EasyTrade is a sophisticated online stock market trading application that affords users the ability to access and view available stocks, as well as to buy and sell stocks on the market, while providing a seamless and efficient means to monitor their investment portfolio.</p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is High price</h1>
        <p id="answer">
A "high price" in the stock market refers to the price of a stock that has reached a higher value than its previous levels.
This can result from various factors such as positive news about the company, strong financial performance or positive investor sentiment.
It is important to note that a high stock price does not necessarily indicate that the company is performing well or that the stock is a good investment opportunity.
Investors should make informed decisions based on a range of factors such as financial reports, market trends and ongoing developments in the industry.
            </p>

          </div>

          <div class="faq-item">
        <h1 id="question">What is Low price</h1>
        <p id="answer">
A "low price" in the stock market refers to a stock that is being sold at a price much lower than its previous levels.
A low-priced stock may be considered to be undervalued or oversold, but investors should still use caution when considering investing in such stocks.
Generally, low-priced stocks are more volatile and risky than higher-priced stocks, and they may not have the same liquidity or trading volume.
            </p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is Open price?</h1>
        <p id="answer">
Open price is a particular security or financial instrument that begins the trading day.
This price is determined by the supply and demand forces of the market and can be influenced by various factors such as economic news, company reports, or global events.
Open price is an important indicator for investors as it provides a starting point for the day's trading activity and can impact the direction and momentum of market trends.
            </p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is close price?</h1>
        <p id="answer">
A close price is the final price at which a financial asset, such as a stock or commodity, is traded at the end of a trading day.
This price is used to determine the net change in value from the previous trading day, and is also used for financial analysis and forecasting.
            </p>
          </div>
          <div class="faq-item">
        <h1 id="question">What is a share?</h1>
        <p id="answer">
In the stock market, a "share" refers to a unit of ownership to a specific company's stock.
When you own shares of a company, you own a portion of that company's assets, profits, and losses.
As the value of the company's stock fluctuates on the market, the value of your share ownership also rises or falls.
Shares can be bought and sold on various stock exchanges, and investors can make money by buying shares when they are low in price and selling them when their value increases.
            </p>
          </div>

          <div class="faq-item">
        <h1 id="question">What is a security?</h1>
        <p id="answer">
In the stock market, "volume" refers to the total number of shares or contracts that have been traded within a specific period of time (usually a day).
High volume indicates a greater number of traders are buying and selling the stock, while low volume indicates a smaller number of traders trading the stock.
Volume is an important factor for traders as it helps to identify potential price movements and market trends.
            </p>
          </div>
        </div>
        );
    }
}

export default Questions;
