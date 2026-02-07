import { ShoppingCart } from 'lucide-react';

const ProductCard = ({ product }) => {
  return (
    <div className="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300">
      <div className="h-48 bg-gray-200 flex items-center justify-center text-gray-500">
        {/* Placeholder for Product Image */}
        <span className="text-sm">Image Placeholder</span>
      </div>
      
      <div className="p-5">
        <div className="flex justify-between items-start">
            <h3 className="text-lg font-bold text-gray-900 truncate">{product.name}</h3>
            <span className="bg-blue-100 text-blue-800 text-xs px-2 py-1 rounded-full">
                {product.category || 'General'}
            </span>
        </div>
        
        <p className="text-gray-500 text-sm mt-2 line-clamp-2">
            {product.description}
        </p>
        
        <div className="mt-4 flex items-center justify-between">
            <span className="text-xl font-bold text-gray-900">
                ${product.price}
            </span>
            <button className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                <ShoppingCart size={18} />
                Add
            </button>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;