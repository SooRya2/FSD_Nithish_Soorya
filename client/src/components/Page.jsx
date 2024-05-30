import React, { useState, useEffect } from 'react';
import { service } from '../service/FSD.service';
import './Page.css';
import UnauthorizedPage from './Unauthorized';

const LandingPage = () => {
  const [data, setData] = useState([]);

  const fetchdata = async () => {
    try {
      const Service = service();
      const result = await Service.getpage();

      
      if (isLoginPage(result)) {
        console.log("User not authorized");
      } else {
     
        setData(result);
      }
    } catch (error) {
      console.log("Fetch Failed", error);
      alert("Fetch Failed");
    }
  }

  const isLoginPage = (html) => {
    
    return html.includes('<h2 class="form-signin-heading">Login with OAuth 2.0</h2>');
  }

  useEffect(() => {
    fetchdata();
  }, []);

  return (
    <div className="landing-page-container">
      {typeof data === 'string' && !isLoginPage(data) ? ( 
        <>
          <header className="header">
            <h1 className="title">{data}</h1>
          </header>
          <main className="content">
        <section className="intro">
          <h2 className="section-title">What is Single Sign-On (SSO)?</h2>
          <p className="section-content">
            Single Sign-On (SSO) is an authentication process that allows a user to access multiple applications with one set of login credentials. This simplifies the user experience by eliminating the need to log in separately to each application.
          </p>
        </section>
        <section className="advantages">
          <h2 className="section-title">Advantages of SSO</h2>
          <ul className="advantages-list">
            <li className="advantage-item">
              <strong>Improved User Experience:</strong> Users only need to log in once to access all their applications, reducing the frustration of multiple logins.
            </li>
            <li className="advantage-item">
              <strong>Increased Security:</strong> Reduces the risk of password fatigue and poor password practices, as users have fewer passwords to remember.
            </li>
            <li className="advantage-item">
              <strong>Centralized Management:</strong> Easier for administrators to manage user access and monitor authentication.
            </li>
            <li className="advantage-item">
              <strong>Reduced IT Costs:</strong> Decreases the number of support calls related to password issues, saving time and resources.
            </li>
          </ul>
        </section>
      </main>
          <footer className="footer">
            <p className="footer-content">&copy; 2024 SSO.</p>
          </footer>
        </>
      ) : <UnauthorizedPage/>}
    </div>
  );
};
 
export default LandingPage;
