export default function Card({ title, value, type }) {
  return (
    <div className={`card ${type}`}>
      <h3>{title}</h3>
      <h2>₹{value}</h2>
    </div>
  );
}