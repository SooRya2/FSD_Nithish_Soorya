import Login from './components/Login';
import LandingPage from './components/Page'
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import GoogleLoginComponent from './components/GoogleLoginComponent';
import React, { useState } from 'react';
import Gloginpass from './components/Gloginpass';
import UnauthorizedPage from './components/Unauthorized';
import Register from './components/Register';
function App() {
  const [authToken, setAuthToken] = useState(localStorage.getItem('token'));


  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Register />} />
        <Route path='/login' element={<Login/>}/>
        <Route path='/page' element={<LandingPage/>}/>
        <Route path="/glogin" element={<GoogleLoginComponent/>} />
        <Route path='/gpage' element={<Gloginpass/>}/>
        <Route path='/unauthorized' element={<UnauthorizedPage/>}/>
       
      </Routes>
    </BrowserRouter>
  );
}

export default App;
