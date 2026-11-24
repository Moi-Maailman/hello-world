package fi.haagahelia.hello_world.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.hello_world.domain.Question;
import fi.haagahelia.hello_world.domain.QuestionRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizRestController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizRestController(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/quizzes")
    public List<Quiz> getPublishedQuizzes() {
        return quizRepository.findByPublishedTrue();
    }

    // Exercise 10: get a quiz by id
    @GetMapping("/quizzes/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Quiz with id " + id + " does not exist"));
    }

    // Exercise 11: get the questions of a quiz, from easy to hard
    @GetMapping("/quizzes/{id}/questions")
    public List<Question> getQuizQuestions(@PathVariable Long id) {
        getQuizById(id);

        List<Question> questions = new ArrayList<>(questionRepository.findByQuizId(id));
        questions.sort(Comparator.comparing(Question::getDifficulty,
                Comparator.nullsLast(Comparator.naturalOrder())));
        return questions;
    }
}