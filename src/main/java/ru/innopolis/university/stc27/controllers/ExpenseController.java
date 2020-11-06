package ru.innopolis.university.stc27.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.university.stc27.domain.Category;
import ru.innopolis.university.stc27.domain.Expense;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.repositories.CategoryRepository;
import ru.innopolis.university.stc27.repositories.ExpenseRepository;
import ru.innopolis.university.stc27.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @GetMapping
    public String Expense(HttpServletRequest request, Model model) {
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        List<Category> categories = categoryRepository.findByUserIdAndType(user.getId(), false);

        List<Expense> expenses = expenseRepository.findExpensesByUser(user);

        model.addAttribute("expenses", expenses);
        model.addAttribute("categories", categories);
        return "expenses";
    }

    @PostMapping
    public String addExpense(HttpServletRequest request,
                             @RequestParam String comment,
                             @RequestParam String categoryName,
                             @RequestParam Double sum,
                             @RequestParam String expenseDate) {

        LocalDate date = LocalDate.parse(expenseDate);

        User user = userRepository.findUserBylogin(request.getRemoteUser());

        Expense expense = new Expense(sum, comment, date);
        Category category = categoryRepository.findByUserIdAndName(user.getId(), categoryName);

        expense.setCategory(category);
        expense.setUser(user);

        expenseRepository.save(expense);
        return "redirect:/expenses";
    }

    @GetMapping("{expense}")
    public String expenseEditForm(@PathVariable Expense expense, Model model, HttpServletRequest request) {
        model.addAttribute("expense", expense);
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        List<Category> categories = categoryRepository.findByUserIdAndType(user.getId(), false);

        model.addAttribute("categories", categories);
        return "expensedit";
    }

    @PostMapping("{expense}")
    public String expenseSave(
            @PathVariable Expense expense,
            @RequestParam String comment,
            @RequestParam String categoryName,
            @RequestParam Double sum,
            @RequestParam String expenseDate, HttpServletRequest request) {

        LocalDate date = LocalDate.parse(expenseDate);

        User user = userRepository.findUserBylogin(request.getRemoteUser());
        Category category = categoryRepository.findByUserIdAndName(user.getId(), categoryName);

        expense.setComment(comment);
        expense.setDate(date);
        expense.setSum(sum);
        expense.setCategory(category);

        expenseRepository.save(expense);

        return "redirect:/expenses";
    }

    @PostMapping("/delete/{expense}")
    public String expenseDelete(@PathVariable Expense expense) {
        expenseRepository.delete(expense);
        return "redirect:/expenses";
    }
}
