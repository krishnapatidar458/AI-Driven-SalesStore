import axios from 'axios';
import { User } from 'oidc-client-ts';

// Point this to your Spring Cloud Gateway
const API_URL = "http://localhost:8080/api/v1"; 

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    // Format: oidc.user:{authority}:{client_id}
    const oidcKey = `oidc.user:http://localhost:8180/realms/sales-store:sales-store-ui`;
    
    // 2. Retrieve string from Session Storage
    const oidcStorage = sessionStorage.getItem(oidcKey);
    
    // 3. Parse and Attach Token
    if (oidcStorage) {
      const user = User.fromStorageString(oidcStorage);
      if (user?.access_token) {
        config.headers.Authorization = `Bearer ${user.access_token}`;
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Define your API calls here
export const fetchProducts = () => api.get('/products');
export const fetchOrders = () => api.get('/orders');
// export const createOrder = (data) => api.post('/orders', data);

export default api;