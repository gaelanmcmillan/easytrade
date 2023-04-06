import React from 'react';
import PropTypes from 'prop-types';
import Scale from "../../assets/scale.gif";

import '../../index.css';

class Logout extends React.Component {
    constructor ( setToken ) {
        super(setToken);
        this.setToken = setToken.setToken;
    }
    logout = () => {
        this.setToken(null);
    }
    render(){
        return (
            <div className='container'>
              <button class="button" onClick={this.logout}>Log Out</button>
              <img src={Scale} class="" />
            </div>
        );
    }
}
Logout.propTypes = {
    setToken: PropTypes.func.isRequired
}
export default Logout;
