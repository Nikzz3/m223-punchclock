package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category create(Category category, Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            return categoryRepository.saveAndFlush(category);
        } else throw new Exception("User is not admin!");
    }

    public void delete(Long id, Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            categoryRepository.deleteById(id);
        } else throw new Exception("User is not admin!");
    }

    public Category update(Category category, Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            return categoryRepository.save(category);
        } else throw new Exception("User is not admin!");
    }

}
