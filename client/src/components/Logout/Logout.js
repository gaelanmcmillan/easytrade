import React from 'react';
import PropTypes from 'prop-types';

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
              <button class="button" onClick={this.logout}>Sign up</button>
            </div>
        );
    }
}
Logout.propTypes = {
    setToken: PropTypes.func.isRequired
}
export default Logout;
