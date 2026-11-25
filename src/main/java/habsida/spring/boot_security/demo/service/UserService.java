package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.User;

import java.util.List;

    public interface UserService {
        List<User> getAllUsers();

        User getUserById(Long id);

        void saveUser(User user, List<Long> roleIds);

        void updateUser(User user, List<Long> roleIds);

        void deleteUser(Long id);
    }

