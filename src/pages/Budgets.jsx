import { useEffect, useState } from "react";
import API from "../api/api";

export default function Budgets() {
  const userId = 1;

  // ================= STATE =================
  const [budgets, setBudgets] = useState([]);
  const [recommendations, setRecommendations] = useState([]);
  const [editId, setEditId] = useState(null);

  const [form, setForm] = useState({
    category: "",
    limitAmount: "",
    month: "",
    year: ""
  });

  // ================= FETCH =================
  const fetchBudgets = () => {
    API.get(`/budgets/${userId}`)
      .then(res => {
        const data = res.data?.data || res.data || [];
        setBudgets(Array.isArray(data) ? data : []);
      })
      .catch(err => console.error(err));
  };

  const fetchRecommendations = () => {
    API.get(`/budgets/recommendations/${userId}`)
      .then(res => {
        const data = res.data?.data || res.data || [];
        setRecommendations(Array.isArray(data) ? data : []);
      })
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchBudgets();
    fetchRecommendations();
  }, []);

  // ================= FORM =================
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const resetForm = () => {
    setForm({
      category: "",
      limitAmount: "",
      month: "",
      year: ""
    });
    setEditId(null);
  };

  // ================= ADD =================
  const addBudget = async (e) => {
    e.preventDefault();

    if (!form.category || !form.limitAmount || !form.month || !form.year) {
      alert("All fields are required");
      return;
    }

    const payload = {
      userId,
      category: form.category.trim(),
      limitAmount: Number(form.limitAmount),
      month: Number(form.month),
      year: Number(form.year)
    };

    if (payload.limitAmount <= 0) {
      alert("Amount must be greater than 0");
      return;
    }

    if (payload.month < 1 || payload.month > 12) {
      alert("Month must be between 1 and 12");
      return;
    }

    try {
      await API.post("/budgets", payload);
      fetchBudgets();
      fetchRecommendations();
      resetForm();
    } catch (err) {
      alert(err.response?.data?.message || "Error adding budget");
    }
  };

  // ================= EDIT =================
  const startEdit = (b) => {
    if (!b.budgetId) {
      alert("Invalid budget ID");
      return;
    }

    setEditId(b.budgetId);

    setForm({
      category: b.category,
      limitAmount: b.limitAmount,
      month: b.month,
      year: b.year
    });

    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  // ================= UPDATE =================
  const updateBudget = async (e) => {
    e.preventDefault();

    if (!editId) {
      alert("No budget selected");
      return;
    }

    const payload = {
      userId,
      category: form.category.trim(),
      limitAmount: Number(form.limitAmount),
      month: Number(form.month),
      year: Number(form.year)
    };

    try {
      await API.put(`/budgets/${editId}`, payload);
      fetchBudgets();
      fetchRecommendations();
      resetForm();
    } catch (err) {
      alert(err.response?.data?.message || "Error updating budget");
    }
  };

  // ================= DELETE =================
  const deleteBudget = async (b) => {
    if (!b.budgetId) {
      alert("Invalid budget ID");
      return;
    }

    if (!window.confirm("Delete this budget?")) return;

    try {
      await API.delete(`/budgets/${b.budgetId}`);
      fetchBudgets();
      fetchRecommendations();
    } catch (err) {
      console.error(err);
      alert("Failed to delete budget");
    }
  };

  // ================= UI =================
  return (
    <div className="budgets">

      <h1>📊 Budgets</h1>

      {/* ===== FORM ===== */}
      <form
        className="form"
        onSubmit={editId ? updateBudget : addBudget}
      >
        <input
          name="category"
          placeholder="Category"
          value={form.category}
          onChange={handleChange}
          required
        />

        <input
          name="limitAmount"
          type="number"
          placeholder="Limit Amount"
          value={form.limitAmount}
          onChange={handleChange}
          required
        />

        <input
          name="month"
          type="number"
          placeholder="Month (1-12)"
          value={form.month}
          onChange={handleChange}
          required
        />

        <input
          name="year"
          type="number"
          placeholder="Year"
          value={form.year}
          onChange={handleChange}
          required
        />

        <button type="submit">
          {editId ? "Update" : "Add Budget"}
        </button>

        {editId && (
          <button type="button" onClick={resetForm}>
            Cancel
          </button>
        )}
      </form>

      {/* ===== BUDGET LIST ===== */}
      <div className="budget-list">
        <h2>Your Budgets</h2>

        {budgets.length === 0 ? (
          <p>No budgets added</p>
        ) : (
          budgets.map((b) => (
            <div key={b.budgetId} className="card">
              <h3>{b.category}</h3>
              <p>Limit: ₹{b.limitAmount}</p>
              <p>Month: {b.month}/{b.year}</p>

              <button
                className="edit-btn"
                onClick={() => startEdit(b)}
              >
                Edit
              </button>

              <button
                className="delete-btn"
                onClick={() => deleteBudget(b)}
              >
                Delete
              </button>
            </div>
          ))
        )}
      </div>

      {/* ===== RECOMMENDATIONS ===== */}
      <div className="recommendations">
        <h2>🧠 Recommendations</h2>

        {recommendations.length === 0 ? (
          <p>No recommendations</p>
        ) : (
          recommendations.map((r, i) => (
            <div key={i} className="insight">
              <h3>{r.category}</h3>
              <p>{r.message}</p>
            </div>
          ))
        )}
      </div>

    </div>
  );
}