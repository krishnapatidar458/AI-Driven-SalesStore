import axios from 'axios';

const KEYCLOAK_URL = "http://localhost:8180";
const REALM = "sales-store";
const CLIENT_ID = "sales-store-ui"; 

const authApi = axios.create({
  baseURL: `${KEYCLOAK_URL}/realms/${REALM}/protocol/openid-connect`,
  headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
});

export const login = async (username, password) => {
  const params = new URLSearchParams();
  params.append('client_id', CLIENT_ID);
  params.append('grant_type', 'password'); // Allows username/password flow
  params.append('username', username);
  params.append('password', password);

  try {
    const response = await authApi.post('/token', params);
    
    // Save tokens to browser storage
    const { access_token, refresh_token } = response.data;
    localStorage.setItem('access_token', access_token);
    localStorage.setItem('refresh_token', refresh_token);
    
    return response.data;
  } catch (error) {
    console.error("Login Error:", error.response?.data);
    throw new Error(error.response?.data?.error_description || "Invalid credentials");
  }
};

export const logout = () => {
  localStorage.removeItem('access_token');
  localStorage.removeItem('refresh_token');
  window.location.href = '/login';
};

export const isAuthenticated = () => {
  const token = localStorage.getItem('access_token');
  // In a real app, you'd also check if the token is expired here
  return !!token;
};

// Helper to get user name from token (for Dashboard)
export const getUserInfo = () => {
  const token = localStorage.getItem('access_token');
  if (!token) return { name: "User" };
  
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return { 
      name: payload.given_name || payload.preferred_username || "User",
      email: payload.email
    };
  } catch (e) {
    return { name: "User" };
  }
};