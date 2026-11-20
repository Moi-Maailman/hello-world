import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [categories, setCategories] = useState([]);
  const [categoryError, setCategoryError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/api/quizzes")
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

  useEffect(() => {
    fetch("http://localhost:8080/api/categories")
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Request failed with status ${response.status}`);
        }

        return response.json();
      })
      .then((data) => {
        setCategories(data);
      })
      .catch((requestError) => {
        setCategoryError(requestError.message);
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

      <section>
        <h2>Categories</h2>

        {categoryError ? (
          <p className="error">
            Could not load categories: {categoryError}
          </p>
        ) : categories.length === 0 ? (
          <p>No categories are currently available.</p>
        ) : (
          <ul>
            {categories.map((category) => (
              <li key={category.id}>{category.name}</li>
            ))}
          </ul>
        )}
      </section>

      {quizzes.length === 0 ? (
        <p>No published quizzes are currently available.</p>
      ) : (
        <ul className="quiz-list">
          {quizzes.map((quiz) => (
            <li key={quiz.id} className="quiz-card">
              <h2>{quiz.name}</h2>
              <p>{quiz.description}</p>
              <p>
                <strong>Course:</strong> {quiz.courseCode}
              </p>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}

export default App;