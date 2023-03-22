import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import useToken from './useToken';


function App() {
  const {token, setToken}= useToken();
  if (!token) {
      return (
          <div className="wrapper">
          <h1>EasyTrade</h1>
          <Signup setToken={setToken}/>
          </div>
      );
  }
  return (
    <div className="wrapper">
      <h1>EasyTrade</h1>
      <BrowserRouter>
        <Routes>
          <Route path='/login' element={<Login/>}/ >
        </Routes>
      </BrowserRouter>
      <h2>WELCOME HOME</h2>
    </div>
  );
}

export default App;
