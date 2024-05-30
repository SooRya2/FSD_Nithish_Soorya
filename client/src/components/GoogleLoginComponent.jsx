import React, { useEffect,useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { service } from '../service/FSD.service';
const GoogleLoginComponent = () => {
  const navigate = useNavigate();
  const CLIENT_ID = '501266156796-rgqs9fmrasgrtcedsie132ku55cg9pdd.apps.googleusercontent.com';
  const [data, setData] = useState([]);
  const handleCredentialResponse = async (response) => {
    try {
      //const result = await fetch(`http://localhost:9090/sso/google-login .......for microservices
      const result = await fetch(`http://localhost:8080/sso/google-login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ token: response.credential }),
      });

      const data = await result.json();
      if (data.token) {
        localStorage.clear();
        localStorage.setItem('TOKEN', data.token);
      
        const Service = service();
      const result = await Service.getpage();
      if(!isLoginPage(result)){
      setData(result);
      navigate('/gpage', { state: { data: result } });
      }
      else{
        console.log("User not authenticated");
        navigate('/unauthorized');
      }
      } else {
        console.error('Login failed: ', data);
      }
    } catch (error) {
      console.error('There was an error!', error);
      alert("User not registered");
    }
  };
  

  useEffect(() => {
    /* global google */
    google.accounts.id.initialize({
      client_id: CLIENT_ID,
      callback: handleCredentialResponse,
    });
    google.accounts.id.renderButton(
      document.getElementById('buttonDiv'),
      { theme: 'outline', size: 'large' }  
    );
  }, []);

  const isLoginPage = (html) => {
    return html.includes('<h2 class="form-signin-heading">Login with OAuth 2.0</h2>');
}

  return (
    <div>
      <div id="buttonDiv"></div>
    </div>
  );
};


export default GoogleLoginComponent;
