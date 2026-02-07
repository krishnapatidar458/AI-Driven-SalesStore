import { useAuth } from "react-oidc-context";
import { Navigate, Link } from "react-router-dom";
import { UserPlus, ShieldCheck } from 'lucide-react';

export default function SignupPage() {
  const auth = useAuth();

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
        <h2 className="text-3xl font-bold text-gray-900">Create Account</h2>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-[400px]">
        <div className="bg-white py-8 px-4 shadow-lg sm:rounded-xl border border-gray-100 sm:px-10">
          
          <div className="space-y-6">
            <div className="flex items-start gap-3 text-sm text-gray-600">
                <ShieldCheck className="text-green-600 flex-shrink-0" size={20} />
                <p>Your account is protected by Enterprise-grade OIDC security. We do not store passwords locally.</p>
            </div>

            <button
              onClick={() => auth.signinRedirect({ extraQueryParams: { kc_action: 'register' } })} 
              // Note: 'kc_action: register' tries to open the Register tab directly if supported by your Keycloak version
              className="w-full flex justify-center items-center gap-2 py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-semibold text-white bg-gray-900 hover:bg-black focus:outline-none transition-all"
            >
              <UserPlus size={18} />
              Register Now
            </button>

            <p className="text-xs text-center text-gray-500">
                By clicking Register, you agree to our Terms of Service and Privacy Policy.
            </p>

            <div className="border-t border-gray-100 pt-6 text-center">
               <p className="text-sm text-gray-600">
                 Already have an account? <Link to="/login" className="font-semibold text-blue-600 hover:underline">Sign in</Link>
               </p>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}