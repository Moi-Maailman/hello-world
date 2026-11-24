import { useEffect, useState } from "react";
import { Link, useParams } from "react-router";

const API_URL = "http://localhost:8080/api";

function CategoryQuizzesPage() {
    const { id } = useParams();
    const [category, setCategory] = useState(null);
    const [quizzes, setQuizzes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchJson = (url) =>
            fetch(url).then((response) => {
                if (!response.ok) {
                    throw new Error(`Request failed with status ${response.status}`);
                }
                return response.json();
            });

        Promise.all([
            fetchJson(`${API_URL}/categories`),
            fetchJson(`${API_URL}/categories/${id}/quizzes`),
        ])
            .then(([categoryData, quizData]) => {
                setCategory(categoryData.find((c) => c.id === Number(id)));
                setQuizzes(quizData);
            })
            .catch((requestError) => {
                setError(requestError.message);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [id]);

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
                <p className="error">Could not load quizzes: {error}</p>
                <Link to="/">Back to quizzes</Link>
            </main>
        );
    }

    return (
        <main>
            <h1>Quizzes in {category ? category.name : "category"}</h1>

            {quizzes.length === 0 ? (
                <p>This category has no published quizzes.</p>
            ) : (
                <ul className="quiz-list">
                    {quizzes.map((quiz) => (
                        <li key={quiz.id} className="quiz-card">
                            <h2>
                                <Link to={`/quizzes/${quiz.id}`}>{quiz.name}</Link>
                            </h2>
                            <p>{quiz.description}</p>
                            <p>
                                <strong>Course:</strong> {quiz.courseCode}
                            </p>
                            <p>
                                <strong>Added on:</strong>{" "}
                                {new Date(quiz.createdAt).toLocaleDateString("fi-FI")}
                            </p>
                        </li>
                    ))}
                </ul>
            )}

            <Link to="/">Back to quizzes</Link>
        </main>
    );
}

export default CategoryQuizzesPage;