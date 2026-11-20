package fi.haagahelia.hello_world.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.hello_world.domain.Category;
import fi.haagahelia.hello_world.domain.CategoryRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;

    public CategoryRestController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }
}