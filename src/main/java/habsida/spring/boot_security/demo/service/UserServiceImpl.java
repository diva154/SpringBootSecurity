package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import habsida.spring.boot_security.demo.UserNotFoundException;
import habsida.spring.boot_security.demo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }


    @Override
    @Transactional
    public void saveUser(User user, List<Long> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(resolveRoles(roleIds));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User formUser, List<Long> roleIds) {
        User existingUser = getUserById(formUser.getId());
        existingUser.setFirstName(formUser.getFirstName());
        existingUser.setLastName(formUser.getLastName());
        existingUser.setEmail(formUser.getEmail());

        // Keep existing password if left blank in the edit form
        if (formUser.getPassword() != null && !formUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(formUser.getPassword()));
        }

        existingUser.setRoles(resolveRoles(roleIds));
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private Set<Role> resolveRoles(List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            roles.addAll(roleRepository.findAllById(roleIds));
        } else {
            roleRepository.findByName("ROLE_USER").ifPresent(roles::add);
        }
        return roles;
    }
}

