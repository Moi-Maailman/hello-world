import { useEffect, useState } from "react";
import { Link, useParams } from "react-router";

const API_URL = `${import.meta.env.VITE_BACKEND_URL}/api`;

function QuizResultsPage() {
    const { id } = useParams();
    const [quiz, setQuiz] = useState(null);
    const [results, setResults] = useState([]);
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
            fetchJson(`${API_URL}/quizzes/${id}`),
            fetchJson(`${API_URL}/quizzes/${id}/results`),
        ])
            .then(([quizData, resultData]) => {
                setQuiz(quizData);
                setResults(resultData);
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
                <p>Loading results...</p>
            </main>
        );
    }

    if (error) {
        return (
            <main>
                <p className="error">Could not load the results: {error}</p>
                <Link to="/">Back to quizzes</Link>
            </main>
        );
    }

    const totalAnswers = results.reduce((sum, result) => sum + result.totalAnswers, 0);

    const correctPercentage = (result) =>
        result.totalAnswers === 0
            ? "-"
            : `${Math.round((result.correctAnswers / result.totalAnswers) * 100)} %`;

    return (
        <main>
            <h1>Results of "{quiz.name}"</h1>
            <p className="quiz-details">
                {totalAnswers} answers to {results.length} questions
            </p>

            {results.length === 0 ? (
                <p>This quiz has no questions yet.</p>
            ) : (
                <table className="results-table">
                    <thead>
                        <tr>
                            <th>Question</th>
                            <th>Difficulty</th>
                            <th>Total answers</th>
                            <th>Correct answer %</th>
                            <th>Correct answers</th>
                            <th>Wrong answers</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results.map((result) => (
                            <tr key={result.questionId}>
                                <td>{result.questionText}</td>
                                <td>{result.questionDifficulty}</td>
                                <td>{result.totalAnswers}</td>
                                <td>{correctPercentage(result)}</td>
                                <td>{result.correctAnswers}</td>
                                <td>{result.wrongAnswers}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            <Link to="/">Back to quizzes</Link>
        </main>
    );
}

export default QuizResultsPage;