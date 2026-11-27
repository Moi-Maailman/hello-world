import { useEffect, useState } from "react";
import { Link } from "react-router";

const API_URL = `${import.meta.env.VITE_BACKEND_URL}/api`;

function CategoryListPage() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`${API_URL}/categories`)
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
        setError(requestError.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <main>
        <p>Loading categories...</p>
      </main>
    );
  }

  if (error) {
    return (
      <main>
        <h1>Categories</h1>
        <p className="error">Could not load categories: {error}</p>
      </main>
    );
  }

  return (
    <main>
      <h1>Categories</h1>

      {categories.length === 0 ? (
        <p>No categories are currently available.</p>
      ) : (
        <ul className="quiz-list">
          {categories.map((category) => (
            <li key={category.id} className="quiz-card">
              <h2>
                <Link to={`/categories/${category.id}/quizzes`}>
                  {category.name}
                </Link>
              </h2>
              <p>{category.description || "No description."}</p>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}

export default CategoryListPage;
