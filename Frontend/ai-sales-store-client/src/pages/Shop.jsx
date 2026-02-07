import { useState, useEffect } from 'react';
import api from '../services/api';
import ProductCard from '../components/ProductCard';
import { Search, Sparkles, Filter, Loader2 } from 'lucide-react';

export default function Shop() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState("");
  const [isAiSearch, setIsAiSearch] = useState(false);

  // 1. Fetch Standard Products (SQL DB)
  const fetchProducts = async () => {
    try {
      setLoading(true);
      const res = await api.get('/products'); // Maps to Product Service
      setProducts(res.data);
    } catch (err) {
      console.error("Failed to load products", err);
    } finally {
      setLoading(false);
    }
  };

  // 2. Fetch AI Recommendations (Vector DB)
  const handleAiSearch = async (e) => {
    e.preventDefault();
    if (!searchQuery.trim()) return fetchProducts();

    try {
      setLoading(true);
      setIsAiSearch(true);
      // This endpoint hits your Spring AI service which queries pgvector
      const res = await api.post('/ai/search', { query: searchQuery });
      setProducts(res.data); // Expecting list of products sorted by similarity
    } catch (err) {
      console.error("AI Search failed", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="min-h-screen bg-gray-50 pt-20 px-6">
      <div className="max-w-7xl mx-auto">
        
        {/* Header & AI Search Bar */}
        <div className="mb-10 flex flex-col md:flex-row gap-6 items-end justify-between">
            <div>
                <h1 className="text-3xl font-bold text-gray-900">Marketplace</h1>
                <p className="text-gray-500 mt-2">Explore our AI-curated collection</p>
            </div>

            {/* AI Search Input */}
            <form onSubmit={handleAiSearch} className="w-full md:w-96 relative group">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    {isAiSearch ? <Sparkles className="text-indigo-500 animate-pulse" size={18} /> : <Search className="text-gray-400" size={18} />}
                </div>
                <input 
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Ask AI: 'Show me sneakers for running...'"
                    className="w-full pl-10 pr-4 py-3 bg-white border border-gray-200 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none shadow-sm transition-all"
                />
                <button type="submit" className="absolute right-2 top-2 bottom-2 bg-gray-100 hover:bg-gray-200 text-gray-600 px-3 rounded-lg text-sm font-medium transition-colors">
                    Search
                </button>
            </form>
        </div>

        {/* Content Grid */}
        <div className="flex gap-8">
            
            {/* Sidebar Filters (Optional Categories) */}
            <aside className="hidden lg:block w-64 space-y-8">
                <div className="flex items-center gap-2 font-semibold text-gray-900">
                    <Filter size={20} /> Filters
                </div>
                <div className="space-y-3">
                    <label className="flex items-center gap-3 text-gray-600 hover:text-indigo-600 cursor-pointer">
                        <input type="checkbox" className="rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"/>
                        Electronics
                    </label>
                    <label className="flex items-center gap-3 text-gray-600 hover:text-indigo-600 cursor-pointer">
                        <input type="checkbox" className="rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"/>
                        Fashion
                    </label>
                </div>
            </aside>

            {/* Product Grid */}
            <main className="flex-1">
                {loading ? (
                    <div className="h-96 flex items-center justify-center">
                        <Loader2 className="animate-spin text-indigo-600" size={40} />
                    </div>
                ) : (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                        {products.map(product => (
                            <ProductCard 
                                key={product.id} 
                                product={product} 
                                onAddToCart={(p) => console.log("Added", p)} 
                            />
                        ))}
                    </div>
                )}
            </main>
        </div>
      </div>
    </div>
  );
}