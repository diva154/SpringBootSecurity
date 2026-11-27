package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
        List<User> getAllUsers();

        User getUserById(Long id);

        void saveUser(User user, List<Long> roleIds);

        void updateUser(User user, List<Long> roleIds);

        void deleteUser(Long id);

        Optional<User> findByEmail(String email);
        List<Role> getAllRoles();
        Set<Role> getRolesByIds(List<Long> roleIds);

    }

