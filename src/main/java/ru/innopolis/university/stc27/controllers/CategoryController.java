package ru.innopolis.university.stc27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.university.stc27.domain.Category;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.repositories.CategoryRepository;
import ru.innopolis.university.stc27.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String CategoryList(HttpServletRequest request, Model model) {
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        List<Category> categories = categoryRepository.findByUserId(user.getId());
        System.out.println(categories.toString());
        model.addAttribute("categories", categories);
        model.addAttribute("user", user);
        return "categories";
    }

    @PostMapping
    public String addCategory(HttpServletRequest request, @RequestParam String categoryName, @RequestParam String type) {
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        boolean categoryType = true;
        if ("расход".equals(type)) {
            categoryType = false;
        }
        Category category = new Category(categoryName, categoryType, user.getId());
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("{category}")
    public String categoryEditForm(@PathVariable Category category, Model model) {
        model.addAttribute("category", category);
        return "categoryedit";
    }

    @PostMapping("{category}")
    public String categorySave(
            @RequestParam String name,
            @PathVariable Category category) {
        category.setName(name);

        categoryRepository.save(category);

        return "redirect:/categories";
    }
}
