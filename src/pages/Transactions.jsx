import { useEffect, useState } from "react";
import API from "../api/api";

export default function Transactions() {
  const userId = 1;

  // ================= STATE =================
  const [transactions, setTransactions] = useState([]);
  const [alertMsg, setAlertMsg] = useState("");

  const [form, setForm] = useState({
    amount: "",
    type: "expense",
    category: "",
    date: ""
  });

  // ================= FETCH =================
  const fetchTransactions = () => {
    API.get(`/transactions/${userId}`)
      .then(res => {
        console.log("Transactions:", res.data);

        if (res.data.data) {
          setTransactions(res.data.data);
        } else if (Array.isArray(res.data)) {
          setTransactions(res.data);
        } else {
          setTransactions([]);
        }
      })
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchTransactions();
  }, []);

  // ================= FORM =================
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const addTransaction = async (e) => {
    e.preventDefault();

    try {
      const res = await API.post("/transactions", {
        userId,
        ...form
      });

      setAlertMsg(res.data.alert || res.data.message);

      fetchTransactions();

      setForm({
        amount: "",
        type: "expense",
        category: "",
        date: ""
      });

    } catch (err) {
      console.error(err);
    }
  };

  // ================= DELETE =================
  const deleteTransaction = async (id) => {
    if (!window.confirm("Delete this transaction?")) return;

    try {
      await API.delete(`/transactions/${id}`);
      fetchTransactions();
    } catch (err) {
      console.error(err);
    }
  };

  // ================= UI =================
  return (
    <div className="transactions">

      <h1>💰 Transactions</h1>

      {/* ===== ADD FORM ===== */}
      <form className="form" onSubmit={addTransaction}>

        <input
          name="amount"
          placeholder="Amount"
          value={form.amount}
          onChange={handleChange}
          required
        />

        <select
          name="type"
          value={form.type}
          onChange={handleChange}
        >
          <option value="income">Income</option>
          <option value="expense">Expense</option>
        </select>

        <input
          name="category"
          placeholder="Category"
          value={form.category}
          onChange={handleChange}
          required
        />

        <input
          type="date"
          name="date"
          value={form.date}
          onChange={handleChange}
          required
        />

        <button type="submit">Add</button>
      </form>

      {/* ===== ALERT ===== */}
      {alertMsg && (
        <div className="alert">
          {alertMsg}
        </div>
      )}

      {/* ===== TRANSACTION LIST ===== */}
      <div className="transaction-list">

        {transactions.length === 0 ? (
          <p>No transactions yet</p>
        ) : (
          transactions.map((t) => (
            <div
              key={t.transactionId || t.id}
              className="transaction-item"
            >
              <span>₹{t.amount}</span>
              <span>{t.category}</span>

              <span
                className={
                  t.type === "income"
                    ? "income-text"
                    : "expense-text"
                }
              >
                {t.type}
              </span>

              <span>{t.date}</span>

              <button
                className="delete-btn"
                onClick={() =>
                  deleteTransaction(t.transactionId || t.id)
                }
              >
                Delete
              </button>
            </div>
          ))
        )}

      </div>

    </div>
  );
}