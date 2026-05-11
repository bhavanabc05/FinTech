import { useState } from "react";

export default function TransactionForm({ onAdd }) {

  const [form, setForm] = useState({
    category: "",
    amount: "",
    type: "expense"
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submit = (e) => {
    e.preventDefault();

    if (!form.category || !form.amount) {
      alert("Fill all fields");
      return;
    }

    onAdd({
      ...form,
      amount: Number(form.amount)
    });

    setForm({
      category: "",
      amount: "",
      type: "expense"
    });
  };

  return (
    <form className="form" onSubmit={submit}>

      <input
        name="category"
        placeholder="Category"
        value={form.category}
        onChange={handleChange}
      />

      <input
        name="amount"
        type="number"
        placeholder="Amount"
        value={form.amount}
        onChange={handleChange}
      />

      <select name="type" onChange={handleChange}>
        <option value="expense">Expense</option>
        <option value="income">Income</option>
      </select>

      <button>Add</button>

    </form>
  );
}