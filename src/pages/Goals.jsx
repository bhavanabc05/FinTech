import { useState, useEffect } from "react";

export default function Goals() {

  // ✅ LOAD FROM LOCAL STORAGE
  const [goals, setGoals] = useState(() => {
    const saved = localStorage.getItem("goals");
    return saved ? JSON.parse(saved) : [];
  });

  const [form, setForm] = useState({
    name: "",
    target: "",
    saved: ""
  });

  // ✅ SAVE TO LOCAL STORAGE
  useEffect(() => {
    localStorage.setItem("goals", JSON.stringify(goals));
  }, [goals]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const addGoal = (e) => {
    e.preventDefault();

    if (!form.name || !form.target) {
      alert("Fill all fields");
      return;
    }

    const newGoal = {
      id: Date.now(),
      name: form.name,
      target: Number(form.target),
      saved: Number(form.saved || 0)
    };

    setGoals([...goals, newGoal]);

    setForm({
      name: "",
      target: "",
      saved: ""
    });
  };

  const deleteGoal = (id) => {
    setGoals(goals.filter(g => g.id !== id));
  };

  return (
    <div className="goals">

      <h1>🎯 Savings Goals</h1>

      {/* FORM */}
      <form className="form" onSubmit={addGoal}>
        <input
          name="name"
          placeholder="Goal Name"
          value={form.name}
          onChange={handleChange}
        />

        <input
          name="target"
          type="number"
          placeholder="Target Amount"
          value={form.target}
          onChange={handleChange}
        />

        <input
          name="saved"
          type="number"
          placeholder="Saved Amount"
          value={form.saved}
          onChange={handleChange}
        />

        <button>Add Goal</button>
      </form>

      {/* LIST */}
      <div className="goal-list">
        {goals.length === 0 ? (
          <p>No goals yet</p>
        ) : (
          goals.map((g) => {
            const percent = Math.min((g.saved / g.target) * 100, 100);

            return (
              <div key={g.id} className="card">
                <h3>{g.name}</h3>
                <p>₹{g.saved} / ₹{g.target}</p>

                <div className="progress-bar">
                  <div
                    className="progress"
                    style={{ width: `${percent}%` }}
                  ></div>
                </div>

                <button
                  className="delete-btn"
                  onClick={() => deleteGoal(g.id)}
                >
                  Delete
                </button>
              </div>
            );
          })
        )}
      </div>

    </div>
  );
}