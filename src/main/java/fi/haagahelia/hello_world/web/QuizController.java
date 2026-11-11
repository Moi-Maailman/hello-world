package fi.haagahelia.hello_world.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.haagahelia.hello_world.domain.Quiz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable; // NEW

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

    // U3.4: update a quiz
    @PostMapping("/updatequiz/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz formQuiz) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        quiz.setName(formQuiz.getName());
        quiz.setDescription(formQuiz.getDescription());
        quiz.setCourseCode(formQuiz.getCourseCode());
        quiz.setPublished(formQuiz.isPublished());
        quizRepository.save(quiz);
        return "redirect:/quizlist";
    }
}