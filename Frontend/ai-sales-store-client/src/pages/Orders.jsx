import { useEffect, useState } from 'react';
import api from '../services/api';
import { Package, Loader2, Calendar } from 'lucide-react';

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Fetches orders for the logged-in user (Backend uses token subject)
    api.get('/orders') 
      .then(res => setOrders(res.data))
      .catch(err => console.error(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-20 text-center"><Loader2 className="animate-spin mx-auto"/></div>;

  return (
    <div className="max-w-7xl mx-auto py-12 px-6">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">Order History</h1>
      
      <div className="space-y-6">
        {orders.map((order) => (
          <div key={order.id} className="bg-white border border-gray-200 rounded-lg overflow-hidden shadow-sm">
            {/* Order Header */}
            <div className="bg-gray-50 px-6 py-4 border-b border-gray-200 flex flex-wrap items-center justify-between gap-4">
              <div className="flex gap-8 text-sm text-gray-600">
                <div>
                  <span className="block font-bold text-gray-900">Date Placed</span>
                  <div className="flex items-center gap-1 mt-1">
                    <Calendar size={14} />
                    {new Date(order.createdAt).toLocaleDateString()}
                  </div>
                </div>
                <div>
                  <span className="block font-bold text-gray-900">Total Amount</span>
                  <span className="block mt-1">${order.totalAmount}</span>
                </div>
                <div>
                  <span className="block font-bold text-gray-900">Status</span>
                  <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium mt-1
                    ${order.status === 'COMPLETED' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'}`}>
                    {order.status}
                  </span>
                </div>
              </div>
              <div className="text-sm text-gray-500">Order ID: {order.id.slice(0,8)}...</div>
            </div>

            {/* Order Items */}
            <div className="px-6 py-4">
               {order.items?.map((item) => (
                 <div key={item.id} className="flex items-center gap-4 py-2">
                    <div className="h-12 w-12 bg-gray-100 rounded border border-gray-200 flex items-center justify-center">
                        <Package size={20} className="text-gray-400"/>
                    </div>
                    <div>
                        <p className="font-medium text-gray-900">{item.productName}</p>
                        <p className="text-sm text-gray-500">Qty: {item.quantity}</p>
                    </div>
                 </div>
               ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}