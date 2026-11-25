package habsida.spring.boot_security.demo.configs;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        //1. Create roles if they dont exists
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        //2. Create ADMIN user (with both ADMIN and USER roles)
        if (userRepository.findByEmail("admin@mail.com").isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@mail.com");
            admin.setPassword(passwordEncoder.encode("${AdminPassword}"));
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);
        }
            // 3. Create regular USER
            if (userRepository.findByEmail("user@mail.com").isEmpty()) {
                User user = new User();
                user.setFirstName("User");
                user.setLastName("User");
                user.setEmail("user@mail.com");
                user.setPassword(passwordEncoder.encode("${UserPassword}"));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
    }
}