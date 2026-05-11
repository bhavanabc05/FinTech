import {
  PieChart, Pie, Tooltip, Cell, ResponsiveContainer
} from "recharts";

export default function ExpenseChart({ data }) {

  // ✅ SAFETY FIX
  if (!data || data.length === 0) {
    return <p style={{ textAlign: "center" }}>No data for chart</p>;
  }

  const COLORS = ["#2563eb", "#dc2626", "#16a34a", "#f59e0b"];

  return (
    <div className="chart">
      <ResponsiveContainer width="100%" height={300}>
        <PieChart>

          <Pie
            data={data}
            dataKey="amount"
            nameKey="category"
            outerRadius={100}
          >
            {data.map((entry, index) => (
              <Cell
                key={index}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>

          <Tooltip />

        </PieChart>
      </ResponsiveContainer>
    </div>
  );
}