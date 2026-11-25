package habsida.spring.boot_security.demo.repository;

import habsida.spring.boot_security.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //  findByEmail(String email) because you log in with email
    Optional<User> findByEmail(String email);
}
