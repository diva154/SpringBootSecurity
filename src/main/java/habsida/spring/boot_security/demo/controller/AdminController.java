package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService,RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String showCreateform(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles",roleRepository.findAll());
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
        @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
        Model model) {

        // Require password only when creating a new user
        if (user.getId() == null && (user.getPassword() == null || user.getPassword().isBlank())) {
            bindingResult.rejectValue("password", "NotBlank", "Password is required");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles",roleRepository.findAll());
            return "user-form";
        }
        if (user.getId() == null) {
            userService.saveUser(user, roleIds);
        } else {
            userService.updateUser(user, roleIds);
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles",roleRepository.findAll());
        return "user-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}


