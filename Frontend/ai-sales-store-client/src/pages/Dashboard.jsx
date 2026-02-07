import { useState } from 'react';
import { useAuth } from "react-oidc-context";
import { Link, useLocation } from "react-router-dom";
import { 
  LogOut, LayoutDashboard, ShoppingBag, History, 
  Settings, User, Menu, X 
} from 'lucide-react';

export default function Dashboard() {
  const auth = useAuth();
  const location = useLocation();
  const [isSidebarOpen, setSidebarOpen] = useState(false);
  
  const userProfile = auth.user?.profile;
  const userName = userProfile?.given_name || userProfile?.preferred_username || "User";

  return (
    <div className="flex h-screen bg-gray-50 overflow-hidden">
      
      {/* Mobile Sidebar Overlay */}
      {isSidebarOpen && (
        <div 
          className="fixed inset-0 bg-gray-900/50 z-40 md:hidden backdrop-blur-sm transition-opacity"
          onClick={() => setSidebarOpen(false)}
        />
      )}

      {/* Sidebar Navigation */}
      <aside className={`
        fixed md:static inset-y-0 left-0 z-50 w-64 bg-white border-r border-gray-200 transform transition-transform duration-300 ease-in-out flex flex-col
        ${isSidebarOpen ? "translate-x-0" : "-translate-x-full"} 
        md:translate-x-0
      `}>
        <div className="p-6 flex items-center justify-between">
           <Link to="/" className="font-bold text-xl tracking-tight text-gray-900">
             Sales<span className="text-blue-600">Store</span>
           </Link>
           <button onClick={() => setSidebarOpen(false)} className="md:hidden text-gray-500">
             <X size={24} />
           </button>
        </div>

        <nav className="flex-1 px-3 space-y-1 overflow-y-auto">
          <NavItem to="/dashboard" icon={<LayoutDashboard size={20} />} label="Overview" active={location.pathname === '/dashboard'} onClick={() => setSidebarOpen(false)} />
          <NavItem to="/shop" icon={<ShoppingBag size={20} />} label="Shop" active={location.pathname === '/shop'} onClick={() => setSidebarOpen(false)} />
          <NavItem to="/orders" icon={<History size={20} />} label="Orders" active={location.pathname === '/orders'} onClick={() => setSidebarOpen(false)} />
          <div className="my-4 border-t border-gray-100" />
          <NavItem to="#" icon={<Settings size={20} />} label="Settings" onClick={() => setSidebarOpen(false)} />
        </nav>

        <div className="p-4 border-t border-gray-100">
           {/* ✅ FIX: Use signoutRedirect() to kill Keycloak Session */}
           <button 
             onClick={() => auth.signoutRedirect()} 
             className="flex items-center gap-3 w-full px-3 py-2 text-sm font-medium text-gray-600 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
           >
             <LogOut size={18} />
             Sign Out
           </button>
        </div>
      </aside>

      {/* Main Content */}
      <div className="flex-1 flex flex-col min-w-0 h-full">
        <header className="bg-white border-b border-gray-200 px-4 sm:px-8 py-4 flex items-center justify-between sticky top-0 z-10">
          <div className="flex items-center gap-4">
            <button onClick={() => setSidebarOpen(true)} className="md:hidden p-2 -ml-2 text-gray-600 hover:bg-gray-100 rounded-lg">
              <Menu size={24} />
            </button>
            <div>
              <h1 className="text-xl sm:text-2xl font-bold text-gray-900 truncate max-w-[200px] sm:max-w-md">
                Hello, {userName}
              </h1>
              <p className="hidden sm:block text-sm text-gray-500">Overview of your store performance.</p>
            </div>
          </div>
          <div className="h-10 w-10 bg-gray-100 rounded-full flex items-center justify-center text-gray-600 border border-gray-200 flex-shrink-0">
             <User size={20} />
          </div>
        </header>

        <main className="flex-1 overflow-y-auto p-4 sm:p-8">
           <div className="max-w-6xl mx-auto space-y-6">
                <div className="bg-white p-6 rounded-lg border border-gray-200 shadow-sm text-center text-gray-500">
                    <h3 className="text-lg font-medium text-gray-900">Authenticated Successfully</h3>
                    <p className="mt-1">You are logged in via Keycloak OIDC.</p>
                </div>
           </div>
        </main>
      </div>
    </div>
  );
}

function NavItem({ icon, label, to, active, onClick }) {
  return (
    <Link to={to} onClick={onClick} className={`flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors mb-1 ${active ? "bg-gray-100 text-gray-900" : "text-gray-600 hover:bg-gray-50 hover:text-gray-900"}`}>
      {icon} {label}
    </Link>
  );
}