import { useEffect, useState } from "react";
import { useParams } from "react-router";

const API_URL = "http://localhost:8080/api";

function QuizPage() {
  const { id } = useParams();
  const [quiz, setQuiz] = useState(null);
  const [questions, setQuestions] = useState([]);
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
      fetchJson(`${API_URL}/quizzes/${id}/questions`),
    ])
      .then(([quizData, questionData]) => {
        setQuiz(quizData);
        setQuestions(questionData);
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
        <p>Loading quiz...</p>
      </main>
    );
  }

  if (error) {
    return (
      <main>
        <p className="error">Could not load the quiz: {error}</p>
      </main>
    );
  }

  return (
    <main>
      <h1>{quiz.name}</h1>
      <p>{quiz.description}</p>
      <p className="quiz-details">
        Added on: {new Date(quiz.createdAt).toLocaleDateString("fi-FI")}
        {" - "}Questions: {questions.length}
        {" - "}Course: {quiz.courseCode}
        {" - "}Category: {quiz.category ? quiz.category.name : "-"}
      </p>

      {questions.length === 0 ? (
        <p>This quiz has no questions yet.</p>
      ) : (
        <ul className="question-list">
          {questions.map((question, index) => (
            <li key={question.id} className="question-card">
              <h2>{question.text}</h2>
              <p className="question-details">
                Question {index + 1} of {questions.length}
                {" - "}Difficulty: {formatDifficulty(question.difficulty)}
              </p>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}

function formatDifficulty(difficulty) {
  if (!difficulty) {
    return "-";
  }
  return difficulty.charAt(0) + difficulty.slice(1).toLowerCase();
}

export default QuizPage;