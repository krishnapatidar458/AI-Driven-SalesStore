import { X, Plus, Minus, Trash2, ShoppingBag } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { useNavigate } from 'react-router-dom';

export default function CartDrawer() {
  const { cart, isCartOpen, setIsCartOpen, removeFromCart, updateQuantity, cartTotal } = useCart();
  const navigate = useNavigate();

  if (!isCartOpen) return null;

  return (
    <div className="fixed inset-0 z-50 overflow-hidden">
      {/* Backdrop */}
      <div 
        className="absolute inset-0 bg-gray-900/50 backdrop-blur-sm transition-opacity" 
        onClick={() => setIsCartOpen(false)}
      />

      {/* Drawer Panel */}
      <div className="absolute inset-y-0 right-0 max-w-md w-full flex">
        <div className="h-full w-full bg-white shadow-xl flex flex-col animate-slide-in-right">
          
          {/* Header */}
          <div className="flex items-center justify-between px-6 py-4 border-b border-gray-100">
            <h2 className="text-lg font-bold text-gray-900 flex items-center gap-2">
              <ShoppingBag size={20} /> Your Cart
            </h2>
            <button onClick={() => setIsCartOpen(false)} className="p-2 hover:bg-gray-100 rounded-full text-gray-500">
              <X size={20} />
            </button>
          </div>

          {/* Cart Items */}
          <div className="flex-1 overflow-y-auto p-6 space-y-6">
            {cart.length === 0 ? (
              <div className="text-center text-gray-500 mt-20">
                <p>Your cart is empty.</p>
                <button onClick={() => setIsCartOpen(false)} className="mt-4 text-indigo-600 font-medium hover:underline">
                  Start Shopping
                </button>
              </div>
            ) : (
              cart.map(item => (
                <div key={item.id} className="flex gap-4">
                  <div className="h-20 w-20 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                    <img src={item.imageUrl || "https://placehold.co/100"} alt={item.name} className="h-full w-full object-cover" />
                  </div>

                  <div className="flex flex-1 flex-col">
                    <div className="flex justify-between text-base font-medium text-gray-900">
                      <h3>{item.name}</h3>
                      <p>${(item.price * item.quantity).toFixed(2)}</p>
                    </div>
                    <p className="mt-1 text-sm text-gray-500">{item.variant || 'Standard'}</p>

                    <div className="flex flex-1 items-end justify-between text-sm">
                      <div className="flex items-center border border-gray-300 rounded-md">
                        <button onClick={() => updateQuantity(item.id, -1)} className="p-1 hover:bg-gray-100"><Minus size={14}/></button>
                        <span className="px-2 font-medium">{item.quantity}</span>
                        <button onClick={() => updateQuantity(item.id, 1)} className="p-1 hover:bg-gray-100"><Plus size={14}/></button>
                      </div>
                      <button onClick={() => removeFromCart(item.id)} className="font-medium text-red-600 hover:text-red-500 flex items-center gap-1">
                        <Trash2 size={14} /> Remove
                      </button>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>

          {/* Footer / Checkout Button */}
          {cart.length > 0 && (
            <div className="border-t border-gray-100 p-6 bg-gray-50">
              <div className="flex justify-between text-base font-medium text-gray-900 mb-4">
                <p>Subtotal</p>
                <p>${cartTotal.toFixed(2)}</p>
              </div>
              <p className="mt-0.5 text-sm text-gray-500 mb-6">Shipping and taxes calculated at checkout.</p>
              <button
                onClick={() => {
                  setIsCartOpen(false);
                  navigate('/checkout');
                }}
                className="w-full flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
              >
                Checkout
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}