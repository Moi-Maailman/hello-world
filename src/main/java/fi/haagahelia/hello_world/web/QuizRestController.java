package fi.haagahelia.hello_world.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizRestController {

    private final QuizRepository quizRepository;

    public QuizRestController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("/quizzes")
    public List<Quiz> getPublishedQuizzes() {
        return quizRepository.findByPublishedTrue();
    }
}