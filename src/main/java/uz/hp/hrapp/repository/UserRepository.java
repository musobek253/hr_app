package uz.hp.hrapp.repository;

import uz.hp.hrapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.*;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    Optional<User> findByEmailAndVerifyCode(@Email String email, String verifyCode);

}
