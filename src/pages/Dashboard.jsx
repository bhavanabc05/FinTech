import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/api";
import Card from "../components/Card";
import ExpenseChart from "../components/ExpenseChart";

export default function Dashboard() {

  const navigate = useNavigate();
  const userId = 1;

  const [summary, setSummary] = useState({
    totalIncome: 0,
    totalExpense: 0,
    savings: 0,
    message: ""
  });

  const [transactions, setTransactions] = useState([]);

  // ===== FETCH DATA =====
  useEffect(() => {

    // Summary
    API.get(`/transactions/summary/${userId}`)
      .then(res => {
        const data = res.data?.data || res.data || {};
        setSummary(data);
      })
      .catch(err => console.error(err));

    // Transactions
    API.get(`/transactions/${userId}`)
      .then(res => {
        const data = res.data?.data || res.data || [];
        setTransactions(Array.isArray(data) ? data : []);
      })
      .catch(err => console.error(err));

  }, []);

  // ===== CHART DATA =====
  const chartData = Object.values(
    transactions.reduce((acc, t) => {
      if (!t.category) return acc;

      if (!acc[t.category]) {
        acc[t.category] = { category: t.category, amount: 0 };
      }
      acc[t.category].amount += t.amount || 0;

      return acc;
    }, {})
  );

  // ===== UI =====
  return (
    <div className="dashboard">

      {/* HEADER */}
      <div className="dashboard-header">
        <h1>📊 Dashboard</h1>
        <p>Welcome back! Here’s your financial overview</p>
      </div>

      {/* ===== SUMMARY CARDS ===== */}
      <div className="row">

        <Card
          title="Income"
          value={summary.totalIncome || 0}
          type="income"
        />

        <Card
          title="Expense"
          value={summary.totalExpense || 0}
          type="expense"
        />

        <Card
          title="Savings"
          value={summary.savings || 0}
          type="savings"
        />

      </div>

      {/* ===== INSIGHT ===== */}
      <div className="insight">
        <h3>💡 Insight</h3>
        <p>{summary.message || "No insights available"}</p>
      </div>

      

      {/* ===== CLICKABLE NAV CARDS ===== */}
      <div className="row" style={{ marginTop: "30px" }}>

        <div
          className="card nav-card"
          onClick={() => navigate("/transactions")}
        >
          <h3>💳 Transactions</h3>
          <h2>Manage</h2>
        </div>

        <div
          className="card nav-card"
          onClick={() => navigate("/budgets")}
        >
          <h3>📊 Budgets</h3>
          <h2>Plan</h2>
        </div>

        <div
          className="card nav-card"
          onClick={() => navigate("/goals")}
        >
          <h3>🎯 Goals</h3>
          <h2>Track</h2>
          
        </div>

      </div>
      {/* ===== CHART ===== */}
      <div className="chart-section">
        <h2>📈 Expense Breakdown</h2>
        <ExpenseChart data={chartData} />
      </div>

    </div>
  );
}