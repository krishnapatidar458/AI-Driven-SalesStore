import React from 'react'
import ReactDOM from 'react-dom/client'
import { AuthProvider } from 'react-oidc-context'
import App from './App.jsx'
import './index.css'

const oidcConfig = {
  authority: "http://localhost:8180/realms/sales-store",
  client_id: "sales-store-ui",
  redirect_uri: window.location.origin + "/",
  post_logout_redirect_uri: window.location.origin + "/", 
  
  response_type: "code", 
  scope: "openid profile email roles",
  automaticSilentRenew: true,
  onSigninCallback: () => {
      window.history.replaceState({}, document.title, window.location.pathname);
  }
};

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AuthProvider {...oidcConfig}>
      <App />
    </AuthProvider>
  </React.StrictMode>,
)