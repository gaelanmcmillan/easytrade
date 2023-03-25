import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import Dashboard from '../Dashboard/Dashboard';


class App extends React.Component {
    state = {
        token: ''
    }
    setToken = (userToken) => {
        this.setState(_ => ({
            token: userToken
        }));
        sessionStorage.setItem('token', JSON.stringify(userToken));
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
               <div class="topnav">
                  <a class="active" href="/">Home</a>
                  <a href="/login">Login</a>
                  <a href="/signup">Signup</a>
                  <a href="/faq">FAQ</a>
                </div>
              <h1>EasyTrade</h1>
              <BrowserRouter>
                <Routes>
                  <Route path='/login' element={<Login setToken={this.setToken}/>}/ >
                  <Route path='/signup' element={<Signup setToken={this.setToken}/>}/ >
                  <Route path='/faq' element={<p hello/>}/ >
                </Routes>
              </BrowserRouter>
              </div>
          );
      }
      return (
        <div className="wrapper">
           <div class="topnav">
              <a class="active" href="/">Home</a>
              <a href="/dashboard">Dashboard</a>
              <a href="/faq">FAQ</a>
          </div>
          <h1>EasyTrade</h1>
          <BrowserRouter>
            <Routes>
              <Route path='' element={<h2 A Route/>}/ >
              <Route path='/dashboard' element={<Dashboard getToken={this.getToken}/>}/ >
            </Routes>
          </BrowserRouter>
        </div>
      );
    }
}

export default App;
