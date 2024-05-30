import React from 'react';
import './Unauthorized.css';

const UnauthorizedPage = () => {
  return (
    <div className="unauthorized-container">
      <h1 className="unauthorized-title">403 - Unauthorized</h1>
      <p className="unauthorized-message">You do not have permission to view this page.</p>
      <a href="/login" className="back-home-button">Back to Home</a>
    </div>
  );
}

export default UnauthorizedPage;
