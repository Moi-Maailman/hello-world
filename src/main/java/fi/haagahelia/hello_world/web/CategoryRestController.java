package fi.haagahelia.hello_world.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.hello_world.domain.Category;
import fi.haagahelia.hello_world.domain.CategoryRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;

    public CategoryRestController(CategoryRepository categoryRepository, QuizRepository quizRepository) {
        this.categoryRepository = categoryRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/categories")
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    // U10.2: get the published quizzes of a category
    @GetMapping("/categories/{id}/quizzes")
    public List<Quiz> getCategoryQuizzes(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category with id " + id + " does not exist"));
        return quizRepository.findByCategoryAndPublishedTrue(category);
    }
}