import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from "react-oidc-context";
import { Loader2 } from 'lucide-react';

// Pages
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import Dashboard from './pages/Dashboard';
import Shop from './pages/Shop';
import Orders from './pages/Orders';

function App() {
  const auth = useAuth();

  // Global Loading State (while checking Keycloak session)
  if (auth.isLoading) {
    return (
        <div className="h-screen w-screen flex items-center justify-center bg-gray-50">
            <Loader2 className="animate-spin text-blue-600" size={48} />
        </div>
    );
  }

  return (
    <Router>
       <Routes>
         {/* Public Routes */}
         <Route path="/" element={<LandingPage />} />
         
         {/* Custom Entry Pages (Triggers for Keycloak) */}
         <Route path="/login" element={<LoginPage />} />
         <Route path="/signup" element={<SignupPage />} />
         
         {/* Protected Routes */}
         <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
         <Route path="/shop" element={<ProtectedRoute><Shop /></ProtectedRoute>} />
         <Route path="/orders" element={<ProtectedRoute><Orders /></ProtectedRoute>} />
         
         {/* Catch-all */}
         <Route path="*" element={<Navigate to="/" replace />} />
       </Routes>
    </Router>
  );
}

// Secure Route Wrapper
function ProtectedRoute({ children }) {
  const auth = useAuth();
  if (!auth.isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

export default App;