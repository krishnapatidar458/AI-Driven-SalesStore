import { useForm } from 'react-hook-form';
import { useCart } from '../context/CartContext';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { Loader2, CheckCircle } from 'lucide-react';

export default function Checkout() {
  const { cart, cartTotal, clearCart } = useCart();
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [isProcessing, setIsProcessing] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    setIsProcessing(true);
    try {
      // Construct Payload matching your Java DTO
      const orderPayload = {
        shippingAddress: {
          street: data.street,
          city: data.city,
          zip: data.zip,
          country: data.country
        },
        items: cart.map(item => ({
          productId: item.id,
          quantity: item.quantity,
          priceAtPurchase: item.price // Important for historical data
        }))
      };

      // Call Order Service (via Gateway)
      await api.post('/orders', orderPayload);
      
      clearCart();
      navigate('/orders'); // Redirect to order history
    } catch (error) {
      console.error("Checkout failed:", error);
      alert("Failed to place order. Please try again.");
    } finally {
      setIsProcessing(false);
    }
  };

  if (cart.length === 0) return <div className="p-10 text-center">Your cart is empty.</div>;

  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-2 gap-12">
        
        {/* Left: Shipping Form */}
        <div>
          <h2 className="text-2xl font-bold text-gray-900 mb-8">Shipping Information</h2>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6 bg-white p-8 rounded-xl shadow-sm border border-gray-200">
            
            <div className="grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-2">
              <div className="sm:col-span-2">
                <label className="block text-sm font-medium text-gray-700">Full Name</label>
                <input {...register("fullName", { required: true })} className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 p-2 border" />
              </div>

              <div className="sm:col-span-2">
                <label className="block text-sm font-medium text-gray-700">Street Address</label>
                <input {...register("street", { required: true })} className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 p-2 border" />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700">City</label>
                <input {...register("city", { required: true })} className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 p-2 border" />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700">ZIP / Postal Code</label>
                <input {...register("zip", { required: true })} className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 p-2 border" />
              </div>
              
              <div className="sm:col-span-2">
                <label className="block text-sm font-medium text-gray-700">Country</label>
                <select {...register("country")} className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 p-2 border">
                  <option value="US">United States</option>
                  <option value="IN">India</option>
                  <option value="CA">Canada</option>
                </select>
              </div>
            </div>

            <button
              type="submit"
              disabled={isProcessing}
              className="w-full mt-6 bg-indigo-600 text-white py-3 px-4 rounded-md shadow hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 flex items-center justify-center gap-2"
            >
              {isProcessing ? <Loader2 className="animate-spin" /> : <CheckCircle size={20} />}
              {isProcessing ? "Processing..." : `Pay $${cartTotal.toFixed(2)}`}
            </button>
          </form>
        </div>

        {/* Right: Order Summary */}
        <div className="bg-gray-100 p-8 rounded-xl h-fit">
          <h3 className="text-lg font-medium text-gray-900 mb-6">Order Summary</h3>
          <div className="flow-root">
            <ul className="-my-4 divide-y divide-gray-200">
              {cart.map((product) => (
                <li key={product.id} className="flex py-4">
                  <div className="h-16 w-16 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                    <img src={product.imageUrl} alt={product.name} className="h-full w-full object-cover object-center" />
                  </div>
                  <div className="ml-4 flex flex-1 flex-col">
                    <div>
                      <div className="flex justify-between text-base font-medium text-gray-900">
                        <h3>{product.name}</h3>
                        <p>${(product.price * product.quantity).toFixed(2)}</p>
                      </div>
                      <p className="mt-1 text-sm text-gray-500">Qty {product.quantity}</p>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          </div>
          <div className="border-t border-gray-200 mt-6 pt-6">
            <div className="flex justify-between text-base font-medium text-gray-900">
              <p>Total</p>
              <p>${cartTotal.toFixed(2)}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}