import { useAuth } from "react-oidc-context";
import { Navigate, Link } from "react-router-dom";
import { LogIn, ArrowRight } from 'lucide-react';

export default function LoginPage() {
  const auth = useAuth();

  // If already logged in, go to Dashboard
  if (auth.isAuthenticated) {
    return <Navigate to="/dashboard" replace />;
  }

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8 font-sans">
      <div className="sm:mx-auto sm:w-full sm:max-w-md text-center">
        <Link to="/" className="inline-flex items-center gap-2 text-2xl font-bold tracking-tight text-gray-900 mb-6">
           <div className="h-8 w-8 bg-blue-600 rounded-lg flex items-center justify-center text-white text-lg">AI</div>
           SalesStore
        </Link>
        <h2 className="text-3xl font-bold text-gray-900">Welcome Back</h2>
        <p className="mt-2 text-gray-600">Secure Enterprise Access</p>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-[400px]">
        <div className="bg-white py-8 px-4 shadow-lg sm:rounded-xl border border-gray-100 sm:px-10">
          
          <div className="space-y-6">
            <div className="bg-blue-50 border border-blue-100 rounded-lg p-4 text-sm text-blue-800">
                You will be redirected to our secure identity provider to complete your sign-in.
            </div>

            <button
              onClick={() => auth.signinRedirect()}
              className="w-full flex justify-center items-center gap-2 py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-semibold text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-all"
            >
              <LogIn size={18} />
              Continue to Secure Login
            </button>

            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-gray-200" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-white text-gray-500">New to SalesStore?</span>
              </div>
            </div>

            <Link
                to="/signup"
                className="w-full flex justify-center py-2.5 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 transition-all"
            >
                Create Account
            </Link>
          </div>

        </div>
      </div>
    </div>
  );
}