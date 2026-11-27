package fi.haagahelia.hello_world.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.hello_world.domain.Category;
import fi.haagahelia.hello_world.domain.CategoryRepository;
import fi.haagahelia.hello_world.domain.Quiz;
import fi.haagahelia.hello_world.domain.QuizRepository;

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;

    public CategoryController(CategoryRepository categoryRepository, QuizRepository quizRepository) {
        this.categoryRepository = categoryRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/addcategory")
    public String showAddCategoryForm() {
        return "addcategory";
    }

    @PostMapping("/savecategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/categorylist";
    }

    // U2.1: show the category list
    @GetMapping("/categorylist")
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    @GetMapping("/editcategory/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category with id " + id + " does not exist"));

        model.addAttribute("category", category);
        return "editcategory";
    }

    @PostMapping("/updatecategory")
    public String updateCategory(@ModelAttribute Category formCategory) {
        Category category = categoryRepository.findById(formCategory.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category with id " + formCategory.getId() + " does not exist"));

        category.setName(formCategory.getName());
        category.setDescription(formCategory.getDescription());
        categoryRepository.save(category);

        return "redirect:/categorylist";
    }

    // U4.1: delete a category
    @PostMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"));

        // U4.2: remove the category from its quizzes first
        for (Quiz quiz : quizRepository.findByCategory(category)) {
            quiz.setCategory(null);
            quizRepository.save(quiz);
        }

        categoryRepository.delete(category);
        return "redirect:/categorylist";
    }
}
