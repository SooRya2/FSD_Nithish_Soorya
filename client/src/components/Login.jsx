import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { service } from '../service/FSD.service';
import { useAuth } from './context/AuthContext.jsx';
import GoogleLoginComponent from './GoogleLoginComponent.jsx';
import './Login.css';

const Login = () => {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      localStorage.clear();
      const Service = service();
      const response = await Service.login(name, password);
      console.log("Login successful:", response);
      login(response);
      navigate('/page');
    } catch (error) {
      console.error("Login failed:", error);
      alert('Invalid username or Pasword');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h1 className="login-title">User Login</h1>
        <form className="login-form" onSubmit={handleLogin}>
          <div className="input-group">
            <label htmlFor="name" className="input-label">Name</label>
            <input 
              type="text" 
              id="name" 
              className="input-field" 
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="password" className="input-label">Password</label>
            <input 
              type="password" 
              id="password" 
              className="input-field" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="login-button">Login</button>
        </form>
        <div className="divider">
          <span>or</span>
        </div>
        <h3>Continue with Google</h3>
        <GoogleLoginComponent />
      </div>
    </div>
  );
};

export default Login;
