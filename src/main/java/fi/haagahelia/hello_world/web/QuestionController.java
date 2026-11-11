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
}