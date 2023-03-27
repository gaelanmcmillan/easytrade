import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import Dashboard from '../Dashboard/Dashboard';
import Questions from '../Questions/Questions';
import Logout from '../Logout/Logout';
import Home from '../Home/Home';


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
              <div className="mainwrapper">
               <div class="topnav">
                  <a class="active" href="/">Home</a>
                  <a href="/login">Login</a>
                  <a href="/signup">Signup</a>
                  <a href="/faq">FAQ</a>
                </div>
              <BrowserRouter>
                <Routes>
                  <Route path='/login' element={<Login setToken={this.setToken}/>}/ >
                  <Route path='/signup' element={<Signup setToken={this.setToken}/>}/ >
                  <Route path='/faq' element={<Questions/>}/ >
                  <Route path='*' element={<Home />}/>
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
              <a href="/logout">logout</a>
              <a href="/faq">FAQ</a>
          </div>
          <BrowserRouter>
            <Routes>
              <Route path='/dashboard' element={<Dashboard getToken={this.getToken}/>}/ >
              <Route path='/logout' element={<Logout setToken={this.setToken}/>}/ >
              <Route path='/faq' element={<Questions/>}/ >
              <Route path='*' element={<Home />}/>
            </Routes>
          </BrowserRouter>
        </div>
      );
    }
}

export default App;
