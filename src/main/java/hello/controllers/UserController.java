package hello.controllers;

import hello.models.User;
import hello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {

        model.addAttribute("name", name);
        model.addAttribute("users", userRepository.getAll());

        return "greeting";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name = "username", required = true) String username,
                           @RequestParam(name = "password", required = true) String password,
                           Model model) {
        userRepository.createInsecure(new User(null, username, password));
        return "redirect:/users";
    }

    @PostMapping("/registerSecure")
    public String registerSecure(@RequestParam(name = "username", required = true) String username,
                           @RequestParam(name = "password", required = true) String password,
                           Model model) {
        userRepository.create(new User(null, username, password));
        return "redirect:/users";
    }
}
