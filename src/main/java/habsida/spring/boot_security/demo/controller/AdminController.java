package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import habsida.spring.boot_security.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String showCreateform(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles",userService.getAllRoles());
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
        @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
        Model model) {

        // 1.Require password only when creating a new user
        if (user.getId() == null && (user.getPassword() == null || user.getPassword().isBlank())) {
            bindingResult.rejectValue("password", "NotBlank", "Password is required");
        }
        // 2. Check for duplicate email (for both create and edit)
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userService.findByEmail(user.getEmail().trim()).ifPresent(existingUser -> {
                if (user.getId() == null || !existingUser.getId().equals(user.getId())) {
                    bindingResult.rejectValue("email", "Duplicate.user.email", "A user with this email already exists");
                }
            });
        }
        // 3. Require at least one role to be selected
        if (roleIds == null || roleIds.isEmpty()) {
            bindingResult.rejectValue("roles", "NotEmpty", "Please select at least one role");
        }
        if (bindingResult.hasErrors()) {
            // Restore selected roles on the model attribute so checkboxes stay checked
            user.setRoles(userService.getRolesByIds(roleIds));
            model.addAttribute("allRoles",userService.getAllRoles());
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
        model.addAttribute("allRoles",userService.getAllRoles());
        return "user-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}


