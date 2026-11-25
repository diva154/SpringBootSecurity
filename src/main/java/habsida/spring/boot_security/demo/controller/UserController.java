package habsida.spring.boot_security.demo.controller;


import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")

public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showUserProfile(Principal principal, Model model) {
        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found:" + principal.getName()));
        model.addAttribute("user", currentUser);
        return "user";
    }
}


