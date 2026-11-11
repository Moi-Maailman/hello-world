package fi.haagahelia.hello_world.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.haagahelia.hello_world.domain.Quiz;

@Controller
public class QuizController {

    @GetMapping("/addquiz")
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "addquiz";
    }
}