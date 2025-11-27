package demo.consign.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import demo.consign.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
