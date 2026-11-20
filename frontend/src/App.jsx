import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [categories, setCategories] = useState([]);
  const [categoryError, setCategoryError] = useState("");
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);

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

  const filteredQuizzes =
    selectedCategoryId === null
      ? quizzes
      : quizzes.filter(
        (quiz) => quiz.category?.id === selectedCategoryId
      );

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
          <ul className="category-list">
            <li>
              <button
                type="button"
                className={`category-button ${selectedCategoryId === null ? "active" : ""
                  }`}
                aria-pressed={selectedCategoryId === null}
                onClick={() => setSelectedCategoryId(null)}
              >
                All categories
              </button>
            </li>

            {categories.map((category) => (
              <li key={category.id}>
                <button
                  type="button"
                  className={`category-button ${selectedCategoryId === category.id ? "active" : ""
                    }`}
                  aria-pressed={selectedCategoryId === category.id}
                  onClick={() => setSelectedCategoryId(category.id)}
                >
                  {category.name}
                </button>
              </li>
            ))}
          </ul>
        )}
      </section>

      {filteredQuizzes.length === 0 ? (
        <p>No published quizzes are currently available.</p>
      ) : (
        <ul className="quiz-list">
          {filteredQuizzes.map((quiz) => (
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