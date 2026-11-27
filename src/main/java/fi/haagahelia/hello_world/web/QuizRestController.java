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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import fi.haagahelia.hello_world.domain.Answer;
import fi.haagahelia.hello_world.domain.AnswerRepository;
import fi.haagahelia.hello_world.domain.Question;
import fi.haagahelia.hello_world.domain.QuestionRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;
import fi.haagahelia.hello_world.dto.QuestionResultDTO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Quizzes", description = "Operations for quizzes")
public class QuizRestController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository; // U8.3

    public QuizRestController(QuizRepository quizRepository, QuestionRepository questionRepository,
            AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository; // U8.3
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

    // U8.3: get the results of a quiz
    @Operation(summary = "Get the results of a quiz", description = "Returns the total, correct and wrong answers for each question of the quiz")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Results of the quiz's questions"),
            @ApiResponse(responseCode = "404", description = "Quiz with the provided id does not exist")
    })
    @GetMapping("/quizzes/{id}/results")
    public List<QuestionResultDTO> getQuizResults(@PathVariable Long id) {
        getQuizById(id);

        List<QuestionResultDTO> results = new ArrayList<>();
        for (Question question : questionRepository.findByQuizId(id)) {
            List<Answer> answers = answerRepository.findByAnswerOptionQuestionId(question.getId());
            long correct = answers.stream()
                    .filter(answer -> answer.getAnswerOption().isCorrect())
                    .count();
            results.add(new QuestionResultDTO(
                    question.getId(),
                    question.getText(),
                    question.getDifficulty(),
                    answers.size(),
                    correct,
                    answers.size() - correct));
        }
        return results;
    }
}