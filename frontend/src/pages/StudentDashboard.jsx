import { useEffect, useState } from "react";
import axios from "axios";

function StudentDashboard() {
  const [data, setData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");

        const res = await axios.get("http://localhost:8080/students/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        setData(res.data);
      } catch (err) {
        console.log(err);
        alert("Failed to load dashboard");
      }
    };

    fetchData();
  }, []);

  if (!data) return <p>Loading...</p>;

  return (
    <div>
      <h2>Student Dashboard 🎓</h2>

      <h3>Student Info</h3>
      <p>Name: {data.student.name}</p>

      <h3>Fees</h3>
      {data.fees.map((f, i) => (
        <p key={i}>Remaining: {f.remainingAmount}</p>
      ))}

      <h3>Attendance</h3>
      {data.attendance.map((a, i) => (
        <p key={i}>{a.date} - {a.status}</p>
      ))}

      <h3>Marks</h3>
      {data.marks.map((m, i) => (
        <p key={i}>{m.subject}: {m.marksObtained}</p>
      ))}
    </div>
  );
}

export default StudentDashboard;