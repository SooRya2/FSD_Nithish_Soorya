import React from 'react';
import ReactDOM from 'react-dom/client';
import { AuthProvider } from './components/context/AuthContext';
import App from './App';
import ErrorBoundary from './ErrorBoundary';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ErrorBoundary>
    <AuthProvider>
    <App />
    </AuthProvider>
    </ErrorBoundary>
  </React.StrictMode>
);

