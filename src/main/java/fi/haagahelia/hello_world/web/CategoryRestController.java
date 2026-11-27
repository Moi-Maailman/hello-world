package fi.haagahelia.hello_world.web;

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

import fi.haagahelia.hello_world.domain.Category;
import fi.haagahelia.hello_world.domain.CategoryRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Categories", description = "Operations for retrieving categories and their quizzes")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;

    public CategoryRestController(CategoryRepository categoryRepository, QuizRepository quizRepository) {
        this.categoryRepository = categoryRepository;
        this.quizRepository = quizRepository;
    }

    @Operation(summary = "Get all categories", description = "Returns all categories that the teacher has added")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    @GetMapping("/categories")
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Operation(summary = "Get a category by id", description = "Returns the category with the provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category with the provided id retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category with the provided id does not exist")
    })
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category with id " + id + " does not exist"));
    }

    @Operation(summary = "Get the published quizzes of a category", description = "Returns the published quizzes that belong to the category with the provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Published quizzes of the category retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category with the provided id does not exist")
    })
    @GetMapping("/categories/{id}/quizzes")
    public List<Quiz> getCategoryQuizzes(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category with id " + id + " does not exist"));
        return quizRepository.findByCategoryAndPublishedTrue(category);
    }
}