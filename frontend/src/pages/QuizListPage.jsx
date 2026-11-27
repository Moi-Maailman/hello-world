import { useEffect, useState } from "react";
import { Link } from "react-router";

function QuizListPage() {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`${import.meta.env.VITE_BACKEND_URL}/api/quizzes`)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Request failed with status ${response.status}`);
        }

        return response.json();
      })
      .then((data) => {
        setQuizzes(data);
      })
      .catch((requestError) => {
        setError(requestError.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <main>
        <p>Loading quizzes...</p>
      </main>
    );
  }

  if (error) {
    return (
      <main>
        <h1>Available quizzes</h1>
        <p className="error">Could not load quizzes: {error}</p>
      </main>
    );
  }

  return (
    <main>
      <h1>Available quizzes</h1>

      {quizzes.length === 0 ? (
        <p>No published quizzes are currently available.</p>
      ) : (
        <ul className="quiz-list">
          {quizzes.map((quiz) => (
            <li key={quiz.id} className="quiz-card">
              <h2><Link to={`/quizzes/${quiz.id}`}>{quiz.name}</Link></h2>
              <p>{quiz.description}</p>
              <p>
                <strong>Course:</strong> {quiz.courseCode}
              </p>
              <p>
                <strong>Category:</strong> {quiz.category ? quiz.category.name : "-"}
              </p>
              <p>
                <strong>Added on:</strong> {new Date(quiz.createdAt).toLocaleDateString("fi-FI")}
              </p>
              <Link to={`/quizzes/${quiz.id}/results`}>See results</Link>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}

export default QuizListPage;
