import { BrowserRouter, Link, Route, Routes } from "react-router";
import QuizListPage from "./pages/QuizListPage";
import QuizPage from "./pages/QuizPage";
import CategoryQuizzesPage from "./pages/CategoryQuizzesPage";
import "./App.css";

function App() {
  return (
    <BrowserRouter>
      <nav className="navbar">
        <Link to="/" className="navbar-brand">
          Quizzer
        </Link>
        <Link to="/" className="navbar-link">
          Quizzes
        </Link>
      </nav>

      <Routes>
        <Route path="/" element={<QuizListPage />} />
        <Route path="/quizzes/:id" element={<QuizPage />} />
        <Route path="/categories/:id/quizzes" element={<CategoryQuizzesPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;