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

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/addcategory")
    public String showAddCategoryForm() {
        return "addcategory";
    }

    @PostMapping("/savecategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/quizlist";
    }

    // U2.1: show the category list
    @GetMapping("/categorylist")
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    // U4.1: delete a category
    @PostMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
        return "redirect:/quizlist";
    }
}