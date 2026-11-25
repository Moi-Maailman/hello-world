import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router";
import QuestionCard from "../components/QuestionCard";

const API_URL = "http://localhost:8080/api";

function QuizPage() {
  const { id } = useParams();
  const [quiz, setQuiz] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [feedback, setFeedback] = useState(null);
  const feedbackTimer = useRef(null);

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

  const showFeedback = (correct, errorMessage) => {
    if (errorMessage) {
      setFeedback({ type: "error", message: `Could not submit the answer: ${errorMessage}` });
    } else if (correct) {
      setFeedback({ type: "correct", message: "That is correct, good job! 🎉" });
    } else {
      setFeedback({ type: "wrong", message: "That is not correct, try again ❌" });
    }
    clearTimeout(feedbackTimer.current);
    feedbackTimer.current = setTimeout(() => setFeedback(null), 3000);
  };

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
            <QuestionCard
              key={question.id}
              question={question}
              number={index + 1}
              total={questions.length}
              onAnswered={showFeedback}
            />
          ))}
        </ul>
      )}

      {feedback && (
        <div className={`feedback ${feedback.type}`} role="status">
          {feedback.message}
        </div>
      )}
    </main>
  );
}

export default QuizPage;