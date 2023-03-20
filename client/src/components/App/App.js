import React, { useState } from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';

function App() {
  const [ token, setToken] = useState();
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
          <Route path='/signup' element={<Signup/>}/ >
        </Routes>
      </BrowserRouter>
      <h2>WELCOME HOME</h2>
    </div>
  );
}

export default App;
