package fi.haagahelia.hello_world.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fi.haagahelia.hello_world.domain.Difficulty;
import fi.haagahelia.hello_world.domain.Question;
import fi.haagahelia.hello_world.domain.QuestionRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuestionController(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    // U5.4: show the add question form for one quiz
    @GetMapping("/addquestion/{quizId}")
    public String showAddQuestionForm(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        model.addAttribute("quiz", quiz);
        model.addAttribute("question", new Question());
        model.addAttribute("difficulties", Difficulty.values());
        return "addquestion";
    }

    // U5.5: save the question to the quiz
    @PostMapping("/savequestion/{quizId}")
    public String saveQuestion(@PathVariable Long quizId, @ModelAttribute Question question) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        question.setQuiz(quiz);
        questionRepository.save(question);
        return "redirect:/quizlist";
    }

    // U7.1: delete a question
    @PostMapping("/deletequestion/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/quizlist";
    }
}