import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div className="navbar">

      {/* LOGO */}
      <div className="logo">💰 FinTrack</div>

      {/* NAV LINKS */}
      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/login">Login</Link>
        <Link to="/register">Register</Link>
      </div>

    </div>
  );
}