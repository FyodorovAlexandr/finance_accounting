package ru.innopolis.university.stc27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.university.stc27.service.UserService;

@Controller
public class GreetingController {

    @Autowired
    private UserService userService;

    @GetMapping("/greetings")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        return "greetings";
    }

    @GetMapping()
    public String main() {
        return "main";
    }

    @GetMapping("/main")
    public String mainTwo() {
        return "main";
    }

    @GetMapping("/login")
    public String logIn() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/eegg")
    public String easternEgg() {
        return "gameoflife";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String login, @RequestParam String email, @RequestParam String password, Model model) {

        if (!userService.addUser(username, login, email, password)) {
            model.addAttribute("message", "Упс, такой пользователь с логином " + login + " уже есть :( придумай другой логин ");
        } else {
            model.addAttribute("message", "Урааа, удачная регистрация! Проверь свою почту, магическая сова уже вылетела к тебе с письмом для дальнейшей активации пользователя;)");
        }

        return "result";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            return "redirect:/login";
        }

        model.addAttribute("message", "Что-то пошло не так, возможно прошли по ссылке повторно");
        return "error";
    }
}