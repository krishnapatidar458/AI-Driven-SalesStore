import { Search, ShoppingBag } from 'lucide-react';

const Navbar = () => {
  return (
    <nav className="bg-white border-b border-gray-100 sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <div className="flex items-center gap-2">
            <div className="bg-blue-600 p-2 rounded-lg">
                <ShoppingBag className="text-white" size={24} />
            </div>
            <span className="text-xl font-bold text-gray-900">SalesStore</span>
          </div>

          {/* AI Search Bar (Placeholder for AI Service) */}
          <div className="hidden md:flex flex-1 mx-8 max-w-lg">
            <div className="relative w-full">
                <input 
                    type="text" 
                    placeholder="Ask AI: 'Show me gaming laptops under $1000'..." 
                    className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
                <Search className="absolute left-3 top-2.5 text-gray-400" size={20} />
            </div>
          </div>

          {/* Auth Placeholder */}
          <div className="flex items-center gap-4">
            <button className="text-gray-600 hover:text-gray-900 font-medium">Login</button>
            <button className="bg-gray-900 text-white px-4 py-2 rounded-lg hover:bg-gray-800">
                Cart (0)
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;