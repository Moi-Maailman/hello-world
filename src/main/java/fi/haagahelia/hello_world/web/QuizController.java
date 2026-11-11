package fi.haagahelia.hello_world.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.haagahelia.hello_world.domain.Quiz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import fi.haagahelia.hello_world.domain.QuizRepository;

@Controller
public class QuizController {

    @GetMapping("/addquiz")
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "addquiz";
    }

    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @PostMapping("/savequiz")
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quizRepository.save(quiz);
        return "redirect:/quizlist";
    }

    @GetMapping("/quizlist")
    public String showQuizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }
}