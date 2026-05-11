import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="hero-container">

     

      {/* HERO SECTION */}
      <div className="hero">

        {/* LEFT CONTENT */}
        <div className="hero-text">
          <h1>
            Financial <br />
            <span>Management</span>
          </h1>

          <p>
            Track your income, manage expenses, and gain smart insights
            into your financial life.
          </p>

          <Link to="/dashboard">
            <button className="hero-btn">Get Started</button>
          </Link>
        </div>

        {/* RIGHT IMAGE */}
        <div className="hero-image">
          <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" />
        </div>

      </div>
    </div>
  );
}