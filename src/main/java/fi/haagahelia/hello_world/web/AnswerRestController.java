package fi.haagahelia.hello_world.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import fi.haagahelia.hello_world.domain.Answer;
import fi.haagahelia.hello_world.domain.AnswerOption;
import fi.haagahelia.hello_world.domain.AnswerOptionRepository;
import fi.haagahelia.hello_world.domain.AnswerRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.dto.AnswerRequestDTO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Answers", description = "Operations for submitting answers to quiz questions")
public class AnswerRestController {

    private final AnswerRepository answerRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public AnswerRestController(AnswerRepository answerRepository,
            AnswerOptionRepository answerOptionRepository) {
        this.answerRepository = answerRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    // Exercise 12: submit an answer for a question
    @Operation(summary = "Submit an answer", description = "Saves the student's answer to a question. The request body contains the id of the selected answer option. The answer option must belong to a published quiz.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Answer saved successfully"),
            @ApiResponse(responseCode = "400", description = "Answer option id is missing or the quiz is not published"),
            @ApiResponse(responseCode = "404", description = "Answer option with the provided id does not exist")
    })
    @PostMapping("/answers")
    public ResponseEntity<Answer> createAnswer(@RequestBody AnswerRequestDTO request) {
        if (request == null || request.answerOptionId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer option id is required");
        }

        Long answerOptionId = request.answerOptionId();
        AnswerOption answerOption = answerOptionRepository.findById(answerOptionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Answer option with id " + answerOptionId + " does not exist"));

        Quiz quiz = answerOption.getQuestion().getQuiz();
        if (quiz == null || !quiz.isPublished()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz is not published");
        }

        Answer answer = answerRepository.save(new Answer(answerOption));
        return ResponseEntity.status(HttpStatus.CREATED).body(answer);
    }
}