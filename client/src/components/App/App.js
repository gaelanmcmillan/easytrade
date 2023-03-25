import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';


class App extends React.Component {
    state = {
        token: ''
    }
    setToken = (userToken) => {
        this.setState(_ => ({
            token: userToken
        }));
        sessionStorage.setItem('token', JSON.stringify(userToken));
        console.log(userToken)
    };

    getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken;
    }

    render() {
      if (!this.getToken()) {
          return (
              <div className="wrapper">
              <h1>EasyTrade</h1>
              <p>
                <a href='http://localhost:3000/login'>
                Login
                </a>
                -or-
                <a href='http://localhost:3000/signup'>
                Signup
                </a>
              </p>
              <BrowserRouter>
                <Routes>
                  <Route path='/login' element={<Login setToken={this.setToken}/>}/ >
                  <Route path='/signup' element={<Signup setToken={this.setToken}/>}/ >
                </Routes>
              </BrowserRouter>
              </div>
          );
      }
      return (
        <div className="wrapper">
          <h1>EasyTrade</h1>
          <BrowserRouter>
            <Routes>
              <Route path='' element={<h2 A Route/>}/ >
            </Routes>
          </BrowserRouter>
          <h2>WELCOME HOME</h2>
        </div>
      );
    }
}

export default App;
