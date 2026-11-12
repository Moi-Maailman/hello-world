package fi.haagahelia.hello_world.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.haagahelia.hello_world.domain.Quiz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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

    @GetMapping("/")
    public String home() {
        return "redirect:/quizlist";
    }

    @GetMapping("/quizlist")
    public String showQuizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }

    @PostMapping("/updatequiz")
    public String updateQuiz(@ModelAttribute Quiz formQuiz) {
        Quiz quiz = quizRepository.findById(formQuiz.getId()).orElseThrow();

        quiz.setName(formQuiz.getName());
        quiz.setDescription(formQuiz.getDescription());
        quiz.setCourseCode(formQuiz.getCourseCode());
        quiz.setPublished(formQuiz.isPublished());

        quizRepository.save(quiz);
        return "redirect:/quizlist";
    }

    @GetMapping("/editquiz/{id}")
    public String showEditQuizForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz not found"));

        model.addAttribute("quiz", quiz);
        return "editquiz";
    }

    @PostMapping("/deletequiz/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizRepository.deleteById(id);
        return "redirect:/quizlist";
    }
}