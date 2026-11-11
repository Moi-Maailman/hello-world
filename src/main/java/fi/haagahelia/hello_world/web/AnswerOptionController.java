package fi.haagahelia.hello_world.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.hello_world.domain.AnswerOption;
import fi.haagahelia.hello_world.domain.AnswerOptionRepository;
import fi.haagahelia.hello_world.domain.Question;
import fi.haagahelia.hello_world.domain.QuestionRepository;

@Controller
public class AnswerOptionController {

    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;

    public AnswerOptionController(AnswerOptionRepository answerOptionRepository,
            QuestionRepository questionRepository) {
        this.answerOptionRepository = answerOptionRepository;
        this.questionRepository = questionRepository;
    }

    // US9: show the answer options of a question
    @GetMapping("/questions/{questionId}/answer-options")
    public String listAnswerOptions(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", questionRepository.findById(questionId).orElseThrow());
        model.addAttribute("answerOptions", answerOptionRepository.findByQuestionId(questionId));
        return "answer-options";
    }

    // US8: show the add answer option form
    @GetMapping("/questions/{questionId}/answer-options/new")
    public String showAddForm(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", questionRepository.findById(questionId).orElseThrow());
        model.addAttribute("answerOption", new AnswerOption());
        return "add-answer-option";
    }

    // US8: save the answer option
    @PostMapping("/questions/{questionId}/answer-options")
    public String addAnswerOption(@PathVariable Long questionId,
            @ModelAttribute AnswerOption answerOption) {
        Question question = questionRepository.findById(questionId).orElseThrow();
        answerOption.setQuestion(question);
        answerOptionRepository.save(answerOption);
        return "redirect:/questions/" + questionId + "/answer-options";
    }

    // US10: delete an answer option
    @PostMapping("/answer-options/{id}/delete")
    public String deleteAnswerOption(@PathVariable Long id) {
        AnswerOption answerOption = answerOptionRepository.findById(id).orElseThrow();
        Long questionId = answerOption.getQuestion().getId();
        answerOptionRepository.delete(answerOption);
        return "redirect:/questions/" + questionId + "/answer-options";
    }
}