import React from 'react';
import '../../index.css';
import Logo from '../../assets/logo.png';

class Home extends React.Component {
    render () {
    return (
                <div class="container">
                  <div class="">
                    <img src={Logo} class="image" />
                  </div>
                  <div>
                    <h1 class="bold-text padding-bottom-10">
                      Practice trading, perfect your strategy
                    </h1>
                    <p class="padding-bottom-10">
                      EasyTrade is a virtual stock market simulator that enables users to
                      practice trading stocks using real-time market data without any
                      financial risk.
                    </p>
                    <a href="#" class="button">Get Started</a>
                  </div>
                </div>
    );
}
}

export default Home;
