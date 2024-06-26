import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () =>{return useContext(AuthContext)};

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const authData = localStorage.getItem('isLoggedIn');
    setIsLoggedIn(authData === 'true');
  }, []);

  const login = (userObj) => {
    const userStr = JSON.stringify(userObj);
    localStorage.setItem('TOKEN',userObj.token)
    setIsLoggedIn(true);
    console.log(userObj)
    localStorage.setItem('isLoggedIn', 'true');
    localStorage.setItem('user', userStr)
  };

  const logout = () => {
    setIsLoggedIn(false);
    localStorage.removeItem('isLoggedIn');
    console.log("get");
    localStorage.removeItem('user')
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

