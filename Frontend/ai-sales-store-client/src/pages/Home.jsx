import { useEffect, useState } from 'react';
import api from '../services/api'; // Default import
import ProductCard from '../components/ProductCard';
import Navbar from '../components/Navbar';

const Home = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Calls the Gateway
    api.get('/products')
      .then((res) => {
        setProducts(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Failed to fetch products:", err);
        setError("Could not load products. Is the Gateway running on port 8080?");
        setLoading(false);
      });
  }, []);

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900">Featured Products</h1>
            <p className="text-gray-600 mt-2">Explore our latest collection.</p>
        </div>

        {error && (
            <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg mb-6">
                {error}
            </div>
        )}

        {loading ? (
            <div className="flex justify-center py-20">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
            </div>
        ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                {products.map((product) => (
                    <ProductCard key={product.id} product={product} />
                ))}
            </div>
        )}
      </main>
    </div>
  );
};

export default Home;