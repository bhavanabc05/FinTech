import { BrowserRouter, Routes, Route } from "react-router-dom";

// Pages
import Home from "./pages/Home";
import Dashboard from "./pages/Dashboard";
import Transactions from "./pages/Transactions";
import Budgets from "./pages/Budgets";
import Goals from "./pages/Goals";
import Login from "./pages/Login";
import Register from "./pages/Register";
// optional

// Components
import Navbar from "./components/Navbar";

function App() {
  return (
    <BrowserRouter>

      {/* 🔝 Navbar (visible on all pages) */}
      <Navbar />

      <Routes>

        {/* ===== MAIN ROUTES ===== */}
        <Route path="/" element={<Home />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/transactions" element={<Transactions />} />
        <Route path="/budgets" element={<Budgets />} />
        <Route path="/goals" element={<Goals />} />

        {/* ===== AUTH ===== */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* ===== OPTIONAL ===== */}

        {/* ===== 404 PAGE ===== */}
        <Route path="*" element={<h2 style={{textAlign:"center"}}>404 Page Not Found</h2>} />

      </Routes>

    </BrowserRouter>
  );
}

export default App;