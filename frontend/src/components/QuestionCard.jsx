import { useState } from "react";

const API_URL = "http://localhost:8080/api";

function QuestionCard({ question, number, total, onAnswered }) {
  const [selectedOptionId, setSelectedOptionId] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    setSubmitting(true);

    fetch(`${API_URL}/answers`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ answerOptionId: selectedOptionId }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Request failed with status ${response.status}`);
        }
        return response.json();
      })
      .then((answer) => {
        onAnswered(answer.answerOption.correct);
      })
      .catch((requestError) => {
        onAnswered(null, requestError.message);
      })
      .finally(() => {
        setSubmitting(false);
      });
  };

  return (
    <li className="question-card">
      <h2>{question.text}</h2>
      <p className="question-details">
        Question {number} of {total}
        {" - "}Difficulty: {formatDifficulty(question.difficulty)}
      </p>

      <form onSubmit={handleSubmit}>
        {question.answerOptions.map((option) => (
          <label key={option.id} className="answer-option">
            <input
              type="radio"
              name={`question-${question.id}`}
              value={option.id}
              checked={selectedOptionId === option.id}
              onChange={() => setSelectedOptionId(option.id)}
            />
            {option.text}
          </label>
        ))}

        <button
          type="submit"
          className="submit-button"
          disabled={selectedOptionId === null || submitting}
        >
          Submit your answer
        </button>
      </form>
    </li>
  );
}

function formatDifficulty(difficulty) {
  if (!difficulty) {
    return "-";
  }
  return difficulty.charAt(0) + difficulty.slice(1).toLowerCase();
}

export default QuestionCard;