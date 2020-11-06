package ru.innopolis.university.stc27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.repositories.CategoryRepository;
import ru.innopolis.university.stc27.repositories.ExpenseRepository;
import ru.innopolis.university.stc27.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Map;

@Controller
public class PersonalCabinetController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private DataSource dataSource;

    @GetMapping("/hello")
    public String hello(HttpServletRequest http, Model model){

        User user = userService.getUser(http.getRemoteUser());
        Map<String, Double> expenses = userService.getDate(http.getRemoteUser(), "expense");
        Map<String, Double> incomes = userService.getDate(http.getRemoteUser(), "income");

        Double sum_expenses = expenses.get("expense_sum");
        expenses.remove("expense_sum");

        Double sum_incomes = incomes.get("income_sum");
        incomes.remove("income_sum");

        if (sum_expenses == null) {
            sum_expenses = 0.0;
        }
        if (sum_incomes == null) {
            sum_incomes = 0.0;
        }

        Double result = sum_incomes - sum_expenses;

        model.addAttribute("user", user);
        model.addAttribute("data_expenses", expenses);
        model.addAttribute("data_incomes", incomes);
        model.addAttribute("balance", (result));
        return "hello";
    }

    @GetMapping("/aboutus")
    public String aboutus(HttpServletRequest http, Model model){
        model.addAttribute("user", userService.getUser(http.getRemoteUser()));
        return "aboutus";
    }
}
