import { Link, Navigate } from "react-router-dom";
import { ArrowRight, CheckCircle2, Menu, X } from 'lucide-react';
import { useState } from "react";

export default function LandingPage() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const isAuthenticated = !!localStorage.getItem("access_token");

  // If user is logged in, skip landing page
  if (isAuthenticated) return <Navigate to="/dashboard" replace />;

  return (
    <div className="min-h-screen bg-white flex flex-col font-sans">
      
      {/* Navbar */}
      <nav className="w-full border-b border-gray-100 bg-white/95 backdrop-blur-sm sticky top-0 z-50">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 h-16 flex items-center justify-between">
          <div className="font-bold text-xl tracking-tight text-gray-900">
            Sales<span className="text-blue-600">Store</span>
          </div>
          
          {/* Desktop Nav */}
          <div className="hidden md:flex gap-4">
            <Link to="/login" className="px-4 py-2 text-sm font-medium text-gray-600 hover:text-gray-900">Log in</Link>
            <Link to="/signup" className="px-4 py-2 text-sm font-medium bg-gray-900 text-white rounded-lg hover:bg-black transition-colors">Get Started</Link>
          </div>

          {/* Mobile Menu Toggle */}
          <button onClick={() => setMobileMenuOpen(!mobileMenuOpen)} className="md:hidden p-2 text-gray-600">
             {mobileMenuOpen ? <X /> : <Menu />}
          </button>
        </div>

        {/* Mobile Dropdown */}
        {mobileMenuOpen && (
          <div className="md:hidden border-t border-gray-100 bg-white px-4 py-4 space-y-3 shadow-lg">
             <Link to="/login" className="block w-full text-center py-2 text-gray-600 font-medium border border-gray-200 rounded-lg">Log in</Link>
             <Link to="/signup" className="block w-full text-center py-2 text-white bg-gray-900 font-medium rounded-lg">Get Started</Link>
          </div>
        )}
      </nav>

      {/* Hero Section */}
      <main className="flex-grow flex flex-col items-center justify-center text-center pt-12 sm:pt-20 pb-16 px-4 sm:px-6">
        <div className="animate-fade-in-up max-w-3xl mx-auto space-y-6 sm:space-y-8">
          
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-blue-50 text-blue-700 text-xs font-semibold uppercase tracking-wide">
            New: AI Search Enabled
          </div>
          
          <h1 className="text-4xl sm:text-5xl md:text-6xl font-bold text-gray-900 tracking-tight leading-tight">
            The smart way to manage <br/>
            <span className="text-blue-600">your modern store.</span>
          </h1>
          
          <p className="text-lg sm:text-xl text-gray-500 max-w-2xl mx-auto leading-relaxed px-2">
            Order management, inventory tracking, and AI-powered insights. 
            Everything you need, nothing you don't.
          </p>

          <div className="flex flex-col sm:flex-row items-stretch sm:items-center justify-center gap-3 sm:gap-4 pt-4 w-full sm:w-auto">
            <Link 
              to="/signup" 
              className="flex items-center justify-center gap-2 bg-blue-600 text-white px-8 py-3.5 rounded-full text-lg font-semibold hover:bg-blue-700 transition-all shadow-lg hover:shadow-blue-500/20 w-full sm:w-auto"
            >
              Start for free <ArrowRight size={18} />
            </Link>
            <Link 
              to="/shop" 
              className="flex items-center justify-center px-8 py-3.5 rounded-full text-lg font-semibold text-gray-700 bg-gray-50 hover:bg-gray-100 transition-colors w-full sm:w-auto"
            >
              Browse Catalog
            </Link>
          </div>

          <div className="pt-8 sm:pt-12 flex flex-wrap justify-center gap-4 sm:gap-8 text-sm text-gray-500">
            <div className="flex items-center gap-2">
              <CheckCircle2 size={16} className="text-green-500" />
              <span>No credit card required</span>
            </div>
            <div className="flex items-center gap-2">
              <CheckCircle2 size={16} className="text-green-500" />
              <span>Enterprise Security</span>
            </div>
          </div>
        </div>

        {/* Product Visual */}
        <div className="mt-12 sm:mt-16 w-full max-w-5xl mx-auto px-2 sm:px-0">
          <div className="rounded-xl bg-gray-100 p-2 ring-1 ring-gray-900/10 lg:rounded-2xl lg:p-4">
            <div className="rounded-lg bg-white overflow-hidden shadow-2xl border border-gray-200 h-48 sm:h-64 md:h-96 flex items-center justify-center text-gray-300">
                <p className="font-medium text-lg">Application Dashboard Preview</p>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}