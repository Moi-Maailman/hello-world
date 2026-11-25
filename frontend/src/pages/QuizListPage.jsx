import { useEffect, useState } from "react";
import { Link } from "react-router";

function QuizListPage() {
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
          <ul className="category-list">
            {categories.map((category) => (
              <li key={category.id}>
                <Link
                  to={`/categories/${category.id}/quizzes`}
                  className="category-button"
                >
                  {category.name}
                </Link>
              </li>
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
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}

export default QuizListPage;