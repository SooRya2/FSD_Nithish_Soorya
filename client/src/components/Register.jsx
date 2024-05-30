import React, { useState } from 'react';
import { Link,useNavigate } from 'react-router-dom'; 
import './Register.css'; 
import { service } from '../service/FSD.service';


const Register = () => {
  
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate=useNavigate();

  const handleSubmit = async(e) => {
    e.preventDefault();
    try{
        localStorage.clear();
        const Service=service();
        const response=await Service.adduser(name,password,email);
        console.log(response);
        navigate('/login');
    }
    catch(error)
    {
        console.log("Register Error",error);
    }
  };



  return (
    <div className="register-container">
      <form className="register-form" onSubmit={handleSubmit}>
        <h2 className="form-title">Register </h2>
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
          <label htmlFor="email" className="input-label">Email</label>
          <input 
            type="email" 
            id="email" 
            className="input-field" 
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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
        <button type="submit" className="register-button" >Register</button>
        <p className="login-link">Already registered? <Link to="/login">Login</Link></p>
      </form>
    </div>
  );
};

export default Register;
